package sparkTest.mypackage
import com.redislabs.provider.redis._
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

//001,Rajiv,Reddy,9848022337,Hyderabad
case class Person(empId: String, empFirstName: String, empLastName:String, empMobNo:String,empCity:String )

object kafkaStreaming2 {
  
  def main(args: Array[String]) {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("kafkaStreaming")
      
      val spark = SparkSession
      .builder
      .appName("kafkaStreaming")
      .config(conf)
      .getOrCreate()
    
    val sc = SparkContext.getOrCreate(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
    
    val file = sc.textFile("/home/raj/Desktop/myFile.txt").map(_.split(",")).map( x => (x(0),x(2)) )
    file.foreach(println)
    file.foreach(records => {
      println("records : "+records);
    })
    
    val df = sc.textFile("/home/raj/Desktop/myFile.txt")
    .map(_.split(","))
    .map(p=>Person(p(0),p(1),p(2), p(3), p(4)))
    .toDF()
    
    df.show()
    
   
    
    
    
     }
}