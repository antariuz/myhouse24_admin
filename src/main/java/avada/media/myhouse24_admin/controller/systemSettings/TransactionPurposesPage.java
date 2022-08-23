package avada.media.myhouse24_admin.controller.systemSettings;

import avada.media.myhouse24_admin.model.system.TransactionPurpose;
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
public class TransactionPurposesPage {

    private final TransactionPurposeRepo transactionPurposeRepo;

    @RequestMapping({"/", ""})
    public ModelAndView showTransactionPurposesPage() {
        return new ModelAndView("pages/system-settings/transaction-purposes");
    }

    @GetMapping("get-all")
    public @ResponseBody List<TransactionPurpose> getAllTransactionPurposes() {
        return transactionPurposeRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @PostMapping("add")
    private ResponseEntity<Void> addTransactionPurpose(@RequestBody TransactionPurpose transactionPurpose) {
        transactionPurpose.setUsed(false);
        transactionPurposeRepo.save(transactionPurpose);
        return ResponseEntity.ok().build();
    }

    @PostMapping("update")
    private ResponseEntity<Void> updateTransactionPurpose(TransactionPurpose transactionPurpose) {
        if (transactionPurpose.getId() != null) transactionPurposeRepo.save(transactionPurpose);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}/delete")
    private ResponseEntity<Void> deleteTransactionPurpose(@PathVariable Long id) {
        transactionPurposeRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
