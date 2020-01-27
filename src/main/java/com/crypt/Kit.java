package com.crypt;


public class Kit {

    private static final String cryptedArr[] = {"QPZJ", "QLZJ", "QPJJJ", "QPFG", "QPZJ", "QPZJ"};

    private static final String mostWanted = "BAKU";

    private static final char downRange = 'A', upperRange = 'Z';

    public static int findResult(String[] cryptedArr, String wanted){

        int key = 0;

        for(int i = 0; i < cryptedArr.length; i++){
            if(matches(mostWanted, cryptedArr[i])){
                System.out.println(" Text : "+mostWanted+",  matches with : "+cryptedArr[i]);
                key = findKey(mostWanted, cryptedArr[0]);
            }else{
                System.out.println(" Text : "+mostWanted+", does not matches with : "+cryptedArr[i]);
            }
        }

        return key;
    }

    public static void main(String[] args){

        int key = findResult(cryptedArr, mostWanted);

        System.out.println("Symetric cript key is "+key);
    }


    /**
     *
     * method designed to test if requested word and existing word are matches for future process
     *
     * @param wanted
     * @param crypted
     * @return true or false depending on result
     */
    public static boolean matches(String wanted, String crypted){

        if(wanted==null || crypted == null || wanted.length()!=crypted.length()) return false;

        char[] wantedChArr = wanted.toCharArray();
        char[] cryptedChArr  = crypted.toCharArray();

        for(int i = 0; i < wantedChArr.length-1; i++){

            int firstDiff = wantedChArr[i+1] - wantedChArr[i];

            if((firstDiff + cryptedChArr[i]) > upperRange){   // in case of next ascii is bigger than upperBound

                if((firstDiff + cryptedChArr[i]) != cryptedChArr[i+1] + 26){
                    return false;
                }
            }
            else if((wantedChArr[i+1] - wantedChArr[i]) != (cryptedChArr[i+1] - cryptedChArr[i])){
                return false;
            }
        }
        return true;
    }

    /**
     * method designed for to find encryption key on match cases
     * @param required
     * @param crypted
     * @return encryption key
     */
    public static int findKey(String required, String crypted){

        char[] requiredArr = required.toCharArray();
        char[] cryptedArr  = crypted.toCharArray();

        int key = keyFinder(requiredArr[0], cryptedArr[0]);

        // r + k > 90(Z) ? c = (r+k)%26 : c = r + k

//        System.out.println("E 0 "+key);

        for(int i=1; i < required.length(); i++){
//            System.out.println("E "+i+" "+keyFinder(requiredArr[i], cryptedArr[i]));
            if(key != keyFinder(requiredArr[i], cryptedArr[i])){
                return 0;
            }

        }

        return key;
    }

    /**
     * main part where defines key
     * @param original
     * @param crypted
     * @return
     */
    public static int keyFinder(int original, int crypted){
        int key;
        if(original > crypted)
            key = crypted + 26 - original;
        else
            key = crypted - original;

        if(key+original < downRange){
            key=-key; // backward case
        }else if(key+original > upperRange){
            //forward case
        }
        return key;
    }


    public static int indexOf(String[] arr, String e){
        for(int i=0; i < arr.length; i++){
            if(arr[i].equals(e)){
                return i;
            }
        }
        return -1;
    }

}
