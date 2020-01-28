package com.crypt;


import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Kit {

    private static final String mostWanted = "THE";

    private static final char downRange = 'A', upperRange = 'Z';


    public static void main(String[] args){



        try(BufferedReader br = new BufferedReader(new FileReader("encdata.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("decoded.txt"))) {


            List<String> str = br.lines().flatMap(s -> Arrays.stream(s.split("\\s+")).map(String::toUpperCase)).collect(Collectors.toList());

            System.out.println("*********** START  *****************");

//            for (String encrypted : str) {

            for(int i = 0; i < 50; i++){

                String encrypted = str.get(i);

                if(matches(mostWanted, encrypted)){

                    System.out.printf("Wanted %s matches with encrypted %s%n", mostWanted, encrypted);

                    int key = findKey(mostWanted, encrypted);
                    if(key!=0){
                        System.out.printf("Key found %d%n", key);
                        break;
                    }
                }
            }

            System.out.println("*********** END  *****************");


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     *
     * method designed to test if requested word and existing word are matches for future process
     *
     * @param wanted
     * @param encrypted
     * @return true or false depending on result
     */
    public static boolean matches(String wanted, String encrypted){

        if(wanted==null || encrypted == null || wanted.length()!=encrypted.length()) return false;

        char[] wantedChArr = wanted.toCharArray();
        char[] encryptedChArr  = encrypted.toCharArray();

        System.out.printf("Wanted %s, Encrypted %s%n", wanted, encrypted);

        for(int i = 0; i < wantedChArr.length-1; i++){

            int firstDiff = wantedChArr[i+1] - wantedChArr[i];

            if((firstDiff + encryptedChArr[i]) > upperRange){   // in case of next ascii is bigger than upperBound

                if((firstDiff + encryptedChArr[i]) != encryptedChArr[i+1] + 26){
                    return false;
                }
            }
            else if((wantedChArr[i+1] - wantedChArr[i]) != (encryptedChArr[i+1] - encryptedChArr[i])){
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
