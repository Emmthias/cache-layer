package com.emmthias.cache.validator;

import com.emmthias.cache.exception.RejectPolicyException;

import java.util.List;
import java.util.Map;

import static com.emmthias.cache.constants.Constants.REJECT_POLICY_MESSAGE;

public enum RejectCacheRepositoryValidator implements MapBehavior {
    INSTANCE;

    @Override
    public void applyEvictionPolicy(Map<?, ?> data, List<String> elementsToRemove) {
        throw new RejectPolicyException(REJECT_POLICY_MESSAGE);
    }
}
