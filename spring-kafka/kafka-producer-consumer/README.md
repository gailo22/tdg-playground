# Testing

`$ curl -X POST http://localhost:8080/send/foo/bar`


`$ curl -X POST http://localhost:8080/send/foo/fail`


## Generate Avro
```
cd scripts
java -jar avro-tools-1.10.2.jar compile schema ../src/main/resources/avro/user_schema.avsc .
```
move generated codes to model package