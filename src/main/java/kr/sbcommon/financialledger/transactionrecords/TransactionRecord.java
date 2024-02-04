package kr.sbcommon.financialledger.transactionrecords;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaction_records")
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "in_or_out", nullable = false)
    private String inOrOut;

    @Column(name = "create_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;

    @Column(name = "update_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDateTime;

    
}
