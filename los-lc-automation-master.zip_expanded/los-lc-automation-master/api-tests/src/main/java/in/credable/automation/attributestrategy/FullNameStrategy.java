package in.credable.automation.attributestrategy;

import com.github.javafaker.Faker;
import in.credable.automation.utils.RandomDataGenerator;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.util.List;

public class FullNameStrategy implements AttributeStrategy<String> {
    @Override
    public String getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
        return RandomDataGenerator.generateRandomFirstName().replaceAll("[^a-zA-Z]", "")+" "+RandomDataGenerator.generateRandomLastName().replaceAll("[^a-zA-Z]", "");
    }
}
