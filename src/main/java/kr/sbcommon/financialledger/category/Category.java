package kr.sbcommon.financialledger.category;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;

@Entity
@Table(name = "fl_category")
@Getter
public class Category {

    public static final String TYPE_INCOME = "INCOME";
    public static final String TYPE_EXPENDITURE = "EXPENDITURE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "print_seq")
    private int print_seq;

    @Column(name = "is_usable")
    private String isUsable;

    @Column(name = "create_datetime", nullable = false)
    @CreationTimestamp
    private Date createDateTime;

    @Column(name = "update_datetime", nullable = false)
    @UpdateTimestamp
    private Date updateDateTime;

    public Category() {
    }

    private Category(String name, String type, int print_seq, String isUsable) {
        this.name = name;
        this.type = type;
        this.print_seq = print_seq;
        this.isUsable = isUsable;
    }

    public static Category of(String name, String type, int print_seq, String isUsable) {
        return new Category(name, type, print_seq, isUsable);
    }
}
