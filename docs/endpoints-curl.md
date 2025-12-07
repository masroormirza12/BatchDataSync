# API Endpoints & curl examples

## Register a Batch
curl --location --request POST 'http://localhost:8080/prices/batch/start'

## To post a Batch Data to the global map
curl --location 'http://localhost:8080/prices/batch/96f5fdf1-f1f6-4345-9990-89ccdf6137e7/chunk' \
--header 'Content-Type: application/json' \
--data '[
{
"id": "AAPL",
"asOf": "2025-01-01T10:00:00",
"payload": {
"price": 175.5
}
},
{
"id": "AAPL",
"asOf": "2025-01-01T10:12:00",
"payload": {
"price": 195.5
}
}
]'

## Complete a Batch
curl --location --request POST 'http://localhost:8080/prices/batch/96f5fdf1-f1f6-4345-9990-89ccdf6137e7/complete'

## Get Prices Based On Tool ID
curl --location 'http://localhost:8080/prices/AAPL'