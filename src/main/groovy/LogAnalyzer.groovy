import domain.ApacheAccessLog
import org.apache.spark.SparkConf
import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.JavaSparkContext
import service.LogAnalyzerService
import util.LogParseUtils

class LogAnalyzer {

  static void main(String[] args) {

    def conf = new SparkConf().setAppName("Log Analyzer").setMaster("local")
    def sc = new JavaSparkContext(conf)

    def logsFolder = System.getProperty("logs.file") //"C:\\Users\\Aliaksei_Stadnik\\Desktop\\RudderLogs\\caselaw.log"

    if (!logsFolder) {
      throw new RuntimeException("Please specify logs.file property")
    }

    JavaRDD<String> logLines = sc.textFile(logsFolder)

    JavaRDD<ApacheAccessLog> accessLogs =
        logLines.map({ log -> LogParseUtils.parseFromLogLine(log) }).cache();


    println LogAnalyzerService.FIND_EXACT_RESPONSE(accessLogs, 200)


  }
}
