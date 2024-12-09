package dev.sana.receipts_tracker;

import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate date;
    private String recipient;
    private BigDecimal price;
    private boolean hasBill;

    public Transaction(){}

    @PersistenceCreator
    public Transaction(Integer id, LocalDate date, String recipient, BigDecimal price, Boolean hasBill) {
        this.id = id;
        this.date = date;
        this.recipient = recipient;
        this.price = price;
        this.hasBill = hasBill;
    }


    public Transaction(Integer id, LocalDate date, String recipient, BigDecimal price) {
        this.id = id;
        this.date = date;
        this.recipient = recipient;
        this.price = price;
        this.hasBill = false;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +  '\'' +
                ", date=" + date + '\'' +
                ", recipient=" + recipient + '\'' +
                ", price=" + price + '\'' +
                ", hasBill=" + hasBill + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getRecipient() {
        return recipient;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public boolean getHasBill(){
        return hasBill;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setHasBill(boolean hasBill) {
        this.hasBill = hasBill;
    }
}
