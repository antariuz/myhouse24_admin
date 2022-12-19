package avada.media.myhouse24_admin.service;

import avada.media.myhouse24_admin.model.dto.AccountDTO;
import avada.media.myhouse24_admin.model.dto.ResponseByPage;
import avada.media.myhouse24_admin.model.request.AccountRequest;
import avada.media.myhouse24_admin.model.request.SelectResponse;

import java.util.List;

public interface AccountService {

    ResponseByPage<AccountDTO> getAllAccounts(AccountRequest accountRequest);

    List<AccountDTO> getAllNotUsedAccounts();

    SelectResponse searchForAccount(String query, Integer page);

    void saveAccount(AccountDTO accountDTO);

    void updateAccount(Long id, AccountDTO accountDTO);

    String getNewUniqueNumber();

    boolean isUniqueNumberNotExists(Long id, String uniqueId);

    Double getAccountsBalance();

    Double getAccountsDebt();

}
