package backend.taskweaver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableJpaAuditing
public class TaskweaverApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskweaverApplication.class, args);
	}

	@GetMapping(value = "/")
	public String helloWorld() {
		return "CI/CD";
	}
}
