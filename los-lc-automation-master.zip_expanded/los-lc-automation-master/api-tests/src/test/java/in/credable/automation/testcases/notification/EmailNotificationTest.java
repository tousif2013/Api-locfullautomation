package in.credable.automation.testcases.notification;

import in.credable.automation.commons.utils.SerializationUtil;
import in.credable.automation.service.notification.NotificationService;
import in.credable.automation.service.vo.notification.EmailNotificationVO;
import in.credable.automation.service.vo.notification.MessageVO;
import in.credable.automation.service.vo.notification.NotificationBodyVO;
import in.credable.automation.service.vo.notification.NotificationResponseVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.TestConstants;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @author Prashant Rana
 */
public class EmailNotificationTest extends BaseTest {
    @Test(description = "Test #707 Verify the email API integration for Sandgrid as email provider, after entering all the details")
    public void verifySendingEmailNotificationViaSendGrid() {
        EmailNotificationVO emailNotificationVO = DataProviderUtil.manufacturePojo(EmailNotificationVO.class);
        MessageVO messageVO = emailNotificationVO.getMessage();

        messageVO.setVendorId(TestConstants.EMAIL_NOTIFICATION_VENDOR_ID);
        messageVO.setTo(List.of(TestConstants.EMAIL_NOTIFICATION_TO_ADDRESS));
        messageVO.setAttachmentDocUrls(List.of(TestConstants.SANCTION_LETTER_ATTACHMENT_URL));
        NotificationResponseVO notificationResponseVO = NotificationService.sendEmailNotification(emailNotificationVO)
                .as(NotificationResponseVO.class);

        Assertions.assertThat(notificationResponseVO.getStatusCode())
                .as(() -> "Status code for sending email notification is not expected")
                .isEqualTo(200);

        NotificationBodyVO notificationBodyVO = SerializationUtil.deserialize(notificationResponseVO.getBody(),
                NotificationBodyVO.class);

        Assertions.assertThat(notificationBodyVO)
                .as(() -> "Body should not be null in notification response")
                .isNotNull()
                .extracting(NotificationBodyVO::getMessage, NotificationBodyVO::getTopicName)
                .doesNotContainNull()
                .containsExactly("Message successfully produced to Kafka topic.", "email_topic");
    }
}
