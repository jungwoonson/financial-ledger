package kr.sbcommon.financialledger.transactionrecords.income;

import kr.sbcommon.financialledger.transactionrecords.TransactionRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IncomeController {

    private final TransactionRecordService transactionRecordService;

    public IncomeController(TransactionRecordService transactionRecordService) {
        this.transactionRecordService = transactionRecordService;
    }

    @RequestMapping("/income")
    public String income() {
        return "transactionrecords/income";
    }

    @RequestMapping("/income/newid")
    public ResponseEntity<Long> newId() {
        return ResponseEntity.ok(transactionRecordService.findNewIncomeRecordId());
    }
}
