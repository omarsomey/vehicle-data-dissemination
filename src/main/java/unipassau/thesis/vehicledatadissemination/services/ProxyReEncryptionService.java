package unipassau.thesis.vehicledatadissemination.services;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ProxyReEncryptionService {
    @Autowired
    org.springframework.core.env.Environment env;

    public String reEncrypt(byte[] data){
        OkHttpClient httpClient = new OkHttpClient();
        Request locationRequest = new Request.Builder()
                .url(env.getProperty("pre.location"))
                .post(RequestBody.create(data))
                .build();


        try (Response response = httpClient.newCall(locationRequest).execute()) {
            String result = response.body().string();

            return result;

        } catch (IOException e) {
            throw new NullPointerException();
        }
    }

}
