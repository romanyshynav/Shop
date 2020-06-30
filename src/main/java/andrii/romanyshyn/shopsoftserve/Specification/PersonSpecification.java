package andrii.romanyshyn.shopsoftserve.Specification;

import andrii.romanyshyn.shopsoftserve._1_Entity.Person;
import andrii.romanyshyn.shopsoftserve._3_DTO.Request.CriteriaRequest.PersonCriteriaRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PersonSpecification implements Specification<Person> {
    private String fname;
    private Integer moneyMin;
    private Integer moneyMax;
    //    Constructor
    public PersonSpecification(PersonCriteriaRequest personCriteriaRequest) {
        this.fname = personCriteriaRequest.getFname();
        this.moneyMin = personCriteriaRequest.getMoneyMin();
        this.moneyMax = personCriteriaRequest.getMoneyMax();
    }

    @Override
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate byFname = findByFname(root,criteriaBuilder);
        Predicate byMoney = findByMoney(root,criteriaBuilder);
        return criteriaBuilder.and(byFname, byMoney);
    }

    private Predicate findByFname(Root<Person> root, CriteriaBuilder criteriaBuilder){
        Predicate predicate;
        if(fname !=null){
            predicate = criteriaBuilder.like(root.get("fname"), "%"+ fname +"%");
        }else {
            predicate = criteriaBuilder.conjunction();
        }
        return predicate;
    }
    private Predicate findByMoney(Root<Person> root, CriteriaBuilder criteriaBuilder) {
        Predicate predicate;
        if (moneyMin != null && moneyMax != null) {
            predicate = criteriaBuilder.between(root.get("money"), moneyMin, moneyMax);
        } else if (moneyMin != null) {
            predicate =  criteriaBuilder.greaterThanOrEqualTo(root.get("money"), moneyMin);
        } else if (moneyMax != null) {
            predicate = criteriaBuilder.lessThanOrEqualTo(root.get("money"), moneyMax);
        } else {
            predicate = criteriaBuilder.conjunction();
        }
        return predicate;
    }
}
