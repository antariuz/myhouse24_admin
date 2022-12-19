package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.dto.TransactionPurposeDTO;
import avada.media.myhouse24_admin.repo.systemSettings.TransactionPurposeRepo;
import avada.media.myhouse24_admin.service.TransactionPurposeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionPurposeServiceImpl implements TransactionPurposeService {

    private final TransactionPurposeRepo transactionPurposeRepo;

    @Override
    public List<TransactionPurposeDTO> getAllTransactionPurposes() {
        return transactionPurposeRepo
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(transactionPurpose -> new TransactionPurposeDTO(transactionPurpose.getId(), transactionPurpose.getName()))
                .collect(Collectors.toList());
    }

}
