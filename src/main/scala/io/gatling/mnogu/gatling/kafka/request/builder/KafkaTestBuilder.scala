package io.gatling.mnogu.gatling.kafka.request.builder

import com.typesafe.scalalogging.Logger
import io.gatling.core.session.Expression
import io.gatling.mnogu.gatling.kafka.action.{KafkaProducerActionBuilder, KafkaProducerConsumerActionBuilder}
import org.slf4j.LoggerFactory

case class KafkaTestBuilder(requestName: String) {

  protected val logger: Logger =
    Logger(LoggerFactory.getLogger(getClass.getName))

  protected val testId: String = randomString(4)

  def produceconsume[V](payload: Expression[V]): KafkaProducerConsumerActionBuilder[_, V] = produceconsume(payload, None)

  def produceconsume[K, V](key: Expression[K], payload: Expression[V]): KafkaProducerConsumerActionBuilder[K, V] = produceconsume(payload, Some(key))

  private def produceconsume[K, V](payload: Expression[V], key: Option[Expression[K]]): KafkaProducerConsumerActionBuilder[K, V] =
    new KafkaProducerConsumerActionBuilder(KafkaAttributes(requestName, key, payload,testId))

  def produce[V](payload: Expression[V]): KafkaProducerActionBuilder[_, V] = produce(payload, None)

  def produce[K, V](key: Expression[K], payload: Expression[V]): KafkaProducerActionBuilder[K, V] = produce(payload, Some(key))

  private def produce[K, V](payload: Expression[V], key: Option[Expression[K]]): KafkaProducerActionBuilder[K, V] =
    new KafkaProducerActionBuilder(KafkaAttributes(requestName, key, payload, testId))

  def randomString(l: Int): String = {
    logger.info("Generating unique testID in KafkaRequestBuilder")
    scala.util.Random.alphanumeric.take(l).mkString
  }


}
