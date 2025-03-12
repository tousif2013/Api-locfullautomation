package in.credable.automation.testcases.core;

import in.credable.automation.config.ConfigFactory;
import in.credable.automation.service.core.LosCoreConfigService;
import in.credable.automation.service.program.ProgramService;
import in.credable.automation.service.vo.dataingestion.CustomPropertyMappingVO;
import in.credable.automation.service.vo.dataingestion.PropertyValidatorType;
import in.credable.automation.service.vo.program.ProgramClientFieldMappingVO;
import in.credable.automation.testcases.BaseTest;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LosCoreConfigTest extends BaseTest {
    private static final Long AUTOMATION_PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();

    @Test(description = "Test #946 - Test the API designed to fetch program level configurations for field validations.")
    public void verifyProgramFieldMappings() {
        List<ProgramClientFieldMappingVO> programClientFieldMappings = ProgramService.getProgramClientFieldMappings(AUTOMATION_PROGRAM_ID)
                .as(new TypeRef<>() {
                });
        Assertions.assertThat(programClientFieldMappings)
                .as(() -> "Program client field mappings should not be null")
                .isNotNull()
                .as(() -> "Program client field mappings should not be empty")
                .isNotEmpty();

        Map<String, ProgramClientFieldMappingVO> standardFieldMappings = programClientFieldMappings.stream()
                .collect(Collectors.toMap(programClientFieldMappingVO -> programClientFieldMappingVO.getClientFieldMappingVO().getStandardFieldName(),
                        Function.identity()));

        List<CustomPropertyMappingVO> customPropertyMappings = LosCoreConfigService.getProgramLevelValidatorFieldMappings(AUTOMATION_PROGRAM_ID)
                .as(new TypeRef<>() {
                });

        Assertions.assertThat(customPropertyMappings)
                .as(() -> "Program level validator mappings should not be null")
                .isNotNull()
                .as(() -> "Program level validator mappings should not be empty")
                .isNotEmpty()
                .allSatisfy(customPropertyMappingVO -> {
                    ProgramClientFieldMappingVO programClientFieldMappingVO =
                            standardFieldMappings.get(customPropertyMappingVO.getStandardFieldName());
                    Assertions.assertThat(customPropertyMappingVO.getClientSpecificFieldName())
                            .as(() -> "Client specific field name should be same as defined at program level")
                            .isEqualTo(programClientFieldMappingVO.getClientFieldMappingVO().getClientFieldName());
                    if (programClientFieldMappingVO.isMandatory()) {
                        Assertions.assertThat(customPropertyMappingVO.getPropertyValidators())
                                .as(() -> "Property validators should be same as defined at program level")
                                .anySatisfy(propertyValidatorVO ->
                                        Assertions.assertThat(propertyValidatorVO.getType())
                                                .as(() -> "MANDATORY validation is not present for " + customPropertyMappingVO.getClientSpecificFieldName())
                                                .isEqualTo(PropertyValidatorType.MANDATORY));
                    }
                });
    }
}
