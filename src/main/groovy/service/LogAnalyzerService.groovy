package service

import domain.ApacheAccessLog
import org.apache.spark.api.java.JavaRDD
import scala.Tuple2


class LogAnalyzerService {

    static SUM_REDUCER = { a, b -> a + b }

    static FIND_EXACT_RESPONSE = { JavaRDD<ApacheAccessLog> accessLogs, int statusCode ->
        def ipAddresses =
                accessLogs
                        .filter { log -> log.responseCode == statusCode }
                        .mapToPair { log -> new Tuple2(log.endpoint, 1L) }
                        .reduceByKey(SUM_REDUCER)
                        .take(100)
        ipAddresses
    }
}
