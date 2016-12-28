import domain.ApacheAccessLog
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.JavaSparkContext
import scala.Tuple2
import util.LogParseUtils

/**
 * Created by Aliaksei_Stadnik on 12/27/2016.
 */
class LogAnalyzer {

  static def SUM_REDUCER = {a,b -> a + b}

  static void main(String[] args) {

    def conf = new SparkConf().setAppName("Log Analyzer").setMaster("local")
    def sc = new JavaSparkContext(conf)

    def logsFolder = System.getProperty("logs.file")

    if (!logsFolder) {
      throw new RuntimeException("Please specify logs.file property")
    }

    JavaRDD<String> logLines = sc.textFile(logsFolder)

    JavaRDD<ApacheAccessLog> accessLogs =
        logLines.map({ log -> LogParseUtils.parseFromLogLine(log) }).cache();


    def ipAddresses =
        accessLogs
            .filter {log -> log.responseCode != 200}
            .mapToPair { log -> new Tuple2(log.endpoint, 1L) }
            .reduceByKey(SUM_REDUCER)
            .take(100)

    println ipAddresses.join("\n")


  }
}
