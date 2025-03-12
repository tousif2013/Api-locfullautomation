package in.credable.automation.utils;

import com.github.javafaker.Faker;

public final class RandomDataGenerator {
    private static final Faker FAKER = Faker.instance();

    private RandomDataGenerator() {
    }

    public static String generateRandomFirstName() {
        return FAKER.name().firstName();
    }

    public static String generateRandomLastName() {
        return FAKER.name().lastName();
    }

    public static String generateRandomFullName() {
        return FAKER.name().firstName() + " " + FAKER.name().lastName();
    }

    public static String generateRandomUniqueEmailId() {
        return FAKER.name().firstName().toLowerCase()
                .concat("-")
                .concat(getUuid())
                .concat("@example.com");
    }

    public static String generateRandomMobileNumber() {
        return FAKER.regexify("[6-9]\\d{9}");
    }

    public static String generateRandomMobileNumberWithCountryCode() {
        return "+91" + generateRandomMobileNumber();
    }

    public static String getUuid() {
        return FAKER.internet().uuid();
    }

    public static String generateRandomString(int characterCount) {
        return FAKER.lorem().characters(characterCount);
    }

    public static Long generateRandomNumber(int length) {
        return FAKER.number().randomNumber(length, true);
    }

    public static String generateRandomPanNumber() {
        return FAKER.regexify("[A-Z]{3}[P][A-Z]{1}[0-9]{4}[A-Z]{1}");
    }

    public static String generateRandomPassword() {
        return FAKER.regexify("[A-Z]{1}[a-z]{3}[@][0-9]{4}");
    }

    public static String generateRandomGstNumber() {
        return FAKER.regexify("[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}");
    }

    public static String generateRandomCompanyName() {
        return FAKER.company().name();
    }

}
