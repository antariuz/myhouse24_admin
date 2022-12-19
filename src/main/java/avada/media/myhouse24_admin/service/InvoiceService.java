package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.InvoiceDTO;
import avada.media.myhouse24_admin.model.dto.ResponseByPage;
import avada.media.myhouse24_admin.model.dto.StatusDTO;
import avada.media.myhouse24_admin.model.request.InvoiceRequest;

import java.util.List;

public interface InvoiceService {

    ResponseByPage<InvoiceDTO> getAllInvoices(InvoiceRequest invoiceRequest);

    String getNewUniqueNumber();

    boolean isUniqueNumberNotExists(Long id, String uniqueNumber);

    List<StatusDTO> getAllStatus();

    void saveInvoice(InvoiceDTO invoiceDTO);

    void updateInvoice(Long id, InvoiceDTO invoiceDTO);

    void deleteInvoice(Long id);

}
