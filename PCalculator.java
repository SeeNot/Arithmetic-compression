import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;

public class PCalculator {

    public static void Calculate(String FileName){

        HashMap<Character,int[]> finalOddsTabel = new HashMap<>();
        HashMap<Character,Integer> charCounter = new HashMap<>();

        long charAmount = 0;


        try {
            BufferedReader br = new BufferedReader
                    (new InputStreamReader
                            (new FileInputStream(FileName), "UTF-8"));
            String line = br.readLine();




            br.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }








    }
    public static void probabilityCalculator(HashMap<Character,Integer> ptabel) {
        String sc = "asdasd";
        ptabel.put('a', 2);

        for (int i = 0; i <= sc.length(); i++) {
            if (ptabel.containsKey(sc.charAt(i))) {
                ptabel.replace(sc.charAt(i), ptabel.get(sc.charAt(i)), ptabel.get(sc.charAt(i))+1);
            }
            else ptabel.put(sc.charAt(i), 1);
        }
    }



}
