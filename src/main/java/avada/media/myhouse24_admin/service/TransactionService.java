package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.TransactionDTO;
import avada.media.myhouse24_admin.model.dto.TypeDTO;
import avada.media.myhouse24_admin.model.request.TransactionRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;

import java.util.List;
import java.util.Map;

public interface TransactionService {

    ResponseByPage<TransactionDTO> getAllTransactions(TransactionRequest transactionRequest);

    String getNewUniqueNumber();

    boolean isUniqueNumberNotExists(Long id, String number);

    List<TypeDTO> getAllTypes();

    void saveTransaction(TransactionDTO transactionDTO);

    void updateTransaction(Long id, TransactionDTO transactionDTO);

    void deleteTransaction(Long id);

    Map<String, Double> getAllTransactionBalancesData();

    void sendDataByWebSocket();

    Map<String, List<Double>> getTransactionsBalancesByMonths();

}
