package QuanLyKyThi;

/**
 * Custom Exception Classes cho hệ thống Quản Lý Kỳ Thi
 * Xử lý các lỗi nghiệp vụ và validation cụ thể
 */

/**
 * Exception chung cho hệ thống Quản Lý Kỳ Thi
 */
class QuanLyKyThiException extends Exception {
    public QuanLyKyThiException(String message) {
        super(message);
    }
    
    public QuanLyKyThiException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Exception cho validation thông tin thí sinh
 */
class ThiSinhValidationException extends QuanLyKyThiException {
    public ThiSinhValidationException(String message) {
        super("Lỗi validation thí sinh: " + message);
    }
}

/**
 * Exception cho validation thông tin giám thị
 */
class GiamThiValidationException extends QuanLyKyThiException {
    public GiamThiValidationException(String message) {
        super("Lỗi validation giám thị: " + message);
    }
}

/**
 * Exception cho validation kỳ thi
 */
class KyThiValidationException extends QuanLyKyThiException {
    public KyThiValidationException(String message) {
        super("Lỗi validation kỳ thi: " + message);
    }
}

/**
 * Exception cho lỗi trùng lặp
 */
class DuplicateException extends QuanLyKyThiException {
    public DuplicateException(String message) {
        super("Lỗi trùng lặp: " + message);
    }
}

/**
 * Exception cho lỗi không tìm thấy
 */
class NotFoundException extends QuanLyKyThiException {
    public NotFoundException(String message) {
        super("Không tìm thấy: " + message);
    }
}

/**
 * Exception cho lỗi phân quyền
 */
class PermissionException extends QuanLyKyThiException {
    public PermissionException(String message) {
        super("Lỗi phân quyền: " + message);
    }
}

/**
 * Exception cho lỗi trạng thái
 */
class StateTransitionException extends QuanLyKyThiException {
    public StateTransitionException(String message) {
        super("Lỗi chuyển trạng thái: " + message);
    }
}

/**
 * Exception cho lỗi thanh toán
 */
class PaymentException extends QuanLyKyThiException {
    public PaymentException(String message) {
        super("Lỗi thanh toán: " + message);
    }
}

/**
 * Exception cho lỗi XML Database
 */
class XMLDatabaseException extends QuanLyKyThiException {
    public XMLDatabaseException(String message) {
        super("Lỗi cơ sở dữ liệu XML: " + message);
    }
    
    public XMLDatabaseException(String message, Throwable cause) {
        super("Lỗi cơ sở dữ liệu XML: " + message, cause);
    }
}

/**
 * Exception cho lỗi authentication
 */
class AuthenticationException extends QuanLyKyThiException {
    public AuthenticationException(String message) {
        super("Lỗi xác thực: " + message);
    }
}
