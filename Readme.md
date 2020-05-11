# Future Transactions - Kafka Producer & Consumer

Here, I've created a Kafka producer and consumer to read and process future transactions from Feed.txt file

## txn-event-producer

This is a spring boot kafka producer which has a Rest API which, on invocation, will trigger a reader service to read the Feed.txt and push the messages to a topic

```bash
POST http://localhost:8080//v1/api/future-txn-event-producer
```

## txn-event-consumer

This is a spring boot kafka consumer which consumes the messages from topic and saves it in the H2 database.

There are 2 API's in the consumer app -

* Summary Report API

This API generated a summary report of all the transactions in the H2 database.
The output.json is a sample output of this API.

```bash
GET http://localhost:8081//v1/api/future-txn-event-consumer/summary-details
```
* Get Product Net Amount API

This API will give the total amount for the qualifying product

```bash
GET http://localhost:8081//v1/api/future-txn-event-consumer/product-transaction-total?exchangeCode=CME&productGroupCode=FU&symbol=N1
```
