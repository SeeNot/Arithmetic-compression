import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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


        sc.close();


    }


}
