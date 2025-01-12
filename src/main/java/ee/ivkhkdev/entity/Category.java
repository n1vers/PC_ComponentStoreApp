package ee.ivkhkdev.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;

    public Category() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category{");
        sb.append("id=").append(id);
        sb.append(", categoryName='").append(categoryName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
