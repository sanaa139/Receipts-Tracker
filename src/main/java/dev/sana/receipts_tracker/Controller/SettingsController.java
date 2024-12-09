package dev.sana.receipts_tracker.Controller;

import dev.sana.receipts_tracker.Category;
import dev.sana.receipts_tracker.Repository.CategoryRepository;
import dev.sana.receipts_tracker.Repository.TransactionRepository;
import dev.sana.receipts_tracker.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class SettingsController {
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public SettingsController(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/settings")
    public String getView(){
        return "settings";
    }

    @GetMapping("/api/settings")
    @ResponseBody
    public List<Category> getData(){
        return categoryRepository.findAll();
    }

    @PostMapping("/api/settings")
    public String addTransaction(@RequestParam LocalDate date, @RequestParam String recipient, @RequestParam BigDecimal price){
        transactionRepository.save(new Transaction(null, date, recipient, price, false));
        return "redirect:/settings";
    }

    @PostMapping("/api/categories")
    public String addCategory(@RequestParam String categoryName){
        if(!categoryRepository.existsByName(categoryName)){
            categoryRepository.save(new Category(null, categoryName));
        }
        return "redirect:/settings";
    }

    @GetMapping("/api/categories")
    @ResponseBody
    public List<Category> getCategory(){
        return categoryRepository.findAll();
    }

    @DeleteMapping("/api/categories/{id}")
    public String deleteCategory(@PathVariable String id){
        categoryRepository.deleteById(Integer.parseInt(id));
        return "redirect:/settings";
    }
}
