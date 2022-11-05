package unipassau.thesis.vehicledatadissemination.controller;

import com.fasterxml.jackson.annotation.JsonRawValue;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import unipassau.thesis.vehicledatadissemination.config.PREConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class LocationController {
    OkHttpClient httpClient = new OkHttpClient();

    @Autowired
    org.springframework.core.env.Environment env;
    @Autowired
    PREConfiguration preConfiguration;

    @RequestMapping(value="/vehicle/location", produces="application/json")
    @JsonRawValue
    public String getLocation() {
        Request locationRequest = new Request.Builder()
                .url(env.getProperty("pre.location"))
                .get()
                .build();

        try (Response response = httpClient.newCall(locationRequest).execute()) {
            JSONObject res = new JSONObject(response.body().string());

            return res.toString();

        } catch (IOException e) {
            throw new NullPointerException();
        }
    }


}
