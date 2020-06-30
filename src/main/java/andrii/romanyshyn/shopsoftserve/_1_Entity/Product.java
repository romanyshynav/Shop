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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private String imageFileName; // для картинки

    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "personFavoriteProductList")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Person> productFavoritePersonList = new ArrayList<>();

    @ManyToMany(mappedBy = "cartProductList")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Cart> productCartList = new ArrayList<>();

    //    Constructor
    //для рандомного створення Продуктів
    public Product(String name, Integer price, String imageFileName, Category category) {
        this.name = name;
        this.price = price;
        this.imageFileName = imageFileName;
        this.category = category;
    }
}
