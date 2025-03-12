package in.credable.automation.testcases.notification;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.service.notification.NotificationService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.notification.TemplateRequestVO;
import in.credable.automation.service.vo.notification.TemplateVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.*;
import io.restassured.common.mapper.TypeRef;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Prashant Rana
 */
public class TemplateTest extends BaseTest {
    private static final String CLIENT_ID_HEADER_VALUE = "automation";
    private final String templateCode = RandomDataGenerator.getUuid();

    @Test(description = "Test #715 - Verify the SMS/Email template is Added , Updated or removed from the third party " +
            "when all details are entered correctly")
    public void verifyCRUDOperationsForTemplateAPIs() {
        // 1. Create template with valid details
        TemplateVO templateVO = new TemplateVO();
        templateVO.setTemplateCode(templateCode);
        TemplateRequestVO templateRequestVO = DataProviderUtil.manufacturePojo(TemplateRequestVO.class);
        templateVO.setTemplateRequestList(List.of(templateRequestVO));

        ResponseWO<TemplateVO> responseWO = NotificationService.registerTemplate(templateVO,
                        TestConstants.EMAIL_NOTIFICATION_VENDOR_ID,
                        CLIENT_ID_HEADER_VALUE)
                .as(new TypeRef<>() {
                });

        assertResponseWO(responseWO);

        TemplateVO createdTemplate = responseWO.getData();
        FrameworkAssertions.assertThat(createdTemplate)
                .vendorIdIs(TestConstants.EMAIL_NOTIFICATION_VENDOR_ID)
                .clientIdIs(CLIENT_ID_HEADER_VALUE)
                .templateCodeIs(templateCode)
                .templateRequestListIsSameAs(List.of(templateRequestVO));

        // 2. Fetch all templates for the vendor
        ResponseWO<List<TemplateVO>> templatesResponseWO = NotificationService.fetchTemplatesForVendor(TestConstants.EMAIL_NOTIFICATION_VENDOR_ID,
                        CLIENT_ID_HEADER_VALUE)
                .as(new TypeRef<>() {
                });

        assertResponseWO(templatesResponseWO);

        Assertions.assertThat(templatesResponseWO.getData())
                .as(() -> "List of templates for the vendor should contain the created template")
                .contains(createdTemplate);

        // 3. Update template
        TemplateRequestVO updateTemplateRequestVO = DataProviderUtil.clone(templateRequestVO);
        updateTemplateRequestVO.setTemplateSubject(templateRequestVO.getTemplateSubject() + " Updated");
        updateTemplateRequestVO.setTemplateBody(templateRequestVO.getTemplateBody() + " Updated");
        updateTemplateRequestVO.setStatus("INACTIVE");

        ResponseWO<TemplateVO> updatedTemplateResponseWO = NotificationService.updateTemplate(updateTemplateRequestVO,
                        TestConstants.EMAIL_NOTIFICATION_VENDOR_ID,
                        CLIENT_ID_HEADER_VALUE,
                        templateCode)
                .as(new TypeRef<>() {
                });

        assertResponseWO(updatedTemplateResponseWO);

        TemplateVO updatedTemplate = updatedTemplateResponseWO.getData();
        FrameworkAssertions.assertThat(updatedTemplate)
                .vendorIdIs(TestConstants.EMAIL_NOTIFICATION_VENDOR_ID)
                .clientIdIs(CLIENT_ID_HEADER_VALUE)
                .templateCodeIs(templateCode);

        Assertions.assertThat(updatedTemplate.getTemplateRequestList())
                .as(() -> "List of templates for the vendor should contain the updated template")
                .contains(updateTemplateRequestVO);

        // 4. Delete template
        ResponseWO<String> deleteResponseWO = NotificationService.deleteTemplate(templateCode,
                        TestConstants.EMAIL_NOTIFICATION_VENDOR_ID,
                        CLIENT_ID_HEADER_VALUE)
                .as(new TypeRef<>() {
                });

        assertResponseWO(deleteResponseWO);

        // 5. Check if template is deleted
        ResponseWO<Void> voidResponseWO = NotificationService.fetchTemplate(TestConstants.EMAIL_NOTIFICATION_VENDOR_ID,
                CLIENT_ID_HEADER_VALUE,
                templateCode,
                StatusCode.INTERNAL_SERVER_ERROR).as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(voidResponseWO)
                .hasSameStatus(500)
                .hasSameCode("TPERR01")
                .hasSameMessage("TPERR01")
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull();
    }

    @AfterClass
    public void deleteTemplate() {
        NotificationService.deleteTemplate(templateCode, TestConstants.EMAIL_NOTIFICATION_VENDOR_ID, CLIENT_ID_HEADER_VALUE);
    }

    private void assertResponseWO(ResponseWO<?> responseWO) {
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull();
    }
}
