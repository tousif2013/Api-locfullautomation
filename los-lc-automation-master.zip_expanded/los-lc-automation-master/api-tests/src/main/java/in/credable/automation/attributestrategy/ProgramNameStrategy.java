package in.credable.automation.attributestrategy;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.util.List;

public class ProgramNameStrategy implements AttributeStrategy<String> {
    @Override
    public String getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
        return StringUtils.join(Faker.instance().lorem().words(3), ' ');
    }

}
