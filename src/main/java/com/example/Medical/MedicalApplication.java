//package com.example.Medical;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class MedicalApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(MedicalApplication.class, args);
//	}
//
//}
package com.example.Medical;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicalApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		setPropertyIfPresent("DB_URL", dotenv.get("DB_URL"));
		setPropertyIfPresent("DB_USERNAME", dotenv.get("DB_USERNAME"));
		setPropertyIfPresent("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		setPropertyIfPresent("JWT_SECRET", dotenv.get("JWT_SECRET"));
		setPropertyIfPresent("JWT_EXPIRATION", dotenv.get("JWT_EXPIRATION"));
		setPropertyIfPresent("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));
		setPropertyIfPresent("OPENAI_MODEL", dotenv.get("OPENAI_MODEL", "gpt-4o-mini"));

		SpringApplication.run(MedicalApplication.class, args);
	}

	private static void setPropertyIfPresent(String key, String value) {
		if (System.getProperty(key) != null || value == null || value.isBlank()) {
			return;
		}
		System.setProperty(key, value);
	}
}
