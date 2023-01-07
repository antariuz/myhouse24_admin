package avada.media.myhouse24_admin.controller.pages;

import avada.media.myhouse24_admin.model.dto.TransactionDTO;
import avada.media.myhouse24_admin.model.dto.TransactionPurposeDTO;
import avada.media.myhouse24_admin.model.dto.TypeDTO;
import avada.media.myhouse24_admin.model.request.TransactionRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import avada.media.myhouse24_admin.service.AccountService;
import avada.media.myhouse24_admin.service.ExcelGeneratorService;
import avada.media.myhouse24_admin.service.TransactionPurposeService;
import avada.media.myhouse24_admin.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("transactions")
public class TransactionsPageController {

    private final TransactionService transactionService;
    private final TransactionPurposeService transactionPurposeService;
    private final AccountService accountService;
    private final ExcelGeneratorService excelGeneratorService;

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
    public @ResponseBody Map<String, Double> getAllTransactionBalancesData() {
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

    @GetMapping("export-transactions-to-excel")
    public ResponseEntity<Resource> exportTransactionsToExcel(TransactionRequest transactionRequest) {
        InputStreamResource file = excelGeneratorService.transactionsToExcel(transactionRequest);
        String filename = "transactions" + new SimpleDateFormat("-ddMMyyyyHHmm").format(new Date()) + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @GetMapping("export-transaction-to-excel")
    public ResponseEntity<Resource> exportTransactionToExcel(Long id) {
        Map<String, InputStreamResource> transactionData = excelGeneratorService.transactionToExcel(id);
        String transactionUniqueNumber = transactionData.keySet().stream().findFirst().orElseThrow(() -> new EntityNotFoundException("There is no such transaction with ID:" + id));
        InputStreamResource file = null;
        if (transactionUniqueNumber != null) file = transactionData.get(transactionUniqueNumber);
        String filename = "transaction-" + transactionUniqueNumber + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

}
