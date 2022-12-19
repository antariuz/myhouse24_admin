package avada.media.myhouse24_admin.spec;

import avada.media.myhouse24_admin.model.Flat;
import avada.media.myhouse24_admin.model.request.FlatRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class FlatSpec {

    private final String DEFAULT_SORT_FIELD = "id";

    public Specification<Flat> getFlats(FlatRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getNumber() != null && !request.getNumber().equals("")) {
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("number").as(String.class)),
                                "%" + request.getNumber().toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("building").get("title")),
                                "%" + request.getNumber().toLowerCase() + "%")
                ));
            }
            if (request.getBuilding() != null && request.getBuilding() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("building").get("id"), request.getBuilding()));
            }
            if (request.getSection() != null && !request.getSection().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("section").get("name"), request.getSection()));
            }
            if (request.getFloor() != null && !request.getFloor().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("floor").get("name"), request.getFloor()));
            }
            if (request.getUser() != null && request.getUser() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUser()));
            }
            if (request.getSortField() == null) query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
            else {
                switch (request.getSortField()) {
                    case "number":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("number")));
                        break;
                    case "building":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("building").get("id")));
                        break;
                    case "section":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("section").get("name")));
                        break;
                    case "floor":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("floor").get("name")));
                        break;
                    case "user":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("user").get("profile").get("lastname")));
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
