package ee.ivkhkdev.interfaces;

import java.util.List;

public interface AppService<T> {
    boolean add();
    boolean print();
    List<T> list();
    boolean edit();

}
