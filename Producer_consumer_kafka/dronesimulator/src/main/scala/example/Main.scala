object Producer extends App {

  import org.apache.log4j.BasicConfigurator
  BasicConfigurator.configure()
  import java.util.Properties
  import org.apache.kafka.clients.producer._

  import java.util.{Calendar, Date}
  import scala.io.Source
  import java.text.SimpleDateFormat



  val  props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)
  val TOPIC=args(0)

  val r = scala.util.Random
    
  val dateFormat = new SimpleDateFormat("d/M/y")
  val timeFormat = new SimpleDateFormat("h:m:s")


  def latitude(): String ={
    (40.493.toFloat+r.nextFloat()%0.430.toFloat).toString()
  }
  def longitude(): String ={
    "-"+(73.685.toFloat+r.nextFloat()%0.59.toFloat).toString()
  }
  def time(dateFormat: SimpleDateFormat): String ={
    dateFormat.format(Calendar.getInstance().getTime())
  }
  def id(): String ={
    r.nextInt(1000).toString()
  }
  def battery(): String ={
    r.nextFloat().toString()
  }
  def plate(): String ={
    r.alphanumeric.filter(_.isLetter).take(3).mkString("").toUpperCase()+r.alphanumeric.filter(_.isDigit).take(4).mkString("")
  }
  def violation_code(): String ={
    r.nextInt(100).toString
  }
  def select_car_make(): String ={
    nthRecursive(r.nextInt(10), List("NISSAN","GMC","TOYOTA","ACURA","HONDA","BMW","AUDI","PORSCHE","FORD","CHEVROLET"))
  }
  def regular_message(): String ={
    "NaN,"+"NaN"+","+"NaN"+","+time(dateFormat)+","+time(timeFormat)+","+"NaN"+","+"NaN"+","+latitude+","+longitude+","+battery+","+id
  }
  def violation_message(): String ={
    "NaN,"+plate+","+"NY"+","+time(dateFormat)+","+time(timeFormat)+","+violation_code+","+select_car_make+","+latitude+","+longitude+","+battery+","+id
  }
  def nthRecursive[A](n: Int, ls: List[A]): A = (n, ls) match {
    case (0, h :: _   ) => h
    case (n, _ :: tail) => nthRecursive(n - 1, tail)
    case (_, Nil      ) => throw new NoSuchElementException
  }

  def select_record(x: Int, prob: Int):Unit = x match {
    case x if x < prob => send_violation
    case x if x > prob-1 => send_regular
  }

  def send_violation(): Unit = {
    Thread.sleep(r.nextInt(3000))
    val record = new ProducerRecord(TOPIC, "key", violation_message)
    producer.send(record)
    println(violation_message)
  }

  def send_regular(): Unit = {
    Thread.sleep(r.nextInt(3000))
    val record = new ProducerRecord(TOPIC, "key", regular_message)
    producer.send(record)
  }

  def send_messages(x: Int, proba: Int): Unit = x match {
    case x if x > 0 => send_message(x, proba)
    case x if x == 0 => return
  }

  def send_message(x: Int, proba: Int): Unit ={
    select_record(r.nextInt(100), proba)
    send_messages(x-1, proba)
  }

  send_messages(args(1).toInt, args(2).toInt)
  producer.close()
}
