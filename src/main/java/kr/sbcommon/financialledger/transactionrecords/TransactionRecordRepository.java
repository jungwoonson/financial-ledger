package kr.sbcommon.financialledger.transactionrecords;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {
}
