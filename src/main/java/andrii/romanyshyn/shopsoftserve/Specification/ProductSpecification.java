package andrii.romanyshyn.shopsoftserve.Specification;

import andrii.romanyshyn.shopsoftserve._1_Entity.Category;
import andrii.romanyshyn.shopsoftserve._1_Entity.Product;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest.ProductCriteriaRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ProductSpecification implements Specification<Product> {

    private String name;
    private Integer priceMin;
    private Integer priceMax;
    private Long categoryId;
    //    Constructor
    public ProductSpecification(ProductCriteriaRequest productCriteriaRequest) {
        this.name = productCriteriaRequest.getName();
        this.priceMin = productCriteriaRequest.getPriceMin();
        this.priceMax = productCriteriaRequest.getPriceMax();
        this.categoryId = productCriteriaRequest.getCategoryId();
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate byName = findByName(root, criteriaBuilder);
        Predicate byPrice = findByPrice(root, criteriaBuilder);
        Predicate byCategoryId = findByCategoryId(root, criteriaBuilder);

        return criteriaBuilder.and(byName, byPrice, byCategoryId);
    }

    private Predicate findByName(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        Predicate predicate;
        if (name != null) {
            predicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
        } else {
            predicate = criteriaBuilder.conjunction();
        }
        return predicate;
    }

    private Predicate findByPrice(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        Predicate predicate;
        if (priceMin != null && priceMax != null) {
            predicate = criteriaBuilder.between(root.get("price"), priceMin, priceMax);
        } else if (priceMin != null) {
            predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceMin);
        } else if (priceMax != null) {
            predicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceMax);
        } else {
            predicate = criteriaBuilder.conjunction();
        }
        return predicate;
    }

    private Predicate findByCategoryId(Root<Product> root, CriteriaBuilder criteriaBuilder) {
        Predicate predicate;
        if (categoryId != null) {
            Join<Product, Category> trainJoin = root.join("category");
            predicate = criteriaBuilder.equal(trainJoin.get("id"), categoryId);
        } else {
            predicate = criteriaBuilder.conjunction();
        }
        return predicate;
    }
}
