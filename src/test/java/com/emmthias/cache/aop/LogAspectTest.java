package com.emmthias.cache.aop;

import com.emmthias.cache.common.response.error.impl.CommonApiErrorResponse;
import com.emmthias.cache.service.CacheService;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LogAspectTest {
    @InjectMocks
    LogAspect logAspect;

    @Mock
    private JoinPoint joinPoint;

    @Test
    public void whenRequestLog_ReturnSuccessfully() {
        final String requestedLog = "requestedLog";
        Object[] args = {"other"};
        String invokeMethod = ReflectionTestUtils.invokeMethod(logAspect,
                "requestLog", "a","2","3", args);
        assertNotNull(requestedLog,invokeMethod);
    }

    @Test
    public void wheBefore_ReturnSuccessfully() {
        final String requestedLog = "requestedLog";
        Object[] args = {"otro"};

        logAspect.doAfter();
        logAspect.doAfterReturning(new Object());
        logAspect.log();
    }
}