package kr.sbcommon.financialledger.transactionrecords;

import kr.sbcommon.financialledger.transactionrecords.dto.CreateRecordRequestDto;
import kr.sbcommon.financialledger.transactionrecords.dto.TransactionRecordDto;
import kr.sbcommon.financialledger.transactionrecords.dto.UpdateRecordRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionRecordService {

    private final TransactionRecordRepository transactionRecordRepository;

    public TransactionRecordService(TransactionRecordRepository transactionRecordRepository) {
        this.transactionRecordRepository = transactionRecordRepository;
    }

    public Long findNewIncomeRecordId(CreateRecordRequestDto dto) {
        LocalDate date = LocalDate.of(dto.getYear(), dto.getMonth(), dto.getDate());
        return transactionRecordRepository
                .save(TransactionRecord.createIncomeRecord(Date.valueOf(date)))
                .getId();
    }

    @Transactional(readOnly = true)
    public List<TransactionRecordDto> findIncomeRecords(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<TransactionRecord> transactionRecords = transactionRecordRepository
                .findTransactionRecordByDateBetweenAndTypeOrderByDate(Date.valueOf(startDate), Date.valueOf(endDate), TransactionRecord.INCOME);

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

    public void delete(Long id) {
        TransactionRecord record = transactionRecordRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        transactionRecordRepository.delete(record);
    }

    public TransactionRecordDto updateCategory(UpdateRecordRequestDto dto) {
        TransactionRecord record = transactionRecordRepository.findById(dto.getId())
                .orElseThrow(RuntimeException::new);
        record.updateCategory(dto.getCategory());
        TransactionRecord saved = transactionRecordRepository.save(record);
        return createTransactionRecordDto(saved);
    }

    public TransactionRecordDto updatePersonName(UpdateRecordRequestDto dto) {
        TransactionRecord record = transactionRecordRepository.findById(dto.getId())
                .orElseThrow(RuntimeException::new);
        record.updatePersonName(dto.getName());
        TransactionRecord saved = transactionRecordRepository.save(record);
        return createTransactionRecordDto(saved);
    }

    public TransactionRecordDto updateAmount(UpdateRecordRequestDto dto) {
        TransactionRecord record = transactionRecordRepository.findById(dto.getId())
                .orElseThrow(RuntimeException::new);
        record.updateAmount(dto.getAmount());
        TransactionRecord saved = transactionRecordRepository.save(record);
        return createTransactionRecordDto(saved);
    }

    public TransactionRecordDto updateDetails(UpdateRecordRequestDto dto) {
        TransactionRecord record = transactionRecordRepository.findById(dto.getId())
                .orElseThrow(RuntimeException::new);
        record.updateDetails(dto.getDetails());
        TransactionRecord saved = transactionRecordRepository.save(record);
        return createTransactionRecordDto(saved);
    }

    public TransactionRecordDto updateDate(UpdateRecordRequestDto dto) {
        TransactionRecord record = transactionRecordRepository.findById(dto.getId())
                .orElseThrow(RuntimeException::new);
        record.updateDate(dto.getDate());
        TransactionRecord saved = transactionRecordRepository.save(record);
        return createTransactionRecordDto(saved);
    }

    public Long findNewExpenditureRecordId(CreateRecordRequestDto dto) {
        LocalDate date = LocalDate.of(dto.getYear(), dto.getMonth(), dto.getDate());
        return transactionRecordRepository
                .save(TransactionRecord.createExpenditureRecord(Date.valueOf(date)))
                .getId();
    }

    public List<TransactionRecordDto> findExpenditureRecords(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<TransactionRecord> transactionRecords = transactionRecordRepository
                .findTransactionRecordByDateBetweenAndTypeOrderByDate(Date.valueOf(startDate), Date.valueOf(endDate), TransactionRecord.EXPENDITURE);

        List<TransactionRecordDto> transactionRecordDtos = new ArrayList<>();
        transactionRecords.forEach(record -> transactionRecordDtos.add(createTransactionRecordDto(record)));
        return transactionRecordDtos;
    }
}
