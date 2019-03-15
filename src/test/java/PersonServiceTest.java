import com.softserve.javaweb.model.Experience;
import com.softserve.javaweb.model.Person;
import com.softserve.javaweb.service.PersonService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonServiceTest {

    PersonService personService = new PersonService();

    public Person[] getPerson() {
        Experience experience1 = new Experience("Yuriy Fedkovych Chernivtsi National University",
                LocalDate.parse("2018-06-10"), LocalDate.parse("2019-02-28"));
        Experience experience2 = new Experience("SoftServe", LocalDate.parse("2019-06-02"),
                LocalDate.parse("2019-09-18"));
        List<Experience> experiences = new ArrayList<>();
        experiences.add(experience1);
        experiences.add(experience2);
        Person person = new Person(104, "TetianaTsanJSON", 22, LocalDate.parse("1997-02-25"),
                "Chernivtsi city, Russka street", "taniakytsun@gmail.com", "(097)5395051", "Java", experiences);
        return new Person[] { person };
    }

    @Test
    public void testParseJSON() throws SQLException {
        Person expectedPerson = getPerson()[0];
        Person person = personService.parsePersonFromJSON("src//main//resources//person.json");
        Assert.assertEquals(person, expectedPerson);
    }
}
