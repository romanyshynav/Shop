package andrii.romanyshyn.shopsoftserve._3_DTO.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequest {
    private String name;
    private Integer price;
    private String imageB64Format;
    private Long categoryId;
}
