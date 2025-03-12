package in.credable.automation.attributestrategy;

import com.github.javafaker.Faker;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.util.List;

public class UuidStrategy implements AttributeStrategy<String> {
    @Override
    public String getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
        return Faker.instance().internet().uuid();
    }
}
