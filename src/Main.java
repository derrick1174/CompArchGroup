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

        System.out.println("File name: " + fileName + "\nCache Size: " + cacheSize + "\nBlock Size: "
        + blockSize + "\nAssociativity: " + associativity + "\nReplacement Policy: " + replacementPolicy);
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
