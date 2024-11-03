package ee.ivkhkdev.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Purchase implements Serializable {
    private UUID id;
    private Component component;
    private Customer customer;
    private LocalDate purchaseDate;

    public Purchase() {
        this.id = UUID.randomUUID();
        this.purchaseDate = LocalDate.now();
    }

    public Purchase(Component component, Customer customer) {
        this.id = UUID.randomUUID();
        this.component = component;
        this.customer = customer;
        this.purchaseDate = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase that = (Purchase) o;
        return Objects.equals(id, that.id) && Objects.equals(component, that.component) && Objects.equals(customer, that.customer) && Objects.equals(purchaseDate, that.purchaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, component, customer, purchaseDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PurchaseReceipt{");
        sb.append("id=").append(id);
        sb.append(", component=").append(component);
        sb.append(", customer=").append(customer);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append('}');
        return sb.toString();
    }
}
