package com.emmthias.cache.exception;

import com.emmthias.cache.common.response.error.impl.CommonApiErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CacheServicesGlobalExceptionHandlerTest {
    @InjectMocks
    CacheServicesGlobalExceptionHandler exceptionHandler;

    @Test
    public void whenGetCodeErrorResponseBuilder() {
        final String not_found = "not found";
        CommonApiErrorResponse invokeMethod = ReflectionTestUtils.invokeMethod(exceptionHandler,
                "getCodeErrorResponseBuilder", not_found);
        assertNotNull(invokeMethod);
        assertNotNull(invokeMethod.getError());
        assertNotNull(invokeMethod.getError().getAttributes());
        assertNotNull(invokeMethod.getError().getAttributes().getPayload());
        assertEquals(not_found, invokeMethod.getError().getAttributes().getPayload().getErrorMessage());


    }
}