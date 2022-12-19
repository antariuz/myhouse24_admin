package avada.media.myhouse24_admin.spec;

import avada.media.myhouse24_admin.model.MasterRequest;
import avada.media.myhouse24_admin.model.request.MasterRequestRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class MasterRequestSpec {

    private final String DEFAULT_SORT_FIELD = "id";

    public Specification<MasterRequest> getMasterRequests(MasterRequestRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getId() != null && request.getId() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("id"), request.getId()));
            }
            if (request.getRequestedDate() != null && !request.getRequestedDate().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("requestedDate").as(LocalDate.class), LocalDate.parse(request.getRequestedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            }
            if (request.getRole() != null && request.getRole() != 0) {
                if (request.getRole() == -1) predicates.add(root.get("role").isNull());
                else predicates.add(criteriaBuilder.equal(root.get("role").get("id"), request.getRole()));
            }
            if (request.getDescription() != null && !request.getDescription().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                        "%" + request.getDescription().toLowerCase() + "%"));
            }
            if (request.getFlat() != null && request.getFlat() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("flat").get("id"), request.getFlat()));
            }
            if (request.getUser() != null && request.getUser() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUser()));
            }
            if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("profile").get("phoneNumber")),
                        "%" + request.getPhoneNumber().toLowerCase() + "%"));
            }
            if (request.getStaff() != null && request.getStaff() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("staff").get("id"), request.getStaff()));
            }
            if (request.getStatus() != null && !request.getStatus().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("status"), MasterRequest.Status.valueOf(request.getStatus())));
            }
            if (request.getSortField() == null) query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
            else {
                switch (request.getSortField()) {
                    case "id":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
                        break;
                    case "requestedDate":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("requestedDate")) : criteriaBuilder.desc(root.get("requestedDate")));
                        break;
                    case "role":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("role").get("id")) : criteriaBuilder.desc(root.get("role").get("id")));
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
