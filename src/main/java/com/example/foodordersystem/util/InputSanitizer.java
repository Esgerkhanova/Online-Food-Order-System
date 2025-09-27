//package com.example.foodordersystem.util;
//
//
//import org.springframework.stereotype.Component;
//
//import java.util.regex.Pattern;
//
//@Component
//public class InputSanitizer {
//
//    public static final PolicyFactory POLICY = new HtmlPolicyBuilder()
//            .allowElements("b", "i", "em", "strong")
//            .toFactory();
//
//    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
//            "('(''|[^'])*')|(;)|(\b(ALTER|CREATE|DELETE|DROP|EXEC(UTE)?|INSERT( +INTO)?|MERGE|SELECT|UPDATE|UNION( +ALL)?)\b)",
//            Pattern.CASE_INSENSITIVE
//    );
//
//    private static final Pattern XSS_PATTERN = Pattern.compile(
//            "<script[^>]*>.*?</script>|javascript:|onload=|onerror=|onclick=",
//            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
//    );
//
//    public String sanitizeHtml(String input) {
//        if (input == null) {
//            return null;
//        }
//        return POLICY.sanitize(input);
//    }
//
//    public String sanitizeInput(String input) {
//        if (input == null) {
//            return null;
//        }
//
//        // Remove potential XSS
//        String sanitized = XSS_PATTERN.matcher(input).replaceAll("");
//
//        // Basic HTML encode
//        sanitized = sanitized.replace("<", "&lt;")
//                .replace(">", "&gt;")
//                .replace("\"", "&quot;")
//                .replace("'", "&#x27;")
//                .replace("/", "&#x2F;");
//
//        return sanitized.trim();
//    }
//
//    public boolean containsSqlInjection(String input) {
//        if (input == null) {
//            return false;
//        }
//        return SQL_INJECTION_PATTERN.matcher(input).find();
//    }
//
//    public void validateInput(String input, String fieldName) {
//        if (input != null && containsSqlInjection(input)) {
//            throw new IllegalArgumentException(
//                    String.format("Potentially malicious content detected in field: %s", fieldName)
//            );
//        }
//    }
//}
//
