package dev.sana.receipts_tracker.Controller;

import dev.sana.receipts_tracker.DTO.ProductDTO;
import dev.sana.receipts_tracker.DTO.TransactionDTO;
import dev.sana.receipts_tracker.Repository.ProductRepository;
import dev.sana.receipts_tracker.Repository.TransactionRepository;
import dev.sana.receipts_tracker.Service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TransactionsController {
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;

    public TransactionsController(TransactionService transactionService,
                                  TransactionRepository transactionRepository,
                                  ProductRepository productRepository){
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/transactions")
    public String getView(){
        return "transactions";
    }

    @PostMapping("/api/transactions/bill/{transactionId}")
    @ResponseBody
    public ResponseEntity<HttpStatus> addBill(@RequestBody List<ProductDTO> products, @PathVariable Integer transactionId){
        return transactionService.addBillToTransaction(products, transactionId);
    }

    @DeleteMapping("/api/transactions/bill/{transactionId}")
    public String deleteBillFromTransaction(@PathVariable String transactionId){
        transactionRepository.deleteBillById(Integer.parseInt(transactionId));
        transactionRepository.updateBillStatusInTransaction(false, Integer.parseInt(transactionId));
        return "redirect:/transactions";
    }


    @GetMapping("/api/transactions/{transactionId}")
    @ResponseBody
    public List<ProductDTO> getProductsOfTransaction(@PathVariable String transactionId) {
        return productRepository.findByTransactionId(Integer.parseInt(transactionId));
    }

    @GetMapping("/api/transactions")
    @ResponseBody
    public Page<TransactionDTO> getTransactions(@RequestParam(required = false) String groupBy, @RequestParam Integer page, @RequestParam Integer size){
        if(groupBy != null) {
            if (groupBy.equals("recipient")) {
                return transactionRepository.getTransactionsGroupedByRecipient(PageRequest.of(page, size));
            } else if (groupBy.equals("date")) {
                return transactionRepository.getTransactionsGroupedByDate(PageRequest.of(page, size));
            }
        }
        return transactionRepository.findAllByOrderByDateDesc(PageRequest.of(page, size));
    }
}
