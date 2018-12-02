import java.util.HashMap;
import java.io.*;

public class Main {

    static String fileName = "";
    static String cacheSize = "";
    static String blockSize = "";
    static String associativity = "";
    static String replacementPolicy = ""; //may not be necessary

    public static void main(String[] args){

        HashMap<String, String> cache = new HashMap<>();
        /*HashMap functions:
        hashMap.put(key, value) - puts a value in the hashmap
        hashMap.remove(key) - removes the key and its associated value
        hashMap.containsKey(key) - returns t/f depending if it has the key
        hashMap.get(key) - returns the value associated with the key
         */
        for(int i = 1; i < args.length-1; i+=2){
            whichOne(args[i], args[i+1]);
        }

        initCache(cache);
        printResults();
        readTraceFile();

    }

    public static void initCache(){
    	
    	int associativityVal;
        if(associativity.equals("Direct Mapped")){
            associativityVal = 1;
        }
        else{
            associativityVal = Integer.valueOf(associativity); //convert it to an int value for calculations
        }

        int cacheVal = Integer.valueOf(cacheSize) * 1024;
        int valBlockSize = Integer.valueOf(blockSize);
        int numSets = cacheVal/(valBlockSize*associativityVal); //cacheSize / (blockSize * associativity)
        int numIndexBits = (int)(Math.log(numSets)/Math.log(2)); //log(base 2) of the number of sets
        int numBlocks = (int)Math.ceil(numSets * associativityVal); //numSets * associativity
        int numAddressBits = 32; //number of address bits
        int numOffsetBits = (int)(Math.log(Integer.valueOf(blockSize))/Math.log(2)); //log(base 2) of block size
        int numTagBits = numAddressBits - (numIndexBits + numOffsetBits);

    	switch(associativityVal){
    	case 1://direct
    		break;
    	case 2://2-way
    		break;
    	case 4://4-way
    		break;
    	case 8://8-way
    		break;
    	default://invalid associativity
    		sys.exit();
    		break;
    	}

    }

    public static void printResults(){
        

    	System.out.println("Cache Simulator CS 3853 Fall 2018 – Group #16");
        System.out.println("\nTrace File: " + fileName);
        System.out.println("\n----- Generic -----");
        System.out.println("Cache Size: " + cacheSize + " KB");
        System.out.println("Block Size: " + blockSize + " bytes");
        System.out.println("Associativity: " + associativity);
        System.out.println("Policy: " + replacementPolicy);


        System.out.println("\n----- Calculated Values ----- ");
        System.out.println("Total #Blocks: " + numBlocks + " KB"); //2^block size KB
        System.out.println("Tag Size: " + numTagBits + " bits"); //# of address bits minus the # of index bits, minus the # of offset bits (within the cache block).
        System.out.println("Index Size: " + numIndexBits + " bits, Total Indices: " + (int)(Math.pow(2, numIndexBits)/1000) + " KB");
        System.out.println("Implementation Memory Size: ***");

        System.out.println("\n----- Results -----");
        System.out.println("Cache Hit Rate: *** %");
    }

    public static void searchCache(){//accesses cache and determines hit or miss
    }
    
    public static void replaceInCache(){//on a compulsory or conflict miss, places/replaces new value in cache
    	
    }

    public static void readTraceFile() {
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
                for (int i = 0; i < words.length; i++) {
                    if (count > 20 || !words[6].equals("srcM:") || !words[0].equals("dstM:"))
                        continue;
                    System.out.println(words[i] + " ");
                }
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void whichOne(String n, String output){
        switch (n){
            case "-f":
                fileName = output;
                break;
            case "-s":
                cacheSize = output;
                break;
            case "-b":
                blockSize = output;
            case "-a":
                if(output.equals("1"))
                    associativity = "Direct Mapped";
                else
                    associativity = output;
            case "-r":
                replacementPolicy = output;
        }
    }
}