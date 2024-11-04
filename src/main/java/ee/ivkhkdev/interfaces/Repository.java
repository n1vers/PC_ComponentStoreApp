package ee.ivkhkdev.interfaces;

import java.util.List;

public interface Repository<T> {
    void save(T entity);
    void saveAll(List<T> entities);
    void update(int index, T entity);
    List<T> load();
}
