package eci.edu.co

import org.apache.spark.sql.functions._


object Exercise01 extends SparkSessionWrapper {

  def main(args: Array[String]): Unit = {
    run()
  }

  def run(): Unit = {
    spark.sparkContext.setLogLevel("Error")
    //org.Apache.spark.sql.kafka010.KafkaSourceProvider
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9093")
      .option("subscribe", "topic")
      .load()

    val df1 = df.select(col("key").cast("string").as("key"), col("value").cast("string").as("value"))

    df1.writeStream
      .format("console")
      .option("truncate", "false")
      .outputMode("append")
      .start()
      .awaitTermination()
  }
}
