package com.wernhervonbraun.edundarocha.exceptions.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
