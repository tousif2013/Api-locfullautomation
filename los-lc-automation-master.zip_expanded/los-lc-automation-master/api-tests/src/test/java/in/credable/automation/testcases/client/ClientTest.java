package in.credable.automation.testcases.client;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.enums.NotificationModeEnum;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.client.AnchorService;
import in.credable.automation.service.client.ClientService;
import in.credable.automation.service.client.NotificationService;
import in.credable.automation.service.vo.ErrorResponseVO;
import in.credable.automation.service.vo.Page;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.client.*;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.RandomDataGenerator;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ClientTest extends BaseTest {
    private static final List<NotificationModeEnum> NOTIFICATION_MODES = List.of(NotificationModeEnum.SMS, NotificationModeEnum.EMAIL);
    private ClientVO client;
    private Long clientId;
    private ClientThemeVO clientThemeVO;
    private List<ClientFieldMappingVO> clientFieldMappingRequestVO;
    private Long anchorId;

    @Test(description = "Test #67 - Verify if user sends request with valid client data then a new client is created")
    public void verifyCreatingNewClientWithValidData() {
        ClientVO clientRequestVO = DataProviderUtil.manufacturePojo(ClientVO.class);
        ClientVO clientResponseVO = ClientService.createClient(clientRequestVO).as(ClientVO.class);
        FrameworkAssertions.assertThat(clientResponseVO)
                .clientIdIsNotNull()
                .hasSameClientCode(clientRequestVO.getClientCode())
                .hasSameClientName(clientRequestVO.getClientName())
                .hasSameClientEmail(clientRequestVO.getClientEmail())
                .hasSameClientAddress(clientRequestVO.getClientAddress())
                .hasSameClientAddress2(clientRequestVO.getClientAddress2())
                .hasSameClientCity(clientRequestVO.getClientCity())
                .hasSameClientState(clientRequestVO.getClientState())
                .hasSamePinCode(clientRequestVO.getPinCode())
                .hasSameCountryId(clientRequestVO.getCountryId())
                .hasSameOrganizationId(clientRequestVO.getOrganizationId())
                .hasSameClientUserLoginLink(clientRequestVO.getClientUserLoginLink())
                .hasSameCorporateUserLoginLink(clientRequestVO.getCorporateUserLoginLink())
                .hasSameClientAdminName(clientRequestVO.getClientAdminName())
                .hasSameClientAdminEmail(clientRequestVO.getClientAdminEmail())
                .hasSameClientAdminMobile(clientRequestVO.getClientAdminMobile())
                .clientAdminUserIdIsNotNull()
                .hasSameSpocName(clientRequestVO.getSpocName())
                .hasSameSpocEmail(clientRequestVO.getSpocEmail())
                .hasSameSpocMobile(clientRequestVO.getSpocMobile())
                .hasSameDefaultLanguage(clientRequestVO.getDefaultLanguage())
                .hasSameLanguages(clientRequestVO.getLanguages());

        this.client = clientResponseVO;
        this.clientId = clientResponseVO.getId();
    }

    @Test(description = "Test #68 - Verify if user sends request with invalid client data then user gets error response")
    public void verifyCreatingNewClientWithInvalidData() {
        ClientVO clientRequestVO = DataProviderUtil.manufacturePojo(ClientVO.class);
        String invalidClientCode = RandomDataGenerator.generateRandomString(11);
        clientRequestVO.setClientCode(invalidClientCode);
        ErrorResponseVO errorResponseVO = ClientService.createClient(clientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Client code should not exceed 10 characters");
    }

    @Test(description = "Test #71 - Verify if user sends request to update both client code and client name " +
            "then both the details are updated"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData"
            , enabled = false)
    public void verifyUpdatingClientCodeAndName() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        String updatedClientCode = RandomDataGenerator.generateRandomString(10);
        String updatedClientName = RandomDataGenerator.getUuid();
        updateClientRequestVO.setClientCode(updatedClientCode);
        updateClientRequestVO.setClientName(updatedClientName);
        ClientVO updatedClientDetails = ClientService.updateClientDetails(updateClientRequestVO).as(ClientVO.class);

        FrameworkAssertions.assertThat(updatedClientDetails)
                .hasSameClientId(clientId)
                .hasSameClientCode(updatedClientCode)
                .hasSameClientName(updatedClientName);

        ClientVO clientDetails = ClientService.getClientDetails(clientId).as(ClientVO.class);

        FrameworkAssertions.assertThat(updatedClientDetails)
                .as(() -> "Updated client response data should match with get client response")
                .isEqualTo(clientDetails);
    }

    @Test(description = "Test #72 - Verify if user sends request to update client name for a client which is not existing then user gets error response")
    public void verifyUpdatingClientNameForNonExistentClient() {
        long clientId = -1L;
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        String updatedClientName = RandomDataGenerator.generateRandomString(6);
        updateClientRequestVO.setClientName(updatedClientName);
        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("EXCEPTION")
                .hasSameErrorMessage("Client not found. Client Id: " + clientId);
    }

    @Test(description = "Test #73 - Verify if user sends request to get client details by client id " +
            "then user should get client details in the response"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyGettingClientDetails() {
        ClientVO clientDetails = ClientService.getClientDetails(clientId).as(ClientVO.class);

        FrameworkAssertions.assertThat(clientDetails)
                .as(() -> "Get client response data should match with create client response")
                .hasSameClientId(clientId)
                .hasSameClientCode(client.getClientCode())
                .hasSameClientName(client.getClientName())
                .hasSameClientEmail(client.getClientEmail())
                .hasSameClientAddress(client.getClientAddress())
                .hasSameClientAddress2(client.getClientAddress2())
                .hasSameClientCity(client.getClientCity())
                .hasSameClientState(client.getClientState())
                .hasSamePinCode(client.getPinCode())
                .hasSameCountryId(client.getCountryId())
                .hasSameOrganizationId(client.getOrganizationId())
                .hasSameClientUserLoginLink(client.getClientUserLoginLink())
                .hasSameCorporateUserLoginLink(client.getCorporateUserLoginLink())
                .hasSameClientAdminName(client.getClientAdminName())
                .hasSameClientAdminEmail(client.getClientAdminEmail())
                .hasSameClientAdminMobile(client.getClientAdminMobile())
                .hasSameClientAdminUserId(client.getClientAdminUserId())
                .hasSameSpocName(client.getSpocName())
                .hasSameSpocEmail(client.getSpocEmail())
                .hasSameSpocMobile(client.getSpocMobile())
                .hasSameDefaultLanguage(client.getDefaultLanguage())
                .hasSameLanguages(client.getLanguages());
    }

    @Test(description = "Test #74 - Verify if user sends request to get non existing client details by client id " +
            "then user gets error response")
    public void verifyGettingNonExistingClientDetails() {
        long clientId = -1L;
        ErrorResponseVO errorResponseVO = ClientService.getClientDetails(clientId, StatusCode.BAD_REQUEST).as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("EXCEPTION")
                .hasSameErrorMessage("Client not found. Client Id: " + clientId);
    }

    @Test(description = "Test #613 - Verify Successful Saving of Client Theme",
            dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifySavingClientTheme() {
        clientThemeVO = DataProviderUtil.manufacturePojo(ClientThemeVO.class);
        clientThemeVO.setClientId(clientId);

        ResponseWO<String> responseWO = ClientService.saveClientTheme(clientThemeVO).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode(ApiMessageConstants.CLIENT_THEME_SAVE_SUCCESS_MESSAGE)
                .hasSameMessage(ApiMessageConstants.CLIENT_THEME_SAVE_SUCCESS_MESSAGE)
                .timestampIsNotNull()
                .dataIsNotNull();

        Assertions.assertThat(responseWO.getData())
                .as(() -> "Save client theme response data should match with expected data")
                .isEqualTo(ApiMessageConstants.CLIENT_THEME_SAVE_SUCCESS_DATA);
    }

    @Test(description = "Test #614 - Verify Persistence of Saved Client Theme, " +
            "Test #610 - Verify Successful Retrieval of Client Theme, " +
            "Test #611 - Verify Client Theme Properties",
            dependsOnMethods = {"verifyCreatingNewClientWithValidData", "verifySavingClientTheme"})
    public void verifyPersistenceOfSavedClientTheme() {
        ResponseWO<ClientThemeVO> clientThemeVOResponseWO = ClientService.getClientTheme(clientId).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(clientThemeVOResponseWO)
                .hasSameStatus(200)
                .hasSameCode(ApiMessageConstants.CLIENT_THEME_FETCH_SUCCESS_MESSAGE)
                .hasSameMessage(ApiMessageConstants.CLIENT_THEME_FETCH_SUCCESS_MESSAGE)
                .timestampIsNotNull()
                .dataIsNotNull();

        FrameworkAssertions.assertThat(clientThemeVOResponseWO.getData())
                .idIsNotNull()
                .clientIdIs(clientId)
                .headerIs(clientThemeVO.getHeader())
                .footerIs(clientThemeVO.getFooter())
                .buttonIs(clientThemeVO.getButton())
                .brandIs(clientThemeVO.getBrand());
    }

    @Test(description = "Test #1070 - Verify if user sends request to update a client with empty request body " +
            "then user should get error message")
    public void verifyUpdatingClientWithEmptyRequestBody() {
        ClientVO updateClientRequestVO = new ClientVO();
        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.INTERNAL_SERVER_ERROR)
                .as(ErrorResponseVO.class);
        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("EXCEPTION")
                .hasSameErrorMessage("The given id must not be null")
                .timestampIsNotNull();
    }

    @Test(description = "Test #1071 - Verify if user sends request to update a client with only client id in the request body " +
            "then no client data should be updated"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientWithOnlyClientIdInRequestBody() {
        ClientVO clientDetails = ClientService.getClientDetails(clientId).as(ClientVO.class);

        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        ClientVO updatedClientDetails = ClientService.updateClientDetails(updateClientRequestVO)
                .as(ClientVO.class);

        FrameworkAssertions.assertThat(updatedClientDetails)
                .hasSameClientId(clientId)
                .as(() -> "Updated client response data should match with get client response")
                .isEqualTo(clientDetails);
    }

    @Test(description = "Test #1072 - Verify if user sends request to update a client with all the updatable fields in the request body " +
            "then client data should be updated"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientWithAllUpdatableFieldsInRequestBody() {
        ClientVO clientDetailsBeforeUpdating = ClientService.getClientDetails(clientId).as(ClientVO.class);

        ClientVO updateClientRequestVO = DataProviderUtil.manufacturePojo(ClientVO.class);
        updateClientRequestVO.setId(clientId);
        updateClientRequestVO.setClientName(null);
        updateClientRequestVO.setClientCode(null);
        updateClientRequestVO.setClientEmail(null);
        updateClientRequestVO.setClientAdminEmail(null);
        updateClientRequestVO.setClientAdminMobile(null);
        updateClientRequestVO.setCountryId(2L);
        MobileNumberVO spocMobile = new MobileNumberVO();
        spocMobile.setDialingCode("+1");
        spocMobile.setNumber("2345551234");
        updateClientRequestVO.setSpocMobile(spocMobile);

        LanguageVO languageVO = new LanguageVO();
        languageVO.setId(2L);
        List<LanguageVO> languages = updateClientRequestVO.getLanguages();
        languages.add(languageVO);

        updateClientRequestVO.setDefaultLanguage(languageVO);

        ClientVO updatedClientDetails = ClientService.updateClientDetails(updateClientRequestVO)
                .as(ClientVO.class);

        FrameworkAssertions.assertThat(updatedClientDetails)
                .hasSameClientId(clientId)
                .hasSameClientName(clientDetailsBeforeUpdating.getClientName())
                .hasSameClientCode(clientDetailsBeforeUpdating.getClientCode())
                .hasSameClientEmail(clientDetailsBeforeUpdating.getClientEmail())
                .hasSameClientAddress(updateClientRequestVO.getClientAddress())
                .hasSameClientAddress2(updateClientRequestVO.getClientAddress2())
                .hasSameClientCity(updateClientRequestVO.getClientCity())
                .hasSameClientState(updateClientRequestVO.getClientState())
                .hasSamePinCode(updateClientRequestVO.getPinCode())
                .hasSameCountryId(updateClientRequestVO.getCountryId())
                .hasSameOrganizationId(updateClientRequestVO.getOrganizationId())
                .hasSameClientUserLoginLink(updateClientRequestVO.getClientUserLoginLink())
                .hasSameCorporateUserLoginLink(updateClientRequestVO.getCorporateUserLoginLink())
                .hasSameClientAdminName(updateClientRequestVO.getClientAdminName())
                .hasSameClientAdminEmail(clientDetailsBeforeUpdating.getClientAdminEmail())
                .hasSameClientAdminMobile(clientDetailsBeforeUpdating.getClientAdminMobile())
                .clientAdminUserIdIsNotNull()
                .hasSameClientAdminUserId(clientDetailsBeforeUpdating.getClientAdminUserId())
                .hasSameSpocName(updateClientRequestVO.getSpocName())
                .hasSameSpocEmail(updateClientRequestVO.getSpocEmail())
                .hasSameSpocMobile(updateClientRequestVO.getSpocMobile())
                .hasSameLanguageIds(updateClientRequestVO.getLanguages())
                .hasSameDefaultLanguage(updateClientRequestVO.getDefaultLanguage());

        ClientVO clientDetails = ClientService.getClientDetails(clientId).as(ClientVO.class);
        FrameworkAssertions.assertThat(updatedClientDetails)
                .hasSameClientId(clientId)
                .as(() -> "Updated client response data should match with get client response")
                .compareAllNotNullFields(clientDetails);
        this.client = updatedClientDetails;
    }

    @Test(description = "Test #1073 - Verify if user sends request to update client code then user should get error message"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientCode() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        updateClientRequestVO.setClientCode(RandomDataGenerator.generateRandomString(10));
        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Value for the field with the name client code is not allowed to update")
                .timestampIsNotNull();
    }

    @Test(description = "Test #1074 - Verify if user sends request to update client name then user should get error message"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientName() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        updateClientRequestVO.setClientName(RandomDataGenerator.generateRandomString(10));
        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Value for the field with the name client name is not allowed to update")
                .timestampIsNotNull();
    }

    @Test(description = "Test #1075 - Verify if user sends request to update client email then user should get error message"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientEmail() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        updateClientRequestVO.setClientEmail(RandomDataGenerator.generateRandomUniqueEmailId());
        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Value for the field with the name client email is not allowed to update")
                .timestampIsNotNull();
    }

    @Test(description = "Test #1076 - Verify if user sends request to update client admin email then user should get error message"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientAdminEmail() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        updateClientRequestVO.setClientAdminEmail(RandomDataGenerator.generateRandomUniqueEmailId());
        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Value for the field with the name client admin email is not allowed to update")
                .timestampIsNotNull();
    }

    @Test(description = "Test #1077 - Verify if user sends request to update client admin mobile number then user should get error message"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientAdminMobile() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        updateClientRequestVO.setClientAdminMobile(DataProviderUtil.manufacturePojo(MobileNumberVO.class));
        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Value for the field with the name client admin mobile number is not allowed to update")
                .timestampIsNotNull();
    }

    @Test(description = "Test #1078 - Verify if user sends request to update a client with all the updatable fields as empty string in the request body " +
            "then no client data should be updated"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientWithAllUpdatableFieldsAsEmptyStringInRequestBody() {
        ClientVO clientDetails = ClientService.getClientDetails(clientId).as(ClientVO.class);

        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        updateClientRequestVO.setClientAddress("");
        updateClientRequestVO.setClientCity("");
        updateClientRequestVO.setClientState("");
        updateClientRequestVO.setOrganizationId("");
        updateClientRequestVO.setClientUserLoginLink("");
        updateClientRequestVO.setCorporateUserLoginLink("");
        updateClientRequestVO.setSpocName("");
        updateClientRequestVO.setSpocEmail("");

        ClientVO updatedClientDetails = ClientService.updateClientDetails(updateClientRequestVO)
                .as(ClientVO.class);

        FrameworkAssertions.assertThat(updatedClientDetails)
                .hasSameClientId(clientId)
                .as(() -> "Updated client response data should match with get client response")
                .isEqualTo(clientDetails);
    }

    @Test(description = "Test #1079 - Verify if user sends request to update a client with all the updatable fields containing only white spaces in the request body " +
            "then no client data should be updated"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientWithAllUpdatableFieldsContainingOnlyWhiteSpacesInRequestBody() {
        ClientVO clientDetails = ClientService.getClientDetails(clientId).as(ClientVO.class);

        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        updateClientRequestVO.setClientAddress(" ");
        updateClientRequestVO.setClientCity(" ");
        updateClientRequestVO.setClientState(" ");
        updateClientRequestVO.setOrganizationId(" ");
        updateClientRequestVO.setClientUserLoginLink(" ");
        updateClientRequestVO.setCorporateUserLoginLink(" ");
        updateClientRequestVO.setSpocName(" ");
        updateClientRequestVO.setSpocEmail(" ");

        ClientVO updatedClientDetails = ClientService.updateClientDetails(updateClientRequestVO)
                .as(ClientVO.class);

        FrameworkAssertions.assertThat(updatedClientDetails)
                .hasSameClientId(clientId)
                .as(() -> "Updated client response data should match with get client response")
                .isEqualTo(clientDetails);
    }

    @Test(description = "Test #1080 - Verify if user sends request to update a client with invalid pincode " +
            "then user should get error message"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData"
            , enabled = false)
    public void verifyUpdatingClientWithInvalidPincode() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Invalid pincode")
                .timestampIsNotNull();
    }

    @Test(description = "Test #1081 - Verify if user sends request to update a client with invalid SPOC email address " +
            "then user should get error message"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientWithInvalidSpocEmailAddress() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        updateClientRequestVO.setSpocEmail("abc@def");
        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Invalid Client SPOC email")
                .timestampIsNotNull();
    }

    @Test(description = "Test #1082 - Verify if user sends request to update a client with invalid SPOC mobile number " +
            "then user should get error message"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientWithInvalidSpocMobileNumber() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        MobileNumberVO spocMobile = new MobileNumberVO();
        spocMobile.setDialingCode("+91");
        spocMobile.setNumber("12345");
        updateClientRequestVO.setSpocMobile(spocMobile);

        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Invalid SPOC mobile")
                .timestampIsNotNull();
    }

    @Test(description = "Test #1083 - Verify if user sends request to update a client with SPOC mobile number having special characters " +
            "then user should get error message"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientWithSpocMobileNumberContainingSpecialCharacters() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        MobileNumberVO spocMobile = new MobileNumberVO();
        spocMobile.setDialingCode(client.getSpocMobile().getDialingCode());
        spocMobile.setNumber("987-654-3210");
        updateClientRequestVO.setSpocMobile(spocMobile);

        ErrorResponseVO errorResponseVO = ClientService.updateClientDetails(updateClientRequestVO, StatusCode.BAD_REQUEST)
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("INVALID_INPUT")
                .hasSameErrorMessage("Mobile number contains invalid character")
                .timestampIsNotNull();
    }

    @Test(description = "Test #1084 - Verify if user sends request to update a client with all the updatable fields with special characters in the request body " +
            "then client data should be updated"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyUpdatingClientWithSpecialCharactersInRequestBody() {
        ClientVO updateClientRequestVO = new ClientVO();
        updateClientRequestVO.setId(clientId);
        updateClientRequestVO.setClientAddress("8841, Lynch Park $treet");
        updateClientRequestVO.setClientCity("West's Almetabury");
        updateClientRequestVO.setClientState("São Paulo");
        updateClientRequestVO.setOrganizationId("los-lc");
        updateClientRequestVO.setSpocName("Mitsue Conn Jr.");

        ClientVO updatedClientDetails = ClientService.updateClientDetails(updateClientRequestVO)
                .as(ClientVO.class);

        FrameworkAssertions.assertThat(updatedClientDetails)
                .hasSameClientId(clientId)
                .hasSameClientAddress(updateClientRequestVO.getClientAddress())
                .hasSameClientCity(updateClientRequestVO.getClientCity())
                .hasSameClientState(updateClientRequestVO.getClientState())
                .hasSameOrganizationId(updateClientRequestVO.getOrganizationId())
                .hasSameSpocName(updateClientRequestVO.getSpocName());

        ClientVO clientDetails = ClientService.getClientDetails(clientId).as(ClientVO.class);

        FrameworkAssertions.assertThat(updatedClientDetails)
                .hasSameClientId(clientId)
                .as(() -> "Updated client response data should match with get client response")
                .isEqualTo(clientDetails);
    }

    @Test(description = "Test #75 - Verify if user sends request to map client fields with valid data " +
            "then user should get success response"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyAddingClientFieldsMappingWithValidData() {
        ClientFieldMappingVO clientFieldMappingVO = DataProviderUtil.manufacturePojo(ClientFieldMappingVO.class);

        clientFieldMappingRequestVO = List.of(clientFieldMappingVO);
        String response = ClientService.addClientFieldMappings(clientId, clientFieldMappingRequestVO).asString();

        Assertions.assertThat(response)
                .as(() -> "Response body is not expected for adding client fields mapping")
                .isEqualTo("SUCCESS");
    }

    @Test(description = "Test #76 - Verify if user sends request to get client fields mapping with valid data " +
            "then user should get success response",
            dependsOnMethods = {"verifyCreatingNewClientWithValidData", "verifyAddingClientFieldsMappingWithValidData"})
    public void verifyGettingClientFieldMappings() {
        List<ClientFieldMappingVO> clientFieldMapping = Arrays.asList(ClientService.getClientFieldMappings(this.clientId)
                .as(ClientFieldMappingVO[].class));
        Assertions.assertThat(clientFieldMapping)
                .isNotEmpty()
                .allMatch(clientFieldMappingVO -> clientFieldMappingVO.getId() != null && clientFieldMappingVO.getId() > 0)
                .usingElementComparator((o1, o2) -> {
                    if (o1.getStandardFieldName().equals(o2.getStandardFieldName())) {
                        return o1.getClientFieldName().compareTo(o2.getClientFieldName());
                    } else {
                        return o1.getStandardFieldName().compareTo(o2.getStandardFieldName());
                    }
                })
                .isEqualTo(this.clientFieldMappingRequestVO)
                .allMatch(clientFieldMappingVO -> clientFieldMappingVO.getClientId() != null &&
                        clientFieldMappingVO.getClientId().equals(this.clientId));
    }

    @Test(description = "Test #77 - Verify if user sends request to add anchor for a client with valid data " +
            "then user should get success response"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyCreatingAnchorForClient() {
        AnchorVO anchorRequestVO = DataProviderUtil.manufacturePojo(AnchorVO.class);
        AnchorVO anchorResponseVO = AnchorService.createAnchor(clientId, anchorRequestVO).as(AnchorVO.class);

        FrameworkAssertions.assertThat(anchorResponseVO)
                .anchorIdIsNotNull()
                .hasSameClientId(clientId)
                .hasSameAnchorCode(anchorResponseVO.getAnchorCode())
                .hasSameAnchorName(anchorRequestVO.getAnchorName());
        anchorId = anchorResponseVO.getId();
    }

    @Test(description = "Test #78 - Verify if user sends request to get anchor for an existing client then " +
            "user should get success response"
            , dependsOnMethods = {"verifyCreatingNewClientWithValidData", "verifyCreatingAnchorForClient"})
    public void verifyGettingAnchorsForClient() {
        Page<AnchorVO> page = AnchorService.getAnchorsForClient(clientId).as(new TypeRef<>() {
        });

        Assertions.assertThat(page.getContent())
                .as(() -> "Newly created anchor is not present in anchors list for the client")
                .anyMatch(anchorVO -> anchorVO.getId().equals(anchorId));
    }

    @Test(description = "Test #79 - Verify if user sends request to configure notification mode for an existing client " +
            "then user should get success response"
            , dependsOnMethods = "verifyCreatingNewClientWithValidData")
    public void verifyAddingNotificationModeForClient() {
        String responseBody = NotificationService.addNotificationModesForClient(this.clientId, NOTIFICATION_MODES)
                .asString();
        Assertions.assertThat(responseBody)
                .as(() -> "Add notification mode API does not provide expected response")
                .isEqualTo("SUCCESS");
    }

    @Test(description = "Test #80 - Verify if user sends request to get all the configured notification modes for an existing client " +
            "then user should get success response"
            , dependsOnMethods = {"verifyCreatingNewClientWithValidData", "verifyAddingNotificationModeForClient"})
    public void verifyConfiguredNotificationModeForClient() {
        List<NotificationModeVO> notificationModes = NotificationService.getNotificationModesForClient(this.clientId)
                .as(new TypeRef<>() {
                });

        Assertions.assertThat(notificationModes)
                .as(() -> "Notification modes are not as per configured values")
                .extracting(NotificationModeVO::getMode)
                .containsAll(NOTIFICATION_MODES);
    }

    @Test(description = "Test #81 - Verify if user sends request to delete notification modes for an existing client " +
            "then user should get success response"
            , dependsOnMethods = {"verifyCreatingNewClientWithValidData", "verifyAddingNotificationModeForClient"})
    public void verifyDeletingNotificationModeForClient() {
        String responseBody = NotificationService.deleteNotificationModeForClient(this.clientId, NOTIFICATION_MODES)
                .asString();
        Assertions.assertThat(responseBody)
                .as(() -> "Delete notification mode API does not provide expected response")
                .isEqualTo("SUCCESS");

        List<NotificationModeVO> notificationModes = NotificationService.getNotificationModesForClient(this.clientId)
                .as(new TypeRef<>() {
                });
        Assertions.assertThat(notificationModes)
                .as(() -> "Notification modes are not deleted for client")
                .isEmpty();
    }

    @AfterClass()
    public void deleteClientAdminUser() {
        if (client != null && StringUtils.isNotBlank(client.getClientAdminUserId())) {
            TestHelper.deleteUser(client.getClientAdminUserId());
        }
    }
}
