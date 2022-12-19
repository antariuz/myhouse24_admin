package avada.media.myhouse24_admin.spec;

import avada.media.myhouse24_admin.model.Building;
import avada.media.myhouse24_admin.model.request.BuildingRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BuildingSpec {

    private final String DEFAULT_SORT_FIELD = "id";

    public Specification<Building> getBuildings(BuildingRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getTitle() != null && !request.getTitle().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                        "%" + request.getTitle().toLowerCase() + "%"));
            }
            if (request.getAddress() != null && !request.getAddress().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")),
                        "%" + request.getAddress().toLowerCase() + "%"));
            }
            if (request.getSortField() == null) query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
            else {
                switch (request.getSortField()) {
                    case "title":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("profile").get("lastname")) : criteriaBuilder.desc(root.get("profile").get("lastname")));
                        break;
                    case "address":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("createdAt")) : criteriaBuilder.desc(root.get("createdAt")));
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
