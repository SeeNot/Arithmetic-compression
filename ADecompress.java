import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


class BitReader {
    int byteIndex = 0;
    int bitIndex = 0;
    byte currentBit = 0;
    boolean hasNextBit = true;
    byte[] bytes;

    BitReader(byte[] bytesToRead) {
        this.bytes = bytesToRead;
        if (bytes.length < 1) {
            this.hasNextBit = false;
        }
    }

    byte getNextBit() {
        byte output = -1;

            if (hasNextBit == true) {
                byte b = bytes[byteIndex];
                output = (byte) (b & 1);
                b = (byte) (b >> 1);
                bytes[byteIndex] = b;
                
                bitIndex += 1;
                if (bitIndex == 8) {
                    if (byteIndex+1 < bytes.length) {
                        byteIndex += 1;
                        bitIndex = 0;
                    }
                    else {
                        hasNextBit = false;
                    }
                }
            }

        return output;
    }

}

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
        catch (java.io.FileNotFoundException ex) {
            System.out.println("readFile() could not find " + filename);
        }
        catch (java.io.IOException ex) {
            System.out.println("readFile() IO error!");
        }

        return output;
    }

    

    static ArrayList<Character> decodeChars(HashMap<Character, long[]> oddsTable, byte[] readBytes) {
        
        ArrayList<Character> output = new ArrayList<Character>();

        final int precision = 62;
        final long maxValue = (long)Math.pow(2,precision);
        final long halfValue = maxValue/2;
        final long quarterValue = maxValue/4;

        BitReader bitReader = new BitReader(readBytes);
        long readLong = 0;
        long upperLimit = 0;
        long lowerLimit = 0;
        
        // read bits from file until readLong is filled
        System.out.println("getting readLong");

        int i = 0;
        while (i < precision && bitReader.hasNextBit) {
            byte currentBit = bitReader.getNextBit();
            if (currentBit == 1) {
                readLong = (long) Math.pow(2, precision-i);
            }
            i += 1;
        }

        System.out.println("init loop");

        // decoding
        boolean loop = true;
        int iter = 0;
        while (loop == true) {
            iter += 1;
            System.out.println("iteration: "+iter + " " + bitReader.hasNextBit);
            for (char letter : oddsTable.keySet()) {
                lowerLimit = oddsTable.get(letter)[0];
                upperLimit = oddsTable.get(letter)[1];
                
                if (lowerLimit <= readLong && readLong < upperLimit) {
                    output.add(letter);
                }
                if (bitReader.hasNextBit == false) {
                    loop = false;
                    break;
                }
            }

            // rescaling
            while (upperLimit < halfValue || lowerLimit > halfValue) {
                if (upperLimit < halfValue) {
                    lowerLimit *= 2;
                    upperLimit *= 2;
                    readLong *= 2;
                }
                else if (lowerLimit > halfValue) {
                    lowerLimit = 2*(lowerLimit-halfValue);
                    upperLimit = 2*(upperLimit-halfValue);
                    readLong = 2*(readLong-halfValue);
                }
                if (bitReader.getNextBit() == 1) {
                    readLong += 1;
                    i += 1;
                }
            }
            while (lowerLimit > quarterValue && upperLimit < quarterValue*3) {
                lowerLimit = 2*(lowerLimit-quarterValue);
                upperLimit = 2*(upperLimit-quarterValue);
                readLong = 2*(readLong-quarterValue);
                if (bitReader.getNextBit() == 1) {
                    readLong += 1;
                    i += 1;
                }
            }
        }

        return output;
    }

    public static void tryToOutput(char theByte, String resultFile) {

        try {
            FileOutputStream output = new FileOutputStream(resultFile, true);

            output.write(theByte);
            output.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }



    }
    
    static HashMap<Character, long[]> getIntervalTable(HashMap<Character, Integer> table) {
        long MAX = (long)Math.pow(2,62);
        HashMap<Character, long[]> output = new HashMap<>();

        int overallCharCount = 0;
        for (int value : table.values()) {
            overallCharCount += value;
        }

        long sectionValue = MAX / overallCharCount;
        long lastInterval = 0;
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
        byte[] datuMasivs = readFile(sourceFile);
        long maxValue = (long)Math.pow(2,62);
        int burts = 0;
        int skaits = 0;

        HashMap<Character,Integer> Tabula = new HashMap<>();

        int tabulasBeigas = 0;
        for (int i = 0; i < datuMasivs.length; i++) {
            size = datuMasivs[i];
            if (size==0){
                tabulasBeigas = i;
                break;
            }
            while (size>0){
                i++;
                size--;
                burts = burts << 8;
                burts = datuMasivs[i]|burts;
                }
            i++;
            size = datuMasivs[i];

            while (size>0){
                i++;
                size--;
                skaits = skaits << 8;
                skaits = datuMasivs[i]|skaits;
            }
            Tabula.put((char) burts, skaits);
            burts = 0;
            skaits = 0;
        }

        // izveido masivu bez tabulas
        byte[] encodedBytes = new byte[datuMasivs.length - tabulasBeigas];
        for (int i = 0; i < encodedBytes.length; i++) {
            System.out.println(tabulasBeigas+i);
            encodedBytes[i] = datuMasivs[tabulasBeigas+i];
        }

        HashMap<Character, long[]> intervaluTabula = getIntervalTable(Tabula);
        System.out.println("got intervals"); // temp
        ArrayList<Character> charList = decodeChars(intervaluTabula, encodedBytes);
        
        System.out.println("decoded chars");

        for (char c : charList) {
            tryToOutput(c, resultFile);
        }

        

        sc.close();
        System.out.println("Done");
        }

    }


