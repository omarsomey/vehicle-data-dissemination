package unipassau.thesis.preserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pre")
@ConstructorBinding
public class PREConfiguration {

    private String cryptoFolder;
    private String dataFolder;
    private String stickyDocumentsFolder;

    public String getStickyDocumentsFolder() {
        return stickyDocumentsFolder;
    }

    public void setStickyDocumentsFolder(String stickyDocumentsFolder) {
        this.stickyDocumentsFolder = stickyDocumentsFolder;
    }

    public String getCryptoFolder() {
        return cryptoFolder;
    }

    public void setCryptoFolder(String cryptoFolder) {
        this.cryptoFolder = cryptoFolder;
    }

    public String getDataFolder() {
        return dataFolder;
    }

    public void setDataFolder(String dataFolder) {
        this.dataFolder = dataFolder;
    }
}