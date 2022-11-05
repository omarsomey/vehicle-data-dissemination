package unipassau.thesis.preserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import unipassau.thesis.preserver.util.OpenPRE;

import java.io.*;

@RestController
public class ReEncryptionController {

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String reEncryptData(InputStream dataStream) throws Exception {
        byte[] brahim = dataStream.readAllBytes();
        try (var out = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream("/home/somey/IdeaProjects/vehicle-data-dissemination/tmp/omar", true)))){

            out.write(brahim);

        } catch (IOException e) {
            e.printStackTrace();
        }

        OpenPRE.INSTANCE.ReEncrypt("/home/somey/IdeaProjects/vehicle-data-dissemination/tmp/omar",
                "/home/somey/IdeaProjects/vehicle-data-dissemination/crypto/a2b-re-enc-key",
                "/home/somey/IdeaProjects/vehicle-data-dissemination/tmp/alina");
        String b = OpenPRE.INSTANCE.Decrypt("/home/somey/IdeaProjects/vehicle-data-dissemination/crypto/b-private-key",
                "/home/somey/IdeaProjects/vehicle-data-dissemination/tmp/alina" );
        return b;
    }

}
