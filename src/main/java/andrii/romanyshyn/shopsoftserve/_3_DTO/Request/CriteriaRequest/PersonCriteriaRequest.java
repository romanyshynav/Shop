package andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonCriteriaRequest {
    private String fname;
    private Integer moneyMin;
    private Integer moneyMax;
}
