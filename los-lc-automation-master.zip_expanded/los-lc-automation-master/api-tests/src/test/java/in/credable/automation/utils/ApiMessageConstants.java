package in.credable.automation.utils;

public final class ApiMessageConstants {
    public static final String ANCHOR_APPROVE_MESSAGE = "Corporate approved";
    public static final String ANCHOR_REJECTED_MESSAGE = "Corporate rejected";
    public static final String LEEGALITY_CANCEL_REQUEST_MESSAGE = "Invitation deleted successfully. Invitee wouldn't be able to access the document anymore.";
    public static final String LEEGALITY_RESEND_NOTIFICATION = "Invitation(s) sent successfully. Please ask invitees to check their inbox.";
    public static final String REQUEST_PROCESSED_SUCCESSFULLY = "Request Processed Successfully";
    public static final String LEEGALITY_DELETE_INVITATION_MESSAGE = "Document deleted successfully.";
    public static final String LIST_ALL_RULES_MESSAGE = "Rules fetched successfully";
    public static final String FETCH_RULE_INFO_MESSAGE = "Rule info fetched successfully";
    public static final String UPDATE_RULE_INFO_MESSAGE = "Rule info updated successfully";
    public static final String BUSINESS_RULE_EXECUTED_MESSAGE = "Business rules request executed";
    public static final String ANCHOR_RECOMMENDED_LIMIT_PASS_MESSAGE = "Anchor recommended limit check passed";
    public static final String AVAILABLE_SECTIONS_FETCHED = "All available sections details fetched for the program";
    public static final String SECTION_PROGRAM_MAPPINGS_CREATED = "Section mapped successfully with the program";
    public static final String SECTION_PROGRAM_MAPPINGS_FETCHED = "Selected sections fetched for the program";
    public static final String SECTION_PROGRAM_MAPPINGS_REMOVED = "Section removed successfully from the program";
    public static final String CUSTOM_SECTION_CREATED = "Section created successfully";
    public static final String CUSTOM_SECTION_UPDATED = "Section updated successfully";
    public static final String SECTION_INSTANCE_UPDATED = "Section instance updated successfully";
    public static final String SECTION_TABS_FETCHED = "All section tabs fetched";
    public static final String ALL_SECTIONS_PROGRAM_MAPPINGS_REMOVED = "Removed all selected sections from the program";
    public static final String SECTION_ORDER_UPDATED = "Selected sections order updated for the program";
    public static final String SEND_OTP_MESSAGE = "OTP sent successfully";
    public static final String VALIDATE_OTP_MESSAGE = "OTP verified";
    public static final String SIGNUP_MESSAGE = "Sign Up done successfully";
    public static final String LOGOUT_MESSAGE = "User Logged out Successfully";
    public static final String LOGIN_WITH_PASSWORD_MESSAGE = "Login Successful";
    public static final String PASSWORD_RESET_MESSAGE = "Password updated successfully";
    public static final String PASSWORD_UPDATE_MESSAGE = "Password update Successful";
    public static final String SUBMIT_PROGRAM_MESSAGE = "Program is successfully submitted for approval";
    public static final String APPROVE_PROGRAM_MESSAGE = "Program is approved and ready to publish";
    public static final String REJECT_PROGRAM_MESSAGE = "Program is rejected";
    public static final String PUBLISH_PROGRAM_MESSAGE = "Program is published to underwrite borrowers";
    public static final String INACTIVE_PROGRAM_MESSAGE = "Program is deactivated and underwrite of new borrowers will stop on this program";
    public static final String INVALID_INPUT = "Invalid Input";
    public static final String INVALID_SOURCE_REFERENCE_ID = "Invalid Source Reference Id";
    public static final String CANNOT_DELETE_THIS_DOCUMENT_DOCUMENT_NOT_PRESENT = "Cannot delete this document. Document not present";

    public static final String PROGRAM_THEME_FETCH_MESSAGE="PROGRAM_THEME_FETCH_SUCCESS_001";
    public static final String PROGRAM_THEME_SAVE_MESSAGE= "PROGRAM_THEME_SAVE_SUCCESS_001";
    public static final String MODULE_INSTANCE_ASSOCIATE_MESSAGE="Module Instance successfully associated with Program with id:";
    public static final String CLIENT_THEME_SAVE_SUCCESS_MESSAGE = "CLIENT_THEME_SAVE_SUCCESS_001";
    public static final String CLIENT_THEME_SAVE_SUCCESS_DATA = "Client Theme saved successfully";
    public static final String CLIENT_THEME_FETCH_SUCCESS_MESSAGE = "CLIENT_THEME_FETCH_SUCCESS_001";
    public static final String TOKEN_GENERATED_SUCCESSFULLY = "Token Generated Successfully";
    public static final String INVALID_AUTH_ID = "Invalid Authentication Id";
    public static final String INCORRECT_OTP="Incorrect OTP";
    public static final String AUTHENTICATIONID_MUST_NOT_NULL= "authenticationId must not be blank";
    public static final String OTP_MUST_NOT_NULL="otp must not be blank";
    public static final String INVALID_GSTIN = "Invalid GSTIN in request";
    public static final String CLIENT_ID_OR_SOURCE_ID_HEADER_NOT_PRESENT_IN_REQUEST = "Client Id or Source Id Header not present in request";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    public static final String REPORT_IS_NOT_PRESENT_FOR_THIS_SOURCE_ID = "Report is not present for this source id";
    public static final String USER_NOT_FOUND ="User not found";
    public static final String USER_REGISTERED_SUCCESSFULLY ="User Registered successfully";
    private ApiMessageConstants() {
    }
}
