package com.gyt.corepackage.utils.exceptions.problemDetails;

public class AccessDeniedProblemDetails extends ProblemDetails {
    public AccessDeniedProblemDetails() {
        setTitle("Access Denied");
        setType("http://mydomain.com/exceptions/access-denied");
        setStatus("403");
    }
}
