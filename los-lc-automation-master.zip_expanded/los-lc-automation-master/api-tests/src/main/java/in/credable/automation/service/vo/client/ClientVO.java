package in.credable.automation.service.vo.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.*;
import in.credable.automation.enums.AuthenticationMethod;
import in.credable.automation.enums.TwoFactorAuthenticationMethod;
import lombok.Data;
import uk.co.jemos.podam.common.PodamCollection;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamLongValue;
import uk.co.jemos.podam.common.PodamStrategyValue;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ClientVO {
    @PodamExclude
    private Long id;

    @PodamStrategyValue(value = ClientCodeStrategy.class)
    private String clientCode;

    @PodamStrategyValue(value = ClientNameStrategy.class)
    private String clientName;

    @PodamStrategyValue(value = UniqueEmailStrategy.class)
    private String clientEmail;

    @PodamStrategyValue(AddressStrategy.class)
    private String clientAddress;

    private String clientAddress2;

    @PodamStrategyValue(CityNameStrategy.class)
    private String clientCity;

    @PodamStrategyValue(StateNameStrategy.class)
    private String clientState;

    @PodamLongValue(minValue = 100000L, maxValue = 999999L)
    private Long pinCode;

    @PodamLongValue(numValue = "1")
    private Long countryId;

    @PodamStrategyValue(OrganizationIdStrategy.class)
    private String organizationId;

    @PodamStrategyValue(UrlStrategy.class)
    private String clientUserLoginLink;

    @PodamStrategyValue(UrlStrategy.class)
    private String corporateUserLoginLink;

    @PodamStrategyValue(FullNameStrategy.class)
    private String clientAdminName;

    @PodamStrategyValue(UniqueEmailStrategy.class)
    private String clientAdminEmail;

    private MobileNumberVO clientAdminMobile;

    @PodamExclude
    private String clientAdminUserId;

    @PodamStrategyValue(FullNameStrategy.class)
    private String spocName;

    @PodamStrategyValue(EmailStrategy.class)
    private String spocEmail;

    private MobileNumberVO spocMobile;

    private LanguageVO defaultLanguage;

    @PodamCollection(nbrElements = 1)
    private List<LanguageVO> languages;

    @PodamExclude
    private Long emailServiceProviderId;

    @PodamExclude
    private Long smsServiceProviderId;

    @PodamExclude
    private Long chatBotServiceProviderId;

    @PodamExclude
    private Long marketingAutomationServiceProviderId;

    @PodamExclude
    private Map<String, AuthenticationMethod> authenticationMethods;

    @PodamExclude
    private Map<AuthenticationMethod, TwoFactorAuthenticationMethod> twoFactorAuthenticationMethods;
}
