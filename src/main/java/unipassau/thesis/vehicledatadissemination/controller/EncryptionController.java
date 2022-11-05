package unipassau.thesis.vehicledatadissemination.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import unipassau.thesis.vehicledatadissemination.VehicleDataDisseminationApplication;
import unipassau.thesis.vehicledatadissemination.config.PREConfiguration;
import unipassau.thesis.vehicledatadissemination.util.StickyDocument;


import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
public class EncryptionController {
    OkHttpClient httpClient = new OkHttpClient();
    Logger LOG = LoggerFactory.getLogger(VehicleDataDisseminationApplication.class);

    @Autowired
    org.springframework.core.env.Environment env;
    @Autowired
    PREConfiguration preConfiguration;


    @RequestMapping(method = RequestMethod.POST, value = "/data/location")
    public String getLocation(InputStream dataStream) throws Exception {
        byte[] input = dataStream.readAllBytes();
        System.out.println(input.length);
        Map<String, byte[]> omar =  StickyDocument.read(input);
        byte[] hash = omar.get("hash");
        byte[] data = omar.get("data");
        System.out.println(data.length);
        Request locationRequest = new Request.Builder()
                .url(env.getProperty("pre.location"))
                .post(RequestBody.create(data))
                .build();


        try (Response response = httpClient.newCall(locationRequest).execute()) {
            String brahim = response.body().string();
            System.out.print(brahim);

            return brahim;

        } catch (IOException e) {
            throw new NullPointerException();
        }
    }
}
