package avada.media.myhouse24_admin.service.impl;

import avada.media.myhouse24_admin.repo.AccountRepo;
import avada.media.myhouse24_admin.repo.TransactionRepo;
import avada.media.myhouse24_admin.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final TransactionRepo transactionRepo;
    private final AccountRepo accountRepo;

    @Override
    public Double getTransactionsBalance() {
        return null;
    }

    @Override
    public Double getAccountsBalance() {
        return null;
    }

    @Override
    public Double getAccountsDebt() {
        return null;
    }

}
