package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.request.TransactionRequest;
import org.springframework.core.io.InputStreamResource;

import java.util.Map;

public interface ExcelGeneratorService {

    InputStreamResource transactionsToExcel(TransactionRequest transactionRequest);

    Map<String, InputStreamResource> transactionToExcel(Long id);

}
