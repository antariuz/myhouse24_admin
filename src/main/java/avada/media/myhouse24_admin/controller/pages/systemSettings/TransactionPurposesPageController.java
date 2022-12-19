package avada.media.myhouse24_admin.controller.pages.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.pages.TransactionPurpose;
import avada.media.myhouse24_admin.repo.systemSettings.TransactionPurposeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("system-settings/transaction-purposes")
public class TransactionPurposesPageController {

    private final TransactionPurposeRepo transactionPurposeRepo;

    @RequestMapping({"/", ""})
    public ModelAndView showTransactionPurposesPage() {
        return new ModelAndView("pages/system-settings/transaction-purposes");
    }

    @GetMapping("get-all")
    public @ResponseBody List<TransactionPurpose> getAllTransactionPurposes() {
        return transactionPurposeRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @GetMapping("get-all-by-type")
    public @ResponseBody List<TransactionPurpose> getAllTransactionPurposes(String type) {
        return transactionPurposeRepo.getTransactionPurposesByType(TransactionPurpose.Type.valueOf(type.toUpperCase()), Sort.by(Sort.Direction.ASC, "id"));
    }

    @PostMapping("add")
    public ResponseEntity<Void> addTransactionPurpose(TransactionPurpose transactionPurpose) {
        transactionPurpose.setUsed(false);
        transactionPurposeRepo.save(transactionPurpose);
        return ResponseEntity.ok().build();
    }

    @PostMapping("update")
    public ResponseEntity<Void> updateTransactionPurpose(TransactionPurpose transactionPurpose) {
        transactionPurposeRepo.save(transactionPurpose);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<Void> deleteTransactionPurpose(@PathVariable Long id) {
        transactionPurposeRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
