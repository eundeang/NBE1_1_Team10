package org.example.gc_coffee.dto;

import java.util.Random;


public class EmailGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-";
    private static final String[] DOMAINS = {"com", "net", "org", "co.kr", "edu", "gov"};
    private static final Random random = new Random();

    // 이메일을 생성하는 메서드
    public static String generateRandomValidEmail() {
        int nameLength = random.nextInt(8) + 3; // 3~10자
        int domainNameLength = random.nextInt(5) + 3; // 3~7자
        String name = generateRandomString(nameLength);
        String domainName = generateRandomString(domainNameLength);
        String domain = DOMAINS[random.nextInt(DOMAINS.length)];

        return name + "@" + domainName + "." + domain;
    }

    // 지정된 길이의 랜덤 문자열을 생성하는 메서드
    private static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

}
