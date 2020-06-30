package andrii.romanyshyn.shopsoftserve._2_Repository;

import andrii.romanyshyn.shopsoftserve._1_Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonRepository  extends JpaRepository<Person,Long>, JpaSpecificationExecutor<Person> {
}
