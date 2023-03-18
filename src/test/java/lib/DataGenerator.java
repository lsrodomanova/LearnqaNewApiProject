package lib;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DataGenerator {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHss").format(new java.util.Date());
        return "learnqa" + timestamp + "@example.com";
    }

    public static String getIncorrectEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHss").format(new java.util.Date());
        return "learnqa" + timestamp + "example.com";
    }

    public static Map<String,String> getRegistrationData() {
        Map<String,String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }

    public static Map<String,String> getRegistrationDataIncorrectEmail() {
        Map<String,String> data = new HashMap<>();
        data.put("email", DataGenerator.getIncorrectEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }

    public static Map<String,String> getRegistrationData(Map<String,String> nonDefaultValues) {
        Map<String,String> defaultValues = DataGenerator.getRegistrationData();

        Map<String,String> userData = new HashMap<>();
        String[] keys = {"email", "password", "username", "firstName", "lastName"};
        for (String key : keys) {
            if (nonDefaultValues.containsKey(key)) {
                userData.put(key, nonDefaultValues.get(key));
            }
            else {
                userData.put(key,defaultValues.get(key));
            }
        }
        return userData;
    }

    public static Map<String,String> getRegistrationDataWithoutEmail() {
        Map<String,String> data = new HashMap<>();
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }

    public static Map<String,String> getRegistrationDataWithoutPassword() {
        Map<String,String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }

    public static Map<String,String> getRegistrationDataWithoutUserName() {
        Map<String,String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }

    public static Map<String,String> getRegistrationDataWithoutFirstName() {
        Map<String,String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }

    public static Map<String,String> getRegistrationDataWithoutLastName() {
        Map<String,String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnqa");
        data.put("firstName", "learnqa");

        return data;
    }

    public static Map<String,String> getRegistrationDataWithShortName() {
        Map<String,String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "l");
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }

    public static Map<String,String> getRegistrationDataWithLongName() {
        Map<String,String> data = new HashMap<>();
        int length = 252;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", generatedString);
        data.put("firstName", "learnqa");
        data.put("lastName", "learnqa");

        return data;
    }


}
