import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

public class ACompress {

    public static void compress(){
        String sourceFile, resultFile;
        Scanner sc = new Scanner(System.in);
        System.out.print("source file name: ");
        sourceFile = sc.next();
        System.out.print("archive name: ");
        resultFile = sc.next();
        long maxValue = Long.MAX_VALUE;
        long minValue = 0L;
        long halfValue = maxValue/2;
        long quarterValue = maxValue/4;


        HashMap<Character,Integer> charCounter = new HashMap<>();

        long charAmount = 0;

        try {
            FileOutputStream output = new FileOutputStream(resultFile);
            BufferedReader br = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream(sourceFile), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                PCalculator.probabilityCalculator(charCounter, line);
                charAmount += line.length();
            }

            br.close();
            long upperLimit, lowerLimit, difference;
            byte unseenBits = 0, toOut = 0, counter = 0;
            HashMap<Character,long[]> oddsTable = PCalculator.recalculateOddsTabel(charCounter, charAmount, maxValue);
            br = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream(sourceFile), StandardCharsets.UTF_8));
            while ((line = br.readLine()) != null) {
                char[] chars = line.toCharArray();
                for (char letter : chars) {

                    lowerLimit = oddsTable.get(letter)[0];
                    upperLimit = oddsTable.get(letter)[1];

                    difference = upperLimit - lowerLimit;

                    oddsTable = PCalculator.recalculateOddsTabel(charCounter, charAmount, difference, lowerLimit);

                    System.out.println(lowerLimit + " " + upperLimit + " " + difference);

                    while (true) {
                        if (upperLimit < halfValue) {
                            toOut <<= 1;
                            counter++;
                        } else if (lowerLimit > halfValue) {
                            toOut |= 1;
                            toOut <<= 1;
                            counter++;
                        } else if (lowerLimit >= quarterValue && upperLimit < quarterValue * 3) {
                            unseenBits++;
                            lowerLimit -= quarterValue;
                            upperLimit -= quarterValue;
                            counter++;
                            tryToOutput(toOut, counter, unseenBits, resultFile);
                        } else break;

                        upperLimit <<= 1;
                        upperLimit++;
                        lowerLimit <<= 1;


                    }





                }

            }

            long intendedupper = Long.MAX_VALUE, intendedlower;




//            for (HashMap.Entry<Character,long[]> entry : oddsTable.entrySet()) {
//                System.out.println(entry.getKey() + " " + entry.getValue()[0] + " " + entry.getValue()[1]);
//
//            }


        } catch (Exception e){
            System.out.println(e.getMessage());
        }







    }

    public static void tryToOutput(byte theByte, byte counter, byte unseenBits, String resultFile) {

        if (counter == 7) {
            try {
                FileOutputStream output = new FileOutputStream(resultFile, true);
                counter = 0;
                byte lastBit = (byte) ((theByte >> 1) & 1);
                for (int i = 0; i < unseenBits; i++) {
                    if (lastBit == 0) {
                        theByte |= 1;
                        theByte <<= 1;
                    } else {
                        theByte <<= 1;
                    }
                }
                theByte <<= 1;
                output.write(theByte);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }


    }




}
