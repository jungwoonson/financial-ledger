package kr.sbcommon.financialledger.transactionrecords;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class TransactionRecordDto {

    private Long id;

    private String category;

    private String person_name;

    private String details;

    private BigDecimal amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
