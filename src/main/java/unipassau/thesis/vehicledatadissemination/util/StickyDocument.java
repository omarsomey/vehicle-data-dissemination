package unipassau.thesis.vehicledatadissemination.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StickyDocument {


    public static byte[] xmlToHash(String filePath){
        MessageDigest digest = null;
        byte[] encodedhash = null;
        try {
            digest = MessageDigest.getInstance("SHA3-256");
            byte[] data = Files.readAllBytes(Paths.get(filePath));
            encodedhash = digest.digest(data);
            return encodedhash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedhash;
    }

    public static void writer(String xmlPolicyPath, String ciphertext, String destinationPath){

        try (var out = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(destinationPath, true)))){
            out.write(xmlToHash(xmlPolicyPath));

            int Size_Buffer = 16 * 1024; //16kb
            // create a reader for data file
            FileInputStream read = new FileInputStream(new File(ciphertext));
            BufferedInputStream buffered_reader = new BufferedInputStream(read, Size_Buffer);

            int byt;
            while ((byt = buffered_reader.read()) != -1) {

                String str = Integer.toHexString(byt);
                out.writeByte(byt);
            }

            buffered_reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Map<String, byte[]> read(byte[] stickydata){
        Map<String, byte[]> stickydocument = new HashMap<>();

        byte[] hash = Arrays.copyOfRange(stickydata, 0, 31);
        byte[] data = Arrays.copyOfRange(stickydata, 32, stickydata.length);
        stickydocument.put("hash", hash);
        stickydocument.put("data", data);
        return stickydocument;


    }

    public static void main(String[] args) throws IOException {


        PREUtils pre = new PREUtils("BFV", 256, 2);
        pre.setCryptoFolder("/home/somey/IdeaProjects/vehicle-data-dissemination/crypto/");
        pre.setDataFolder("/home/somey/IdeaProjects/vehicle-data-dissemination/data/");

        try (var out = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream("helena", true)))){
            out.write(xmlToHash("/home/somey/IdeaProjects/automotive-access-control/src/main/resources/permit-all.xml"));

            int Size_Buffer = 16 * 1024; //16kb
            // create a reader for data file
            FileInputStream read = new FileInputStream(new File(pre.getDataFolder() + "ciphertext"));
            BufferedInputStream buffered_reader = new BufferedInputStream(read, Size_Buffer);

            int byt;
            while ((byt = buffered_reader.read()) != -1) {

                String str = Integer.toHexString(byt);
                out.writeByte(byt);
            }

            buffered_reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
