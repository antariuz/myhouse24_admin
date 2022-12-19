package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.dto.ResponseByPage;
import avada.media.myhouse24_admin.model.dto.TransactionDTO;
import avada.media.myhouse24_admin.model.dto.TransactionPurposeDTO;
import avada.media.myhouse24_admin.model.dto.TypeDTO;
import avada.media.myhouse24_admin.model.request.TransactionRequest;
import avada.media.myhouse24_admin.service.AccountService;
import avada.media.myhouse24_admin.service.TransactionPurposeService;
import avada.media.myhouse24_admin.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("transactions")
public class TransactionsPageController {

    private final TransactionService transactionService;
    private final TransactionPurposeService transactionPurposeService;
    private final AccountService accountService;

    @GetMapping({"/", ""})
    public ModelAndView showTransactionsPage() {
        return new ModelAndView("pages/transactions");
    }

    @GetMapping("get-all-transactions")
    public @ResponseBody ResponseByPage<TransactionDTO> getAllTransactions(TransactionRequest transactionRequest) {
        return transactionService.getAllTransactions(transactionRequest);
    }

    @GetMapping("get-new-unique-number")
    public @ResponseBody String getNewUniqueNumber() {
        return transactionService.getNewUniqueNumber();
    }

    @PostMapping("is-unique-number-not-exists")
    public @ResponseBody boolean isUniqueNumberNotExists(@RequestParam(required = false) Long id, String uniqueNumber) {
        return transactionService.isUniqueNumberNotExists(id, uniqueNumber);
    }

    @GetMapping("get-all-types")
    public @ResponseBody List<TypeDTO> getAllTypes() {
        return transactionService.getAllTypes();
    }

    @GetMapping("get-all-transaction-purposes")
    public @ResponseBody List<TransactionPurposeDTO> getAllTransactionPurposes() {
        return transactionPurposeService.getAllTransactionPurposes();
    }

    @GetMapping("get-all-transaction-balances-data")
    public @ResponseBody HashMap<String, Double> getAllTransactionBalancesData() {
        return transactionService.getAllTransactionBalancesData();
    }

    @GetMapping("get-all-accounts-balance")
    public @ResponseBody Double getAllAccountsBalance() {
        return accountService.getAccountsBalance();
    }

    @GetMapping("get-all-accounts-debt")
    public @ResponseBody Double getAllAccountsDebt() {
        return accountService.getAccountsDebt();
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.saveTransaction(transactionDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/update")
    public ResponseEntity<Void> updateTransaction(@PathVariable Long id,
                                                  @RequestBody TransactionDTO transactionDTO) {
        transactionService.updateTransaction(id, transactionDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok().build();
    }

}
