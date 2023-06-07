package ru.teachmeplz.microinaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RequestMapping(value = "/hello")
@EnableEurekaClient
public class MicroinactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroinactionApplication.class, args);
	}

	public String helloRemoteServiceCall(String firstName, String lastName) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> restExchange =
				restTemplate.exchange(
						"http://logical-service-id/name/" + "{firstName}/ {lastName}",
						HttpMethod.GET, null, String.class,
						firstName, lastName);
		return restExchange.getBody();
	}

	@RequestMapping(value = "/{firstName}/{lastName}/", method = RequestMethod.GET)
	public String helloGET(
			@PathVariable("firstName") String firstName,
			@RequestParam("lastName") String lastName) {
		return helloRemoteServiceCall(firstName, lastName);
	}
}


