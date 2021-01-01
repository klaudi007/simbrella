package com.crypt;


import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Kit {

    private static final String mostWanted = "AND";

    private static final char downRange = 'A', upperRange = 'Z';

    public static void main(String[] args){

        try(BufferedReader br = new BufferedReader(new FileReader("encdata.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("decoded.txt"))) {


            List<String> str = br.lines().flatMap(s -> Arrays.stream(s.split("\\s+")).map(String::toUpperCase)).collect(Collectors.toList());

            System.out.println("*********** START  *****************");

            int key = 0;

            for (String encrypted : str) {


                if(matches(mostWanted, encrypted)){

                    System.out.printf("Wanted %s matches with encrypted %s%n", mostWanted, encrypted);

                        key = findKey(mostWanted, encrypted);
                    if(key!=0){
                        System.out.printf("Key found %d%n", key);
                        break;
                    }else{
                        System.out.printf("%s", "Text is not encrypted!");
                    }
                }
            }


            if (key != 0) {  // data really encrypted and key found

                StringBuilder builder = new StringBuilder();

                for (String encrypted : str) {

                    builder.append(decrypt(encrypted, key)).append(" ");

                }

                bw.write(builder.toString());

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
                if((firstDiff + encryptedChArr[i]) == encryptedChArr[i+1] + 26){
                    return true;
                }else{
                    return false;
                }
            }
            else if(firstDiff == (encryptedChArr[i+1] - encryptedChArr[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * method designed for to find encryption key on match cases
     * @param required
     * @param encrypted
     * @return encryption key
     */
    public static int findKey(String required, String encrypted){

        char[] requiredArr = required.toCharArray();
        char[] encryptedArr  = encrypted.toCharArray();

        int key = keyFinder(requiredArr[0], encryptedArr[0]);

        for(int i=1; i < required.length(); i++){

            if(key != keyFinder(requiredArr[i], encryptedArr[i])){
                return 0;
            }

        }

        return key;
    }

    /**
     * main part where defines key
     * @param original
     * @param encrypted
     * @return
     */
    public static int keyFinder(int original, int encrypted){
        // r + k > 90(Z) ? c = (r+k)%26 : c = r + k
        int key;
        // E(x) = (x+n)%26; // mode is not inversible operator
        //(encrypted - 65) = (original-65 + key) % 26;
        //65 < x < 90
        if(original > encrypted)
            key = encrypted + 26 - original;
        else
            key = encrypted - original;

        if(key+original < downRange){
            key=-key; // backward case
        }else if(key+original > upperRange){
            //forward case... e.i. normal case
        }
        return key;
    }

    public static String decrypt(String encrypted, int key){
        char[] arr = encrypted.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            arr[i] += key;
            arr[i] = (arr[i] < downRange) ? (char) (arr[i] + 26) : ((arr[i] > upperRange) ? (char) (arr[i] - 26) : arr[i]);
        }
        return new String(arr);
    }
}
