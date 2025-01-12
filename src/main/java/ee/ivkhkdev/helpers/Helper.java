package ee.ivkhkdev.helpers;

import java.util.List;
import java.util.Optional;

public interface Helper<T> {
    Optional<T> create();
    Optional<T> update(T t);
    boolean printList(List<T> ts,boolean enableAll);
}
