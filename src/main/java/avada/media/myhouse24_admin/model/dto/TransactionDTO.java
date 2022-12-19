package avada.media.myhouse24_admin.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionDTO {

    private Long id;
    private String uniqueNumber;
    private Date requestedDate;
    private TypeDTO type;
    private String comment;
    private boolean used;
    private Double amount;
    private TransactionPurposeDTO transactionPurpose;
    private StaffDTO staff;
    private AccountDTO account;
    private UserDTO user;

}
