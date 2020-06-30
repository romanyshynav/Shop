package andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCriteriaRequest {
    private String name;
    private Integer priceMin;
    private Integer priceMax;
    private Long categoryId;

}
