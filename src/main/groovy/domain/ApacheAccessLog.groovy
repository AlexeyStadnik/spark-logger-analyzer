package domain

import java.util.regex.Matcher
import java.util.regex.Pattern

class ApacheAccessLog {
  String ipAddress;
  String clientIdentd;
  String userID;
  String dateTimeString;
  String method;
  String endpoint;
  String protocol;
  int responseCode;
  long contentSize;

  public ApacheAccessLog() {
  }

  private ApacheAccessLog(String ipAddress, String clientIdentd, String userID,
                          String dateTime, String method, String endpoint,
                          String protocol, String responseCode,
                          String contentSize) {
    this.ipAddress = ipAddress;
    this.clientIdentd = clientIdentd;
    this.userID = userID;
    this.dateTimeString = dateTime;
    this.method = method;
    this.endpoint = endpoint;
    this.protocol = protocol;
    this.responseCode = Integer.parseInt(responseCode);
    this.contentSize = Long.parseLong(contentSize);
  }
}
