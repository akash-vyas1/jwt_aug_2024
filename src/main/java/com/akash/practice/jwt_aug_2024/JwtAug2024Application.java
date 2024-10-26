package com.akash.practice.jwt_aug_2024;

import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.akash.practice.jwt_aug_2024.models.HumanBeing;
import com.akash.practice.jwt_aug_2024.models.MedicalDoc;
import com.akash.practice.jwt_aug_2024.repositories.MedicalDocRepo;
import com.akash.practice.jwt_aug_2024.repositories.UserRepo;

@SpringBootApplication
public class JwtAug2024Application {

	public static void main(String[] args) {
		SpringApplication.run(JwtAug2024Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return new CommandLineRunner() {

			@Autowired
			UserRepo userRepo;

			@Autowired
			MedicalDocRepo medicalDocRepo;

			@Autowired
			PasswordEncoder passwordEncoder;

			@Override
			public void run(String... args) throws Exception {

				UnaryOperator<String> encode = string -> passwordEncoder.encode(string);

				HumanBeing user1 = new HumanBeing("Akash Vyas",
						"akash@email.com", (short) 25, "ADMIN", "1/1/2000",
						encode.apply("akash"));

				HumanBeing user2 = new HumanBeing("Sanjay Nayak",
						"sanjay@email.com", (short) 25, "ADMIN",
						"1/1/2001", encode.apply("sanjay"));
				HumanBeing user3 = new HumanBeing("Patient1",
						"Patient1@email.com", (short) 29, "USER", "1/1/2005",
						encode.apply("patient1"));
				HumanBeing user4 = new HumanBeing("Patient2",
						"Patient2@email.com", (short) 20, "USER", "1/1/1995",
						encode.apply("patient2"));
				HumanBeing user5 = new HumanBeing("Khushal pokar",
						"khushal@email.com", (short) 24, "DOCTOR",
						"1/3/2001", encode.apply("khushal"));
				HumanBeing user6 = new HumanBeing("Apan patel",
						"apan@email.com", (short) 23, "DOCTOR", "1/1/1999",
						encode.apply("apan"));
				HumanBeing user7 = new HumanBeing("Nisarg patel", "nisarg@email.com", (short) 26, "MANAGER",
						"1/3/1995", encode.apply("nisarg"));

				userRepo.save(user1);
				userRepo.save(user2);
				userRepo.save(user3);
				userRepo.save(user4);
				userRepo.save(user5);
				userRepo.save(user6);
				userRepo.save(user7);

				MedicalDoc doc1 = new MedicalDoc(3, 5, 5000, "Eat well", "15/8/2024");
				MedicalDoc doc2 = new MedicalDoc(4, 6, 10000, "Do yoga", "15/9/2024");
				medicalDocRepo.save(doc1);
				medicalDocRepo.save(doc2);
				// System.out.println("your default running things");
			}

		};
	}

}
