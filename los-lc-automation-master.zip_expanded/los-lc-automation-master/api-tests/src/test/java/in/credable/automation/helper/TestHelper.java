package in.credable.automation.helper;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.config.EnvironmentConfig;
import in.credable.automation.service.core.LoanApplicationService;
import in.credable.automation.service.integration.VendorService;
import in.credable.automation.service.program.ProgramService;
import in.credable.automation.service.user.UserService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.core.LoanApplicationVO;
import in.credable.automation.service.vo.core.LoanCreationRequestVO;
import in.credable.automation.service.vo.integration.ListVendorsRequestVO;
import in.credable.automation.service.vo.integration.VendorStatusEnum;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.service.vo.program.ProgramVO;
import in.credable.automation.service.vo.user.CreateUserResponseVO;
import in.credable.automation.service.vo.user.UserRoleEnum;
import in.credable.automation.service.vo.user.UserVO;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import io.restassured.common.mapper.TypeRef;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public final class TestHelper {
    private static final EnvironmentConfig ENVIRONMENT_CONFIG = ConfigFactory.getEnvironmentConfig();

    private TestHelper() {
    }

    public static ProgramVO createProgram() {
        log.info("Creating program");
        ProgramVO programRequestVO = DataProviderUtil.manufacturePojo(ProgramVO.class);
        programRequestVO.setAnchorId(ENVIRONMENT_CONFIG.getAutomationAnchorId());
        programRequestVO.setClientId(ENVIRONMENT_CONFIG.getAutomationClientId());

        ProgramVO programResponseVO = ProgramService.createProgram(programRequestVO).as(ProgramVO.class);

        FrameworkAssertions.assertThat(programResponseVO)
                .programIdIsNotNull();

        log.info("Created program id: {}", programResponseVO.getId());
        return programResponseVO;
    }

    public static UserVO createUser() {
        log.info("Creating user");
        UserVO createUserRequestVO = DataProviderUtil.manufacturePojo(UserVO.class);
        createUserRequestVO.setUsername(createUserRequestVO.getEmailId());
        createUserRequestVO.setRoles(List.of(UserRoleEnum.ADMIN.getUserRole(), UserRoleEnum.CLIENT_ADMIN.getUserRole()));
        ResponseWO<CreateUserResponseVO> createdUserResponseVO = UserService.createUser(createUserRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(createdUserResponseVO)
                .hasSameStatus(200)
                .hasSameCode("USR001")
                .hasSameMessage("User Registered successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        createUserRequestVO.setUserId(createdUserResponseVO.getData().getUserId());
        return createUserRequestVO;
    }

    public static ResponseWO<Void> deleteUser(String userId) {
        log.info("Deleting user");
        return UserService.deleteUser(userId).as(new TypeRef<>() {
        });
    }

    public static VendorVO createVendor(String vendorType, String applicationServiceId) {
        log.info("Creating vendor");
        VendorVO vendorRequestVO = DataProviderUtil.manufacturePojo(VendorVO.class);
        vendorRequestVO.setVendorType(vendorType);
        vendorRequestVO.setApplicationServiceId(applicationServiceId);
        ResponseWO<VendorVO> responseWO = VendorService.addVendor(vendorRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
        return responseWO.getData();
    }

    public static void changeVendorStatusToInactive(String vendorId) {
        log.info("Changing vendor status to inactive");
        VendorVO vendorVO = new VendorVO();
        vendorVO.setStatus(VendorStatusEnum.INACTIVE);
        VendorService.updateVendor(vendorVO, vendorId).as(new TypeRef<>() {
        });
    }

    public static VendorVO getFirstVendor(String vendorType, String countryCode) {
        log.info("Get the first vendor");
        ListVendorsRequestVO listVendorsRequestVO = new ListVendorsRequestVO();
        listVendorsRequestVO.setType(vendorType);
        listVendorsRequestVO.setCountryCode(countryCode);
        ResponseWO<List<VendorVO>> responseWO = VendorService.listAllVendors(listVendorsRequestVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
        return responseWO.getData().getFirst();
    }

    public static LoanApplicationVO createLoanApplication(Long programId) {
        LoanCreationRequestVO loanCreationRequestVO = DataProviderUtil.manufacturePojo(LoanCreationRequestVO.class);
        loanCreationRequestVO.setProgramId(programId);

        ResponseWO<LoanApplicationVO> responseWO = LoanApplicationService.createLoanApplication(loanCreationRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(201)
                .hasSameCode("loan.created")
                .hasSameMessage("Loan application created successfully")
                .timestampIsNotNull()
                .dataIsNotNull();

        return responseWO.getData();
    }
}
