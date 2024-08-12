package com.gyt.corepackage.utils.exceptions.problemDetails;

public class ResourceNotFoundProblemDetails extends ProblemDetails {
    public ResourceNotFoundProblemDetails() {
        setTitle("Resource Not Found");
        setType("http://mydomain.com/exceptions/resource-not-found");
        setStatus("404");
    }
}
