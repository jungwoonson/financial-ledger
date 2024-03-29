package kr.sbcommon.financialledger.transactionrecords;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {

    List<TransactionRecord> findTransactionRecordByDateBetweenAndTypeOrderByDate(Date date1, Date date2, String inOrOut);
}
