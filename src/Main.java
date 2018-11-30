import java.util.HashMap;

public class Main {

    static String fileName = "";
    static String cacheSize = "";
    static String blockSize = "";
    static String associativity = "";
    static String replacementPolicy = ""; //may not be necessary

    public static void main(String[] args){

        HashMap<String, String> hashMap = new HashMap<>();
        /*HashMap functions:
        hashMap.put(key, value) - puts a value in the hashmap
        hashMap.remove(key) - removes the key and its associated value
        hashMap.containsKey(key) - returns t/f depending if it has the key
        hashMap.get(key) - returns the value associated with the key
         */
        for(int i = 1; i < args.length-1; i+=2){
            whichOne(args[i], args[i+1]);
        }

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

        /*System.out.println("numIndexBits = " + numIndexBits+ "\nassocitvityVal = " + associativityVal +
                "\nnumSets = " + numSets + "\nnumBlocks = " + numBlocks + "\nnumAddressBits = " + numAddressBits);*/

        
    }

    public static void createCache(){
    	
    	
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
    
    public static void executeSim(){
    	
    	
    	
    }
    
    public static void readTraceFile(){
    	
    	//read in first line and split it with space as delimiter, get the address from position 2 and the length from position 1
    	//read in second line and split it with space as delimiter, get destination address from position 1 and if it's not only zeros, save it into a variable maybe. 
    	//Then note that the length of the data is 4 bytes. Don't know if that's important or not.
    	//
    	//do the same for the source address from position 4 and save it if it's not only zeros
    	//Then note that the length of the data is 4 bytes. Don't know if that's important or not.
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
