package in.credable.automation.attributestrategy;

import in.credable.automation.utils.RandomDataGenerator;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.util.List;

public class MobileNumberStrategy implements AttributeStrategy<String> {
    @Override
    public String getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
        return RandomDataGenerator.generateRandomMobileNumber();
    }
}
