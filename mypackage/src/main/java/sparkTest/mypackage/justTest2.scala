package sparkTest.mypackage
import java.text.SimpleDateFormat
import org.apache.spark.sql.types._
import java.util.{Date, Properties}
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
object justTest2{

    def main(args: Array[String]) {
      
      //KAFKA PROPS
       val configProperties = new Properties();
       configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"107.6.151.182:8092");
       configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
       configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer");
       val outtopic="axestrack1"
       val producer = new KafkaProducer(configProperties);
       val objectMapper = new ObjectMapper();
      
     
      val conf = new SparkConf()
      //.setMaster("spark://192.168.0.40:7077")
      .setMaster("local[2]")
      .setAppName("kafkaStreaming")
      .set("spark.authenticate.secret", "root")
      .set("spark.cassandra.connection.host", "107.6.151.182")
      .set("spark.cassandra.connection.keep_alive_ms", "20000")
      .set("spark.executor.memory", "1g")
      .set("spark.driver.memory", "2g")
      .set("spark.submit.deployMode", "cluster")
      .set("spark.executor.instances", "1")
      .set("spark.executor.cores", "1")
      .set("spark.cores.max", "4")
 
    val spark = SparkSession
      .builder
      .appName("kafkaStreaming")
      .config(conf)
      //.master("spark://192.168.0.40:7077")
      .getOrCreate()
    
    val sc = SparkContext.getOrCreate(conf)
    val ssc = new StreamingContext(sc, Seconds(10));
    val topics = Map("axestrack" -> 2)
    val kafkaParams = Map[String, String](
      "zookeeper.connect" -> "107.6.151.182:2187",
      "group.id" -> "axGrpukl6",
      "auto.offset.reset" -> "smallest")
    import spark.implicits._
    val kafkaStream = KafkaUtils.createStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics, StorageLevel.MEMORY_AND_DISK_SER) 
    val parsedJsonRDDList = kafkaStream.map(_._2)//.map(parsingMethod)
    parsedJsonRDDList.foreachRDD(rdd =>
      rdd.foreachPartition(
          partitionOfRecords =>
            {
                //val props = new HashMap[String, String]()
                val props = new Properties()
                props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "107.6.151.182:8092")
                props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                  "org.apache.kafka.common.serialization.StringSerializer")
                props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                  "org.apache.kafka.common.serialization.StringSerializer")
                val producer = new KafkaProducer[String,String](props)

                partitionOfRecords.foreach
                {
                    case x:String=>{
                        println(x)

                        val message=new ProducerRecord[String, String](outtopic,null,x)
                        producer.send(message)
                        println("Data Has Been Produced To Kafka...................................")
                    }
                }
          })
) 
    
    
    ssc.start();
    ssc.awaitTermination();
 
    }
    
  
}