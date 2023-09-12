package com.pets.widget.components;

import com.pets.widget.entity.WidgetDto;
import com.pets.widget.exceptions.ServiceNotAvailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
@Slf4j
public class WidgetService {

    private final Path WIDGET_PATTERN_PATH = Path.of("WidgetPattern.txt");
    private final int CACHE_REFRESH_RATE_IN_DAYS = 1;

    public WidgetDto getWidget(String userId) {
        try {
            WidgetDto widgetCachedDto = checkCash(userId);
            if (widgetCachedDto != null && widgetCachedDto.isCacheActual()) {
                log.info("getWidget = return cached widget");
                return widgetCachedDto;
            } else {
                return createNewWidget(userId);
            }
        } catch (IOException | InterruptedException exp) {
            log.error(exp.getMessage());
            throw new ServiceNotAvailableException();
        }
    }

    private WidgetDto checkCash(String userId) throws InterruptedException {
        CacheHandler ch = new CacheHandler(userId);
        Thread thread = new Thread(ch);
        thread.start();
        thread.join();
        log.info("checkCash = ok");
        return ch.getWidgetDtoCached();
    }

    private WidgetDto createNewWidget(String userId) throws IOException {
        String parsedPage = WebParser.parsePageByUri(userId);
        int level = WebParser.parseLevel(parsedPage);
        int rating = WebParser.parseRating(parsedPage);
        String widgetPatternString = Files.readString(WIDGET_PATTERN_PATH)
            .replaceAll("#LEVEL#", Integer.toString(level))
            .replaceAll("#RATING#", Integer.toString(rating));
        WidgetDto newWidget = new WidgetDto(widgetPatternString,
            LocalDateTime.now().plusDays(CACHE_REFRESH_RATE_IN_DAYS));
        saveCash(userId, newWidget);
        log.info("createNewWidget = ok");
        return newWidget;
    }

    private void saveCash(String userId, WidgetDto widgetDto) {
        CacheHandler ch = new CacheHandler(userId, widgetDto);
        Thread thread = new Thread(ch);
        thread.start();
    }
}