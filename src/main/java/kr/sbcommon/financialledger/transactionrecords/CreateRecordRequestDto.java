package kr.sbcommon.financialledger.transactionrecords;

import lombok.Data;

@Data
public class CreateRecordRequestDto {

    private int year;

    private int month;

    private int date;
}
