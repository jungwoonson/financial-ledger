package kr.sbcommon.financialledger.transactionrecords;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

@Entity
@Table(name = "transaction_records")
@Getter
public class TransactionRecord {

    public static final String IN = "IN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "details")
    private String details;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "in_or_out", nullable = false)
    private String inOrOut;

    @Column(name = "create_datetime", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CreationTimestamp
    private Date createDateTime;

    @Column(name = "update_datetime", nullable = false)
    @UpdateTimestamp
    private Date updateDateTime;

    public TransactionRecord() {
    }

    private TransactionRecord(String inOrOut, Date date) {
        this.inOrOut = inOrOut;
        this.date = date;
        this.category = "1";
        this.amount = new BigDecimal(0);
        this.personName = "";
        this.details = "";
    }

    public static TransactionRecord createIncomeRecord(Date date) {
        return new TransactionRecord(IN, date);
    }

    public Long getId() {
        return id;
    }
}
