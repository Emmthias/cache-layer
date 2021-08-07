package com.emmthias.cache.validator;

import com.emmthias.cache.exception.RejectPolicyException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

import static com.emmthias.cache.constants.Constants.REJECT_POLICY_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RejectCacheRepositoryValidatorTest {
    @Test
    public void whenRejectCacheRepositoryValidator_shouldThrowException() throws IOException {
        Exception exception = assertThrows(RejectPolicyException.class, () -> {
            RejectCacheRepositoryValidator.INSTANCE.applyEvictionPolicy(Collections.EMPTY_MAP, Collections.EMPTY_LIST);
        });

        String actualMessage = exception.getMessage();

        assertTrue(REJECT_POLICY_MESSAGE.contains(actualMessage));
    }
}