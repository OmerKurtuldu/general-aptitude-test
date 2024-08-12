package com.gyt.corepackage.utils.exceptions.handler;

import com.gyt.corepackage.utils.exceptions.problemDetails.BusinessProblemDetails;
import com.gyt.corepackage.utils.exceptions.problemDetails.ValidationProblemDetails;
import com.gyt.corepackage.utils.exceptions.problemDetails.AccessDeniedProblemDetails;
import com.gyt.corepackage.utils.exceptions.problemDetails.ResourceNotFoundProblemDetails;
import com.gyt.corepackage.utils.exceptions.types.BusinessException;
import com.gyt.corepackage.utils.exceptions.types.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BusinessProblemDetails handleBusinessException(BusinessException exception) {
        BusinessProblemDetails businessProblemDetails = new BusinessProblemDetails();
        businessProblemDetails.setDetail(exception.getMessage());
        businessProblemDetails.setTimestamp(LocalDateTime.now());
        businessProblemDetails.setTrackingId(UUID.randomUUID().toString());
        return businessProblemDetails;
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationProblemDetails handleValidationException(MethodArgumentNotValidException exception) {
        Map<String,String> validationErrors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage())
        );

        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setErrors(validationErrors);
        validationProblemDetails.setTimestamp(LocalDateTime.now());
        validationProblemDetails.setTrackingId(UUID.randomUUID().toString());

        return validationProblemDetails;
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public AccessDeniedProblemDetails handleAccessDeniedException(AccessDeniedException exception) {
        AccessDeniedProblemDetails problemDetails = new AccessDeniedProblemDetails();
        problemDetails.setDetail("Bu islem icin yetkiniz bulunmamaktadir.");
        problemDetails.setTimestamp(LocalDateTime.now());
        problemDetails.setTrackingId(UUID.randomUUID().toString());

        return problemDetails;
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResourceNotFoundProblemDetails handleResourceNotFoundException(ResourceNotFoundException exception) {
        ResourceNotFoundProblemDetails problemDetails = new ResourceNotFoundProblemDetails();
        problemDetails.setDetail(exception.getMessage());
        problemDetails.setTimestamp(LocalDateTime.now());
        problemDetails.setTrackingId(UUID.randomUUID().toString());

        return problemDetails;
    }
}
