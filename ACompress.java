import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

public class ACompress {

    public static void compress(){
        String sourceFile = "C:\\Users\\ozoli\\Desktop\\testaFails_1.txt", resultFile;
       // C:\Users\ozoli\Desktop\lol.txt
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
            int counter = 0, extranumbers=0;
            byte toOut = 0;
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


                    if ((lowerLimit <= halfValue && lowerLimit > halfValue/2) && (upperLimit < halfValue + halfValue/2 && upperLimit >= halfValue)) {

                    }
                    else if (lowerLimit <= halfValue/2 && upperLimit >= halfValue) {
                    }
                    else if (lowerLimit <= halfValue && upperLimit <= halfValue + halfValue/2) {
                    // krc sitie tie if, kuri mums jaizdoma, un man liekas mes esam done, bet the tos while man liekas vjg
                    }
                    // un te vel jaizdoma, ka mes dalam baitos, jo var but ( es pielauju ), ka mums uzreiz 5 nulles jaliek klat, un tad vinas kka uz nakamo
                    // baitu japarceļ vai kkas tads
                    // un sitas bus visiem tajiem extra 3 ifiem

                    else if (upperLimit < halfValue) {
                        toOut <<= 1;
                        halfValue /= 2;
                        counter++;

                    }
                    else if (lowerLimit > halfValue) {
                        toOut |= 1;
                        toOut <<= 1;
                        halfValue = halfValue/2 + halfValue;
                        counter++;
                    }

                    if (counter == 8){
                        output.write(toOut);
                        toOut = 0;
                        counter = 0;
                    }
                    System.out.println(halfValue);


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


}
