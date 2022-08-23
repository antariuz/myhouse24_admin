package avada.media.myhouse24_admin.controller.systemSettings;

import avada.media.myhouse24_admin.model.systemSettings.PaymentDetails;
import avada.media.myhouse24_admin.repo.systemSettings.PaymentDetailsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("system-settings/payment-details")
public class PaymentDetailsPage {

    private final PaymentDetailsRepo paymentDetailsRepo;

    @RequestMapping({"/", ""})
    public ModelAndView showPaymentDetailsPage() {
        if (paymentDetailsRepo.findById(1L).isEmpty()) paymentDetailsRepo.save(new PaymentDetails());
        return new ModelAndView("pages/system-settings/payment-details");
    }

    @GetMapping("get")
    public @ResponseBody PaymentDetails getPaymentDetails() {
        return paymentDetailsRepo.findById(1L).get();
    }

    @PostMapping("update")
    private ResponseEntity<Void> updatePaymentDetails(PaymentDetails paymentDetails) {
        paymentDetailsRepo.save(paymentDetails);
        return ResponseEntity.ok().build();
    }

}
