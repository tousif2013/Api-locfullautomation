package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author PriyankaSingh on 21/03/2024.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CashFreeVO {

    private String bankAccount;
    private String ifsc;
    private String phone;
    private String name;
    private String remarks;

}
