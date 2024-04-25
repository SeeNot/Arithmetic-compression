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

}
