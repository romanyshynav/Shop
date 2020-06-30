package andrii.romanyshyn.shopsoftserve._1_Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Category {

    //todo "Продукти" ВРУЧНУ встановити атрибути зовнішнього ключа "on delete set null" - знайти анотацію під це
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> productList = new ArrayList<>();

    //Constructor
    //для рандомного створення Категорії
    public Category(String name) {
        this.name = name;
    }
}
