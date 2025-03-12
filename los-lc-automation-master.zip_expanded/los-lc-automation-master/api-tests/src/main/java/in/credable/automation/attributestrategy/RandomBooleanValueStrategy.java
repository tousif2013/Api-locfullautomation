package in.credable.automation.attributestrategy;

import com.github.javafaker.Faker;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.util.List;

public class RandomBooleanValueStrategy implements AttributeStrategy<Boolean> {
    @Override
    public Boolean getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
        return Faker.instance().bool().bool();
    }
}
