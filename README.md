# Kafka pet project N1


## Starting services
`docker compose --project-name=kafka-pet up --build`

## Postman collection

_Kafka_Pet_Project.postman_collection.json_

## Database

##### _jdbc:postgresql://localhost:25432/kafka_pet_

user: test, password: test

## kafkacat commands
kafkacat -C -b broker:9092 -t jdbc-connector-transaction -s key=s -s value=avro -r schema-registry:8081
kafkacat -C -b broker:9092 -t kafka.public.transaction -s key=s -s value=avro -r schema-registry:8081
kafkacat -C -b broker:9092 -t kafka.public.client -s key=s -s value=avro -r schema-registry:8081