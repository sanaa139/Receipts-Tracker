package dev.sana.receipts_tracker.Service;

import dev.sana.receipts_tracker.Category;
import dev.sana.receipts_tracker.DTO.ProductDTO;
import dev.sana.receipts_tracker.Product;
import dev.sana.receipts_tracker.Repository.CategoryRepository;
import dev.sana.receipts_tracker.Repository.ProductRepository;
import dev.sana.receipts_tracker.Repository.TransactionRepository;
import dev.sana.receipts_tracker.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillService {
    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public BillService(TransactionRepository transactionRepository,
                              ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void saveBillToTransaction(List<ProductDTO> data, Integer transactionId){
        if(transactionRepository.existsById(transactionId) && !transactionRepository.findById(transactionId).get().getHasBill()){
            List<Product> productsList = new ArrayList<>();

            for(ProductDTO productWithCategoryName : data){
                Optional<Category> category = categoryRepository.findCategoryByName(productWithCategoryName.getCategoryName());
                if(category.isPresent()){
                    Product product = new Product(null,
                            productWithCategoryName.getName(),
                            productWithCategoryName.getPrice(),
                            productWithCategoryName.getQuantity(),
                            productWithCategoryName.getPrice().multiply(productWithCategoryName.getQuantity()),
                            transactionId,
                            category.get());
                    productsList.add(product);
                }
            }
            productRepository.saveAll(productsList);
            transactionRepository.updateBillStatusInTransaction(true, transactionId);
        }
    }
    public ResponseEntity<List<Transaction>> findTransactionForBill(List<ProductDTO> products){
        BigDecimal billPrice = new BigDecimal(0);

        for (ProductDTO product : products) {
            billPrice = billPrice.add(product.getPrice().multiply(product.getQuantity()));
        }
        List<Transaction> transactions = transactionRepository.findByPrice(billPrice.multiply(new BigDecimal(-1)));
        List<Transaction> transactionsWithoutBill = new ArrayList<>();
        for(Transaction t : transactions){
            if(!t.getHasBill()){
                transactionsWithoutBill.add(t);
            }
        }

        return new ResponseEntity<>(transactionsWithoutBill, HttpStatus.OK);
    }
}
