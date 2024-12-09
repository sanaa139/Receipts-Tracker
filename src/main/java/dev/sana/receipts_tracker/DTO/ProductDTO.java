package dev.sana.receipts_tracker.DTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.relational.core.sql.In;

import java.math.BigDecimal;

@Entity
public class ProductDTO {
    @Id
    private Integer id;
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal fullPrice;
    private Integer transactionId;
    private String categoryName;

    public ProductDTO(Integer id, String name, BigDecimal fullPrice, Integer transactionId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = null;
        this.quantity = null;
        this.fullPrice = fullPrice;
        this.transactionId = transactionId;
        this.categoryName = categoryName;
    }

    public ProductDTO(Integer id,String name, BigDecimal price, BigDecimal quantity, BigDecimal fullPrice, Integer transactionId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.fullPrice = fullPrice;
        this.transactionId = transactionId;
        this.categoryName = categoryName;
    }

    public ProductDTO() {

    }

    @Override
    public String toString(){
        return "id: " + id + ", name: " + name + ", price: " + price + ", quantity: " + quantity + ", full price: " +
                fullPrice + ", transaction id: " + transactionId + ",category name: " + categoryName;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
    public BigDecimal getFullPrice() {
        return fullPrice;
    }
    public Integer getTransactionId(){
        return transactionId;
    }
    public String getCategoryName(){
        return categoryName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
