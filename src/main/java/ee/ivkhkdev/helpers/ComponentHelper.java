package ee.ivkhkdev.helpers;

import ee.ivkhkdev.entity.Component;

import java.util.List;
import java.util.Optional;

public interface ComponentHelper extends Helper<Component>{
    Optional<Long> getIdModifyComponent(List<Component> components, boolean enabled);
}
