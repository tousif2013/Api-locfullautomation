package in.credable.automation.service.vo.core;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BorrowerResponseVO {

    private Map<String, Object> borrowerData;
    private List<BorrowerFieldMappingVO> fields;

}
