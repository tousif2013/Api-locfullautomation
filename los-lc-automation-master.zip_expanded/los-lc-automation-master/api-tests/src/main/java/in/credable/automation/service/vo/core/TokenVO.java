package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenVO {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("refresh_expires_in")
    private Integer refreshExpiresIn;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("not_before_policy")
    private String notBeforePolicy;

    @JsonProperty("token_type")
    private String tokenType;

}