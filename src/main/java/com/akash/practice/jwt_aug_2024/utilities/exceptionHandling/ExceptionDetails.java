package com.akash.practice.jwt_aug_2024.utilities.exceptionHandling;

import java.time.LocalDateTime;

public class ExceptionDetails {

    private String errorDescription;
    private LocalDateTime exceptionDateTime;
    private String requestDetails;

    public ExceptionDetails(String errorDescription, LocalDateTime exceptionDateTime, String requestDetails) {
        this.errorDescription = errorDescription;
        this.exceptionDateTime = exceptionDateTime;
        this.requestDetails = requestDetails;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public LocalDateTime getExceptionDateTime() {
        return exceptionDateTime;
    }

    public void setExceptionDateTime(LocalDateTime exceptionDateTime) {
        this.exceptionDateTime = exceptionDateTime;
    }

    public String getRequestDetails() {
        return requestDetails;
    }

    public void setRequestDetails(String requestDetails) {
        this.requestDetails = requestDetails;
    }

}
