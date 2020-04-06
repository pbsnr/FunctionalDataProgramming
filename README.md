Func_Prog_EFREI_2021

### How to use the CSV reader: ?

after sbt, use : run [topic_name] [file name]


### How to use the drone simulator ?

after sbt, use : run [topic_name] [number_of_messages_to_send] [percentage_of_alert_messages]

Please note that the second argument is a percentage and not a probability, for example, if you want to send 100 messages in the "DRONE" topic with 20% of alerts, you should write : run DRONE 100 20 


### How to setup kafka cluster ?

Execute 'kafka_2.12-2.4.0/kafka_start.sh' [topic_name]

Logs from zookeeper/ kafka servers are stored in .logs files.

