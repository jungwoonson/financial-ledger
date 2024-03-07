package kr.sbcommon.financialledger.transactionrecords.dto;

import lombok.Data;

@Data
public class CreateRecordRequestDto {

    private int year;

    private int month;

    private int date;
}
