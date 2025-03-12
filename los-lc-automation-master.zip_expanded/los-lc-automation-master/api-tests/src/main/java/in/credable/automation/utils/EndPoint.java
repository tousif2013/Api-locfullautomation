package in.credable.automation.utils;

public final class EndPoint {
    private EndPoint() {
    }

    // Document vault service constants
    public static final String DOCUMENT_VAULT_SERVICE_BASE_PATH = "/doc-vault-service/v1";
    public static final String DOCUMENT_VAULT_UPLOAD_PATH = "/upload";
    public static final String DOCUMENT_VAULT_DOWNLOAD_PATH = "/download";
    public static final String UPLOAD_STATUS_PATH = "/uploadStatus/{transactionId}";

    // Program configurator service constants
    public static final String PROGRAM_CONFIGURATOR_SERVICE_BASE_PATH = "/los-program-configurator";
    public static final String INTEGRATION_SERVICE_BASE_PATH = "/integration";
    public static final String CLIENT_PATH = "/client";
    public static final String CREATE_CLIENT_PATH = CLIENT_PATH + "/create";
    public static final String UPDATE_CLIENT_PATH = CLIENT_PATH + "/update";
    public static final String GET_CLIENT_PATH = CLIENT_PATH + "/{clientId}";
    public static final String ADD_CLIENT_FIELD_MAPPING_PATH = CLIENT_PATH + "/{clientId}/fieldMappings/add";
    public static final String GET_CLIENT_FIELD_MAPPING_PATH = CLIENT_PATH + "/{clientId}/fieldMappings";
    public static final String CREATE_ANCHOR_PATH = CLIENT_PATH + "/{clientId}/anchor/add";
    public static final String GET_ANCHORS_FOR_CLIENT_PATH = CLIENT_PATH + "/{clientId}/anchors";
    public static final String ADD_NOTIFICATION_MODES_FOR_CLIENT_PATH = CLIENT_PATH + "/{clientId}/addNotificationModes";
    public static final String GET_NOTIFICATION_MODES_FOR_CLIENT_PATH = CLIENT_PATH + "/{clientId}/notificationModes";
    public static final String DELETE_NOTIFICATION_MODES_FOR_CLIENT_PATH = CLIENT_PATH + "/{clientId}/deleteNotificationModes";
    public static final String GET_ALL_ANCHORS_PATH = CLIENT_PATH + "/{clientId}/anchors/all";
    public static final String PROGRAM_PATH = "/program";
    public static final String CREATE_PROGRAM_PATH = PROGRAM_PATH + "/create";
    public static final String UPDATE_PROGRAM_PATH = PROGRAM_PATH + "/update";
    public static final String GET_PROGRAM_PATH = PROGRAM_PATH + "/{programId}";
    public static final String ADD_PROGRAM_CLIENT_FIELD_MAPPING_PATH = PROGRAM_PATH + "/{programId}/fieldMappings/add";
    public static final String GET_PROGRAM_CLIENT_FIELD_MAPPING_PATH = PROGRAM_PATH + "/{programId}/fieldMappings";
    public static final String GET_ALL_MODULES_PATH = "/modules";
    public static final String CREATE_MODULE_INSTANCE_PATH = "/moduleInstance/create";
    public static final String UPDATE_MODULE_INSTANCE_PATH = "/moduleInstance/update";
    public static final String ASSOCIATE_MODULE_INSTANCE_WITH_PROGRAM_PATH = PROGRAM_PATH + "/{programId}/moduleInstances/{moduleInstanceId}/associate";
    public static final String GET_ASSOCIATED_MODULE_INSTANCE_WITH_PROGRAM_PATH = PROGRAM_PATH + "/{programId}/moduleInstances";
    public static final String GET_ASSOCIATED_MODULE_FOR_COUNTRY_PATH="/modulesForCountry/{countryId}";
    public static final String LIST_ALL_AVAILABLE_SECTIONS_PATH = "/listAllAvailableSections/{programId}";
    public static final String CREATE_CUSTOM_SECTION_PATH = "/section";
    public static final String UPDATE_CUSTOM_SECTION_PATH = "/section/{sectionId}";
    public static final String CREATE_SECTION_PROGRAM_MAPPING_PATH = "/sectionMapping";
    public static final String REMOVE_SECTION_PROGRAM_MAPPING_PATH = "/sectionMapping/{sectionProgramMappingId}";
    public static final String LIST_ALL_SELECTED_SECTIONS_FOR_PROGRAM_PATH = "/sectionMapping/{programId}";
    public static final String REMOVE_ALL_SECTIONS_FROM_PROGRAM_PATH = "/sectionMappings/reset/{programId}";
    public static final String UPDATE_SECTION_INSTANCE_PATH = "/section/instance";
    public static final String LIST_ALL_SECTION_TABS_PATH = "/listAllSectionTabs";
    public static final String UPDATE_SECTION_ORDER_PATH = "/updateSectionOrder";
    public static final String GET_PROGRAM_THEME_PATH =PROGRAM_PATH+ "/{programId}/theme";
    public static final String SAVE_PROGRAM_THEME_PATH =PROGRAM_PATH+ "/theme/save";
    public static final String PLATFORM_PATH = "/platform";
    public static final String GET_COUNTRY_DETAILS_PATH = PLATFORM_PATH + "/country/{countryId}";
    public static final String GET_PRODUCT_TYPES_PATH = PLATFORM_PATH + "/product/types";
    public static final String GET_PRODUCT_CATEGORIES_PATH = PLATFORM_PATH + "/product/categories";
    public static final String GET_ALL_COUNTRY_PATH = PLATFORM_PATH + "/country/all";
    public static final String GET_ALL_LANGUAGE_PATH = PLATFORM_PATH + "/language/all";
    public static final String GET_EMAIL_SERVICE_PROVIDERS_PATH = PLATFORM_PATH + "/service-providers/EMAIL";
    public static final String GET_SMS_SERVICE_PROVIDERS_PATH = PLATFORM_PATH + "/service-providers/SMS";
    public static final String GET_CHATBOT_SERVICE_PROVIDERS_PATH = PLATFORM_PATH + "/service-providers/CHAT_BOT";
    public static final String GET_MARKETING_AUTOMATION_SERVICE_PROVIDERS_PATH = PLATFORM_PATH + "/service-providers/MARKETING_AUTOMATION";
    public static final String SAVE_CLIENT_THEME_PATH = CLIENT_PATH + "/theme/save";
    public static final String GET_CLIENT_THEME_PATH = CLIENT_PATH + "/{clientId}/theme";

    // IAM service end-points
    public static final String IAM_SERVICE_BASE_PATH = "/iam";
    public static final String CREATE_USER_PATH = "/v1/user";
    public static final String DELETE_USER_PATH = "/v1/user/{userId}";
    public static final String CREATE_USERS_PATH = "/v1/users";
    public static final String FETCH_USERS_PATH = "/v1/fetchUsers";
    public static final String ADD_ANCHOR_PATH = "/anchor/add";
    public static final String UPDATE_ANCHOR_PATH = "/anchor/update";
    public static final String APPROVE_ANCHOR_PATH = "/anchor/{id}/approve";
    public static final String REJECT_ANCHOR_PATH = "/anchor/{id}/reject";
    public static final String VERIFY_BUSINESSPAN_PATH = "/anchor/businessPAN/verify";
    public static final String FETCH_GSTIN_LIST_PATH = "/anchor/gstin/list";
    public static final String FETCH_ANCHOR_DETAIL_PATH = "/anchor/{id}";
    public static final String GET_ANCHOR_LIST_PATH = "/anchor/list";
    public static final String ADD_SUBSEGMENT_PATH = "/anchor/{id}/subsegment/add";
    public static final String GET_ANCHOR_SUBSEGMENT_PATH = "/anchor/{id}/subsegments";
    public static final String FILTER_ANCHOR_PATH = "/anchor/filter";
    public static final String AUTHORIZATION_PATH = "/auth/v1";
    public static final String DIRECT_GRANT_PATH = AUTHORIZATION_PATH + "/direct_grant";

    // LOS-Core service end-points
    public static final String LOS_CORE_SERVICE_BASE_PATH = "/los-core";
    public static final String DOWNLOAD_TEMPLATE_PATH = "/v1/download-template";
    public static final String UPLOAD_BORROWERS_PATH = "/v1/upload";
    public static final String LIST_BORROWERS_PATH = "/v1/borrowers";
    public static final String FETCH_BORROWER_PATH = "/v1/borrowers/{borrowerId}";
    public static final String CREATE_LOAN_APPLICATION_PATH = "/v1/loanApplication";
    public static final String GET_LOAN_APPLICATIONS_FOR_BORROWER_PATH = "/v1/loanApplications";
    public static final String GET_LOAN_APPLICATION_BY_ID_PATH = "/v1/loanApplications/{loanApplicationId}";
    public static final String UPDATE_LOAN_APPLICATION_PATH = "/v1/loanApplication/{id}";
    public static final String LOS_MODULE_CONTROLLER_PATH = "/v1/module";
    public static final String EXECUTE_LOS_MODULE_PATH = LOS_MODULE_CONTROLLER_PATH + "/execute";
    public static final String FETCH_MODULE_STATUS_PATH = LOS_MODULE_CONTROLLER_PATH + "/status";
    public static final String LOS_CONFIG_CONTROLLER_PATH = "/v1/config";
    public static final String FETCH_PROGRAM_VALIDATOR_FIELD_MAPPINGS_PATH = LOS_CONFIG_CONTROLLER_PATH + "/program/{programId}";

    // region LOS Integration Service end-points
    public static final String MCA_COMPANY_BASICDETAILS_PATH = "/business-details/v1/vendor/{id}/mca/company/basic-details";
    public static final String MCA_COMPANY_COMPREHENSIVEDETAILS_PATH = "/business-details/v1/vendor/{id}/mca/company/comprehensive-details";
    public static final String MCA_LLP_BASICDETAILS_PATH = "/business-details/v1/vendor/{id}/mca/llp/basic-details";
    public static final String MCA_LLP_COMPREHENSIVEDETAILS_PATH = "/business-details/v1/vendor/{id}/mca/llp/comprehensive-details";
    public static final String ITR_SUBMIT_REQUEST_PATH = "/business-details/v1/vendor/{id}/itr/submit-request";
    public static final String ITR_REPORT_PATH = "/business-details/v1/vendor/{id}/itr/report";
    public static final String ESIGN_SUBMIT_REQUEST = "/esign/v1/vendor/{vendorId}/document/submit-request";
    public static final String ESIGN_DETAILS_REQUEST = "/esign/v1/vendor/{vendorId}/document/details";
    public static final String ESIGN_CANCEL_REQUEST = "/esign/v1/vendor/{vendorId}/document/cancel";
    public static final String CREDIT_CHECK_PATH = "/bureau/v1/vendor/{id}/credit-check ";
    public static final String ESIGN_DOWNLOADS_REQUEST = "/esign/v1/vendor/{vendorId}/document/signed/download";
    public static final String ESIGN_RESEND_NOTIFICATION_PATH = "/esign/v1/vendor/{vendorId}/document/resend/notification";
    public static final String ESIGN_TRANSACTION_STATUS_PATH = "/esign/v1/vendor/{vendorId}/document/transaction/status";
    public static final String ESIGN_DELETE_INVITATION_PATH = "/esign/v1/vendor/{vendorId}/document/delete/invitation";
    public static final String ADD_VENDOR_PATH = "/v1/vendors/add";
    public static final String UPDATE_VENDOR_PATH = "/v1/vendors/{id}/update";
    public static final String LIST_ALL_VENDORS_PATH = "/v1/vendors/list";
    public static final String BUSINESS_DETAILS_CONTROLLER_PATH = "/business-details";
    public static final String VERIFY_PAN_PATH = BUSINESS_DETAILS_CONTROLLER_PATH + "/v1/vendor/{vendorId}/pan/verify";
    public static final String LIST_GSTIN_PATH = BUSINESS_DETAILS_CONTROLLER_PATH + "/v1/vendor/{vendorId}/gst/list";
    public static final String GET_GSTIN_DETAILS_PATH = BUSINESS_DETAILS_CONTROLLER_PATH + "/v1/vendor/{vendorId}/gst/details";
    public static final String CREATE_GST_ORDER_PATH = BUSINESS_DETAILS_CONTROLLER_PATH + "/v1/vendor/{vendorId}/gst/consent/create-order";
    public static final String CONFIRM_GST_ORDER_PATH = BUSINESS_DETAILS_CONTROLLER_PATH + "/v1/vendor/{vendorId}/gst/consent/confirm-order";
    public static final String GET_GST_REPORT_PATH = BUSINESS_DETAILS_CONTROLLER_PATH + "/v1/vendor/{vendorId}/gst/consent/report";
    public static final String FRAUD_CHECK_CONTROLLER_PATH = "/fraud-check";
    public static final String WATCH_OUT_CHECK_PATH = FRAUD_CHECK_CONTROLLER_PATH + "/v1/vendor/{vendorId}/watchOutCheck";
    public static final String CRIME_CHECK_FOR_COMPANY_PATH = FRAUD_CHECK_CONTROLLER_PATH + "/v1/vendor/{vendorId}/crime-check/company/submit-request";
    public static final String CRIME_CHECK_FOR_INDIVIDUAL_PATH = FRAUD_CHECK_CONTROLLER_PATH + "/v1/vendor/{vendorId}/crime-check/individual/submit-request";
    public static final String GET_CRIME_CHECK_JSON_REPORT_PATH = FRAUD_CHECK_CONTROLLER_PATH + "/v1/vendor/{vendorId}/crime-check/json/report";
    public static final String GET_CRIME_CHECK_PDF_REPORT_PATH = FRAUD_CHECK_CONTROLLER_PATH + "/v1/vendor/{vendorId}/crime-check/download/report";
    public static final String CKYC_SEARCH_PATH = "/ckyc/v1/vendor/{vendorId}/search";
    public static final String CKYC_DOWNLOAD_PATH = "/ckyc/v1/vendor/{vendorId}/download";

    // endregion LOS Integration Service

    // Data Ingestion Service
    public static final String DATA_INGESTION_SERVICE_BASE_PATH = "/data-ingestion-service";
    public static final String DATA_INGESTION_SERVICE_UPLOAD_BORROWERS_PATH = "/v1/upload";

    //BRE Endpoints
    public static final String BUSINESS_RULES_SERVICE_BASE_PATH = "/business-rules-service";
    public static final String LIST_ALL_RULES_PATH = "/v1/rules";
    public static final String RULE_DETAIL_PATH = "/v1/rule/{ruleId}/{ruleVersion}";
    public static final String EXECUTE_RULE_PATH = "/v2/execute";

    public static final String CASHFREE_BANKACCOUNT_VERIFICATION = "/banking/v1/vendor/{vendorId}/bank/account-verification";

    // Locize API end-points
    public static final String LOCIZE_API_BASE_URL = "https://api.locize.app";
    public static final String LOCIZE_API_GET_TRANSLATIONS_PATH = "/d18b02ee-a7fd-402c-b918-77de09e9c4fc/latest/{languageCodeIso2}/translation";

    //User Authentication Endpoints
    public static final String SEND_OTP_PATH = "/auth/v1/login/send-otp";
    public static final String RESEND_OTP_PATH = "/auth/v1/login/resend-otp";
    public static final String VALIDATE_OTP_PATH = "/auth/v1/login/validate-otp";
    public static final String SIGNUP_PATH = "/auth/v1/sign-up";
    public static final String LOGOUT_PATH = "/auth/v1/logout";
    public static final String LOGIN_WITH_PASSWORD_PATH = "/auth/v1/login/verify-password";
    public static final String RESEND_OTP_2FA_PATH = "/auth/v1/login/two-fa/resend-otp";
    public static final String VALIDATE_OTP_2FA_PATH = "/auth/v1/login/two-fa/validate-otp";
    public static final String PASSWORD_RESET_SEND_OTP_PATH = "/auth/v1/password/reset/send-otp";
    public static final String PASSWORD_RESET_VALIDATE_OTP_PATH = "/auth/v1/password/reset/validate-otp";
    public static final String PASSWORD_UPDATE_PATH = "/auth/v1/password/update";
    public static final String SSO_LOGIN_PATH = "/auth/v1/sso";

    //Program Status
    public static final String SUBMIT_PROGRAM_PATH = PROGRAM_PATH + "/{programId}/submit";
    public static final String APPROVE_PROGRAM_PATH = PROGRAM_PATH + "/{programId}/approve";
    public static final String REJECT_PROGRAM_PATH = PROGRAM_PATH + "/{programId}/reject";
    public static final String PUBLISH_PROGRAM_PATH = PROGRAM_PATH + "/{programId}/publish";
    public static final String INACTIVE_PROGRAM_PATH = PROGRAM_PATH + "/{programId}/inactivate";

    // Notification Service end-points
    public static final String SEND_NOTIFICATION_BASE_URL = "https://rf6smolvdb.execute-api.ap-south-1.amazonaws.com";
    public static final String SEND_NOTIFICATION_BASE_PATH = "/default";
    public static final String NOTIFICATION_SERVICE_BASE_PATH = "/notification-service";
    public static final String TEMPLATE_CONTROLLER_PATH = "/template";
    public static final String REGISTER_TEMPLATE_PATH = TEMPLATE_CONTROLLER_PATH + "/register-template/{vendorId}";
    public static final String FETCH_TEMPLATE_PATH = TEMPLATE_CONTROLLER_PATH + "/read-template/{vendorId}/{templateCode}";
    public static final String FETCH_TEMPLATES_PATH = TEMPLATE_CONTROLLER_PATH + "/read-template/{vendorId}";
    public static final String UPDATE_TEMPLATE_PATH = TEMPLATE_CONTROLLER_PATH + "/update-template/{vendorId}/{templateCode}";
    public static final String DELETE_TEMPLATE_PATH = TEMPLATE_CONTROLLER_PATH + "/delete-template/{vendorId}/{templateCode}";
}
