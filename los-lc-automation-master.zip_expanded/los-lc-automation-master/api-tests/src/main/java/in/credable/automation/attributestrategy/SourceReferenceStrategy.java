package in.credable.automation.attributestrategy;

import in.credable.automation.utils.DateTimeUtils;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.util.List;

public class SourceReferenceStrategy implements AttributeStrategy<String> {

    @Override
    public String getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
        return String.valueOf(DateTimeUtils.getCurrentTimeInSeconds());
    }
}
