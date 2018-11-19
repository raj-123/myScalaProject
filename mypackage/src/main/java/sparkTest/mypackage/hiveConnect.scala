package sparkTest.mypackage
import java.text.SimpleDateFormat
import org.apache.spark.sql.types._
import org.apache.spark.sql.hive.HiveContext
import java.util.{Date, Properties}
import org.apache.spark.sql.hive.orc._
import org.apache.spark.sql._
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


object hiveConnect {
  def main(args: Array[String]) {
    
    
       val spark = SparkSession.builder().appName("Java Spark Hive Example").master("local[2]")
       .config("spark.sql.employee.dir", "hdfs://localhost:9000/hive_warehouse2").enableHiveSupport().getOrCreate();

        spark.sql("select * from wordcount ").show();
  
}
}