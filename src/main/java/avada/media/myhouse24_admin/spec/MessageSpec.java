package avada.media.myhouse24_admin.spec;

import avada.media.myhouse24_admin.model.Message;
import avada.media.myhouse24_admin.model.request.MessageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageSpec {

    private final String DEFAULT_SORT_FIELD = "id";

    public Specification<Message> getMessages(MessageRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getSubject() != null && !request.getSubject().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("subject")), "%" + request.getSubject().toLowerCase() + "%"));
            }
            query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
