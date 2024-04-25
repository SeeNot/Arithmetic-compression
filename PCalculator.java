import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PCalculator {

    public static void Calculate(String FileName){

        HashMap<Character,int[]> finalOddsTabel = new HashMap<>();
        HashMap<Character,Integer> charCounter = new HashMap<>();

        long charAmount = 0;


        try {
            BufferedReader br = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream(FileName), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                probabilityCalculator(charCounter, line);
                charAmount += line.length();
            }



            br.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }








    }
    public static void probabilityCalculator(HashMap<Character,Integer> ptabel, String line) {

        for (int i = 0; i <= line.length(); i++) {
            if (ptabel.containsKey(line.charAt(i))) {
                ptabel.replace(line.charAt(i), ptabel.get(line.charAt(i)), ptabel.get(line.charAt(i))+1);
            }
            else ptabel.put(line.charAt(i), 1);
        }
    }



}
