package kr.sbcommon.financialledger.transactionrecords;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionRecordService {

    private final TransactionRecordRepository transactionRecordRepository;

    public TransactionRecordService(TransactionRecordRepository transactionRecordRepository) {
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Transactional
    public Long findNewIncomeRecordId() {
        return transactionRecordRepository
                .save(TransactionRecord.createIncomeRecord())
                .getId();
    }
}
