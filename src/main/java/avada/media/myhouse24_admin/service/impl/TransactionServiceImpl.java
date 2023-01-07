package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.Account;
import avada.media.myhouse24_admin.model.Transaction;
import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.model.request.TransactionRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import avada.media.myhouse24_admin.repo.AccountRepo;
import avada.media.myhouse24_admin.repo.StaffRepo;
import avada.media.myhouse24_admin.repo.TransactionRepo;
import avada.media.myhouse24_admin.repo.UserRepo;
import avada.media.myhouse24_admin.repo.systemSettings.TransactionPurposeRepo;
import avada.media.myhouse24_admin.service.AccountService;
import avada.media.myhouse24_admin.service.TransactionService;
import avada.media.myhouse24_admin.spec.TransactionSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static avada.media.myhouse24_admin.model.Transaction.Type;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final TransactionSpec transactionSpec;
    private final UserRepo userRepo;
    private final TransactionPurposeRepo transactionPurposeRepo;
    private final AccountService accountService;
    private final AccountRepo accountRepo;
    private final StaffRepo staffRepo;
    private final SimpMessagingTemplate template;

    @Override
    @Transactional
    public ResponseByPage<TransactionDTO> getAllTransactions(TransactionRequest transactionRequest) {
        Page<Transaction> transactionPage = transactionRepo.findAll(transactionSpec.getTransactions(transactionRequest), PageRequest.of(transactionRequest.getPageIndex() - 1, transactionRequest.getPageSize()));
        ResponseByPage<TransactionDTO> transactionsByPageDTO = new ResponseByPage<>();
        transactionsByPageDTO.setItemsCount(transactionPage.getTotalElements());
        for (Transaction transaction : transactionPage) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setId(transaction.getId());
            transactionDTO.setType(new TypeDTO(transaction.getType().name(), transaction.getType().getTitle()));
            transactionDTO.setUniqueNumber(transaction.getUniqueNumber());
            transactionDTO.setRequestedDate(transaction.getRequestedDate());
            transactionDTO.setUsed(transaction.isUsed());
            if (transaction.getTransactionPurpose() != null) {
                TransactionPurposeDTO transactionPurposeDTO = new TransactionPurposeDTO();
                transactionPurposeDTO.setId(transaction.getTransactionPurpose().getId());
                transactionPurposeDTO.setName(transaction.getTransactionPurpose().getName());
                transactionDTO.setTransactionPurpose(transactionPurposeDTO);
            }
            if (transaction.getUser() != null) {
                Hibernate.initialize(transaction.getUser().getProfile());
                UserDTO userDTO = new UserDTO();
                userDTO.setId(transaction.getUser().getId());
                userDTO.setFullName(transaction.getUser().getProfile().getLastname(), transaction.getUser().getProfile().getFirstname(), transaction.getUser().getProfile().getMiddleName());
                transactionDTO.setUser(userDTO);
            }
            if (transaction.getAccount() != null) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setId(transaction.getAccount().getId());
                accountDTO.setUniqueNumber(transaction.getAccount().getUniqueNumber());
                transactionDTO.setAccount(accountDTO);
            }
            if (transaction.getStaff() != null) {
                StaffDTO staffDTO = new StaffDTO();
                staffDTO.setId(transaction.getStaff().getId());
                staffDTO.setFullName(transaction.getStaff().getLastname(), transaction.getStaff().getFirstname());
                transactionDTO.setStaff(staffDTO);
            }
            transactionDTO.setAmount(transaction.getAmount());
            transactionDTO.setComment(transaction.getComment());
            transactionsByPageDTO.getData().add(transactionDTO);
        }
        return transactionsByPageDTO;
    }

    @Override
    public String getNewUniqueNumber() {
        Long INITIAL_UNIQUE_NUMBER = 5102200000L;
        Long lastId = transactionRepo.getLastId();
        Long newId = (lastId != null ? lastId : 0) + 1L;
        return Long.toString(INITIAL_UNIQUE_NUMBER + newId);
    }

    @Override
    public boolean isUniqueNumberNotExists(Long id, String number) {
        if (id != null && number.equals(transactionRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id)).getUniqueNumber())) {
            return true;
        } else return !transactionRepo.existsByUniqueNumber(number);
    }

    @Override
    public List<TypeDTO> getAllTypes() {
        List<TypeDTO> typeDTOList = new ArrayList<>();
        for (Type type : Type.values()) {
            typeDTOList.add(new TypeDTO(type.name(), type.getTitle()));
        }
        return typeDTOList;
    }

    @Override
    public void saveTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setType(Type.valueOf(transactionDTO.getType().getValue()));
        transaction.setUniqueNumber(transactionDTO.getUniqueNumber());
        transaction.setRequestedDate(transactionDTO.getRequestedDate());
        if (transactionDTO.getUser() != null)
            transaction.setUser(userRepo.findById(transactionDTO.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + transactionDTO.getUser().getId())));
        if (transactionDTO.getStaff() != null)
            transaction.setStaff(staffRepo.findById(transactionDTO.getStaff().getId()).orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + transactionDTO.getStaff().getId())));
        if (transactionDTO.getAccount() != null) {
            Account account = accountRepo.findById(transactionDTO.getAccount().getId()).orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + transactionDTO.getAccount().getId()));
            transaction.setAccount(account);
            if (transactionDTO.isUsed()) {
                account.setBalance(account.getBalance() + transactionDTO.getAmount());
                accountRepo.save(account);
            }
        }
        transaction.setUsed(transactionDTO.isUsed());
        if (transactionDTO.getTransactionPurpose() != null)
            transaction.setTransactionPurpose(transactionPurposeRepo.findById(transactionDTO.getTransactionPurpose().getId()).orElseThrow(() -> new EntityNotFoundException("Transaction Purpose not found with ID: " + transactionDTO.getTransactionPurpose().getId())));
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setComment(transactionDTO.getComment());
        transactionRepo.save(transaction);
        sendDataByWebSocket();
        template.convertAndSend("/data/new-transaction", transaction.getId());
    }

    @Override
    public void sendDataByWebSocket() {
        Map<String, Double> transactionsBalanceData = getAllTransactionBalancesData();
        template.convertAndSend("/data/transaction-amounts-sum-IN", transactionsBalanceData.get("transactionAmountsSumByInType"));
        template.convertAndSend("/data/transaction-amounts-sum-OUT", transactionsBalanceData.get("transactionAmountsSumByOutType"));
        template.convertAndSend("/data/transactions-balance", transactionsBalanceData.get("transactionAmountsSumByInType") - transactionsBalanceData.get("transactionAmountsSumByOutType"));
        template.convertAndSend("/data/accounts-balance", accountService.getAccountsBalance());
        template.convertAndSend("/data/accounts-debt", accountService.getAccountsDebt());
    }

    @Override
    public void updateTransaction(Long id, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
        transaction.setType(Type.valueOf(transactionDTO.getType().getValue()));
        transaction.setUniqueNumber(transactionDTO.getUniqueNumber());
        transaction.setRequestedDate(transactionDTO.getRequestedDate());
        if (transactionDTO.getUser() != null)
            transaction.setUser(userRepo.findById(transactionDTO.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + transactionDTO.getUser().getId())));
        if (transactionDTO.getStaff() != null)
            transaction.setStaff(staffRepo.findById(transactionDTO.getStaff().getId()).orElseThrow(() -> new EntityNotFoundException("Staff not found with ID: " + transactionDTO.getStaff().getId())));
        if (transactionDTO.getAccount() != null) {
            Account account = accountRepo.findById(transactionDTO.getAccount().getId()).orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + transactionDTO.getAccount().getId()));
            transaction.setAccount(account);
            if (transactionDTO.isUsed()) {
                account.setBalance(account.getBalance() + transactionDTO.getAmount() - transaction.getAmount());
                accountRepo.save(account);
            }
        }
        transaction.setUsed(transactionDTO.isUsed());
        if (transactionDTO.getTransactionPurpose() != null)
            transaction.setTransactionPurpose(transactionPurposeRepo.findById(transactionDTO.getTransactionPurpose().getId()).orElseThrow(() -> new EntityNotFoundException("Transaction Purpose not found with ID: " + transactionDTO.getTransactionPurpose().getId())));
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setComment(transactionDTO.getComment());
        transactionRepo.save(transaction);
        sendDataByWebSocket();
        template.convertAndSend("/data/updated-transaction", id);
    }

    @Override
    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));
        if (transaction.getAccount() != null && transaction.isUsed()) {
            Account account = transaction.getAccount();
            account.setBalance(account.getBalance() - transaction.getAmount());
            accountRepo.save(account);
        }
        transactionRepo.deleteById(id);
        template.convertAndSend("/data/deleted-transaction", "id");
        sendDataByWebSocket();
    }

    @Override
    public Map<String, Double> getAllTransactionBalancesData() {
        Map<String, Double> transactionsBalances = new HashMap<>();
        transactionsBalances.put("transactionAmountsSumByInType", transactionRepo.getAmountsByType(Type.IN.name()).stream().mapToDouble(Double::doubleValue).sum());
        transactionsBalances.put("transactionAmountsSumByOutType", transactionRepo.getAmountsByType(Type.OUT.name()).stream().mapToDouble(Double::doubleValue).sum());
        return transactionsBalances;
    }

    @Override
    public Map<String, List<Double>> getTransactionsBalancesByMonths() {
        Map<String, List<Double>> transactionsBalances = new HashMap<>();
        Date lastDayOfPastYear = java.sql.Date.valueOf(LocalDate.ofYearDay(LocalDate.now().getYear(), 1).minusDays(1));
        List<Transaction> incomingTransactionsOfCurrentYear = transactionRepo.getAllTransactionsByTypeAndRequestedDateAfter(Type.IN, lastDayOfPastYear);
        List<Transaction> expenseTransactionsOfCurrentYear = transactionRepo.getAllTransactionsByTypeAndRequestedDateAfter(Type.OUT, lastDayOfPastYear);
        List<List<Transaction>> sortedIncomingTransactionsByMonths = sortTransactionsByMonths(incomingTransactionsOfCurrentYear);
        List<List<Transaction>> sortedExpenseTransactionsByMonths = sortTransactionsByMonths(expenseTransactionsOfCurrentYear);
        transactionsBalances.put("Приход", TransactionsBalancesSumByMonths(sortedIncomingTransactionsByMonths));
        transactionsBalances.put("Расход", TransactionsBalancesSumByMonths(sortedExpenseTransactionsByMonths));
        return transactionsBalances;
    }

    protected List<List<Transaction>> sortTransactionsByMonths(List<Transaction> transactions) {
        List<List<Transaction>> monthTransactions = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int month = i;
            List<Transaction> sortedTransactions = transactions.stream()
                    .filter(transaction -> {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(transaction.getRequestedDate());
                        return calendar.get(Calendar.MONTH) == month;
                    })
                    .collect(Collectors.toList());
            monthTransactions.add(sortedTransactions);
        }
        return monthTransactions;
    }

    private List<Double> TransactionsBalancesSumByMonths(List<List<Transaction>> sortTransactionsByMonths) {
        return sortTransactionsByMonths
                .stream()
                .map(transactions -> transactions
                        .stream()
                        .mapToDouble(Transaction::getAmount).sum())
                .collect(Collectors.toList());
    }

}
