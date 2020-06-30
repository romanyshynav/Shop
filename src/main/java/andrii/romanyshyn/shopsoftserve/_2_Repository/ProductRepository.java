package andrii.romanyshyn.shopsoftserve._2_Repository;

import andrii.romanyshyn.shopsoftserve._1_Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository  extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
}
