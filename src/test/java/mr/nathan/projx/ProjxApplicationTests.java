package mr.nathan.projx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
// @SpringBootTest
@EnableJpaRepositories(basePackages = "mr.nathan.projx")
@Import(Config4Test.class)
public class ProjxApplicationTests {
	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void contextLoads() {
		GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4326);
		employeeRepository.save(createEmployee("FROM", "TEST", gf.createPoint(new Coordinate(70.0, 70.0))));
		System.out.println("Done?");
	}

	private Employee createEmployee(String firstName, String lastName, Point point) {
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setLocation(point);
		return employee;
	}


}
