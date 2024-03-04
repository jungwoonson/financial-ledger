package kr.sbcommon.financialledger.transactionrecords;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionRecordService {

    private final TransactionRecordRepository transactionRecordRepository;

    public TransactionRecordService(TransactionRecordRepository transactionRecordRepository) {
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Transactional
    public Long findNewIncomeRecordId(CreateRecordRequestDto dto) {
        LocalDate date = LocalDate.of(dto.getYear(), dto.getMonth(), dto.getDate());
        return transactionRecordRepository
                .save(TransactionRecord.createIncomeRecord(Date.valueOf(date)))
                .getId();
    }

    public List<TransactionRecordDto> findIncomeRecords(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<TransactionRecord> transactionRecords = transactionRecordRepository.findTransactionRecordByDateBetween(Date.valueOf(startDate), Date.valueOf(endDate));

        List<TransactionRecordDto> transactionRecordDtos = new ArrayList<>();

        transactionRecords.forEach(record -> transactionRecordDtos.add(createTransactionRecordDto(record)));

        return transactionRecordDtos;
    }

    private TransactionRecordDto createTransactionRecordDto(TransactionRecord record) {
        return TransactionRecordDto.builder()
                .id(record.getId())
                .amount(record.getAmount())
                .category(record.getCategory())
                .date(record.getDate())
                .details(record.getDetails())
                .person_name(record.getPersonName())
                .build();
    }
}
