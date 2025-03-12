package in.credable.automation.attributestrategy;

import in.credable.automation.utils.DateTimeUtils;
import lombok.SneakyThrows;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.util.List;

public class ClientCodeStrategy implements AttributeStrategy<String> {
    @SneakyThrows
    @Override
    public String getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
        // Wait for 1 second so that client code is unique.
        Thread.sleep(1000);
        return String.valueOf(DateTimeUtils.getCurrentTimeInSeconds());
    }

}
