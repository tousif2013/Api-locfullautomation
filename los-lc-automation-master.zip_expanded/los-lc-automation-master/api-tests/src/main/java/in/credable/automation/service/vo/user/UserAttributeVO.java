package in.credable.automation.service.vo.user;

import lombok.Data;

@Data
public class UserAttributeVO {
    private String name;
    private String value;
    private boolean secure;
}
