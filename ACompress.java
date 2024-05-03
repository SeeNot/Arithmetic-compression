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
        long maxValue = (long)Math.pow(2,62);
        System.out.println(maxValue);
        long halfValue = maxValue/2;
        long quarterValue = maxValue/4;


        HashMap<Character,Integer> charCounter = new HashMap<>();

        long charAmount = 0;

        try {
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
            tableEmbeder(oddsTable, resultFile);
            br = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream(sourceFile), StandardCharsets.UTF_8));
            while ((line = br.readLine()) != null) {
                char[] chars = line.toCharArray();
                for (char letter : chars) {

                    lowerLimit = oddsTable.get(letter)[0];
                    upperLimit = oddsTable.get(letter)[1];




                    //System.out.println(lowerLimit + " " + upperLimit);

                    while (true) {
                        if (Long.compareUnsigned(halfValue, upperLimit) == 1) {
                            toOut <<= 1;
                            counter++;


                            for (int i = 0; i < unseenBits; unseenBits--) {

                                if (counter == 7) {
                                    tryToOutput(toOut, resultFile);
                                    counter = 0;
                                    toOut = 0;
                                }

                                toOut |= 1;
                                toOut <<= 1;
                                counter++;
                            }
                            if (counter == 7){
                                tryToOutput(toOut, resultFile);
                                toOut = 0;

                                counter = 0;

                            }

                            upperLimit <<= 1;
                            lowerLimit <<= 1;
                        } else if (Long.compareUnsigned(halfValue, lowerLimit) != 1) {
                            toOut |= 1;
                            toOut <<= 1;
                            counter++;
                            for (int i = 0; i< unseenBits; unseenBits--) {

                                if (counter == 7) {
                                    tryToOutput(toOut, resultFile);
                                    toOut = 0;

                                    counter = 0;
                                }
                                toOut <<= 1;
                                counter++;
                            }

                            if (counter == 7){
                                tryToOutput(toOut, resultFile);
                                toOut = 0;

                                counter = 0;
                            }


                            upperLimit = 2*(upperLimit-halfValue);
                            lowerLimit = 2*(lowerLimit-halfValue);
                        } else if (Long.compareUnsigned(quarterValue, lowerLimit) != 1 && Long.compareUnsigned(quarterValue*3, upperLimit) == 1) {
                            unseenBits++;
                            lowerLimit = 2*(lowerLimit - quarterValue);
                            upperLimit = 2*(upperLimit - quarterValue);
                            counter++;

                        } else {
                            difference = upperLimit - lowerLimit;
                            oddsTable = PCalculator.recalculateOddsTabel(charCounter, charAmount, difference, lowerLimit);
                            break;
                        }
                        //System.out.println(counter);
                    }

                }

            }

            System.out.println("Done");

//            for (HashMap.Entry<Character,long[]> entry : oddsTable.entrySet()) {
//                System.out.println(entry.getKey() + " " + entry.getValue()[0] + " " + entry.getValue()[1]);
//
//            }


        } catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    public static void tryToOutput(byte theByte, String resultFile) {

        try {
            FileOutputStream output = new FileOutputStream(resultFile, true);

            theByte <<= 1;
            output.write(theByte);
            output.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }



    }


    public static void tableEmbeder (HashMap<Character,long[]> simbolsSkaits, String resultFile) {

        String numericValueOfSymbol;
        String numericValueOfCount;
        byte outPutByte = 0;
        int counter = 0;
        try {
            FileOutputStream output = new FileOutputStream(resultFile, true);

            for (HashMap.Entry<Character, long[]> entry : simbolsSkaits.entrySet()) {

                numericValueOfSymbol = Integer.toBinaryString(Character.getNumericValue(entry.getKey()));

                for (int i = 0; i < numericValueOfSymbol.length(); i++) {
                    if (numericValueOfSymbol.charAt(i) == '0') outPutByte <<= 1;
                    else outPutByte |= 1; outPutByte <<= 1;
                    counter++;
                    if (counter == 8) output.write(outPutByte);
                }

                numericValueOfCount = Integer.toBinaryString((int) entry.getValue()[0]);

                for (int i = 0; i < numericValueOfCount.length(); i++) {
                    if (numericValueOfSymbol.charAt(i) == '0') outPutByte <<= 1;
                    else outPutByte |= 1; outPutByte <<= 1;
                    counter++;
                    if (counter == 8) output.write(outPutByte);
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
