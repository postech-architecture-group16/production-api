package com.fiap.challenge.production;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=test")
class ProductionApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
