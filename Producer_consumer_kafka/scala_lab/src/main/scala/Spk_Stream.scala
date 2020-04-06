import java.io.{File, PrintWriter}

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Milliseconds, Seconds, StreamingContext}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import java.util.{Collections, Properties}

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, ByteArraySerializer, StringDeserializer, StringSerializer}

object Spk_Stream extends App {

  val conf = new SparkConf().setMaster("local[*]").setAppName("KafkaReceiver")
  val sc = new SparkContext(conf)
  val ssc = new StreamingContext(sc, Seconds(1))
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._

  val producerProperties = new Properties()
  producerProperties.put("bootstrap.servers", "localhost:9092")
  producerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  producerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  val producer = new KafkaProducer[String, String](producerProperties)
  val topic = Array("rtrann")

  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "stream_id",
    "auto.offset.reset" -> "earliest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )
  val kafkaStreams = KafkaUtils.createDirectStream[String, String](
    ssc,
    PreferConsistent,
    Subscribe[String, String](topic, kafkaParams)
  )

  kafkaStreams.map(record => (record.value)).foreachRDD( rdd => {
    if(!rdd.isEmpty())
    {
      rdd.map(x => {
        val split = x.split(',')
        if(split(5).toString == "15") {
          val file = new File("/home/rafa/alert_messages/alert.txt")
          val w = new PrintWriter(file)
          w.append(x)
          w.close()
        }
      }).foreach(print)
      rdd.saveAsTextFile("/home/rafa/sink_test")
      rdd.saveAsTextFile("hdfs://localhost:9000/rtrann/")
    }
   })

  ssc.start()
  ssc.awaitTermination()
}
