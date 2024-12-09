package dev.sana.receipts_tracker.Controller;

import dev.sana.receipts_tracker.*;
import dev.sana.receipts_tracker.DTO.ProductDTO;
import dev.sana.receipts_tracker.Service.BillService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("bill")
    public String getView(){
        return "bill";
    }

    @PostMapping("/api/bill/{transactionId}")
    @ResponseBody()
    public String saveBillToTransaction(@RequestBody List<ProductDTO> products, @PathVariable Integer transactionId){
        billService.saveBillToTransaction(products, transactionId);
        return "redirect:/bill";
    }

    @PostMapping("/api/bill/transaction")
    @ResponseBody
    public ResponseEntity<List<Transaction>> findTransactionsForBill(@RequestBody List<ProductDTO> products){
        return billService.findTransactionForBill(products);
    }
}
