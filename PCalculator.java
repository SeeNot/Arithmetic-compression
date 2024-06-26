import java.util.HashMap;

public class PCalculator {

    public static void probabilityCalculator(HashMap<Character,Integer> ptabel, String line) {

        for (int i = 0; i < line.length(); i++) {
            if (ptabel.containsKey(line.charAt(i))) {
                ptabel.replace(line.charAt(i), ptabel.get(line.charAt(i)), ptabel.get(line.charAt(i))+1);
            }
            else ptabel.put(line.charAt(i), 1);
        }
    }

    public static HashMap<Character,long[]> recalculateOddsTabel(HashMap<Character,Integer> ptabel,long charAmount , long maxvalue ) {
        HashMap<Character,long[]> finalOddsTabel = new HashMap<>();

        long iedalasvertiba = maxvalue/charAmount;
        long apaksejaVertiba = 0;
        for (HashMap.Entry<Character, Integer> entry : ptabel.entrySet()) {

            long[] limiti = {apaksejaVertiba, apaksejaVertiba + iedalasvertiba*entry.getValue()};
            finalOddsTabel.put(entry.getKey(), limiti);
            apaksejaVertiba += iedalasvertiba*entry.getValue();



        }

        return finalOddsTabel;

    }


    public static HashMap<Character,long[]> recalculateOddsTabel(HashMap<Character,Integer> ptabel,long charAmount , long maxvalue, long offset ) {
        HashMap<Character,long[]> finalOddsTabel = new HashMap<>();

        long iedalasvertiba = Math.round(maxvalue*1.0/charAmount);
        long apaksejaVertiba = offset;
        for (HashMap.Entry<Character, Integer> entry : ptabel.entrySet()) {

            long[] limiti = {apaksejaVertiba, apaksejaVertiba + iedalasvertiba*entry.getValue()};
            finalOddsTabel.put(entry.getKey(), limiti);
            apaksejaVertiba += iedalasvertiba*entry.getValue();

        }



        return finalOddsTabel;



    }



    public static HashMap<Character,Long> oddsAsNumbers(HashMap<Character,Integer> ptabel, long charAmount) {
        HashMap<Character,Long> finalOddsTabel = new HashMap<>();


        for (HashMap.Entry<Character, Integer> entry : ptabel.entrySet()) {
//            Long odd = Math.round(entry.getValue()*1.0/charAmount);
            int value = entry.getValue();
            Long odd = (long)value;
            finalOddsTabel.put(entry.getKey(), odd);

        }

        return finalOddsTabel;
    }



}