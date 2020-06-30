package andrii.romanyshyn.shopsoftserve._3_DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CartRequest {
    private List<Long> cartProductIdList;
}
