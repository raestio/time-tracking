package cz.cvut.fit.timetracking;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimeTrackingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TimeTrackingApplication.class, args);
	}

	@Autowired
	private DataAccessApi dataAccessApi;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(dataAccessApi.findUserById(null));
    }
}
