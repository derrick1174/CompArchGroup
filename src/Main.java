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

        int numBlocks = (int)(Math.pow(2, Integer.valueOf(blockSize)));
        int associativityVal;
        if(associativity.equals("Direct Mapped")){
            associativityVal = 1;
        }
        else{
            associativityVal = Integer.valueOf(associativity); //convert it to an int value for calculations
        }
        int numSets = Integer.valueOf(cacheSize)/(Integer.valueOf(blockSize)*associativityVal);
        numBlocks = numSets * associativityVal;
        int numAddressBits = 32; //number of bits
        int numIndexBits = (int)(Math.log(numSets)/Math.log(2));
        int numOffsetBits = (int)(Math.log(Integer.valueOf(blockSize))/Math.log(2));
        int numTagBits = numAddressBits - (numIndexBits + numOffsetBits);

        System.out.println("associtvityVal = " + associativityVal +
                "\nnumSets = " + numSets + "\nnumBlocks = " + numBlocks + "\nnumAddressBits = " + numAddressBits);

        System.out.println("Cache Simulator CS 3853 Fall 2018 – Group #16");
        System.out.println("\nTrace File: " + fileName);
        System.out.println("\n----- Generic -----");
        System.out.println("Cache Size: " + cacheSize + " KB");
        System.out.println("Block Size: " + blockSize + " bytes");
        System.out.println("Associativity: " + associativity);
        System.out.println("Policy: " + replacementPolicy);


        System.out.println("\n----- Calculated Values ----- ");
        System.out.println("Total #Blocks: " + (int)(Math.pow(2, Integer.valueOf(blockSize))/1000) + " KB"); //2^block size KB
        System.out.println("Tag Size: " + numTagBits + " bits"); //# of address bits minus the # of index bits, minus the # of offset bits (within the cache block).
        System.out.println("Index Size: *** bits, Total Indices: ***");
        System.out.println("Implementation Memory Size: ***");

        System.out.println("\n----- Results -----");
        System.out.println("Cache Hit Rate: *** %");
    }

    public static void createCache(){
    	
    	
    }
    
    public static void printResults(){
    	
    	
    	
    }
    
    public static void executeSim(){}
    
    public static void readTraceFile(){}

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
