package avada.media.myhouse24_admin.spec;

import avada.media.myhouse24_admin.model.Account;
import avada.media.myhouse24_admin.model.request.AccountRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountSpec {

    private final String DEFAULT_SORT_FIELD = "id";

    public Specification<Account> getAccounts(AccountRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getUniqueNumber() != null && !request.getUniqueNumber().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("uniqueNumber")),
                        "%" + request.getUniqueNumber().toLowerCase() + "%"));
            }
            if (request.getStatus() != null && !request.getStatus().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("status"), Account.Status.valueOf(request.getStatus())));
            }
            if (request.getFlat() != null && request.getFlat() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("flat").get("id"), request.getFlat()));
            }
            if (request.getBuilding() != null && request.getBuilding() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("building").get("id"), request.getBuilding()));
            }
            if (request.getSection() != null && !request.getSection().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("section").get("name"), request.getSection()));
            }
            if (request.getUser() != null && request.getUser() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUser()));
            }
            query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };

    }
}
