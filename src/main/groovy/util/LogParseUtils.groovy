package util

import domain.ApacheAccessLog

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Aliaksei_Stadnik on 12/28/2016.
 */
class LogParseUtils {

    // Example Apache log line:
    //   127.0.0.1 - - [21/Jul/2014:9:55:27 -0800] "GET /home.html HTTP/1.1" 200 2048
    // 1:IP  2:client 3:user 4:date time 5:method 6:req 7:proto   8:respcode 9:size
    static final String LOG_ENTRY_PATTERN =
            "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+)"

    static final Pattern PATTERN = Pattern.compile(LOG_ENTRY_PATTERN);

    static parseFromLogLine = { String logline ->
        Matcher matcher = PATTERN.matcher(logline)

        if (!matcher.find()) {
            throw new RuntimeException("Error parsing logline : ${logline}")
        }

        ApacheAccessLog
                .builder()
                .ipAddress(matcher.group(1))
                .clientIdentd(matcher.group(2))
                .userID(matcher.group(3))
                .dateTimeString(matcher.group(4))
                .method(matcher.group(5))
                .endpoint(matcher.group(6))
                .protocol(matcher.group(7))
                .responseCode(matcher.group(8) as int)
                .contentSize(matcher.group(9) as long)

    }
}
