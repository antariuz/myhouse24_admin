package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.Account;
import avada.media.myhouse24_admin.model.Flat;
import avada.media.myhouse24_admin.model.Invoice;
import avada.media.myhouse24_admin.model.Invoice.Status;
import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.model.request.InvoiceRequest;
import avada.media.myhouse24_admin.model.response.ResponseByPage;
import avada.media.myhouse24_admin.repo.AccountRepo;
import avada.media.myhouse24_admin.repo.FlatRepo;
import avada.media.myhouse24_admin.repo.InvoiceRepo;
import avada.media.myhouse24_admin.repo.InvoiceServiceRepo;
import avada.media.myhouse24_admin.repo.systemSettings.ServiceRepo;
import avada.media.myhouse24_admin.repo.systemSettings.TariffRepo;
import avada.media.myhouse24_admin.repo.systemSettings.UnitRepo;
import avada.media.myhouse24_admin.service.InvoiceService;
import avada.media.myhouse24_admin.service.TransactionService;
import avada.media.myhouse24_admin.spec.InvoiceSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepo invoiceRepo;
    private final InvoiceSpec invoiceSpec;
    private final FlatRepo flatRepo;
    private final TariffRepo tariffRepo;
    private final ServiceRepo serviceRepo;
    private final UnitRepo unitRepo;
    private final InvoiceServiceRepo invoiceServiceRepo;

    private final AccountRepo accountRepo;

    private final TransactionService transactionService;

    @Override
    @Transactional
    public ResponseByPage<InvoiceDTO> getAllInvoices(InvoiceRequest invoiceRequest) {
        Page<Invoice> invoices =
                invoiceRepo.findAll(invoiceSpec.getInvoices(invoiceRequest), PageRequest.of(invoiceRequest.getPageIndex() - 1, invoiceRequest.getPageSize()));
        ResponseByPage<InvoiceDTO> responseByPage = new ResponseByPage<>();
        responseByPage.setItemsCount(invoices.getTotalElements());
        responseByPage.setData(invoices.stream().map(invoice -> {
                    InvoiceDTO invoiceDTO = new InvoiceDTO();
                    invoiceDTO.setId(invoice.getId());
                    invoiceDTO.setUniqueNumber(invoice.getUniqueNumber());
                    invoiceDTO.setStatus(new StatusDTO(invoice.getStatus().name(), invoice.getStatus().getTitle()));
                    invoiceDTO.setRequestedDate(invoice.getRequestedDate());
                    FlatDTO flatDTO = new FlatDTO();
                    flatDTO.setId(invoice.getFlat().getId());
                    flatDTO.setNumber("№" + invoice.getFlat().getNumber());
                    flatDTO.setTitle(invoice.getFlat().getNumber(), invoice.getFlat().getBuilding().getTitle());
                    if (invoice.getFlat().getAccount() != null) {
                        AccountDTO accountDTO = new AccountDTO();
                        accountDTO.setId(invoice.getFlat().getAccount().getId());
                        accountDTO.setUniqueNumber(invoice.getFlat().getAccount().getUniqueNumber());
                        flatDTO.setAccount(accountDTO);
                    }
                    invoiceDTO.setFlat(flatDTO);
                    BuildingDTO buildingDTO = new BuildingDTO();
                    buildingDTO.setId(invoice.getFlat().getBuilding().getId());
                    buildingDTO.setTitle(invoice.getFlat().getBuilding().getTitle());
                    invoiceDTO.setBuilding(buildingDTO);
                    invoiceDTO.setSection(invoice.getFlat().getSection());
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(invoice.getFlat().getUser().getId());
                    Hibernate.initialize(invoice.getFlat().getUser().getProfile());
                    userDTO.setFullName(invoice.getFlat().getUser().getProfile().getLastname(), invoice.getFlat().getUser().getProfile().getFirstname(), invoice.getFlat().getUser().getProfile().getMiddleName());
                    invoiceDTO.setUser(userDTO);
                    invoiceDTO.setTariff(new TariffDTO(invoice.getTariff().getId(), invoice.getTariff().getName()));
                    invoiceDTO.setPeriodStart(invoice.getPeriodStart());
                    invoiceDTO.setPeriodEnd(invoice.getPeriodEnd());
                    invoiceDTO.setUsed(invoice.isUsed());
                    invoiceDTO.setInvoiceServices(invoice
                            .getInvoiceService()
                            .stream()
                            .map(invoiceService -> {
                                InvoiceServiceDTO invoiceServiceDTO = new InvoiceServiceDTO();
                                invoiceServiceDTO.setId(invoiceService.getId());
                                invoiceServiceDTO.setAmount(invoiceService.getAmount());
                                invoiceServiceDTO.setUnit(invoiceService.getUnit());
                                ServiceDTO serviceDTO = new ServiceDTO();
                                serviceDTO.setId(invoiceService.getService().getId());
                                serviceDTO.setName(invoiceService.getService().getName());
                                invoiceServiceDTO.setService(serviceDTO);
                                invoiceServiceDTO.setUnitPrice(invoiceService.getUnitPrice());
                                invoiceServiceDTO.setTotalPrice(invoiceService.getTotalPrice());
                                return invoiceServiceDTO;
                            })
                            .collect(Collectors.toList()));
                    return invoiceDTO;
                })
                .collect(Collectors.toList()));
        return responseByPage;
    }

    @Override
    public String getNewUniqueNumber() {
        Long INITIAL_UNIQUE_NUMBER = 12102200000L;
        Long lastId = invoiceRepo.getLastId();
        Long newId = (lastId != null ? lastId : 0) + 1L;
        return Long.toString(INITIAL_UNIQUE_NUMBER + newId);
    }

    @Override
    public boolean isUniqueNumberNotExists(Long id, String number) {
        if (id != null && number.equals(invoiceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found with ID: " + id))
                .getUniqueNumber())) {
            return true;
        } else return !invoiceRepo.existsByUniqueNumber(number);
    }

    @Override
    public List<StatusDTO> getAllStatus() {
        return Arrays.stream(Status.values())
                .map(status -> new StatusDTO(status.name(), status.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public void saveInvoice(InvoiceDTO invoiceDTO) {
        Invoice invoice = new Invoice();
        invoice.setUniqueNumber(invoiceDTO.getUniqueNumber());
        invoice.setRequestedDate(invoiceDTO.getRequestedDate());
        Flat flat = flatRepo.findById(invoiceDTO.getFlat().getId()).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + invoiceDTO.getFlat().getId()));
        invoice.setFlat(flat);
        if (flat.getUser() != null) invoice.setUser(flat.getUser());
        if (flat.getAccount() != null) invoice.setAccount(flat.getAccount());
        invoice.setStatus(Status.valueOf(invoiceDTO.getStatus().getValue()));
        invoice.setTariff(tariffRepo.findById(invoiceDTO.getTariff().getId()).orElseThrow(() -> new EntityNotFoundException("Tariff not found with ID: " + invoiceDTO.getTariff().getId())));
        invoice.setPeriodStart(invoiceDTO.getPeriodStart());
        invoice.setPeriodEnd(invoiceDTO.getPeriodEnd());
        invoice.setUsed(invoiceDTO.isUsed());
        if (!invoiceDTO.getInvoiceServices().isEmpty()) {
            List<Double> totalPrices = new ArrayList<>();
            invoice.getInvoiceService().addAll(invoiceDTO
                    .getInvoiceServices()
                    .stream()
                    .map(invoiceServiceDTO -> {
                        avada.media.myhouse24_admin.model.InvoiceService invoiceService = new avada.media.myhouse24_admin.model.InvoiceService();
                        invoiceService.setService(serviceRepo.findById(invoiceServiceDTO.getService().getId()).orElseThrow(() -> new EntityNotFoundException("Service not found with ID: " + invoiceServiceDTO.getService().getId())));
                        invoiceService.setUnit(unitRepo.findById(invoiceServiceDTO.getUnit().getId()).orElseThrow(() -> new EntityNotFoundException("Unit not found with ID: " + invoiceServiceDTO.getUnit().getId())));
                        invoiceService.setAmount(invoiceServiceDTO.getAmount());
                        invoiceService.setUnitPrice(invoiceServiceDTO.getUnitPrice());
                        Double totalPrice = invoiceServiceDTO.getAmount() * invoiceServiceDTO.getUnitPrice();
                        invoiceService.setTotalPrice(totalPrice);
                        invoiceServiceRepo.save(invoiceService);
                        totalPrices.add(totalPrice);
                        return invoiceService;
                    })
                    .collect(Collectors.toList()));
            if (flat.getAccount() != null) {
                Account account = flat.getAccount();
                account.setBalance(account.getBalance() - totalPrices.stream().mapToDouble(Double::doubleValue).sum());
                accountRepo.save(account);
            }
        }
        invoiceRepo.save(invoice);
        transactionService.sendDataByWebSocket();
    }

    @Override
    @Transactional
    public void updateInvoice(Long id, InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found with ID: " + id));
        invoice.setUniqueNumber(invoiceDTO.getUniqueNumber());
        invoice.setRequestedDate(invoiceDTO.getRequestedDate());
        Flat flat = flatRepo.findById(invoiceDTO.getFlat().getId()).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + invoiceDTO.getFlat().getId()));
        invoice.setFlat(flat);
        if (flat.getUser() != null) invoice.setUser(flat.getUser());
        if (flat.getAccount() != null) invoice.setAccount(flat.getAccount());
        invoice.setStatus(Status.valueOf(invoiceDTO.getStatus().getValue()));
        invoice.setTariff(tariffRepo.findById(invoiceDTO.getTariff().getId()).orElseThrow(() -> new EntityNotFoundException("Tariff not found with ID: " + invoiceDTO.getTariff().getId())));
        invoice.setPeriodStart(invoiceDTO.getPeriodStart());
        invoice.setPeriodEnd(invoiceDTO.getPeriodEnd());
        invoice.setUsed(invoiceDTO.isUsed());
        if (!invoiceDTO.getInvoiceServices().isEmpty()) {
            Hibernate.initialize(invoice.getInvoiceService());
            double prevTotalPrice = invoice.getInvoiceService().stream().map(avada.media.myhouse24_admin.model.InvoiceService::getTotalPrice).mapToDouble(Double::doubleValue).sum();
            List<Double> totalPrices = new ArrayList<>();
            invoice.setInvoiceService((invoiceDTO
                    .getInvoiceServices()
                    .stream()
                    .map(invoiceServiceDTO -> {
                        avada.media.myhouse24_admin.model.InvoiceService invoiceService = new avada.media.myhouse24_admin.model.InvoiceService();
                        invoiceService.setService(serviceRepo.findById(invoiceServiceDTO.getService().getId()).orElseThrow(() -> new EntityNotFoundException("Service not found with ID: " + invoiceServiceDTO.getService().getId())));
                        invoiceService.setUnit(unitRepo.findById(invoiceServiceDTO.getUnit().getId()).orElseThrow(() -> new EntityNotFoundException("Unit not found with ID: " + invoiceServiceDTO.getUnit().getId())));
                        invoiceService.setAmount(invoiceServiceDTO.getAmount());
                        invoiceService.setUnitPrice(invoiceServiceDTO.getUnitPrice());
                        Double totalPrice = invoiceServiceDTO.getAmount() * invoiceServiceDTO.getUnitPrice();
                        invoiceService.setTotalPrice(totalPrice);
                        invoiceServiceRepo.save(invoiceService);
                        totalPrices.add(totalPrice);
                        return invoiceService;
                    })
                    .collect(Collectors.toList())));
            if (flat.getAccount() != null) {
                Account account = flat.getAccount();
                account.setBalance(account.getBalance() - totalPrices.stream().mapToDouble(Double::doubleValue).sum() + prevTotalPrice);
                accountRepo.save(account);
            }
        }
        invoiceRepo.save(invoice);
        transactionService.sendDataByWebSocket();
    }

    @Override
    @Transactional
    public void deleteInvoice(Long id) {
        Invoice invoice = invoiceRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found with ID: " + id));
        if (invoice.getAccount() != null) {
            Account account = invoice.getAccount();
            Hibernate.initialize(invoice.getInvoiceService());
            Double prevTotalPrice = invoice.getInvoiceService().stream().mapToDouble(avada.media.myhouse24_admin.model.InvoiceService::getTotalPrice).sum();
            account.setBalance(account.getBalance() + prevTotalPrice);
            accountRepo.save(account);
        }
        invoiceRepo.deleteById(id);
        transactionService.sendDataByWebSocket();
    }

    @Override
    @Transactional
    public Map<String, List<Double>> getInvoicesSumByTypeByMonths() {
        Date lastDayOfPastYear = java.sql.Date.valueOf(LocalDate.ofYearDay(LocalDate.now().getYear(), 1).minusDays(1));
        List<Invoice> invoicesOfCurrentYear = invoiceRepo.getInvoicesByAccountNotNullAndRequestedDateAfter(lastDayOfPastYear);
        List<List<Invoice>> sortedInvoicesByMonths = sortInvoicesByMonths(invoicesOfCurrentYear);

        Map<String, List<Double>> invoicesSumByTypeByMonths = new HashMap<>();
        invoicesSumByTypeByMonths.put("positive", new ArrayList<>());
        invoicesSumByTypeByMonths.put("debt", new ArrayList<>());
        sortedInvoicesByMonths.forEach(monthInvoices -> {
            double monthPositiveSum = 0d;
            double monthDebtSum = 0d;
            for (Invoice invoice : monthInvoices) {
                if (invoice.getStatus() == Status.PAID) {
                    monthPositiveSum += invoice.getInvoiceService().stream().mapToDouble(avada.media.myhouse24_admin.model.InvoiceService::getTotalPrice).sum();
                } else {
                    monthDebtSum -= invoice.getInvoiceService().stream().mapToDouble(avada.media.myhouse24_admin.model.InvoiceService::getTotalPrice).sum();
                }
            }
            List<Double> positive = invoicesSumByTypeByMonths.get("positive");
            positive.add(monthPositiveSum);
            invoicesSumByTypeByMonths.put("positive", positive);
            List<Double> debt = invoicesSumByTypeByMonths.get("debt");
            debt.add(monthDebtSum);
            invoicesSumByTypeByMonths.put("debt", debt);
        });

        return invoicesSumByTypeByMonths;
    }

    private List<List<Invoice>> sortInvoicesByMonths(List<Invoice> invoices) {
        List<List<Invoice>> monthInvoices = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int month = i;
            List<Invoice> sortedInvoices = invoices.stream()
                    .filter(object -> {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(object.getRequestedDate());
                        return calendar.get(Calendar.MONTH) == month;
                    })
                    .collect(Collectors.toList());
            monthInvoices.add(sortedInvoices);
        }
        return monthInvoices;
    }

}
