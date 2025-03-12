package in.credable.automation.attributestrategy;

import in.credable.automation.service.vo.client.ApprovalStatusEnum;
import uk.co.jemos.podam.common.AttributeStrategy;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class ApprovalStatusStrategy implements AttributeStrategy<ApprovalStatusEnum> {
    @Override
    public ApprovalStatusEnum getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
        return Arrays.stream(ApprovalStatusEnum.values())
                .findAny()
                .orElse(ApprovalStatusEnum.PENDING);
    }
}
