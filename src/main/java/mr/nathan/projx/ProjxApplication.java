package mr.nathan.projx;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

// figuring out how to do spring unit test using a Postgres db, with a custom dialect without using embedded H2
// core was based on https://programmerfriend.com/spring-boot-integration-testing-done-right/

@EnableJpaRepositories
@SpringBootApplication
public class ProjxApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjxApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandlineRunner(EmployeeRepository employeeRepository) throws Exception {

		return args -> {
			//employeeRepository.deleteAllInBatch();
			GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
			ArrayList<Employee> employees = new ArrayList<>();
			employees.add(createEmployee("First", "Last", gf.createPoint(new Coordinate(10.0, 10.0))));
			employees.add(createEmployee("Mr.", "Frost", gf.createPoint(new Coordinate(20.0, 20.0))));
			employees.add(createEmployee("Santa", "Clause", gf.createPoint(new Coordinate(30.0, 30.0))));
			employees.add(createEmployee("Peter", "Pan", gf.createPoint(new Coordinate(40.0, 40.0))));
			employees.add(createEmployee("Cinder", "ella", gf.createPoint(new Coordinate(50.0, 50.0))));
			employees.add(createEmployee("What", "ever", gf.createPoint(new Coordinate(60.0, 60.0))));
			employeeRepository.saveAll(employees);
		};
	}

	private Employee createEmployee(String firstName, String lastName, Point point) {
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setLocation(point);
		return employee;
	}

}

@Repository
interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

@RestController
@AllArgsConstructor
class EmployeeController {
	private EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
}

@Data
@Entity
class Employee {
	@Id
	@GeneratedValue
	private Long id;

	private String firstName;
	private String lastName;

	@Column(columnDefinition = "Geometry(Point,4326)")
	private Point location;
}
