package QuanLyKyThi;

/**
 * Container class for custom Exception Classes for the Examination Management System.
 * This class holds all custom exceptions related to specific business logic and validation.
 */
public final class Exceptions {

    // Private constructor to prevent instantiation
    private Exceptions() {}

    /**
     * Base exception for the Examination Management System.
     */
    public static class QuanLyKyThiException extends Exception {
        public QuanLyKyThiException(String message) {
            super(message);
        }

        public QuanLyKyThiException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception for candidate information validation errors.
     */
    public static class ThiSinhValidationException extends QuanLyKyThiException {
        public ThiSinhValidationException(String message) {
            super("Lỗi validation thí sinh: " + message);
        }
    }

    /**
     * Exception for proctor information validation errors.
     */
    public static class GiamThiValidationException extends QuanLyKyThiException {
        public GiamThiValidationException(String message) {
            super("Lỗi validation giám thị: " + message);
        }
    }

    /**
     * Exception for examination validation errors.
     */
    public static class KyThiValidationException extends QuanLyKyThiException {
        public KyThiValidationException(String message) {
            super("Lỗi validation kỳ thi: " + message);
        }
    }

    /**
     * Exception for duplicate entry errors.
     */
    public static class DuplicateException extends QuanLyKyThiException {
        public DuplicateException(String message) {
            super("Lỗi trùng lặp: " + message);
        }
    }

    /**
     * Exception for not-found errors.
     */
    public static class NotFoundException extends QuanLyKyThiException {
        public NotFoundException(String message) {
            super("Không tìm thấy: " + message);
        }
    }

    /**
     * Exception for permission errors.
     */
    public static class PermissionException extends QuanLyKyThiException {
        public PermissionException(String message) {
            super("Lỗi phân quyền: " + message);
        }
    }

    /**
     * Exception for state transition errors.
     */
    public static class StateTransitionException extends QuanLyKyThiException {
        public StateTransitionException(String message) {
            super("Lỗi chuyển trạng thái: " + message);
        }
    }

    /**
     * Exception for payment-related errors.
     */
    public static class PaymentException extends QuanLyKyThiException {
        public PaymentException(String message) {
            super("Lỗi thanh toán: " + message);
        }
    }

    /**
     * Exception for XML Database errors.
     */
    public static class XMLDatabaseException extends QuanLyKyThiException {
        public XMLDatabaseException(String message) {
            super("Lỗi cơ sở dữ liệu XML: " + message);
        }

        public XMLDatabaseException(String message, Throwable cause) {
            super("Lỗi cơ sở dữ liệu XML: " + message, cause);
        }
    }

    /**
     * Exception for authentication errors.
     */
    public static class AuthenticationException extends QuanLyKyThiException {
        public AuthenticationException(String message) {
            super("Lỗi xác thực: " + message);
        }
    }
}