package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.InvoiceDTO;
import avada.media.myhouse24_admin.model.dto.StatusDTO;
import avada.media.myhouse24_admin.model.request.InvoiceRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;

import java.util.List;
import java.util.Map;

public interface InvoiceService {

    ResponseByPage<InvoiceDTO> getAllInvoices(InvoiceRequest invoiceRequest);

    String getNewUniqueNumber();

    boolean isUniqueNumberNotExists(Long id, String uniqueNumber);

    List<StatusDTO> getAllStatus();

    void saveInvoice(InvoiceDTO invoiceDTO);

    void updateInvoice(Long id, InvoiceDTO invoiceDTO);

    void deleteInvoice(Long id);

    Map<String, List<Double>> getInvoicesSumByTypeByMonths();

}
