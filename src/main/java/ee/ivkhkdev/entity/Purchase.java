package ee.ivkhkdev.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
@Entity
public class Purchase implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "component_id")
    private Component component;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private LocalDate purchaseDate;

    public Purchase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(id, purchase.id) && Objects.equals(component, purchase.component) && Objects.equals(customer, purchase.customer) && Objects.equals(purchaseDate, purchase.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, component, customer, purchaseDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Purchase{");
        sb.append("id=").append(id);
        sb.append(", component=").append(component);
        sb.append(", customer=").append(customer);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append('}');
        return sb.toString();
    }
}
