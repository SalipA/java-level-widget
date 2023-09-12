package com.pets.widget.components;

import com.pets.widget.exceptions.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
public class WebParser {

    private static final String USER_PROFILE_URI = "https://javarush.com/users/";
    private static final String LEVEL_REGEX = "user-profile-card__level\">\\d+\\s\\D+</span>";
    private static final String RATING_REGEX = "user-profile-card__rating-value\">\\d+</span>";

    private static Integer parseDataForWidget(String htmlPage, String regex) {
        Matcher levelMatcher = Pattern.compile(regex).matcher(htmlPage);
        if (levelMatcher.find()) {
            log.info("WebParser.parseDataForWidget = data is found");
            return Integer.parseInt(htmlPage.substring(levelMatcher.start(), levelMatcher.end()).replaceAll("\\D", ""));
        } else {
            log.error("WebParser.parseDataForWidget = user's bad request");
            throw new DataNotFoundException();
        }
    }

    public static String parsePageByUri(String userId) throws IOException {
        URL url = new URL(USER_PROFILE_URI + userId);
        StringBuilder htmlPage = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            while (reader.ready()) {
                htmlPage.append(reader.readLine());
            }
        }
        return htmlPage.toString();
    }

    public static Integer parseLevel(String htmlPage) {
        return parseDataForWidget(htmlPage, LEVEL_REGEX);
    }

    public static Integer parseRating(String htmlPage) {
        return parseDataForWidget(htmlPage, RATING_REGEX);
    }

}