package avada.media.myhouse24_admin.spec;

import avada.media.myhouse24_admin.model.Counter;
import avada.media.myhouse24_admin.model.request.CounterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CounterSpec {

    private final String DEFAULT_SORT_FIELD = "id";

    public Specification<Counter> getCounters(CounterRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getUniqueNumber() != null && !request.getUniqueNumber().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("uniqueNumber")),
                        "%" + request.getUniqueNumber().toLowerCase() + "%"));
            }
            if (request.getRequestedDate() != null && !request.getRequestedDate().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("requestedDate").as(LocalDate.class), LocalDate.parse(request.getRequestedDate())));
            }
            if (request.getBuilding() != null && request.getBuilding() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("building").get("id"), request.getBuilding()));
            }
            if (request.getSection() != null && !request.getSection().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("section").get("name"), request.getSection()));
            }
            if (request.getFlat() != null && request.getFlat() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("flat").get("id"), request.getFlat()));
            }
            if (request.getService() != null && request.getService() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("service").get("id"), request.getService()));
            }
            if (request.getSortField() == null) query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
            else {
                switch (request.getSortField()) {
                    case "requestedDate":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("requestedDate")) : criteriaBuilder.desc(root.get("requestedDate")));
                        break;
                    default:
                        query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
                        break;
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };

    }
}
