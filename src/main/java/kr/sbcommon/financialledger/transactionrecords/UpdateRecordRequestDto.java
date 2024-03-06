package kr.sbcommon.financialledger.transactionrecords;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
public class UpdateRecordRequestDto {

    private Long id;
    private String category;
    private Long amount;
    private String name;
    private String details;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
}
