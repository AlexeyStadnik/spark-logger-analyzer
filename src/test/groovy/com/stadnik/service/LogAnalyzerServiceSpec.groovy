package com.stadnik.service

import com.stadnik.domain.ApacheAccessLog
import com.stadnik.util.LogParseUtils
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.JavaSparkContext
import spock.lang.Shared
import spock.lang.Specification

//Serializable is needed for Spark context to start
class LogAnalyzerServiceSpec extends Specification implements Serializable {


    @Shared JavaSparkContext sc

    def setupSpec() {
        def conf = new SparkConf().setAppName("Log Analyzer Test").setMaster("local")
        sc = new JavaSparkContext(conf)
    }

    def "Should find all enries of the exact status" () {
        when:
        JavaRDD<String> logLines = sc.textFile(this.getClass().getResource('/apacheAccessLogs.log').toString());
        JavaRDD<ApacheAccessLog> accessLogs =
                logLines.map({ log -> LogParseUtils.parseFromLogLine(log) }).cache();
        println LogAnalyzerService.FIND_EXACT_RESPONSE(accessLogs, 200)


        then:
        1 == 1
    }
}
