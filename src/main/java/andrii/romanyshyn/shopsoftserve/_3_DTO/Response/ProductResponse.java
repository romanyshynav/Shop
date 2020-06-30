package andrii.romanyshyn.shopsoftserve._3_DTO.Response;

import andrii.romanyshyn.shopsoftserve._1_Entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class ProductResponse {
    private Long id;
    private String name;
    private Integer price;
    private String imageFileName;
    private Long categoryId;
    private List<Long> productFavoritePersonIdList;
    private List<Long> productCartIdList;

    //    Constructor
    //    для перетворення з Ентіті-класу до Респонсе-класу
    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageFileName = product.getImageFileName();
        if (product.getCategory() != null) {
            this.categoryId = product.getCategory().getId();
        }
        if (!product.getProductFavoritePersonList().isEmpty()) {
            this.productFavoritePersonIdList = product.getProductFavoritePersonList().stream()
                    .map(person -> person.getId())
                    .collect(Collectors.toList());
        }
        if (!product.getProductCartList().isEmpty()) {
            this.productCartIdList = product.getProductCartList().stream()
                    .map(cart -> cart.getId())
                    .collect(Collectors.toList());
        }
    }
}
