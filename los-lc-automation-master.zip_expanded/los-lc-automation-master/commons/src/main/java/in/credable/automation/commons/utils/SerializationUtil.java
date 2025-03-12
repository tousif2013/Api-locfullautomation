package in.credable.automation.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.Map;

@Log4j2
public final class SerializationUtil {
    private SerializationUtil() {
    }

    public static String serialize(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
            return objectWriter.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // return empty json
            log.error("Error in serializing object", e);
            return "{}";
        }
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Error in deserializing object", e);
            return null;
        }
    }

    public static Map<String, Object> convertObjectToMap(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, new TypeReference<>() {
        });
    }

    public static <T> T convertObjectToType(Object object, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        return convertObjectToType(object, objectMapper.constructType(typeReference));
    }

    public static <T> T convertObjectToType(Object object, JavaType javaType) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(object, javaType);
    }

    /**
     * Reads a JSON file and returns its content as a string.
     *
     * @param file
     * @return String
     */
    @SneakyThrows
    public static String readJsonFile(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, JsonNode.class).toString();
    }


}
