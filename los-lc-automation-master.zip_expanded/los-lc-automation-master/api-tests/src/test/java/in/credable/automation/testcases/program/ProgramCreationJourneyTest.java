package in.credable.automation.testcases.program;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.assertions.JsonPathAssertion;
import in.credable.automation.commons.utils.SerializationUtil;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.bre.BREService;
import in.credable.automation.service.client.AnchorService;
import in.credable.automation.service.module.ModuleService;
import in.credable.automation.service.platform.PlatformService;
import in.credable.automation.service.program.ProgramService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.SuccessResponseVO;
import in.credable.automation.service.vo.bre.BreVO;
import in.credable.automation.service.vo.bre.RuleDetailVO;
import in.credable.automation.service.vo.bre.RuleNames;
import in.credable.automation.service.vo.client.AnchorSubSegmentVO;
import in.credable.automation.service.vo.client.AnchorVO;
import in.credable.automation.service.vo.client.LanguageVO;
import in.credable.automation.service.vo.integration.VendorType;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.service.vo.module.ModuleCode;
import in.credable.automation.service.vo.module.ModuleInstanceVO;
import in.credable.automation.service.vo.module.ModuleVO;
import in.credable.automation.service.vo.module.ProgramModuleInstanceVO;
import in.credable.automation.service.vo.platform.CountryVO;
import in.credable.automation.service.vo.platform.ProductTypesVO;
import in.credable.automation.service.vo.platform.ServiceProviderVO;
import in.credable.automation.service.vo.program.ProgramStatus;
import in.credable.automation.service.vo.program.ProgramThemeVO;
import in.credable.automation.service.vo.program.ProgramVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.RandomDataGenerator;
import in.credable.automation.utils.TestConstants;
import in.credable.automation.utils.TestUtils;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;

public class ProgramCreationJourneyTest extends BaseTest {
    private static final Long CLIENT_ID = 19L;
    private Long programId;
    private static final long COUNTRY_ID = 1L;
    private static final Random RANDOM = new Random();
    private static final HashMap<ModuleCode, ModuleVO> MODULE_LIST_MAP = new HashMap<>();
    private static final HashMap<ModuleCode, ModuleInstanceVO> PROGRAM_MODULE_INSTANCES_MAP = new HashMap<>();
    private static final HashMap<String, RuleDetailVO> BRE_RULE_DETAILS_MAP = new HashMap<>();
    private static final String VENDOR_ID = "vendorId";
    private static final String RULE_ID = "ruleId";
    private static final String RULE_NAME = "ruleName";
    private static final String RULE_VERSION = "ruleVersion";
    private static final String RULE_TYPE = "ruleType";


    @Test(description = "Test#934-Program Creation-Get All Anchor.," +
            "Test#935-Program Creation-Get All Product Types.," +
            "Test#936-Program Creation-Get All Product category.," +
            "Test#937-Program Creation-Get anchor subsegment.," +
            "Test#938-Program Creation-create Program.")
    public void programDetailPage() {
        /*Get all anchor*/
        List<AnchorVO> anchorVO = Arrays.asList(AnchorService.getAllAnchors(CLIENT_ID).as(AnchorVO[].class));
        Long anchorId = anchorVO.get(RANDOM.nextInt(anchorVO.size())).getId();
        //Remove later
        anchorId = 7L;

        /*Get all productTypes*/
        List<ProductTypesVO> productTypesVO = Arrays.asList(PlatformService.getProductTypes().as(ProductTypesVO[].class));
        Long productTypesId = productTypesVO.get(RANDOM.nextInt(productTypesVO.size())).getId();

        /*Get all product Categories*/
        Response productCategories = PlatformService.getProductCategories();
        JsonPathAssertion.assertThat(productCategories).assertNotNull("[0].id");
        Long productCategoriesId = productCategories.jsonPath().getLong("[0].id");

        /*Get all anchor Subsegment*/
        List<AnchorSubSegmentVO> anchorSubSegmentVO = Arrays.asList(AnchorService.getAnchorSubsegment(anchorId)
                .as(AnchorSubSegmentVO[].class));
        Long anchorSubsegmentId;
        if (CollectionUtils.isNotEmpty(anchorSubSegmentVO)) {
            anchorSubsegmentId = anchorSubSegmentVO.get(RANDOM.nextInt(anchorSubSegmentVO.size())).getId();
        }

        /*create program*/
        ProgramVO programVO = new ProgramVO();
        programVO.setClientId(CLIENT_ID);
        programVO.setProgramCode("TEST");
        programVO.setProgramName("AUTOProgram" + RandomDataGenerator.getUuid());
        programVO.setProductCategoryId(productCategoriesId);
        programVO.setProductTypeId(productTypesId);
        programVO.setAnchorId(anchorId);
        programVO.setCountryId(COUNTRY_ID);
        ProgramVO programVO2 = ProgramService.createProgram(programVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(programVO2)
                .programIdIsNotNull()
                .hasSameProgramCode(programVO.getProgramCode())
                .hasSameProgramName(programVO.getProgramName())
                .hasSameClientId(programVO.getClientId())
                .hasSameAnchorId(programVO.getAnchorId())
                .hasSameProgramStatus(ProgramStatus.DRAFT)
                .hasSameProductCategoryId(programVO.getProductCategoryId())
                .hasSameProductTypeId(programVO.getProductTypeId())
                .hasSameCountryId(programVO.getCountryId());
        programId = programVO2.getId();
    }

    /*@Test(description = "Test#604-Verify Successful Retrieval of Program Theme.," +
            "Test#607-Verify Successful Saving of Program Theme." +
            "Test#608-Verify Persistence of Saved Program Theme."
            , dependsOnMethods = "programDetailPage")*/
    public void verifyThemeConfigurationPage() {
        /*Theme Configuration*/
        /*Get program Theme*/
        ResponseWO<ProgramThemeVO> getProgramThemerResponseWO = ProgramService.getProgramTheme(programId).as(new TypeRef<>() {
        });
        /*FrameworkAssertions.assertThat(getProgramThemerResponseWO)
                .dataIsNotNull()
                .hasSameMessage(ApiMessageConstants.PROGRAM_THEME_FETCH_MESSAGE);*/

        /*Save program Theme*/
        ProgramThemeVO programThemeVO = new ProgramThemeVO();
        programThemeVO.setProgramId(programId);
        programThemeVO.setHeaderImageUrl("");
        programThemeVO.setHeaderSkinColor("#411f1f");
        programThemeVO.setPrimaryButtonColor("#2e5a2c");
        programThemeVO.setSecondaryButtonColor("#315054");
        programThemeVO.setFontName("Georgia");
        programThemeVO.setFooterImageUrl("");
        programThemeVO.setFooterSkinColor("#3b3519");
        programThemeVO.setFooterText("ABC Footers");
        programThemeVO.setHeaderImage(TestUtils.getFileFromResources(TestConstants.TEST_IMAGE_PATH_FROM_RESOURCES_ROOT).toString());
        programThemeVO.setFooterImage(TestUtils.getFileFromResources(TestConstants.TEST_IMAGE_PATH_FROM_RESOURCES_ROOT).toString());
        ResponseWO saveProgramThemeResponseWO = ProgramService.saveProgramTheme(programThemeVO, programId).as(ResponseWO.class);
        FrameworkAssertions.assertThat(saveProgramThemeResponseWO)
                .hasSameMessage(ApiMessageConstants.PROGRAM_THEME_SAVE_MESSAGE)
                .dataIsNotNull();

        //Test#608
        ResponseWO<ProgramThemeVO> getProgramThemerResponseWO2 = ProgramService.getProgramTheme(programId).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(getProgramThemerResponseWO2)
                .dataIsNotNull()
                .hasSameMessage(ApiMessageConstants.PROGRAM_THEME_FETCH_MESSAGE);
        //Bug
        /*FrameworkAssertions.assertThat(getProgramThemerResponseWO2.getData())
                .hasSameProgramId(programId)
                .hasSameheaderSkinColor(programThemeVO.getHeaderSkinColor())
                .hasSamePrimaryButtonColor(programThemeVO.getPrimaryButtonColor())
                .hasSameSecondaryButtonColor(programThemeVO.getSecondaryButtonColor())
                .hasSamefontName(programThemeVO.getFontName())
                .hasSameFooterText(programThemeVO.getFooterText());*/
    }

    @Test(description = "Test#939-Program Creation-get all country.," +
            "Test#940-Program Creation-get all language.," +
            "Test#941-Program Creation-get Email service provider.," +
            "Test#942-Program Creation-get SMS service provider.," +
            "Test#943-Program Creation-get Chatbot service provider.," +
            "Test#944-Program Creation-get Marketing Automation service provider.," +
            "Test#954-Program Creation-update Basic configuration.", dependsOnMethods = "programDetailPage")
    public void verifyBasicConfigurationPage() {
        /*All country*/
        List<CountryVO> countryVO = Arrays.asList(PlatformService.getAllCountry().as(CountryVO[].class));
        // countryId = countryVO.get(random.nextInt(countryVO.size())).getId();

        /*All Language*/
        List<LanguageVO> languageVO = Arrays.asList(PlatformService.getAllLanguage().as(LanguageVO[].class));
        Long languageId = languageVO.get(RANDOM.nextInt(languageVO.size())).getId();

        /*Get all Email Service provider*/
        List<ServiceProviderVO> emailServiceProviderVO = Arrays.asList(PlatformService.getAllEmailServiceProvider()
                .as(ServiceProviderVO[].class));
        Long emailServiceProviderId = emailServiceProviderVO.get(RANDOM.nextInt(emailServiceProviderVO.size())).getId();

        /*Get all SMS Service provider*/
        List<ServiceProviderVO> smsServiceProviderVO = Arrays.asList(PlatformService.getAllSMSServiceProvider()
                .as(ServiceProviderVO[].class));
        Long smsServiceProviderId = smsServiceProviderVO.get(RANDOM.nextInt(smsServiceProviderVO.size())).getId();

        /*Get all Chat Bot Service provider*/
        List<ServiceProviderVO> chatBotServiceProviderVO = Arrays.asList(PlatformService.getAllChatBotServiceProvider()
                .as(ServiceProviderVO[].class));
        Long chatBotServiceProviderId = chatBotServiceProviderVO.get(RANDOM.nextInt(chatBotServiceProviderVO.size())).getId();

        /*Get all Marketing Automation Service provider*/
        List<ServiceProviderVO> marketingAutomationServiceProviderVO = Arrays
                .asList(PlatformService.getAllMarketingAutomationServiceProvider()
                        .as(ServiceProviderVO[].class));
        Long marketingAutomationServiceProviderId = marketingAutomationServiceProviderVO
                .get(RANDOM.nextInt(marketingAutomationServiceProviderVO.size())).getId();

        /*Update Basic configuration*/
        ProgramVO programVO = new ProgramVO();
        programVO.setCountryId(COUNTRY_ID);
        programVO.setChatBotServiceProviderId(chatBotServiceProviderId);
        programVO.setDefaultLanguageId(languageId);
        programVO.setEmailServiceProviderId(emailServiceProviderId);
        programVO.setId(programId);
        List languageIdList = new ArrayList();
        languageIdList.add(languageId);
        programVO.setLanguageIds(languageIdList);
        programVO.setMarketingAutomationServiceProviderId(marketingAutomationServiceProviderId);
        programVO.setSmsServiceProviderId(smsServiceProviderId);
        ProgramVO programWO = ProgramService.updateProgram(programVO).as(new TypeRef<>() {
        });
        //Use it later/ after bug fix.
        FrameworkAssertions.assertThat(programWO)
                .programIdIsNotNull()
                .hasSameCountryId(COUNTRY_ID)
                .hasSameChatBotServiceProviderId(chatBotServiceProviderId)
                .hasSameDefaultLanguageId(languageId)
                .hasSameEmailServiceProviderId(emailServiceProviderId)
                .hasSameMarketingAutomationServiceProviderId(marketingAutomationServiceProviderId)
                .hasSameSMSServiceProviderId(smsServiceProviderId)
                .hasSameLanguageIdList(languageIdList);
    }

    //Skipping Borrower onboarding & client field mapping
    @Test(description = "Test#970-Program Creation-get modules for country." +
            "Test#971-Program Creation-create module instance." +
            "Test#972-Program Creation-Associate module instance.",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage"})
    public void verifyJourneyModule() {
        /*Capturing all the moduleId*/
        List<ModuleVO> moduleList = Arrays.asList(ModuleService.getModulesForCountry(COUNTRY_ID).as(ModuleVO[].class));
        for (ModuleVO moduleVO : moduleList) {
            /*Creating  all the modules*/
            ModuleInstanceVO moduleInstanceVO = new ModuleInstanceVO();
            moduleInstanceVO.setModuleId(moduleVO.getId());
            ModuleInstanceVO moduleInstanceResponseVO = ModuleService.createModuleInstance(moduleInstanceVO).as(ModuleInstanceVO.class);
            /*Associate for modules*/
            SuccessResponseVO successResponseVO = ModuleService.associateModuleInstanceWithProgram(programId, moduleInstanceResponseVO.getId()).as(new TypeRef<>() {
            });
            FrameworkAssertions.assertThat(successResponseVO)
                    .hasSameResponseCode("SUCCESS")
                    .hasSameResponseMessage(ApiMessageConstants.MODULE_INSTANCE_ASSOCIATE_MESSAGE + programId);
        }
    }

    @Test(description = "Test#996-Program Creation-Get all modules." +
            "Test#997-Program Creation-module instance associated with program.",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage", "verifyJourneyModule"})
    public void journeyModuleConfig() {
        /*get modules*/
        List<ModuleVO> moduleResponseWO = Arrays.asList(ModuleService.getAllModules().as(ModuleVO[].class));
        for (ModuleVO module : moduleResponseWO) {
            MODULE_LIST_MAP.put(module.getModuleCode(), module);
        }
        /*getProgramModuleInstancesByProgramId*/
        List<ProgramModuleInstanceVO> programModuleInstanceWO = Arrays.asList(ModuleService.getProgramModuleInstancesByProgramId(programId).as(ProgramModuleInstanceVO[].class));
        for (ProgramModuleInstanceVO programModuleInstanceVO : programModuleInstanceWO) {
            PROGRAM_MODULE_INSTANCES_MAP.put(programModuleInstanceVO.getModuleInstanceVO().getModuleCode(), programModuleInstanceVO.getModuleInstanceVO());
        }
        /*get List of rules*/
        BreVO<List<RuleDetailVO>> responseWO = BREService.listAllRules().as(new TypeRef<>() {
        });
        for (RuleDetailVO ruleDetailVO : responseWO.getData()) {
            BRE_RULE_DETAILS_MAP.put(ruleDetailVO.getName(), ruleDetailVO);
        }
    }

    @Test(description = "Test#999-Program Creation-module-Business Detail/Tell us about your self.",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage", "verifyJourneyModule", "journeyModuleConfig"})
    public void businessDetailModule() {
        //Tell us about yourself
        ModuleCode moduleCode = ModuleCode.USER_INFO_FORM;
        ModuleInstanceVO businessDetailModuleInstance = PROGRAM_MODULE_INSTANCES_MAP.get(moduleCode);
        businessDetailModuleInstance.setModuleInstanceUiJson(MODULE_LIST_MAP.get(moduleCode).getModuleJson());
        businessDetailModuleInstance.setModuleVO(MODULE_LIST_MAP.get(moduleCode));
        ModuleInstanceVO moduleInstanceVO = ModuleService.updateModuleInstance(businessDetailModuleInstance).as(ModuleInstanceVO.class);
        FrameworkAssertions.assertThat(moduleInstanceVO)
                .hasSameModuleId(businessDetailModuleInstance.getModuleId())
                .hasSameId(businessDetailModuleInstance.getId())
                .hasSameModuleInstanceDataKey(businessDetailModuleInstance.getModuleInstanceDataKey())
                .hasSameModuleInstanceDataJson(businessDetailModuleInstance.getModuleInstanceDataJson())
                .hasSameModuleCode(businessDetailModuleInstance.getModuleCode())
                .hasSameTaskType(businessDetailModuleInstance.getTaskType());

    }

    @Test(description = "Test#1009-Program Creation-module-Bank Statement page.",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage", "verifyJourneyModule", "journeyModuleConfig"})
    public void bankStatementPageModuleSetup() {
        ModuleCode moduleCode = ModuleCode.BANK_STATEMENTS_FORM;
        //Update moduleInstance
        ModuleInstanceVO bankStatementPageModuleInstance = PROGRAM_MODULE_INSTANCES_MAP.get(moduleCode);
        bankStatementPageModuleInstance.setModuleInstanceUiJson(MODULE_LIST_MAP.get(moduleCode).getModuleJson());
        bankStatementPageModuleInstance.setModuleVO(MODULE_LIST_MAP.get(moduleCode));
        ModuleInstanceVO moduleInstanceVO = ModuleService.updateModuleInstance(bankStatementPageModuleInstance).as(ModuleInstanceVO.class);
        FrameworkAssertions.assertThat(moduleInstanceVO)
                .hasSameModuleId(bankStatementPageModuleInstance.getModuleId())
                .hasSameId(bankStatementPageModuleInstance.getId())
                .hasSameModuleInstanceDataKey(bankStatementPageModuleInstance.getModuleInstanceDataKey())
                .hasSameModuleInstanceDataJson(bankStatementPageModuleInstance.getModuleInstanceDataJson())
                .hasSameModuleCode(bankStatementPageModuleInstance.getModuleCode())
                .hasSameTaskType(bankStatementPageModuleInstance.getTaskType());
    }

    /*@Test(description = "Test#1010-Program Creation module-Bank Statement submitted page.",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage", "verifyJourneyModule", "journeyModuleConfig"})*/
    public void bankStatementSubmittedModuleSetup() {
        ModuleCode moduleCode = ModuleCode.BANK_STATEMENTS_SUBMITTED;
        //Update moduleInstance
        ModuleInstanceVO bankStatementSubmittedModuleInstance = PROGRAM_MODULE_INSTANCES_MAP.get(moduleCode);
        bankStatementSubmittedModuleInstance.setModuleInstanceUiJson(MODULE_LIST_MAP.get(moduleCode).getModuleJson());
        bankStatementSubmittedModuleInstance.setModuleVO(MODULE_LIST_MAP.get(moduleCode));
        ModuleInstanceVO moduleInstanceVO = ModuleService.updateModuleInstance(bankStatementSubmittedModuleInstance).as(ModuleInstanceVO.class);
        FrameworkAssertions.assertThat(moduleInstanceVO)
                .hasSameModuleId(bankStatementSubmittedModuleInstance.getModuleId())
                .hasSameId(bankStatementSubmittedModuleInstance.getId())
                .hasSameModuleInstanceDataKey(bankStatementSubmittedModuleInstance.getModuleInstanceDataKey())
                .hasSameModuleInstanceDataJson(bankStatementSubmittedModuleInstance.getModuleInstanceDataJson())
                .hasSameModuleCode(bankStatementSubmittedModuleInstance.getModuleCode())
                .hasSameTaskType(bankStatementSubmittedModuleInstance.getTaskType());
    }

    @Test(description = "Test#1008-Program Creation-module-Bank Statement Analysis.",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage", "verifyJourneyModule", "journeyModuleConfig"})
    public void bankStatementAnalysisModuleSetup() {
        ModuleCode moduleCode = ModuleCode.BANK_STATEMENTS_ANALYSIS;
        String ruleName = RuleNames.BANK_STATEMENT_ANALYSIS_RULE;
        String ruleid = (BRE_RULE_DETAILS_MAP.get(ruleName)).getBaseId();
        //Get vendorId
        VendorVO vendor = TestHelper.getFirstVendor(VendorType.BSA.name(), "");
        String vendorId = vendor.getId();
        //Update moduleInstance
        ModuleInstanceVO bankStatementAnalysisModuleInstance = PROGRAM_MODULE_INSTANCES_MAP.get(moduleCode);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(VENDOR_ID, vendorId);
        valuesMap.put(RULE_ID, ruleid);
        valuesMap.put(RULE_NAME, BRE_RULE_DETAILS_MAP.get(ruleName).getName());
        valuesMap.put(RULE_VERSION, BRE_RULE_DETAILS_MAP.get(ruleName).getVersion());
        valuesMap.put(RULE_TYPE, BRE_RULE_DETAILS_MAP.get(ruleName).getType());
        File fileFromResources = TestUtils.getFileFromResources(TestConstants.BANK_STATEMENT_ANALYSIS_JSON_FROM_RESOURCES_ROOT);
        String jsonContent = SerializationUtil.readJsonFile(fileFromResources);
        jsonContent = TestUtils.substituteString(jsonContent, valuesMap);
        bankStatementAnalysisModuleInstance.setModuleInstanceDataJson(jsonContent);
        bankStatementAnalysisModuleInstance.setModuleVO(MODULE_LIST_MAP.get(moduleCode));
        ModuleInstanceVO moduleInstanceVO = ModuleService.updateModuleInstance(bankStatementAnalysisModuleInstance).as(ModuleInstanceVO.class);
        FrameworkAssertions.assertThat(moduleInstanceVO)
                .hasSameModuleId(bankStatementAnalysisModuleInstance.getModuleId())
                .hasSameId(bankStatementAnalysisModuleInstance.getId())
                .hasSameModuleInstanceDataKey(bankStatementAnalysisModuleInstance.getModuleInstanceDataKey())
                .hasSameModuleInstanceDataJson(bankStatementAnalysisModuleInstance.getModuleInstanceDataJson())
                .hasSameModuleCode(bankStatementAnalysisModuleInstance.getModuleCode())
                .hasSameTaskType(bankStatementAnalysisModuleInstance.getTaskType());
    }

    @Test(description = "Test#810-Verify the Selected GST report type should passed into the API payload",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage", "verifyJourneyModule", "journeyModuleConfig"})
    public void gstAnalysisModuleSetup() {
        ModuleCode moduleName = ModuleCode.GST_ORDER_SERVICE_TASK;
        String ruleName = RuleNames.GST_ANALYSIS_RULE;
        String ruleid = (BRE_RULE_DETAILS_MAP.get(ruleName)).getBaseId();
        //GST module setup
        //Get vendorId
        VendorVO vendor = TestHelper.getFirstVendor(VendorType.PAN_GST.getValue(), "");
        String vendorId = vendor.getId();
        //Update moduleInstance
        ModuleInstanceVO gstModuleInstance = PROGRAM_MODULE_INSTANCES_MAP.get(moduleName);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(VENDOR_ID, vendorId);
        valuesMap.put(RULE_ID, ruleid);
        valuesMap.put(RULE_NAME, BRE_RULE_DETAILS_MAP.get(ruleName).getName());
        valuesMap.put(RULE_VERSION, BRE_RULE_DETAILS_MAP.get(ruleName).getVersion());
        valuesMap.put(RULE_TYPE, BRE_RULE_DETAILS_MAP.get(ruleName).getType());
        File fileFromResources = TestUtils.getFileFromResources(TestConstants.GST_ANALYSIS_JSON_FROM_RESOURCES_ROOT);
        String jsonContent = SerializationUtil.readJsonFile(fileFromResources);
        jsonContent = TestUtils.substituteString(jsonContent, valuesMap);
        gstModuleInstance.setModuleInstanceDataJson(jsonContent);
        gstModuleInstance.setModuleVO(MODULE_LIST_MAP.get(moduleName));
        ModuleInstanceVO moduleInstanceVO = ModuleService.updateModuleInstance(gstModuleInstance).as(ModuleInstanceVO.class);
        FrameworkAssertions.assertThat(moduleInstanceVO)
                .hasSameModuleId(gstModuleInstance.getModuleId())
                .hasSameId(gstModuleInstance.getId())
                .hasSameModuleInstanceDataKey(gstModuleInstance.getModuleInstanceDataKey())
                .hasSameModuleInstanceDataJson(gstModuleInstance.getModuleInstanceDataJson())
                .hasSameModuleCode(gstModuleInstance.getModuleCode())
                .hasSameTaskType(gstModuleInstance.getTaskType());
    }

    @Test(description = "Test#808-Verify the Credit Bureau Setup Module on Module Configuration section",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage", "verifyJourneyModule", "journeyModuleConfig"})
    public void creditBureauCheckModuleSetup() {
        ModuleCode moduleCode = ModuleCode.CREDIT_BUREAU_VALIDATION;
        String ruleName = RuleNames.CREDIT_BUREAU_CHECK_RULE;
        String ruleid = (BRE_RULE_DETAILS_MAP.get(ruleName)).getBaseId();
        //Update moduleInstance
        ModuleInstanceVO creditBureauCheckModuleInstance = PROGRAM_MODULE_INSTANCES_MAP.get(moduleCode);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(RULE_ID, ruleid);
        valuesMap.put(RULE_NAME, BRE_RULE_DETAILS_MAP.get(ruleName).getName());
        valuesMap.put(RULE_VERSION, BRE_RULE_DETAILS_MAP.get(ruleName).getVersion());
        valuesMap.put(RULE_TYPE, BRE_RULE_DETAILS_MAP.get(ruleName).getType());
        File fileFromResources = TestUtils.getFileFromResources(TestConstants.CREDIT_BUREAU_VALIDATION_JSON_FROM_RESOURCES_ROOT);
        String jsonContent = SerializationUtil.readJsonFile(fileFromResources);
        jsonContent = TestUtils.substituteString(jsonContent, valuesMap);
        creditBureauCheckModuleInstance.setModuleInstanceDataJson(jsonContent);
        creditBureauCheckModuleInstance.setModuleVO(MODULE_LIST_MAP.get(moduleCode));
        ModuleInstanceVO moduleInstanceVO = ModuleService.updateModuleInstance(creditBureauCheckModuleInstance).as(ModuleInstanceVO.class);
        FrameworkAssertions.assertThat(moduleInstanceVO)
                .hasSameModuleId(creditBureauCheckModuleInstance.getModuleId())
                .hasSameId(creditBureauCheckModuleInstance.getId())
                .hasSameModuleInstanceDataKey(creditBureauCheckModuleInstance.getModuleInstanceDataKey())
                .hasSameModuleInstanceDataJson(creditBureauCheckModuleInstance.getModuleInstanceDataJson())
                .hasSameModuleCode(creditBureauCheckModuleInstance.getModuleCode())
                .hasSameTaskType(creditBureauCheckModuleInstance.getTaskType());
    }

    @Test(description = "Test#998-Program Creation-module-Company Detail_MCA.",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage", "verifyJourneyModule", "journeyModuleConfig"})
    public void companyDetailsMCAModuleSetup() {
        ModuleCode moduleCode = ModuleCode.COMPANY_DETAILS;
        String ruleName = RuleNames.COMPANY_DETAILS_MCA_RULE;
        String ruleid = (BRE_RULE_DETAILS_MAP.get(ruleName)).getBaseId();
        //Get vendorId
        VendorVO vendor = TestHelper.getFirstVendor(VendorType.MCA.getValue(), "");
        String vendorId = vendor.getId();
        //Update moduleInstance
        ModuleInstanceVO companyDetailsMCAModuleInstance = PROGRAM_MODULE_INSTANCES_MAP.get(moduleCode);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(VENDOR_ID, vendorId);
        valuesMap.put(RULE_ID, ruleid);
        valuesMap.put(RULE_NAME, BRE_RULE_DETAILS_MAP.get(ruleName).getName());
        valuesMap.put(RULE_VERSION, BRE_RULE_DETAILS_MAP.get(ruleName).getVersion());
        valuesMap.put(RULE_TYPE, BRE_RULE_DETAILS_MAP.get(ruleName).getType());
        File fileFromResources = TestUtils.getFileFromResources(TestConstants.COMPANY_DETAILS_MCA_JSON_FROM_RESOURCES_ROOT);
        String jsonContent = SerializationUtil.readJsonFile(fileFromResources);
        jsonContent = TestUtils.substituteString(jsonContent, valuesMap);
        companyDetailsMCAModuleInstance.setModuleInstanceDataJson(jsonContent);
        companyDetailsMCAModuleInstance.setModuleVO(MODULE_LIST_MAP.get(moduleCode));
        ModuleInstanceVO moduleInstanceVO = ModuleService.updateModuleInstance(companyDetailsMCAModuleInstance).as(ModuleInstanceVO.class);
        FrameworkAssertions.assertThat(moduleInstanceVO)
                .hasSameModuleId(companyDetailsMCAModuleInstance.getModuleId())
                .hasSameId(companyDetailsMCAModuleInstance.getId())
                .hasSameModuleInstanceDataKey(companyDetailsMCAModuleInstance.getModuleInstanceDataKey())
                .hasSameModuleInstanceDataJson(companyDetailsMCAModuleInstance.getModuleInstanceDataJson())
                .hasSameModuleCode(companyDetailsMCAModuleInstance.getModuleCode())
                .hasSameTaskType(companyDetailsMCAModuleInstance.getTaskType());
    }

    @Test(description = "Test#1014-Program Creation module-BRE module config page.",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage",
                    "verifyJourneyModule", "journeyModuleConfig"})
    public void breModuleSetup() {
        ModuleCode moduleCode = ModuleCode.BRE;
        String ruleName = RuleNames.BRE_RULE;
        //Get vendorId
        String ruleid = (BRE_RULE_DETAILS_MAP.get(ruleName)).getBaseId();
        //Update moduleInstance
        ModuleInstanceVO companyDetailsMCAModuleInstance = PROGRAM_MODULE_INSTANCES_MAP.get(moduleCode);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(RULE_ID, ruleid);
        valuesMap.put(RULE_NAME, BRE_RULE_DETAILS_MAP.get(ruleName).getName());
        valuesMap.put(RULE_VERSION, BRE_RULE_DETAILS_MAP.get(ruleName).getVersion());
        valuesMap.put(RULE_TYPE, BRE_RULE_DETAILS_MAP.get(ruleName).getType());
        File fileFromResources = TestUtils.getFileFromResources(TestConstants.BRE_JSON_FROM_RESOURCES_ROOT);
        String jsonContent = SerializationUtil.readJsonFile(fileFromResources);
        jsonContent = TestUtils.substituteString(jsonContent, valuesMap);
        companyDetailsMCAModuleInstance.setModuleInstanceDataJson(jsonContent);
        companyDetailsMCAModuleInstance.setModuleVO(MODULE_LIST_MAP.get(moduleCode));
        ModuleInstanceVO moduleInstanceVO = ModuleService.updateModuleInstance(companyDetailsMCAModuleInstance).as(ModuleInstanceVO.class);
        FrameworkAssertions.assertThat(moduleInstanceVO)
                .hasSameModuleId(companyDetailsMCAModuleInstance.getModuleId())
                .hasSameId(companyDetailsMCAModuleInstance.getId())
                .hasSameModuleInstanceDataKey(companyDetailsMCAModuleInstance.getModuleInstanceDataKey())
                .hasSameModuleInstanceDataJson(companyDetailsMCAModuleInstance.getModuleInstanceDataJson())
                .hasSameModuleCode(companyDetailsMCAModuleInstance.getModuleCode())
                .hasSameTaskType(companyDetailsMCAModuleInstance.getTaskType());
    }

    @Test(description = "Test#1015-Program Creation module-PAN to gst config page.",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage",
                    "verifyJourneyModule", "journeyModuleConfig"})
    public void panToGstModuleSetup() {
        ModuleCode moduleCode = ModuleCode.PAN_TO_GST;
        //Get vendorId
        VendorVO vendor = TestHelper.getFirstVendor(VendorType.PAN_GST.getValue(), "");
        String vendorId = vendor.getId();
        //Update moduleInstance
        ModuleInstanceVO companyDetailsMCAModuleInstance = PROGRAM_MODULE_INSTANCES_MAP.get(moduleCode);
        Map<String, Object> valuesMap = new HashMap<>();
        valuesMap.put(VENDOR_ID, vendorId);
        File fileFromResources = TestUtils.getFileFromResources(TestConstants.PAN_TO_GST_JSON_FROM_RESOURCES_ROOT);
        String jsonContent = SerializationUtil.readJsonFile(fileFromResources);
        jsonContent = TestUtils.substituteString(jsonContent, valuesMap);
        companyDetailsMCAModuleInstance.setModuleInstanceDataJson(jsonContent);
        companyDetailsMCAModuleInstance.setModuleVO(MODULE_LIST_MAP.get(moduleCode));
        ModuleInstanceVO moduleInstanceVO = ModuleService.updateModuleInstance(companyDetailsMCAModuleInstance).as(ModuleInstanceVO.class);
        FrameworkAssertions.assertThat(moduleInstanceVO)
                .hasSameModuleId(companyDetailsMCAModuleInstance.getModuleId())
                .hasSameId(companyDetailsMCAModuleInstance.getId())
                .hasSameModuleInstanceDataKey(companyDetailsMCAModuleInstance.getModuleInstanceDataKey())
                .hasSameModuleInstanceDataJson(companyDetailsMCAModuleInstance.getModuleInstanceDataJson())
                .hasSameModuleCode(companyDetailsMCAModuleInstance.getModuleCode())
                .hasSameTaskType(companyDetailsMCAModuleInstance.getTaskType());
    }

    @Test(description = "Test#1005-Verify the module configurator Sanction Letter API.",
            dependsOnMethods = {"programDetailPage", "verifyBasicConfigurationPage",
                    "verifyJourneyModule", "journeyModuleConfig"})
    public void sanctionLettertModuleSetup() {
        ModuleCode moduleCode = ModuleCode.SANCTION_LETTER_SETUP;
        //Update moduleInstance
        ModuleInstanceVO companyDetailsMCAModuleInstance = PROGRAM_MODULE_INSTANCES_MAP.get(moduleCode);
        File fileFromResources = TestUtils.getFileFromResources(TestConstants.SANCTION_LETTER_FROM_RESOURCES_ROOT);
        String jsonContent = SerializationUtil.readJsonFile(fileFromResources);
        companyDetailsMCAModuleInstance.setModuleInstanceDataJson(jsonContent);
        companyDetailsMCAModuleInstance.setModuleVO(MODULE_LIST_MAP.get(moduleCode));
        ModuleInstanceVO moduleInstanceVO = ModuleService.updateModuleInstance(companyDetailsMCAModuleInstance).as(ModuleInstanceVO.class);
        FrameworkAssertions.assertThat(moduleInstanceVO)
                .hasSameModuleId(companyDetailsMCAModuleInstance.getModuleId())
                .hasSameId(companyDetailsMCAModuleInstance.getId())
                .hasSameModuleInstanceDataKey(companyDetailsMCAModuleInstance.getModuleInstanceDataKey())
                .hasSameModuleInstanceDataJson(companyDetailsMCAModuleInstance.getModuleInstanceDataJson())
                .hasSameModuleCode(companyDetailsMCAModuleInstance.getModuleCode())
                .hasSameTaskType(companyDetailsMCAModuleInstance.getTaskType());
    }
}