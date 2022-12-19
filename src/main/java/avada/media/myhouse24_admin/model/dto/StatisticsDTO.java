package avada.media.myhouse24_admin.model.dto;

import lombok.Data;

@Data
public class StatisticsDTO {

    private long buildingsCount;
    private long activeUsersCount;
    private long masterRequestsCount;
    private long flatsCount;
    private long accountsCount;
    private long newMasterRequestsCount;
    private double accountsTotalDebt;
    private double accountsTotalBalance;
    private double accountsTotalTransaction;

}
