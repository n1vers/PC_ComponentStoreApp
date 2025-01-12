package ee.ivkhkdev.helpers;

import java.util.List;
import java.util.Optional;

public interface Helper<T> {
    Optional<T> create();
    boolean printList();
    boolean update(T t);
}
