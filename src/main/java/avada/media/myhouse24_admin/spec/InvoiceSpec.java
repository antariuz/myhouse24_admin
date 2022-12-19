package avada.media.myhouse24_admin.spec;

import avada.media.myhouse24_admin.model.Invoice;
import avada.media.myhouse24_admin.model.Invoice.Status;
import avada.media.myhouse24_admin.model.request.InvoiceRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InvoiceSpec {

    private final String DEFAULT_SORT_FIELD = "id";

    public Specification<Invoice> getInvoices(InvoiceRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getUniqueNumber() != null && !request.getUniqueNumber().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("uniqueNumber")),
                        "%" + request.getUniqueNumber().toLowerCase() + "%"));
            }
            if (request.getStatus() != null && !request.getStatus().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("status"), Status.valueOf(request.getStatus())));
            }
            if (request.getRequestedDate() != null && !request.getRequestedDate().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("requestedDate").as(LocalDate.class), LocalDate.parse(request.getRequestedDate())));
            }
            if (request.getRequestedMonth() != null && !request.getRequestedMonth().equals("")) {
                Integer month = Integer.parseInt(request.getRequestedMonth().substring(5, 7));
                predicates.add(criteriaBuilder.equal(criteriaBuilder.function("month", Integer.class, root.get("requestedDate")), month));
            }
            if (request.getFlat() != null && request.getFlat() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("flat").get("id"), request.getFlat()));
            }
            if (request.getUser() != null && request.getUser() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUser()));
            }
            if (request.getUsed() != null && !request.getUsed().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("used"), request.getUsed().equals("true")));
            }
            if (request.getSortField() == null) query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
            else {
                switch (request.getSortField()) {
                    case "requestedDate":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("requestedDate")) : criteriaBuilder.desc(root.get("requestedDate")));
                        break;
                    case "requestedMonth":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("requestedMonth")) : criteriaBuilder.desc(root.get("requestedMonth")));
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
