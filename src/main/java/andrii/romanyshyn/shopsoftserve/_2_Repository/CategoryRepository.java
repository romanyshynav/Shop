package andrii.romanyshyn.shopsoftserve._2_Repository;

import andrii.romanyshyn.shopsoftserve._1_Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository  extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Category> {
}
