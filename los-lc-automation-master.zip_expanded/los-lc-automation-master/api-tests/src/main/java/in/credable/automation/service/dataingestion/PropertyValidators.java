package in.credable.automation.service.dataingestion;

import in.credable.automation.service.vo.dataingestion.PropertyValidatorType;
import in.credable.automation.service.vo.dataingestion.PropertyValidatorVO;

import java.util.function.Function;

public final class PropertyValidators {
    public static final PropertyValidatorVO MANDATORY = PropertyValidatorVO.builder()
            .type(PropertyValidatorType.MANDATORY)
            .errorMessage("${propertyName} is Mandatory")
            .build();

    public static final PropertyValidatorVO PAN = PropertyValidatorVO.builder()
            .type(PropertyValidatorType.PAN)
            .errorMessage("${propertyValue} is not a valid PAN")
            .build();

    public static final PropertyValidatorVO UNIQUE = PropertyValidatorVO.builder()
            .type(PropertyValidatorType.UNIQUE)
            .errorMessage("${propertyName} is used already")
            .build();

    public static final Function<String, PropertyValidatorVO> REGEX = regex -> PropertyValidatorVO.builder()
            .type(PropertyValidatorType.REGEX)
            .regexPattern(regex)
            .errorMessage("${propertyName} is not valid")
            .build();
}
