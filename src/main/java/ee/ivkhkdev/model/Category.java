package ee.ivkhkdev.model;

import java.io.Serializable;
import java.util.Objects;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private String categoryName;

    public Category() {

    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryName, category.categoryName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(categoryName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Category{");
        sb.append("category=").append(categoryName);
        sb.append('}');
        return sb.toString();
    }
}
