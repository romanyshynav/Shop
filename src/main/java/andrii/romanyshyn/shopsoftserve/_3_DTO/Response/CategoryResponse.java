package andrii.romanyshyn.shopsoftserve._3_DTO.Response;

import andrii.romanyshyn.shopsoftserve._1_Entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class CategoryResponse {
    private Long id;
    private String name;
    private List<Long> productIdList;

    //    Constructor
    //    для перетворення з Ентіті-класу до Респонсе-класу
    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        if (!category.getProductList().isEmpty()) {
            this.productIdList = category.getProductList().stream()
                    .map(product -> product.getId())
                    .collect(Collectors.toList());
        }
    }
}
