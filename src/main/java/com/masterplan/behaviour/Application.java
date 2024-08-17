package com.masterplan.behaviour;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.masterplan.behaviour.config.RsaKeyProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class Application {

	// private static final Logger LOG=LoggerFactory.getLogger(JwtDemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	


}
