package in.credable.automation.utils;

import org.apache.commons.lang3.SerializationUtils;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.io.Serializable;

public final class DataProviderUtil {
    private DataProviderUtil() {
    }

    public static <T> T manufacturePojo(Class<T> pojoClass) {
        return new PodamFactoryImpl().manufacturePojo(pojoClass);
    }

    public static <T extends Serializable> T clone(final T object) {
        return SerializationUtils.clone(object);
    }

}
