docker run -i --net=confluent --rm \
  confluentinc/cp-kafka kafka-console-consumer \
  --bootstrap-server kafka:9092 \
  --topic call-store-topic \
  --property print.key=true
