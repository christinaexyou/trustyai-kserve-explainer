package org.kie.trustyai.payloads;

import java.util.Date;
import org.kie.trustyai.ConfigService;
import org.kie.trustyai.ExplainerType;

import jakarta.inject.Inject;

public class BaseExplanationResponse {
    @Inject
    ConfigService configService;

    public final Date timestamp = new Date();

    public ExplainerType explainerType = configService.getExplainerType();

    public BaseExplanationResponse() {
    }

    public String getExplainerType() {
        return explainerType.toString();
    }

    public void setType(ExplainerType explainerType) {
        this.explainerType = explainerType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
