package kr.sbcommon.financialledger.category;

import lombok.Data;

@Data
public class CreateCategoryRequestDto {

    private String name;

    private String type;

    private int print_seq;

    private String isUsable;
}
