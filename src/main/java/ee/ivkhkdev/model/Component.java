package ee.ivkhkdev.model;

import java.io.Serializable;
import java.util.*;

public class Component implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String brand;
    private String model;
    private double price;
    private List<Category> categories=new ArrayList<>();

    public Component() {
        this.id = UUID.randomUUID();
    }

    public Component( String brand, String model, double price,List<Category> categories ) {
        this.id = UUID.randomUUID();
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.categories = categories;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public List<Category> getCategory() {
        return categories;
    }

    public void setCategory(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component component = (Component) o;
        return Double.compare(price, component.price) == 0 && Objects.equals(id, component.id) && Objects.equals(brand, component.brand) && Objects.equals(model, component.model) && Objects.equals(categories, component.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, price, categories);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Component{");
        sb.append("id=").append(id);
        sb.append(", brand='").append(brand).append('\'');
        sb.append(", categories=").append(Arrays.toString(categories.toArray()));
        sb.append(", model=").append(model);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
