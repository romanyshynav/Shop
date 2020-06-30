package andrii.romanyshyn.shopsoftserve._3_DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PersonRequest {
    private String fname;
    private Integer money;
    private String imageB64Format;
    private List<Long> personFavoriteProductIdList;
}
