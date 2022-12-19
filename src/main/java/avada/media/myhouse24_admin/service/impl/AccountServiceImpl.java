package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.model.Account;
import avada.media.myhouse24_admin.model.dto.*;
import avada.media.myhouse24_admin.model.request.AccountRequest;
import avada.media.myhouse24_admin.model.request.SelectResponse;
import avada.media.myhouse24_admin.repo.AccountRepo;
import avada.media.myhouse24_admin.repo.FlatRepo;
import avada.media.myhouse24_admin.service.AccountService;
import avada.media.myhouse24_admin.spec.AccountSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountSpec accountSpec;
    private final FlatRepo flatRepo;

    private final SimpMessagingTemplate template;

    @Override
    @Transactional
    public ResponseByPage<AccountDTO> getAllAccounts(AccountRequest accountRequest) {
        Page<Account> accountPage =
                accountRepo.findAll(accountSpec.getAccounts(accountRequest), PageRequest.of(accountRequest.getPageIndex() - 1, accountRequest.getPageSize()));
        ResponseByPage<AccountDTO> responseByPage = new ResponseByPage<>();
        responseByPage.setItemsCount(accountPage.getTotalElements());
        for (Account account : accountPage) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setId(account.getId());
            accountDTO.setUniqueNumber(account.getUniqueNumber());
            accountDTO.setStatus(new StatusDTO(account.getStatus().name(), account.getStatus().getTitle()));
            if (account.getFlat() != null) {
                FlatDTO flatDTO = new FlatDTO();
                flatDTO.setId(account.getFlat().getId());
                flatDTO.setTitle(account.getFlat().getNumber(), account.getFlat().getBuilding().getTitle());
                flatDTO.setNumber(account.getFlat().getNumber().toString());
                accountDTO.setFlat(flatDTO);
                accountDTO.setSection(account.getFlat().getSection());
                BuildingDTO buildingDTO = new BuildingDTO();
                buildingDTO.setId(account.getFlat().getBuilding().getId());
                buildingDTO.setTitle(account.getFlat().getBuilding().getTitle());
                accountDTO.setBuilding(buildingDTO);
                if (account.getFlat().getUser() != null) {
                    Hibernate.initialize(account.getFlat().getUser().getProfile());
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(account.getFlat().getUser().getId());
                    userDTO.setFullName(account.getFlat().getUser().getProfile().getLastname(), account.getFlat().getUser().getProfile().getFirstname(), account.getFlat().getUser().getProfile().getMiddleName());
                    accountDTO.setUser(userDTO);
                }
            }
            accountDTO.setBalance(account.getBalance());
            responseByPage.getData().add(accountDTO);
        }
        return responseByPage;
    }

    @Override
    public List<AccountDTO> getAllNotUsedAccounts() {
        List<Account> accounts = accountRepo.getAllNotUsedAccounts();
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for (Account account : accounts) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setId(account.getId());
            accountDTO.setUniqueNumber(account.getUniqueNumber());
            accountDTO.setStatus(new StatusDTO(account.getStatus().name(), account.getStatus().getTitle()));
            if (account.getFlat() != null) {
                FlatDTO flatDTO = new FlatDTO();
                flatDTO.setId(account.getFlat().getId());
                flatDTO.setTitle(account.getFlat().getNumber(), account.getFlat().getBuilding().getTitle());
                flatDTO.setNumber(account.getFlat().getNumber().toString());
                accountDTO.setFlat(flatDTO);
                accountDTO.setSection(account.getFlat().getSection());
                BuildingDTO buildingDTO = new BuildingDTO();
                buildingDTO.setId(account.getFlat().getBuilding().getId());
                buildingDTO.setTitle(account.getFlat().getBuilding().getTitle());
                accountDTO.setBuilding(buildingDTO);
                if (account.getFlat().getUser() != null) {
                    Hibernate.initialize(account.getFlat().getUser().getProfile());
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(account.getFlat().getUser().getId());
                    userDTO.setFullName(account.getFlat().getUser().getProfile().getLastname(), account.getFlat().getUser().getProfile().getFirstname(), account.getFlat().getUser().getProfile().getMiddleName());
                    accountDTO.setUser(userDTO);
                }
            }
            accountDTO.setBalance(account.getBalance());
            accountDTOList.add(accountDTO);
        }
        return accountDTOList;
    }


    @Override
    @Transactional
    public SelectResponse searchForAccount(String query, Integer page) {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setUniqueNumber(query);
        accountRequest.setPageIndex(page);
        accountRequest.setPageSize(10);
        Page<Account> accounts = accountRepo.findAll(accountSpec.getAccounts(accountRequest), PageRequest.of(accountRequest.getPageIndex() - 1, accountRequest.getPageSize()));
        SelectResponse selectResponse = new SelectResponse();
        if (!accounts.getContent().isEmpty()) {
            List<SelectResponse.Result> resultsList = selectResponse.getResults();
            for (Account account : accounts) {
                SelectResponse.Result result = new SelectResponse.Result();
                result.setId(account.getId());
                result.setText(account.getUniqueNumber());
                resultsList.add(result);
            }
            selectResponse.setResults(resultsList);
        }
        selectResponse.setItemsCount(accounts.getTotalElements());
        return selectResponse;
    }

    @Override
    public void saveAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setUniqueNumber(accountDTO.getUniqueNumber());
        account.setStatus(Account.Status.valueOf(accountDTO.getStatus().getValue()));
        account.setBalance(0.00d);
        if (accountDTO.getFlat() != null) {
            account.setFlat(flatRepo.findById(accountDTO.getFlat().getId()).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + accountDTO.getFlat().getId())));
        }
        accountRepo.save(account);
    }

    @Override
    public void updateAccount(Long id, AccountDTO accountDTO) {
        Account account = accountRepo.findById(accountDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + accountDTO.getId()));
        account.setId(accountDTO.getId());
        account.setUniqueNumber(accountDTO.getUniqueNumber());
        account.setStatus(Account.Status.valueOf(accountDTO.getStatus().getValue()));
        if (accountDTO.getFlat() != null) {
            account.setFlat(flatRepo.findById(accountDTO.getFlat().getId()).orElseThrow(() -> new EntityNotFoundException("Flat not found with ID: " + accountDTO.getFlat().getId())));
        }
        accountRepo.save(account);
    }

    @Override
    public String getNewUniqueNumber() {
        long id = 26092200000L;
        try {
            id = id + accountRepo.findFirstByOrderByIdDesc().getId() + 1L;
        } catch (NullPointerException e) {
            log.error("There is no any accounts in DB. Error: " + e.getMessage());
            id++;
        }
        return Long.toString(id);
    }

    @Override
    public boolean isUniqueNumberNotExists(Long id, String number) {
        if (id != null && number.equals(accountRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + id))
                .getUniqueNumber())) {
            return true;
        } else return !accountRepo.existsByUniqueNumber(number);
    }

    @Override
    public Double getAccountsBalance() {
        return accountRepo.getAmounts().stream().filter(amount -> amount > 0).mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public Double getAccountsDebt() {
        return accountRepo.getAmounts().stream().filter(amount -> amount < 0).mapToDouble(Double::doubleValue).sum();
    }

}
