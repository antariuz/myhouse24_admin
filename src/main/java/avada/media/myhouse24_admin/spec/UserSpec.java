package avada.media.myhouse24_admin.spec;

import avada.media.myhouse24_admin.model.Status;
import avada.media.myhouse24_admin.model.User;
import avada.media.myhouse24_admin.model.request.UserRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserSpec {

    private final String DEFAULT_SORT_FIELD = "id";

    public Specification<User> getUsers(UserRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getUniqueId() != null && !request.getUniqueId().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("uniqueId")),
                        "%" + request.getUniqueId().toLowerCase() + "%"));
            }
            if (request.getFullName() != null && !request.getFullName().equals("")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("profile").get("firstname")),
                                "%" + request.getFullName().toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("profile").get("middleName")),
                                "%" + request.getFullName().toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("profile").get("lastname")),
                                "%" + request.getFullName().toLowerCase() + "%")
                ));
            }
            if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("profile").get("phoneNumber")),
                        "%" + request.getPhoneNumber().toLowerCase() + "%"));
            }
            if (request.getEmail() != null && !request.getEmail().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")),
                        "%" + request.getEmail().toLowerCase() + "%"));
            }
            if (request.getBuilding() != null && request.getBuilding() > 0) {
                predicates.add(criteriaBuilder.equal(root.joinList("flats").get("building").get("id"), request.getBuilding()));
            }
            if (request.getFlat() != null && request.getFlat() > 0) {
                predicates.add(criteriaBuilder.equal(root.joinList("flats").get("id"), request.getFlat()));
            }
            if (request.getCreatedAt() != null && !request.getCreatedAt().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("createdAt").as(LocalDate.class), LocalDate.parse(request.getCreatedAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            }
            if (request.getStatus() != null && !request.getStatus().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("status"), Status.valueOf(request.getStatus())));
            }
            if (request.getHasDebt() != null && request.getHasDebt()) {
                predicates.add(criteriaBuilder.equal(root.get("hasDebt"), request.getHasDebt()));
            }
            if (request.getSortField() == null) query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
            else {
                switch (request.getSortField()) {
                    case "fullName":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("profile").get("lastname")) : criteriaBuilder.desc(root.get("profile").get("lastname")));
                        break;
                    case "createdAt":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")));
                        break;
                    default:
                        query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
                        break;
                }
            }
            query.distinct(true);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };

    }
}
