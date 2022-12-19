package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.ResponseByPage;
import avada.media.myhouse24_admin.model.dto.TransactionDTO;
import avada.media.myhouse24_admin.model.dto.TypeDTO;
import avada.media.myhouse24_admin.model.request.TransactionRequest;

import java.util.HashMap;
import java.util.List;

public interface TransactionService {

    ResponseByPage<TransactionDTO> getAllTransactions(TransactionRequest transactionRequest);

    String getNewUniqueNumber();

    boolean isUniqueNumberNotExists(Long id, String number);

    List<TypeDTO> getAllTypes();

    void saveTransaction(TransactionDTO transactionDTO);

    void updateTransaction(Long id, TransactionDTO transactionDTO);

    void deleteTransaction(Long id);

    HashMap<String, Double> getAllTransactionBalancesData();

    void sendDataByWebSocket();

}
