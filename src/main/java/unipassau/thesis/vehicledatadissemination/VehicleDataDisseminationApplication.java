package unipassau.thesis.vehicledatadissemination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import unipassau.thesis.vehicledatadissemination.util.OpenPRE;


@SpringBootApplication
public class VehicleDataDisseminationApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory
			.getLogger(VehicleDataDisseminationApplication.class);

	@Value("${app.cryptoFolder}")
	private String cryptoFolder;
	@Value("${app.policyFolder}")
	private String policyFolder;
	@Value("${pre.schemeName}")
	private String schemeName;
	@Value("${pre.plainTextModulus}")
	private int plainTextModulus;

	public static void main(String[] args) {
		SpringApplication.run(VehicleDataDisseminationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
/*		LOG.info("EXECUTING : Generating crypto context");
		OpenPRE.INSTANCE.cryptoContextGen(schemeName, cryptoFolder, "cryptocontext", plainTextModulus);
		LOG.info("EXECUTING : Generating key pairs for Alice and Bob");
		OpenPRE.INSTANCE.keysGen(cryptoFolder + "cryptocontext", cryptoFolder + "alice");
		OpenPRE.INSTANCE.keysGen(cryptoFolder + "cryptocontext", cryptoFolder + "bob");
		LOG.info("EXECUTING : Generating re encryption keys from Alice to Bob");
		OpenPRE.INSTANCE.ReKeyGen(cryptoFolder + "alice-private-key",
				cryptoFolder + "bob-public-key",
				cryptoFolder + "alice2bob" );*/
	}

}
