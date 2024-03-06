package kr.sbcommon.financialledger.transactionrecords.income;

import kr.sbcommon.financialledger.transactionrecords.CreateRecordRequestDto;
import kr.sbcommon.financialledger.transactionrecords.TransactionRecordDto;
import kr.sbcommon.financialledger.transactionrecords.TransactionRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping("/income/{year}/{month}")
    public ResponseEntity<List<TransactionRecordDto>> list(@PathVariable int year, @PathVariable int month) {
        return ResponseEntity.ok(transactionRecordService.findIncomeRecords(year, month));
    }

    @PostMapping("/income")
    public ResponseEntity<Long> createIncome(@RequestBody CreateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.findNewIncomeRecordId(dto));
    }

    @DeleteMapping("/income/{id}")
    public ResponseEntity<Long> deleteIncome(@PathVariable Long id) {
        transactionRecordService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
