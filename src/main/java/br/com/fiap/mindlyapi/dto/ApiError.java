package br.com.fiap.mindlyapi.dto;

import java.time.LocalDateTime;

public record ApiError(
        int status,
        String error,
        String path,
        LocalDateTime timestamp
) {
}
