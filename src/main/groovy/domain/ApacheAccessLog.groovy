package domain

import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

@Canonical
@EqualsAndHashCode
@Builder
class ApacheAccessLog {
  String ipAddress
  String clientIdentd
  String userID
  String dateTimeString
  String method
  String endpoint
  String protocol
  int responseCode
  long contentSize
}
