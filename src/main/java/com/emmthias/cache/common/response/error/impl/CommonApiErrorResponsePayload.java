package com.emmthias.cache.common.response.error.impl;

import com.emmthias.cache.common.response.error.IApiErrorResponsePayload;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import static com.emmthias.cache.validator.ValidatorUtil.isBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"errorCode", "errorMessage", "recommendedAction", "errorTrace"})
@JsonDeserialize(builder = CommonApiErrorResponsePayload.Builder.class)
public class CommonApiErrorResponsePayload implements IApiErrorResponsePayload {
    
    private static final long serialVersionUID = -4760825395877856384L;

    @JsonProperty("errorCode")
    private final String errorCode;

    @JsonProperty("errorMessage")
    private final String errorMessage;
    
    @JsonProperty("recommendedAction")
    private final String recommendedAction;

    @JsonProperty("errorTrace")
    private final String errorTrace;

    /**
     * @param builder
     */
    private CommonApiErrorResponsePayload(Builder builder) {
        if (isBlank(builder.errorMessage)) {
            throw new IllegalArgumentException("errorMessage field can't be null or empty");
        }

        this.errorCode = builder.errorCode;
        this.errorMessage = builder.errorMessage;
        this.recommendedAction = builder.recommendedAction;
        this.errorTrace = builder.errorTrace;
    }

    /**
     * Creates builder to build {@link CommonApiErrorResponsePayload}.
     *
     * @return created builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.dowjones.mosaic.mirage.api.contracts.common.response.error.
     * ApiErrorResponsePayload#getErrorCode()
     */
    @Override
    public String getErrorCode() {
        return errorCode;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.dowjones.mosaic.mirage.api.contracts.common.response.error.
     * ApiErrorResponsePayload#getErrorMessage()
     */
    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.dowjones.mosaic.mirage.api.contracts.common.response.error.
     * ApiErrorResponsePayload#getRecommendedAction()
     */
    @Override
    public String getRecommendedAction() {
        return recommendedAction;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.dowjones.mosaic.mirage.api.contracts.common.response.error.
     * ApiErrorResponsePayload#getErrorTrace()
     */
    @Override
    public String getErrorTrace() {
        return errorTrace;
    }

    /**
     * Builder to build {@link CommonApiErrorResponsePayload}.
     */
    public static final class Builder {
        private String errorCode;
        private String errorMessage;
        private String recommendedAction;
        private String errorTrace;

        private Builder() {
        }

        public Builder withErrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder withErrorMessage(String errorMessage) {
            if (isBlank(errorMessage)) {
                throw new IllegalArgumentException("errorMessage field can't be null or empty");
            }

            this.errorMessage = errorMessage;
            return this;
        }

        public Builder withRecommendedAction(String recommendedAction) {
            this.recommendedAction = recommendedAction;
            return this;
        }

        public Builder withErrorTrace(String errorTrace) {
            this.errorTrace = errorTrace;
            return this;
        }

        public CommonApiErrorResponsePayload build() {
            return new CommonApiErrorResponsePayload(this);
        }
    }
}
