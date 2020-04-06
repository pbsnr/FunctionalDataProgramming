object Main extends App {

  import java.util.{Calendar, Date}
  import java.text.SimpleDateFormat
  import scala.io.Source

  import org.apache.log4j.BasicConfigurator
  BasicConfigurator.configure()
  import java.util.Properties
  import org.apache.kafka.clients.producer._

  val  props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)
  val TOPIC=args(0)

  val r = scala.util.Random
  var filename = "csv_files/"+args(1)
  var data = Source.fromFile(filename).getLines

  def nthRecursive[A](n: Int, ls: List[String]): String = (n, ls) match {
    case (0, h :: _   ) => h
    case (n, _ :: tail) => nthRecursive(n - 1, tail)
    case (_, Nil      ) => "NaN"
  }

  def read_csv_line(d: String): String = {
    return nthRecursive(0,(d.split(',').toList))+','+nthRecursive(1,(d.split(',').toList))+','+nthRecursive(2,(d.split(',').toList))+','+nthRecursive(4,(d.split(',').toList))+",NaN,"+nthRecursive(5,(d.split(',').toList))+','+nthRecursive(7,(d.split(',').toList))+','+nthRecursive(43,(d.split(',').toList))+','+nthRecursive(44,(d.split(',').toList))+",NaN,NaN"
  }

  def read_lines(data: Iterator[String]):Unit = {
    for(line <- data){
      val record = new ProducerRecord(TOPIC, "key", read_csv_line(line))
      producer.send(record)
    }
  }


  read_lines(data)

}
