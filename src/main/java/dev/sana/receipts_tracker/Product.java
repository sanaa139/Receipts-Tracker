package dev.sana.receipts_tracker;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private BigDecimal price;
    private BigDecimal quantity;
    private BigDecimal fullPrice;
    private Integer transactionId;

    @ManyToOne
    @JoinColumn(name="category_id")
    public Category category;

    public Product(){}

    public Product(Integer id, String name, BigDecimal price, BigDecimal quantity, BigDecimal fullPrice, Integer transactionId, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.fullPrice = fullPrice;
        this.transactionId = transactionId;
        this.category = category;
    }
    @Override
    public String toString(){
        return "Product{" +
                "id=" + id + '\'' +
                ", name=" + name + '\'' +
                ", price=" + price + '\'' +
                ", quantity=" + quantity + '\'' +
                ", fullPrice=" + fullPrice + '\'' +
                ", transactionId=" + transactionId + '\'' +
                ", category id=" + category.getId() + '\'' +
                ", category name=" + category.getName() + '\'' +
                '}';
    }


    public Integer getId(){
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
    public Category getCategory(){
        return category;
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

    public void setCategory(Category category) {
        this.category = category;
    }
}
