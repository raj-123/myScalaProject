package sparkTest.mypackage
import org.apache.spark.util.SizeEstimator
import org.apache.commons.mail._
import org.slf4j.LoggerFactory
import com.redislabs.provider.redis._
import org.apache.commons.lang3.exception.ExceptionUtils
import com.datastax.spark.connector.SomeColumns
import com.datastax.spark.connector.writer._
import com.datastax.spark.connector.toRDDFunctions
import com.datastax.spark.connector.streaming._
import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.sql.expressions.Window
import java.text.SimpleDateFormat
import org.apache.spark.sql.types._
import java.util.{Date, Properties}
import net.liftweb.json.DefaultFormats
import java.sql.Timestamp
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions
import org.apache.kafka.common.serialization.StringSerializer
import java.text.SimpleDateFormat
import java.util.ArrayList
import scala.util.parsing.json._
import java.util.Date
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql._
import kafka.serializer.StringDecoder

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.storage.StorageLevel

import org.apache.commons.collections.ListUtils
import java.util.Calendar

import java.util.Locale
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import com.fasterxml.jackson.databind.ObjectMapper;
import net.liftweb.json.parse


case class kafkaDummyData(a : Int, b : String)
case class CustomException(s: String)  extends Exception(s)

case class kafkaliveData(id:String, lT:Double, lN:Double, evT: Long, lstevT:Long, s : Int, d:Int, agl:Int, chg:Int,d1:Int, d2:Int, d3:Int, d4:Int,eBt:Int,iBt:Int,
port:Int, a1:Int, a2:Int, a3:Int, a4:Int, nOs:Int, dFrmD:Long )

object kafkaStreaming {
  def parsingMethod(json: String): kafkaDummyData = {
    //println("JSON : "+json.toString())
    implicit val formats = DefaultFormats
    val parsedJson = parse(json)
    val getJson = parsedJson.extract[kafkaDummyData]
    /*
    return kafkaliveData(getJson.id, getJson.lT, getJson.lN, getJson.evT, getJson.lstevT, getJson.s, getJson.d, getJson.agl, getJson.chg,getJson.d1, getJson.d2, getJson.d3, getJson.d4,getJson.eBt,getJson.iBt,
getJson.port, getJson.a1, getJson.a2, getJson.a3, getJson.a4, getJson.nOs, getJson.dFrmD)
    */
    return kafkaDummyData(getJson.a, getJson.b)
    
  }
  
  
  
  def main(args: Array[String]) {
    try{
      
      
    
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("kafkaStreaming") 
      .set("spark.authenticate.secret", "root")
    val spark = SparkSession
      .builder
      .appName("kafkaStreaming")
      .config(conf)
      //.config("spark.authenticate.secret", "root")
      .getOrCreate()
    val sc = SparkContext.getOrCreate(conf)
    
    val ssc = new StreamingContext(sc, Seconds(10));
    val topics = Map("axestrack" -> 2)
    val kafkaParams = Map[String, String](
      "zookeeper.connect" -> "107.6.151.182:2185",
      "group.id" -> "koll19090890",
      "auto.offset.reset" -> "largest")
    import spark.implicits._
    val kafkaStream = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics, StorageLevel.MEMORY_AND_DISK_SER) 
    
    val parsedJsonRDDList = kafkaStream.map(_._2).map(parsingMethod)
    parsedJsonRDDList.foreachRDD(rdd => {
      if(!rdd.isEmpty()){
        val noOfPartitions = rdd.getNumPartitions
        println("No Of Partitions is : "+noOfPartitions);
        val df = rdd.toDF("a", "b") 
        df.createOrReplaceTempView("table1")
        val x = List.range(1, 10)
        val sizeOfDF = SizeEstimator.estimate(x);
        println("Size of sizeOfDF : "+sizeOfDF);
        
        val rs = spark.sql("select a, b,  LAG(a) OVER ( ORDER BY  a) as prev_a from table1");
        rs.show()
        
        }
      else{
        println("rdd is empty ..........")
      }
      
    })
    ssc.start();
    ssc.awaitTermination();
    }
     catch {
          case ex : Exception => {
              println ("\n  ExceptionCaught : " + ex)
              println ("\n  ExceptionCaught : " + ex.getStackTrace + "\n")
  }
        }
  }
}