package org.capstone.exception;

public class LeaveFinancialException extends Throwable {
    public LeaveFinancialException(String message) {
        super(message);
    }

    public LeaveFinancialException(String message, Throwable cause) {
        super(message, cause);
    }
}
