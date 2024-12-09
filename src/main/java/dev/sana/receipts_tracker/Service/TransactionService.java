package dev.sana.receipts_tracker.Service;

import dev.sana.receipts_tracker.Category;
import dev.sana.receipts_tracker.DTO.ProductDTO;
import dev.sana.receipts_tracker.Product;
import dev.sana.receipts_tracker.Repository.CategoryRepository;
import dev.sana.receipts_tracker.Repository.ProductRepository;
import dev.sana.receipts_tracker.Repository.TransactionRepository;
import dev.sana.receipts_tracker.Transaction;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository,
                                  ProductRepository productRepository,
                                  CategoryRepository categoryRepository){
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

   public ResponseEntity<HttpStatus> addBillToTransaction(List<ProductDTO> data, Integer transactionId){
       BigDecimal billCost = new BigDecimal(0);
       for(ProductDTO product : data){
           billCost = billCost.add(product.getPrice().multiply(product.getQuantity()));
       }
       billCost = billCost.multiply(new BigDecimal(-1));

       Optional<Transaction> transaction = transactionRepository.findById(transactionId);
       if(transaction.isPresent()){
           if(transaction.get().getPrice().compareTo(billCost) == 0){
               productRepository.deleteByTransactionId(transactionId);

               List<Product> productsList = new ArrayList<>();

               for(int i = 0; i < data.size(); i++) {
                   Optional<Category> category = categoryRepository.findCategoryByName(data.get(i).getCategoryName());
                   if(category.isPresent()){
                       Product product = new Product(null,
                               data.get(i).getName(),
                               data.get(i).getPrice(),
                               data.get(i).getQuantity(),
                               data.get(i).getPrice().multiply(data.get(i).getQuantity()),
                               transactionId,
                               category.get());
                       productsList.add(product);
                   }
               }

               productRepository.saveAll(productsList);
               transactionRepository.updateBillStatusInTransaction(true, transactionId);
               return new ResponseEntity<>(HttpStatus.OK);
           }else{
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
}

