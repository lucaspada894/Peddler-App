package org.springframework.samples.peddler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.samples.peddler.images.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class PeddlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeddlerApplication.class, args);
	}
}
