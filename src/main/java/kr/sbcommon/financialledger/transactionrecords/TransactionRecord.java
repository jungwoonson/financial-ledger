package kr.sbcommon.financialledger.transactionrecords;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "transaction_records")
@Getter
public class TransactionRecord {

    public static final String INCOME = "INCOME";
    public static final String EXPENDITURE = "EXPENDITURE";

    @Getter
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

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "create_datetime", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CreationTimestamp
    private Date createDateTime;

    @Column(name = "update_datetime", nullable = false)
    @UpdateTimestamp
    private Date updateDateTime;

    public TransactionRecord() {
    }

    private TransactionRecord(String type, Date date) {
        this.type = type;
        this.date = date;
        this.category = "1";
        this.amount = new BigDecimal(0);
        this.personName = "";
        this.details = "";
    }

    public static TransactionRecord createIncomeRecord(Date date) {
        return new TransactionRecord(INCOME, date);
    }

    public static TransactionRecord createExpenditureRecord(Date date) {
        return new TransactionRecord(EXPENDITURE, date);
    }

    public void updateDate(Date date) {
        this.date = date;
    }

    public void updateDetails(String detail) {
        this.details = detail;
    }

    public void updateAmount(Long amount) {
        this.amount = new BigDecimal(amount);
    }

    public void updatePersonName(String name) {
        this.personName = name;
    }

    public void updateCategory(String category) {
        this.category = category;
    }
}
