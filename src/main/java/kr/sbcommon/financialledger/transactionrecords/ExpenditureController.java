package kr.sbcommon.financialledger.transactionrecords;

import kr.sbcommon.financialledger.transactionrecords.dto.CreateRecordRequestDto;
import kr.sbcommon.financialledger.transactionrecords.dto.TransactionRecordDto;
import kr.sbcommon.financialledger.transactionrecords.dto.UpdateRecordRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/expenditure")
public class ExpenditureController {

    private final TransactionRecordService transactionRecordService;

    public ExpenditureController(TransactionRecordService transactionRecordService) {
        this.transactionRecordService = transactionRecordService;
    }

    @GetMapping
    public String expenditure() {
        return "transactionrecords/expenditure";
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<TransactionRecordDto>> list(@PathVariable int year, @PathVariable int month) {
        return ResponseEntity.ok(transactionRecordService.findExpenditureRecords(year, month));
    }

    @PostMapping
    public ResponseEntity<Long> createIncome(@RequestBody CreateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.findNewExpenditureRecordId(dto));
    }

    @PutMapping("/category")
    public ResponseEntity<TransactionRecordDto> updateCategory(@RequestBody UpdateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.updateCategory(dto));
    }

    @PutMapping("/name")
    public ResponseEntity<TransactionRecordDto> updateName(@RequestBody UpdateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.updatePersonName(dto));
    }

    @PutMapping("/amount")
    public ResponseEntity<TransactionRecordDto> updateAmount(@RequestBody UpdateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.updateAmount(dto));
    }

    @PutMapping("/details")
    public ResponseEntity<TransactionRecordDto> updateDetails(@RequestBody UpdateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.updateDetails(dto));
    }

    @PutMapping("/date")
    public ResponseEntity<TransactionRecordDto> updateDate(@RequestBody UpdateRecordRequestDto dto) {
        return ResponseEntity.ok(transactionRecordService.updateDate(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteIncome(@PathVariable Long id) {
        transactionRecordService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
