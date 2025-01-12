package ee.ivkhkdev.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;
@Entity
public class Component  implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private double price;
    private int amount;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "Category_id")
    private Category category;

    public Component() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Double.compare(price, component.price) == 0 && amount == component.amount && Objects.equals(id, component.id) && Objects.equals(brand, component.brand) && Objects.equals(model, component.model) && Objects.equals(category, component.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, price, amount, category);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Component{");
        sb.append("id=").append(id);
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", model='").append(model).append('\'');
        sb.append(", price=").append(price);
        sb.append(", amount=").append(amount);
        sb.append(", category=").append(category);
        sb.append('}');
        return sb.toString();
    }
}
