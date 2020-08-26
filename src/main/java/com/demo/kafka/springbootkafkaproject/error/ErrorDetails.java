package com.demo.kafka.springbootkafkaproject.error;

import java.util.Date;

public class ErrorDetails {
    private final Date time;
    private final String restErrorMessage;
    private final String detailedMessage;

    public Date getTime() {
        return time;
    }

    public String getRestErrorMessage() {
        return restErrorMessage;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

    public ErrorDetails(Date time, String restErrorMessage, String detailedMessage) {
        this.time = time;
        this.restErrorMessage = restErrorMessage;
        this.detailedMessage = detailedMessage;
    }
}
