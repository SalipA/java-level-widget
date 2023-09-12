package com.pets.widget.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class WidgetDto {
    private String content;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime expires;

    private boolean isCacheActual;

    public WidgetDto(String content, LocalDateTime expires) {
        this.content = content;
        this.expires = expires;
    }

    public boolean isCacheActual() {
        return isCacheActual;
    }

    public void setCacheActual(boolean cacheActual) {
        isCacheActual = cacheActual;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

}