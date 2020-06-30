package andrii.romanyshyn.shopsoftserve.Specification;

import andrii.romanyshyn.shopsoftserve._1_Entity.Category;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest.CategoryCriteriaRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CategorySpecification implements Specification<Category> {

    private String name;
    //    Constructor
    public CategorySpecification(CategoryCriteriaRequest categoryCriteriaRequest) {
        this.name = categoryCriteriaRequest.getName();
    }

    @Override
    public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate byName = findByName(root,criteriaBuilder);
        return criteriaBuilder.and(byName);
    }

    private Predicate findByName(Root<Category> root, CriteriaBuilder criteriaBuilder){
        Predicate predicate;
        if(name !=null){
            predicate = criteriaBuilder.like(root.get("name"), "%"+ name +"%");
        }else {
            predicate = criteriaBuilder.conjunction();
        }
        return predicate;
    }
}
