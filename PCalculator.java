import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class PCalculator {

    public static HashMap<Character,long[]> Calculate(String FileName){

        HashMap<Character,long[]> finalOddsTabel = new HashMap<>();
        HashMap<Character,Integer> charCounter = new HashMap<>();

        long charAmount = 0;

        try {
            BufferedReader br = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream(FileName), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                probabilityCalculator(charCounter, line);
                charAmount += line.length();
            }

            long iedalasvertiba = Long.MAX_VALUE/charAmount;
            long apaksejaVertiba = 0;
            for (HashMap.Entry<Character, Integer> entry : charCounter.entrySet()) {

                long[] limiti = {apaksejaVertiba, apaksejaVertiba + iedalasvertiba*entry.getValue()};
                finalOddsTabel.put(entry.getKey(), limiti);
                apaksejaVertiba += iedalasvertiba*entry.getValue();

            }






            br.close();



        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        return finalOddsTabel;

    }
    public static void probabilityCalculator(HashMap<Character,Integer> ptabel, String line) {

        for (int i = 0; i < line.length(); i++) {
            if (ptabel.containsKey(line.charAt(i))) {
                ptabel.replace(line.charAt(i), ptabel.get(line.charAt(i)), ptabel.get(line.charAt(i))+1);
            }
            else ptabel.put(line.charAt(i), 1);
        }
    }



}
