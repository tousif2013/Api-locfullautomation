package in.credable.automation.service.vo.bre;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RuleDetailVO {
    @JsonProperty("baseId")
    private String baseId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("lastUpdate")
    private Date lastUpdate;

    @JsonProperty("name")
    private String name;

    @JsonProperty("ruleAlias")
    private String ruleAlias;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tags")
    private ArrayList<Object> tags;

    @JsonProperty("type")
    private String type;

    @JsonProperty("version")
    private int version;
}