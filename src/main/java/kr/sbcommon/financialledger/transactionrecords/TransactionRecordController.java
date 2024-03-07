package kr.sbcommon.financialledger.transactionrecords;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TransactionRecordController {

    private final TransactionRecordService transactionRecordService;

    public TransactionRecordController(TransactionRecordService transactionRecordService) {
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

    @PutMapping("/income/category")
    public ResponseEntity<TransactionRecordDto> updateCategory(@RequestBody UpdateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.updateCategory(dto));
    }

    @PutMapping("/income/name")
    public ResponseEntity<TransactionRecordDto> updateName(@RequestBody UpdateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.updatePersonName(dto));
    }

    @PutMapping("/income/amount")
    public ResponseEntity<TransactionRecordDto> updateAmount(@RequestBody UpdateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.updateAmount(dto));
    }

    @PutMapping("/income/details")
    public ResponseEntity<TransactionRecordDto> updateDetails(@RequestBody UpdateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.updateDetails(dto));
    }

    @PutMapping("/income/date")
    public ResponseEntity<TransactionRecordDto> updateDate(@RequestBody UpdateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.updateDate(dto));
    }
}
