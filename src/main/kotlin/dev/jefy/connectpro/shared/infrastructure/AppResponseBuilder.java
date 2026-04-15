package dev.jefy.connectpro.shared.infrastructure;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import dev.jefy.connectpro.shared.application.dtos.AppResponse;

/**
 * @author Jôph Yamba
 */
public class AppResponseBuilder<T> {

    private HttpStatus status = HttpStatus.OK;
    private String message = "done successfully";
    private T data = null;

    private AppResponseBuilder() {}

    public static <T> AppResponseBuilder<T> builder() {
        return new AppResponseBuilder<>();
    }

    public AppResponseBuilder<T> status(HttpStatus status) {
        this.status = status;
        return this;
    }

    public AppResponseBuilder<T> message(String message) {
        this.message = message;
        return this;
    }

    public AppResponseBuilder<T> data(T data) {
        this.data = data;
        return this;
    }

    public ResponseEntity<AppResponse<T>> build() {
        AppResponse<T> response = new AppResponse<>(
                message,
                data,
                status.value(),
                Instant.now()
        );

        return ResponseEntity.status(status).body(response);
    }
}