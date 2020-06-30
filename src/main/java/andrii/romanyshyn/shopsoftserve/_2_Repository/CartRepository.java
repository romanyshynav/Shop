package andrii.romanyshyn.shopsoftserve._2_Repository;

import andrii.romanyshyn.shopsoftserve._1_Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository  extends JpaRepository<Cart,Long> {
}
