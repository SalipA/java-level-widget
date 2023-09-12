package com.pets.widget.components;

import com.pets.widget.entity.WidgetDto;
import com.pets.widget.exceptions.ServiceNotAvailableException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class CacheHandler implements Runnable {

    private final String HANDLER_MODE;
    private final String CACHE_FILE_DIRECTORY = "cache";
    private final Path cacheFilePath;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private WidgetDto widgetDtoCached;

    public CacheHandler(String userId) {
        this.cacheFilePath = Path.of(CACHE_FILE_DIRECTORY + "/" + userId + ".txt");
        this.HANDLER_MODE = "READ";
    }

    public CacheHandler(String userId, WidgetDto widgetDto) {
        this.cacheFilePath = Path.of(CACHE_FILE_DIRECTORY + "/" + userId + ".txt");
        this.HANDLER_MODE = "WRITE";
        this.widgetDtoCached = widgetDto;
    }

    public WidgetDto getWidgetDtoCached() {
        return widgetDtoCached;
    }

    @Override
    public void run() {
        try {
            switch (HANDLER_MODE) {
                case "WRITE":
                    saveCache();
                    break;
                case "READ":
                    if (isCacheExist())
                        initializeCache();
                    break;
            }
        } catch (IOException exp) {
            log.error(exp.getMessage());
            throw new ServiceNotAvailableException();
        }
    }

    private boolean isCacheExist() {
        log.info("isCacheExist = {}", Files.exists(cacheFilePath));
        return Files.exists(cacheFilePath);
    }

    public String readCacheFile() throws IOException {
        String result = Files.readString(cacheFilePath);
        log.info("readCacheFile = ok");
        return result;
    }

    public void initializeCache() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String[] result = readCacheFile().split("\n");
        LocalDateTime expiresDate = LocalDateTime.parse(result[0], formatter);
        this.widgetDtoCached = new WidgetDto(result[1], expiresDate);
        if (expiresDate.isBefore(now)) {
            widgetDtoCached.setCacheActual(false);
            log.info("initializeCache = cache is not actual");
        } else {
            widgetDtoCached.setCacheActual(true);
            log.info("initializeCache = cache is actual");
        }
    }

    private void saveCache() throws IOException {
        createDirectoryForCacheFilesIfNotExist();
        File cacheFile = cacheFilePath.toFile();
        try (FileWriter writer = new FileWriter(cacheFile)) {
            writer.write(getWidgetDtoCached().getExpires().format(formatter) + "\n" + getWidgetDtoCached()
                .getContent());
            log.info("saveCache = ok");
        }
    }

    private void createDirectoryForCacheFilesIfNotExist() throws IOException {
        Path dirForCacheFiles = Path.of(CACHE_FILE_DIRECTORY);
        if (!Files.isDirectory(dirForCacheFiles)) {
            Files.createDirectory(dirForCacheFiles);
            log.info("createDirectoryForCache = ok");
        }
    }
}