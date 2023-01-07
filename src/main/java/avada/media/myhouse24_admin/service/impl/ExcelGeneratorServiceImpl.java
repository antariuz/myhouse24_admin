package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.Transaction;
import avada.media.myhouse24_admin.model.dto.UserDTO;
import avada.media.myhouse24_admin.model.request.TransactionRequest;
import avada.media.myhouse24_admin.repo.TransactionRepo;
import avada.media.myhouse24_admin.spec.TransactionSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExcelGeneratorServiceImpl extends ExcelGeneratorAbstract {

    private final TransactionRepo transactionRepo;
    private final TransactionSpec transactionSpec;

    @Override
    @Transactional
    public InputStreamResource transactionsToExcel(TransactionRequest transactionRequest) {

        final String[] HEADERs = {"№", "Дата", "Приход/расход", "Статус", "Статья",
                "Сумма", "Валюта", "Владелец квартиры", "Лицевой счет"};
        final String SHEET = "Transactions";
        final int INITIAL_HEADER_INDEX_OF_ROW = 0;
        int INITIAL_DATA_INDEX_OF_ROW = INITIAL_HEADER_INDEX_OF_ROW + 1;

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            setWorkbookAuthor(workbook);
            Sheet sheet = workbook.createSheet(SHEET);
            setHeaderRow(workbook, sheet, HEADERs, INITIAL_HEADER_INDEX_OF_ROW);

            //  Write data to rows
            Page<Transaction> transactions =
                    transactionRepo.findAll(transactionSpec.getTransactions(transactionRequest),
                            PageRequest.of(transactionRequest.getPageIndex() - 1, transactionRequest.getPageSize()));

            for (Transaction transaction : transactions) {
                Row row = createInitialRows(workbook, sheet, HEADERs.length, INITIAL_DATA_INDEX_OF_ROW++);
                row.getCell(0).setCellValue(transaction.getUniqueNumber());
                row.getCell(1).setCellValue(new SimpleDateFormat("dd.MM.yyyy").format(transaction.getRequestedDate()));
                row.getCell(2).setCellValue(transaction.getType().getTitle());
                row.getCell(3).setCellValue(transaction.isUsed() ? "Проведен" : "Не проведен");
                row.getCell(4).setCellValue(transaction.getTransactionPurpose().getName());
                row.getCell(5).setCellValue(transaction.getAmount());
                row.getCell(6).setCellValue("грн");
                if (transaction.getUser() != null) {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(transaction.getUser().getId());
                    userDTO.setFullName(transaction.getUser().getProfile().getLastname(), transaction.getUser().getProfile().getFirstname(), transaction.getUser().getProfile().getMiddleName());
                    row.getCell(7).setCellValue(userDTO.getFullName());
                }
                if (transaction.getAccount() != null) {
                    row.getCell(8).setCellValue(transaction.getAccount().getUniqueNumber());
                }
            }

            autoSizeAllColumns(sheet, HEADERs.length);
            workbook.write(out);
            return new InputStreamResource(new ByteArrayInputStream(out.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Map<String, InputStreamResource> transactionToExcel(Long id) {
        Map<String, InputStreamResource> transactionMap = new HashMap<>();

        Transaction transaction =
                transactionRepo.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Transaction not found with ID: " + id));

        final String[] HEADERs = {"Платеж", "Дата", "Владелец квартиры", "Лицевой счет", "Приход/расход",
                "Статус", "Статья", "Квитанция", "Услуга", "Сумма", "Валюта", "Комментарий", "Менеджер"};

        final String SHEET = "transaction-№" + transaction.getUniqueNumber();
        int INITIAL_INDEX_OF_ROW = 0;

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            setWorkbookAuthor(workbook);
            Sheet sheet = workbook.createSheet(SHEET);
            setHeadersOfRows(workbook, sheet, HEADERs, INITIAL_INDEX_OF_ROW);

            createInitialCellsForRows(workbook, sheet, HEADERs.length, INITIAL_INDEX_OF_ROW);

            sheet.getRow(INITIAL_INDEX_OF_ROW).getCell(1).setCellValue("№" + transaction.getUniqueNumber());
            sheet.getRow(1 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue(new SimpleDateFormat("dd.MM.yyyy").format(transaction.getRequestedDate()));
            if (transaction.getUser() != null) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(transaction.getUser().getId());
                userDTO.setFullName(transaction.getUser().getProfile().getLastname(), transaction.getUser().getProfile().getFirstname(), transaction.getUser().getProfile().getMiddleName());
                sheet.getRow(2 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue(userDTO.getFullName());
            }
            if (transaction.getAccount() != null) {
                sheet.getRow(3 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue(transaction.getAccount().getUniqueNumber());
            }
            sheet.getRow(4 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue(transaction.getType().getTitle());
            sheet.getRow(5 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue(transaction.isUsed() ? "Проведен" : "Не проведен");
            sheet.getRow(6 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue(transaction.getTransactionPurpose().getName());
            sheet.getRow(7 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue("");
            sheet.getRow(8 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue("");
            sheet.getRow(9 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue(transaction.getAmount());
            sheet.getRow(10 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue("грн");
            sheet.getRow(11 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue(transaction.getComment());
            if (transaction.getStaff() != null) {
                sheet.getRow(12 + INITIAL_INDEX_OF_ROW).getCell(1).setCellValue(transaction.getStaff().getLastname() + " " + transaction.getStaff().getFirstname());
            }

            autoSizeAllColumns(sheet, 2);
            workbook.write(out);
            transactionMap.put(transaction.getUniqueNumber(), new InputStreamResource(new ByteArrayInputStream(out.toByteArray())));
            return transactionMap;
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
        }

    }

}
