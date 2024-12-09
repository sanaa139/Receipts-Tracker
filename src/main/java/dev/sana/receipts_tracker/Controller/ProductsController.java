package dev.sana.receipts_tracker.Controller;

import dev.sana.receipts_tracker.DTO.ProductDTO;
import dev.sana.receipts_tracker.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductsController {
    private final ProductRepository productRepository;

    public ProductsController(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public String getView() {
        return "products";
    }

    @GetMapping("/api/products")
    @ResponseBody
    public Page<ProductDTO> getProducts(@RequestParam(required = false) String groupBy, @RequestParam Integer page, @RequestParam Integer size){
        if(groupBy != null){
            if(groupBy.equals("name")){
                return productRepository.getProductsGroupedByName(PageRequest.of(page, size));
            }else if(groupBy.equals("category")){
                return productRepository.getProductsGroupedByCategory(PageRequest.of(page, size));
            }
        }
        return productRepository.getProductsWithCategoryName(PageRequest.of(page, size));
    }

}
