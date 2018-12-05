import java.lang.reflect.Array;
import java.util.*;
import java.io.*;
import java.text.*;

public class Main {

    static String fileName = "";
    static String cacheSize = "";
    static String blockSize = "";
    static String associativity = "";
    static String replacementPolicy = ""; //may not be necessary
    static int compulMiss = 0;
    static int confMiss = 0;
    static double hits = 0;

    static int cacheVal = 0;
    static int valBlockSize = 0;
    static int numSets =  0; //cacheSize / (blockSize * associativity)
    static int numIndexBits = 0; //log(base 2) of the number of sets
    static int numBlocks = 0; //numSets * associativity
    static int numAddressBits = 0; //number of address bits
    static int numOffsetBits = 0; //log(base 2) of block size
    static int numTagBits = 0;
    static int associativityVal = 0;
    static double hitRate = 0;

    static HashMap<String, directObject> cache = new HashMap<>();

    public static void main(String[] args) {


        /*HashMap functions:
        hashMap.put(key, value) - puts a value in the hashmap
        hashMap.remove(key) - removes the key and its associated value
        hashMap.containsKey(key) - returns t/f depending if it has the key
        hashMap.get(key) - returns the value associated with the key
         */
        for (int i = 1; i < args.length - 1; i += 2) {
            whichOne(args[i], args[i + 1]);
        }

        readTraceFile();

    }

    //accesses cache and determines hit or miss
    public static void searchCache(String input, HashMap<String, directObject> cache) {
        //searchCache will call replaceInCache() when cache index is full and needs to replace
        //take in the String (input) and convert to int->binary. Remove furthest right bits = # of offset bits (log2 of block size)
        //convert remaining input into tag and index (two local variables)
        //make a direct object with the tag and valid bit
        //check to see if at the index the hashmap the valid bit of the object set to 0. If yes, compulsory miss - replace it and increment compulMiss
        //if not, check the tag. If the tag matches, that's a hit. But if it doesn't match, conflict miss - replace it and increment confMiss

        int decimal = Integer.parseInt(input, 16);
        String binary = Integer.toBinaryString(decimal); //now have binary string

        char[] binaryArr =  new char[32];
        int count = 0;
        for(int i = 0; i < binaryArr.length; i++){
            if(i > 32- binary.length()) {
                binaryArr[i] = binary.charAt(count);
                count++;
            }
            else{
                binaryArr[i] = '0';
            }
        }
        String string = new String(binaryArr);
        //at this point we have consistent 32 length strings with leading 0s where needed
        String withoutOffset = new String(string.substring(0, 32-numOffsetBits));
        //tag is 0 until (32-numBitsOffset-indexSize)
        String tagString = new String(withoutOffset.substring(0, 32-numOffsetBits - numIndexBits));
        String indexString = new String(withoutOffset.substring(32-numOffsetBits - numIndexBits, withoutOffset.length()));
        //at this point have the tag and index bits

        //look inside cache (HashMap)
        //directObject[] inCache = new directObject[associativityVal];
        //inCache = cache.get(indexString);
        //cache.get(indexString).equals(tagString);
        /*for(int i = 0; i < associativityVal; i++){
            System.out.println("Valid bit at index " + i +": " + inCache[i].getValue() +
                    "\nTag at index " + i + ": " + inCache[i].getTag());
        }*/
        /*cache.get(indexString).equals(tagString);
        if(cache.get(indexString).equals(tagString)){
            //if inside here, it was a hit
            hits++;
        }*/
    }

    //on a compulsory or conflict miss, places/replaces new value in cache
    public static void replaceInCache() {
        //take in the object and index we made in searchCache
        //for Direct Map, put the object into the hashMap at key (index)
    }

    public static void readTraceFile() {

        //initialize
        if (associativity.equals("Direct Mapped")) {
            associativityVal = 1;
        } else {
            associativityVal = Integer.valueOf(associativity); //convert it to an int value for calculations
        }
        cacheVal = Integer.valueOf(cacheSize) * 1024;
        valBlockSize = Integer.valueOf(blockSize);
        numSets = cacheVal / (valBlockSize * associativityVal); //cacheSize / (blockSize * associativity)
        numIndexBits = (int) (Math.log(numSets) / Math.log(2)); //log(base 2) of the number of sets
        numBlocks = (int) Math.ceil(numSets * associativityVal); //numSets * associativity
        numAddressBits = 32; //number of address bits
        numOffsetBits = (int) (Math.log(Integer.valueOf(blockSize)) / Math.log(2)); //log(base 2) of block size
        numTagBits = numAddressBits - (numIndexBits + numOffsetBits);

        //HashMap<String, directObject> cache = new HashMap<>();
        if(!(associativityVal == 1 || associativityVal == 2 || associativityVal == 4 || associativityVal == 8))
            System.exit(1); //invalid associativity size, exit program


        //read trace file and execute

        //skip empty lines
        //read in first line of pair and skip it
        //read in second line and split it with space as delimiter, get destination address from position 1 and perform a write if not only zeros.
        //do the same for the source address from position 7 and perform a read if not only zeros.
        try {
            // Open the file that is the first command line parameter
            FileInputStream fstream = new FileInputStream(fileName);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int count = 0;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                if (strLine.equals(""))
                    continue;
                count++;
                String[] words = strLine.split(" "); //split by spaces
                if(words[0].equals("dstM:") && words[6].equals("srcM:")) {
                    if(!words[1].equals("00000000")){
                        //searchCache(words[1], cache);
                        if(associativityVal == 1)
                            directMapped(words[1]);
                        else if(associativityVal == 2)
                            twoWayMapped(words[1]);
                        else if(associativityVal == 4)
                            fourWayMapped(words[1]);
                        else{
                            eightWayMapped(words[1]);
                        }
                    }
                    if(!words[7].equals("00000000")){
                        //searchCache(words[7], cache);
                        directMapped(words[7]);
                    }
                }
                if(words[0].equals("EIP")){
                    if(associativityVal == 1)
                        directMapped(words[2]);

                }
                //if dstM isn't zero, call searchCache() to check for it in/add it to cache
                //if srcM isn't zero, call searchCache() to check for it in/add it to cache

            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        DecimalFormat df = new DecimalFormat("#.####");
        hitRate = (hits/(hits+confMiss+compulMiss))*100;


        //print result
        System.out.println("Cache Simulator CS 3853 Fall 2018 - Group #16");
        System.out.println("\nTrace File: " + fileName);
        System.out.println("\n----- Generic -----");
        System.out.println("Cache Size: " + cacheSize + " KB");
        System.out.println("Block Size: " + blockSize + " bytes");
        System.out.println("Associativity: " + associativity);
        System.out.println("Policy: " + replacementPolicy);


        System.out.println("\n----- Calculated Values ----- ");
        System.out.println("Total #Blocks: " + numBlocks + " KB"); //2^block size KB
        System.out.println("Tag Size: " + numTagBits + " bits"); //# of address bits minus the # of index bits, minus the # of offset bits (within the cache block).
        System.out.println("Index Size: " + numIndexBits + " bits, Total Indices: " + (int) (Math.pow(2, numIndexBits) / 1000) + " KB");
        System.out.println("Implementation Memory Size: ***");

        System.out.println("\n----- Results -----");
        System.out.println("Total Cache Acceses: " + (int)(hits+compulMiss+confMiss));
        System.out.println("Cache Hits: " + (int)hits);
        System.out.println("Cache Misses: " + (compulMiss+confMiss));
        System.out.println("---Compulsory Misses: " + compulMiss);
        System.out.println("---Conflict Misses: " + confMiss);
        System.out.println("Cache Hit Rate: " + df.format(hitRate) + "%");
        System.out.println("Cache Miss Rate: " + df.format(100-hitRate) + "%");
    }

    private static void eightWayMapped(String word) {

    }

    private static void fourWayMapped(String address) {

    }

    private static void twoWayMapped(String address) {

    }

    private static void directMapped(String address) {
        int decimal = Integer.parseInt(address, 16);
        String binary = Integer.toBinaryString(decimal); //now have binary string

        char[] binaryArr =  new char[32];
        int count = 0;
        //make sure the length of each binary number is the same
        for(int i = 0; i < binaryArr.length; i++){
            if(i > 32- binary.length()) {
                binaryArr[i] = binary.charAt(count);
                count++;
            }
            else{
                binaryArr[i] = '0';
            }
        }
        String string = new String(binaryArr);
        //at this point we have consistent 32 length strings with leading 0s where needed
        String withoutOffset = new String(string.substring(0, 32-numOffsetBits));
        //tag is 0 until (32-numBitsOffset-indexSize)
        String tagString = new String(withoutOffset.substring(0, 32-numOffsetBits - numIndexBits));
        String indexString = new String(withoutOffset.substring(32-numOffsetBits - numIndexBits, withoutOffset.length()));
        //at this point have the tag and index bits

        if(cache.containsKey(indexString)){ //check if the cache already has this index
            //cache already has this index, check the valid bit
            if(cache.get(indexString).getValue() == 1){
                //valid bit is 1, so check if the tags match
                if(cache.get(indexString).getTag().equals(tagString)){
                    //System.out.println("A hit has been made!");
                    hits++;
                    //TODO: replacement policy
                }else{
                    //conflict miss
                    confMiss++;
                    directObject newObject = createNew(tagString, 1);
                    cache.put(indexString, newObject);
                        System.out.println("Conflict MISS!");
                }
            }else{
                //compulsory miss
                compulMiss++;
                directObject newObject = createNew(tagString, 1);
                cache.put(indexString, newObject);
                //System.out.println("Compulsory Miss. An index has been replaced at: " + indexString);
            }
        }else{ //cache doesn't contain this index, insert it
            directObject newObject = createNew(tagString, 0);
            cache.put(indexString, newObject);
            //System.out.println("A new index has been inserted at: " + indexString);
        }
    }

    private static directObject createNew(String tagString, int valid){
        directObject newObject = new directObject();
        newObject.setTag(tagString);
        newObject.setValue(valid);
        return newObject;
    }

    public static void whichOne(String n, String output) {
        switch (n) {
            case "-f":
                fileName = output;
                break;
            case "-s":
                cacheSize = output;
                break;
            case "-b":
                blockSize = output;
            case "-a":
                if (output.equals("1"))
                    associativity = "Direct Mapped";
                else
                    associativity = output;
            case "-r":
                replacementPolicy = output;
        }
    }
}
