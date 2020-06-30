package andrii.romanyshyn.shopsoftserve._3_DTO.Response;

import andrii.romanyshyn.shopsoftserve._1_Entity.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class PersonResponse {
    private Long id;
    private String fname;
    private Integer money;
    private String imageFileName;
    private List<Long> personFavoriteProductIdList;
    private Long cartId;

    //    Constructor
    //    для перетворення з Ентіті-класу до Респонсе-класу
    public PersonResponse(Person person) {
        this.id = person.getId();
        this.fname = person.getFname();
        this.money = person.getMoney();
        this.imageFileName = person.getImageFileName();
        if (!person.getPersonFavoriteProductList().isEmpty()) {
            this.personFavoriteProductIdList = person.getPersonFavoriteProductList().stream()
                    .map(product -> product.getId())
                    .collect(Collectors.toList());
        }
        if (person.getCart() != null) {
            this.cartId = person.getCart().getId();
        }
    }
}
