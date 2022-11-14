package unipassau.thesis.vehicledatadissemination.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import unipassau.thesis.vehicledatadissemination.util.Encoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

@Configuration
public class PolicyConfiguration {

    private final Logger logger = LoggerFactory.getLogger(PolicyConfiguration.class);

    private final String POLICY_STORE_PATH = "policies";


    @Bean
    public HashMap<String, String> policyMap() throws FileNotFoundException {

        // Instantiate PDP which also loads policies from disk.
        logger.info("Loading policies configuration.");
        HashMap<String, String> policyMap = new HashMap<>() ;
        File directoryPath = new File(POLICY_STORE_PATH);
        File filesList[] = directoryPath.listFiles();
        for(File file : filesList) {
            logger.info("Loading policy : "+ file.getName() );
            policyMap.put(Encoder.bytesToHex(Encoder.xmlToHash(file.getAbsolutePath())), file.getName());
        }
        logger.info(policyMap.toString());



        logger.info("Loaded policies configuration.");

        return policyMap;
    }
}
