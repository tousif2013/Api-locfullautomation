package in.credable.automation.testcases.program;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.module.ModuleService;
import in.credable.automation.service.vo.ErrorResponseVO;
import in.credable.automation.service.vo.SuccessResponseVO;
import in.credable.automation.service.vo.module.ModuleInstanceVO;
import in.credable.automation.service.vo.module.ModuleTypeEnum;
import in.credable.automation.service.vo.module.ModuleVO;
import in.credable.automation.service.vo.module.ProgramModuleInstanceVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.*;

public class ModuleTest extends BaseTest {

    private ModuleInstanceVO createdModuleInstanceVO;
    private Long programId;

    @Test(description = "Test #92 - Verify if user sends request to get all the modules then user gets all modules details")
    public void verifyGettingAllModules() {
        Response modulesResponse = ModuleService.getAllModules();
        modulesResponse
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/modules-list-json-schema.json"));
        List<ModuleVO> modules = modulesResponse.as(new TypeRef<>() {
        });

        Assertions.assertThat(modules)
                .as(() -> "Modules list should not be null")
                .isNotNull()
                .as(() -> "Module id should not be null")
                .allMatch(moduleVO -> moduleVO.getId() != null)
                .as(() -> "Module code should not be empty")
                .allMatch(moduleVO -> moduleVO.getModuleCode()!=null)
                .as(() -> "Module name should not be empty")
                .allMatch(moduleVO -> StringUtils.isNotBlank(moduleVO.getModuleName()))
                .as(() -> "Module type is not expected")
                .allMatch(moduleVO -> StringUtils.equalsAny(moduleVO.getModuleType(), Arrays.stream(ModuleTypeEnum.values()).map(Enum::name).toList().toArray(new String[0])))
                .as(() -> "Module country id should not be null")
                .allMatch(moduleVO -> moduleVO.getCountryId() != null);
    }

    @Test(description = "Test #93 - Verify if user sends request to create module instance with valid data " +
            "then user gets success response")
    public void verifyCreatingModuleInstance() {
        List<ModuleVO> modules = ModuleService.getAllModules().as(new TypeRef<>() {
        });
        ModuleVO selectedModule = modules.get(new Random().nextInt(modules.size()));

        ModuleInstanceVO moduleInstanceVO = new ModuleInstanceVO();
        moduleInstanceVO.setModuleId(selectedModule.getId());
        moduleInstanceVO.setModuleInstanceName(selectedModule.getModuleName());

        createdModuleInstanceVO = ModuleService.createModuleInstance(moduleInstanceVO)
                .as(ModuleInstanceVO.class);

        FrameworkAssertions.assertThat(createdModuleInstanceVO)
                .moduleInstanceIdIsNotNull()
                .hasSameModuleId(selectedModule.getId())
                .moduleInstanceNameIs(moduleInstanceVO.getModuleInstanceName())
                .hasSameModuleCode(selectedModule.getModuleCode())
                .isModuleInstanceOfModule(selectedModule);
    }

    @Test(description = "Test #267 - Verify if user sends request to update existing module instance with valid data " +
            "then user gets success response"
            , dependsOnMethods = "verifyCreatingModuleInstance")
    public void verifyUpdatingModuleInstance() {
        String moduleJson = prepareModuleJson();
        ModuleInstanceVO updateModuleInstanceVO = DataProviderUtil.clone(createdModuleInstanceVO);
        if (StringUtils.isNotBlank(updateModuleInstanceVO.getModuleInstanceUiKey())) {
            updateModuleInstanceVO.setModuleInstanceUiJson(moduleJson);
        } else {
            updateModuleInstanceVO.setModuleInstanceDataJson(moduleJson);
        }

        ModuleInstanceVO updatedModuleInstance = ModuleService.updateModuleInstance(updateModuleInstanceVO)
                .as(ModuleInstanceVO.class);

        FrameworkAssertions.assertThat(updatedModuleInstance)
                .moduleInstanceIdIsNotNull()
                .hasSameId(createdModuleInstanceVO.getId())
                .hasSameModuleId(createdModuleInstanceVO.getModuleId())
                .moduleInstanceNameIs(createdModuleInstanceVO.getModuleInstanceName())
                .hasSameModuleCode(createdModuleInstanceVO.getModuleCode())
                .isEqualTo(updateModuleInstanceVO);
    }

    @Test(description = "Test #94 - Verify if user sends request to associate program with module instance " +
            "then user gets success response"
            , dependsOnMethods = "verifyCreatingModuleInstance")
    public void verifyAssociatingModuleInstanceWithProgram() {
        programId = TestHelper.createProgram().getId();
        SuccessResponseVO successResponseVO = ModuleService.associateModuleInstanceWithProgram(programId, this.createdModuleInstanceVO.getId())
                .as(SuccessResponseVO.class);
        FrameworkAssertions.assertThat(successResponseVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameResponseMessage("Module Instance successfully associated with Program with id:" + programId);
    }

    @Test(description = "Test #95 - Verify if user sends request to associate same program with duplicate module instances " +
            "then user gets error response"
            , dependsOnMethods = "verifyAssociatingModuleInstanceWithProgram")
    public void verifyAssociatingSameModuleInstanceWithProgram() {
        ErrorResponseVO errorResponseVO = ModuleService.associateModuleInstanceWithProgram(programId,
                        this.createdModuleInstanceVO.getId(),
                        StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("EXCEPTION");
    }

    @Test(description = "Test #96 - Verify if user sends request to get all module instances for a program then user gets success response"
            , dependsOnMethods = "verifyAssociatingModuleInstanceWithProgram")
    public void verifyGettingAllModuleInstancesForProgram() {
        List<ProgramModuleInstanceVO> programModuleInstances = ModuleService.getProgramModuleInstancesByProgramId(programId)
                .as(new TypeRef<>() {
                });

        Assertions.assertThat(programModuleInstances)
                .as(() -> "Program module instances should not be null or empty")
                .isNotNull()
                .isNotEmpty()
                .as(() -> "There should be only 1 module instance with the program")
                .hasSize(1)
                .first()
                .as(() -> "Program id should be same for all the module instances")
                .matches(programModuleInstanceVO -> this.programId.equals(programModuleInstanceVO.getProgramId()))
                .as(() -> "Module instance id should be matching with created module instance id")
                .matches(programModuleInstanceVO -> this.createdModuleInstanceVO.getId().equals(programModuleInstanceVO.getModuleInstanceId()))
                .as(() -> "Module instance should be matching with created module instance")
                .matches(programModuleInstanceVO -> programModuleInstanceVO.getModuleInstanceVO().equals(this.createdModuleInstanceVO));
    }

    private String prepareModuleJson() {
        Map<String, Object> moduleJsonMap = new HashMap<>();
        moduleJsonMap.put("title", "Enter your details");
        moduleJsonMap.put("pan", "Enter PAN number:");
        moduleJsonMap.put("name", "Enter your full name:");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(moduleJsonMap);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
