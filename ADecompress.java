import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ADecompress {

    static byte[] readFile(String filename) {
        byte[] output = new byte[0];

        ArrayList<Byte> dynamicArray = new ArrayList<Byte>();
        try {
            BufferedInputStream reader = new BufferedInputStream(new FileInputStream(filename));
            byte data;
            while((data = (byte) reader.read()) != -1) {
                dynamicArray.add(data);
            }
            // parvers ArrayList<Byte> uz byte[]
            // itka byte[] atrak velak nolasit 
            output = new byte[dynamicArray.size()];
            for(int i = 0; i < dynamicArray.size(); i++) {
                output[i] = dynamicArray.get(i).byteValue();
            }

            reader.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("readFile() could not find " + filename);
        }
        catch (IOException ex) {
            System.out.println("readFile() IO error!");
        }

        return output;
    }

    static HashMap<Character, long[]> getIntervalTable(HashMap<Character, Integer> table, long end, long start) {
        HashMap<Character, long[]> output = new HashMap<>();

        int overallCharCount = 0;
        for (int value : table.values()) {
            overallCharCount += value;
        }

        long sectionValue = end / overallCharCount;
        long lastInterval = start;
        for (char c : table.keySet()) {
            int count = table.get(c);
            long intervalEnd = lastInterval + (sectionValue * count);
            long intervalStart = lastInterval;
            lastInterval = intervalEnd;

            output.put(c, new long[]{intervalStart, intervalEnd} );
        }

        return output;
    }


    public static void deCompress(){
        String sourceFile, resultFile;
        Scanner sc = new Scanner(System.in);
        System.out.print("source file name: ");
        sourceFile = sc.next();
        System.out.print("archive name: ");
        resultFile = sc.next();
        int size;
        byte[] masivs = readFile(sourceFile);
        long maxValue = (long)Math.pow(2,62);
        int burts = 0;
        int skaits = 0;

        HashMap<Character,Integer> Tabula = new HashMap<>();

        int tabulasBeigas = 0;
        for (int i = 0; i < masivs.length; i++) {
            size = masivs[i];
            System.out.println("IZMERS BURTAM= "+ size);
            if (size==0){
                tabulasBeigas = i;
                break;
            }
            while (size>0){
                i++;
                size--;
                burts = burts << 8;
                burts = masivs[i]|burts;
                }
            i++;
            size = masivs[i];
            System.out.println("IZMERS SKAITAM= "+ size);

            while (size>0){
                i++;
                size--;
                skaits = skaits << 8;
                skaits = masivs[i]|skaits;
            }
            Tabula.put((char) burts, skaits);
            burts = 0;
            skaits = 0;
        }

        long garais = 0;
        for (int i = tabulasBeigas+1; i < masivs.length; i++){
            garais = garais << 8;
            garais = masivs[i]|garais;
        }



        
        HashMap<Character, long[]> intervalTable = getIntervalTable(Tabula, (long)Math.pow(2,62), 0);
        
        for (char c : intervalTable.keySet()) {
            long[] interval = intervalTable.get(c);
            System.out.println(Character.toString(c) + " " + interval[0] + " " + interval[1]);
        }
        

        sc.close();
        }

    }


