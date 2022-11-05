package unipassau.thesis.vehicledatadissemination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class VehicleDataDisseminationApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory
			.getLogger(VehicleDataDisseminationApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(VehicleDataDisseminationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING : command line runner");


	}

}
