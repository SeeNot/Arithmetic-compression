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

        HashMap<Integer,Integer> Tabula = new HashMap<>();

        int tabulasBeigas = 0;
        for (int i = 0; i < masivs.length; i++) {
            size = masivs[i];
            System.out.println("IZMERS BURTAM= "+ size);
            if (size==0){
                tabulasBeigas = 1;
                break;
            }
            while (size>0){
                i++;
                size--;
                burts = burts << 8;
                burts = masivs[i]|burts;

                // TODO parveidot int uz char
                }
            i++;
            size = masivs[i];
            System.out.println("IZMERS SKAITAM= "+ size);

            while (size>0){
                i++;
                size--;
                skaits = skaits >> 8;
                skaits = masivs[i]|skaits;
            }
            System.out.println("Skaits-" + skaits +"| Burts-"+ burts);
            Tabula.put(burts, skaits);
            burts = 0;
            skaits = 0;
            }

        for ( int key : Tabula.keySet() ) {
            System.out.println( key );
        }

        sc.close();
        }

    }


