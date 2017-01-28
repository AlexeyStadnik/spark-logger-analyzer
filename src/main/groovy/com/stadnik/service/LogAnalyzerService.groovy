package com.stadnik.service

import com.stadnik.domain.ApacheAccessLog
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

    static FIND_POPULAR_ENDPOINTS = { JavaRDD<ApacheAccessLog> accessLogs, int times ->
        def ipAddresses =
                accessLogs.mapToPair { log -> new Tuple2<>(log.ipAddress, 1L)}
                        .reduceByKey(SUM_REDUCER)
                        .filter { tuple -> tuple._2() > 10 }
                        .map{ tuple -> tuple._1 }
                        .take(100)
    }
}
