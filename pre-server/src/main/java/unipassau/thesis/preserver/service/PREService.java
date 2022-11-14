package unipassau.thesis.preserver.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unipassau.thesis.preserver.util.OpenPRE;

import java.io.*;

@Service
public class PREService {

    @Autowired
    org.springframework.core.env.Environment env;

    public String reEncryptionService(byte[] encryptedData) throws FileNotFoundException {

        String tempFileName = RandomStringUtils.randomAlphanumeric(12);
        try (var out = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream(env.getProperty("pre.tmpFolder") + tempFileName, true)))){

            out.write(encryptedData);

        } catch (IOException e) {
            e.printStackTrace();
        }

        OpenPRE.INSTANCE.ReEncrypt(env.getProperty("app.tmpFolder") + tempFileName,
                env.getProperty("app.cryptoFolder") + "alice2bob-re-enc-key",
                env.getProperty("app.tmpFolder") + tempFileName + "-re");
        FileInputStream read = new FileInputStream(new File(env.getProperty("app.tmpFolder") + tempFileName + "-re"));

        String decryptedData = OpenPRE.INSTANCE.Decrypt(env.getProperty("app.cryptoFolder") + "bob-private-key",
                env.getProperty("app.tmpFolder") + tempFileName + "-re");
        return decryptedData;
    }
}
