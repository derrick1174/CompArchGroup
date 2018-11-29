public class Main {

    static String fileName = "";
    static String cacheSize = "";
    static String blockSize = "";
    static String associativity = "";
    static String replacementPolicy = ""; //may not be necessary

    public static void main(String[] args){


        for(int i = 1; i < args.length-1; i+=2){
            whichOne(args[i], args[i+1]);
        }

        //System.out.println("File name: " + fileName + "\nCache Size: " + cacheSize + "\nBlock Size: "
        //+ blockSize + "\nAssociativity: " + associativity + "\nReplacement Policy: " + replacementPolicy);

        System.out.println("Cache Simulator CS 3853 Fall 2018 – Group #16");
        System.out.println("\nTrace File: " + fileName);
        System.out.println("\n----- Generic -----");
        System.out.println("Cache Size: " + cacheSize + " KB");
        System.out.println("Block Size: " + blockSize + " bytes");
        System.out.println("Associativity: " + associativity);
        System.out.println("Policy: " + replacementPolicy);


        System.out.println("\n----- Calculated Values ----- ");
        System.out.println("Total #Blocks: " + (int)(Math.pow(2, Integer.valueOf(blockSize))/1000) + " KB"); //2^block size KB
        System.out.println("Tag Size: " + " bits"); //# of address bits minus the # of index bits, minus the # of offset bits (within the cache block).
        System.out.println("Index Size: *** bits, Total Indices: ***");
        System.out.println("Implementation Memory Size: ***");

        System.out.println("\n----- Results -----");
        System.out.println("Cache Hit Rate: *** %");
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
                associativity = output;
            case "-r":
                replacementPolicy = output;
        }
    }
}
