package davidof.misrutas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import davidof.misrutas.service.FileUploadProperties;

@EnableConfigurationProperties({
    FileUploadProperties.class
})
@SpringBootApplication
public class MisRutasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MisRutasApplication.class, args);
	}

}
