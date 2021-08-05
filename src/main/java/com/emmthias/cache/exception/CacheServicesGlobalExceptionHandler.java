package com.emmthias.cache.exception;

import com.emmthias.cache.common.response.error.impl.CommonApiErrorResponse;
import com.emmthias.cache.common.response.error.impl.CommonApiErrorResponseAttribute;
import com.emmthias.cache.common.response.error.impl.CommonApiErrorResponseData;
import com.emmthias.cache.common.response.error.impl.CommonApiErrorResponsePayload;
import com.emmthias.cache.common.response.error.IApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static com.emmthias.cache.constants.Constants.UNKNOWN_ERROR_MSG;


@ControllerAdvice
public class CacheServicesGlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(CacheServicesGlobalExceptionHandler.class);
    private final String message = "%s - %s";

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<IApiErrorResponse> handleAllExceptions(
            HttpServletRequest httpServletRequest, Exception ex) throws Throwable {
        this.logger.error(ex.getMessage(), ex);

        CommonApiErrorResponse codeErrorResponse =
                getCodeErrorResponseBuilder(String.format(message, UNKNOWN_ERROR_MSG, ex.getMessage()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(codeErrorResponse);
    }

    private CommonApiErrorResponse getCodeErrorResponseBuilder(String errorMessage) {
        return CommonApiErrorResponse.builder()
                .withError(CommonApiErrorResponseData.builder()
                        .withAttributes(CommonApiErrorResponseAttribute.builder()
                                .withPayload(CommonApiErrorResponsePayload.builder().withErrorMessage(errorMessage)
                                        .build())
                                .build())
                        .withId(UUID.randomUUID().toString())
                        .build())
                .build();
    }

    @ExceptionHandler({AlreadyKeyFoundException.class})
    protected ResponseEntity<IApiErrorResponse> handleAlreadyKeyFoundException(
            HttpServletRequest httpServletRequest, Exception ex) throws Throwable {
        String currentExceptionMessage = ex.getMessage();
        this.logger.error(currentExceptionMessage);

        CommonApiErrorResponse codeErrorResponse =
                getCodeErrorResponseBuilder(currentExceptionMessage);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(codeErrorResponse);
    }

    @ExceptionHandler({KeyNotFoundException.class})
    protected ResponseEntity<IApiErrorResponse> handleKeyNotFoundException(
            HttpServletRequest httpServletRequest, Exception ex) throws Throwable {
        String currentExceptionMessage = ex.getMessage();
        this.logger.error(currentExceptionMessage);

        CommonApiErrorResponse codeErrorResponse =
                getCodeErrorResponseBuilder(currentExceptionMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(codeErrorResponse);
    }

    @ExceptionHandler({RejectPolicyException.class})
    protected ResponseEntity<IApiErrorResponse> handleRejectPolicyException(
            HttpServletRequest httpServletRequest, Exception ex) throws Throwable {
        String currentExceptionMessage = ex.getMessage();
        this.logger.error(currentExceptionMessage);

        CommonApiErrorResponse codeErrorResponse =
                getCodeErrorResponseBuilder(currentExceptionMessage);
        return ResponseEntity.status(HttpStatus.INSUFFICIENT_STORAGE).body(codeErrorResponse);
    }
}
