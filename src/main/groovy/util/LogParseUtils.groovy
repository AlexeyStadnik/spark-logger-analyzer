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
  private static final String LOG_ENTRY_PATTERN =
      "^(\\S+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(\\S+) (\\S+) (\\S+)\" (\\d{3}) (\\d+)";
  private static
  final Pattern PATTERN = Pattern.compile(LOG_ENTRY_PATTERN);

  public static parseFromLogLine = { String logline ->
    Matcher m = PATTERN.matcher(logline);
    if (!m.find()) {
      throw new RuntimeException("Error parsing logline");
    }

    return new ApacheAccessLog(m.group(1), m.group(2), m.group(3), m.group(4),
        m.group(5), m.group(6), m.group(7), m.group(8), m.group(9));
  }

}
