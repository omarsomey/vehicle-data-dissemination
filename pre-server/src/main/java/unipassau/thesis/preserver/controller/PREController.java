package unipassau.thesis.preserver.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import unipassau.thesis.preserver.service.PREService;
import unipassau.thesis.preserver.util.OpenPRE;

import java.io.*;

@RestController
public class PREController {

    @Autowired
    PREService preService;

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String reEncryptData(InputStream dataStream) throws Exception {
        byte[] encryptedData = dataStream.readAllBytes();
        return preService.reEncryptionService(encryptedData);
    }

}
