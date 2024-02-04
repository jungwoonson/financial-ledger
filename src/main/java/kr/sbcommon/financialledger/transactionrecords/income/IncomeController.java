package kr.sbcommon.financialledger.transactionrecords.income;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IncomeController {

    @RequestMapping("/income")
    public String income() {
        return "transactionrecords/income";
    }
}
