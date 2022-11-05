package unipassau.thesis.vehicledatadissemination.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pre")
@ConstructorBinding
public class PREConfiguration {

    private String schemeName;
    private int plainttextModulus;
    private String cryptoFolder;
    private String dataFolder;
    private String stickyDocumentsFolder;

    public String getStickyDocumentsFolder() {
        return stickyDocumentsFolder;
    }

    public void setStickyDocumentsFolder(String stickyDocumentsFolder) {
        this.stickyDocumentsFolder = stickyDocumentsFolder;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public int getPlainttextModulus() {
        return plainttextModulus;
    }

    public void setPlainttextModulus(int plainttextModulus) {
        this.plainttextModulus = plainttextModulus;
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
