package unipassau.thesis.vehicledatadissemination.demo;

import okhttp3.OkHttpClient;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Bob {
    private static Logger LOG = LoggerFactory
            .getLogger(Bob.class);

    public static String cryptoFolder = System.getProperty("user.dir")+"/crypto/";
    public static String dataFolder = System.getProperty("user.dir")+"/data/";
    public static String pubKey = cryptoFolder + "bob-public-key";
    public static String policyFolder = System.getProperty("user.dir")+"/policies/";


    public static int count=0;
    public static JSONObject res = new JSONObject();




    public static void main(String[] args) {

        OkHttpClient httpClient = new OkHttpClient();
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {


                if (++count>= Integer.parseInt(args[0]) ) {
                    exec.shutdown();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

    }
}
