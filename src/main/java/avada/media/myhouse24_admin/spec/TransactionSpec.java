package avada.media.myhouse24_admin.spec;

import avada.media.myhouse24_admin.model.Transaction;
import avada.media.myhouse24_admin.model.request.TransactionRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static avada.media.myhouse24_admin.model.Transaction.Type;

@Component
public class TransactionSpec {

    private final String DEFAULT_SORT_FIELD = "id";

    public Specification<Transaction> getTransactions(TransactionRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getUniqueNumber() != null && !request.getUniqueNumber().equals("")) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("uniqueNumber")),
                        "%" + request.getUniqueNumber().toLowerCase() + "%"));
            }
            if (request.getRequestedDate() != null && !request.getRequestedDate().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("requestedDate").as(LocalDate.class), LocalDate.parse(request.getRequestedDate())));
            }
            if (request.getUsed() != null && !request.getUsed().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("used"), request.getUsed().equals("true")));
            }
            if (request.getTransactionPurpose() != null && request.getTransactionPurpose() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("transactionPurpose").get("id"), request.getTransactionPurpose()));
            }
            if (request.getUser() != null && request.getUser() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), request.getUser()));
            }
            if (request.getAccount() != null && request.getAccount() > 0) {
                predicates.add(criteriaBuilder.equal(root.get("account").get("id"), request.getAccount()));
            }
            if (request.getType() != null && !request.getType().equals("")) {
                predicates.add(criteriaBuilder.equal(root.get("type"), Type.valueOf(request.getType())));
            }
            if (request.getSortField() == null) query.orderBy(criteriaBuilder.desc(root.get(DEFAULT_SORT_FIELD)));
            else {
                switch (request.getSortField()) {
                    case "date":
                        query.orderBy(request.getSortOrder().equals("asc") ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
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
