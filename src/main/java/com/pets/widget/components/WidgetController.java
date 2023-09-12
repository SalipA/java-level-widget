package com.pets.widget.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WidgetController {
    private final WidgetService service;

    public WidgetController(WidgetService service) {
        this.service = service;
    }

    @GetMapping("/widget/{userId}")
    public ResponseEntity<String> getWidget(@PathVariable String userId) {
        log.info("GET: /widget/{}", userId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Cache-Control",
            "no-cache");
        responseHeaders.add("Content-Type", "image/svg+xml");

        String body = service.getWidget(userId).getContent();

        return ResponseEntity.ok()
            .headers(responseHeaders)
            .body(body);
    }


}
