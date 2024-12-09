package dev.sana.receipts_tracker;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Category(){}

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString(){
        return "Category{" +
                "id=" + id +
                ", name=" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
