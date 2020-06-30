package andrii.romanyshyn.shopsoftserve._1_Entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Cart {

    //todo "Корзинка-Продукти" ВРУЧНУ встановити атрибути зовнішнього ключа "on delete caskade" - знайти анотацію під це діло
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(20) DEFAULT 'Моя корзинка'")
    private String name;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person person;

    @ManyToMany
    private List<Product> cartProductList = new ArrayList<>();
}
