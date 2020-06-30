package andrii.romanyshyn.shopsoftserve._3_DTO.Response;

import andrii.romanyshyn.shopsoftserve._1_Entity.Cart;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class CartResponse {
    private Long id;
    private String name;
    private Long personId;
    private List<Long> cartProductIdList;

    //    Constructor
    //    для перетворення з Ентіті-класу до Респонсе-класу
    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.name = cart.getName();
        if (cart.getPerson() != null) {
            this.personId = cart.getPerson().getId();
        }
        if (!cart.getCartProductList().isEmpty()) {
            this.cartProductIdList = cart.getCartProductList().stream()
                    .map(product -> product.getId())
                    .collect(Collectors.toList());
        }
    }
}
