package andrii.romanyshyn.shopsoftserve._1_Entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    //todo "Люди-Продукти" ВРУЧНУ встановити атрибути зовнішнього ключа "on delete caskade" - знайти анотацію під це
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fname;
    private Integer money;
    private String imageFileName;

    @ManyToMany
    private Set<Product> personFavoriteProductList = new HashSet<>();

    @OneToOne(mappedBy = "person")
    private Cart cart;

    //    Constructor
    //для рандомного створення Людини
    public Person(String fname, Integer money, String imageFileName, Set<Product> personFavoriteProductList) {
        this.fname = fname;
        this.money = money;
        this.imageFileName = imageFileName;
        this.personFavoriteProductList = personFavoriteProductList;
    }
}
