# BatchDataSync
📌 Project: Last Price Service
Overview
This project implements an in-memory Java service for tracking the last price of financial instruments. It provides a clean API for producers to publish price data in batch runs and for consumers to retrieve the latest available prices.

✨ Features
Price Records Each record contains:
id: Instrument identifier (string)
asOf: Timestamp when the price was determined
payload: Flexible data structure holding the price information
Batch Run Workflow Producers can upload prices in controlled batch runs:
Start a batch run
Upload records in chunks (up to 1000 per chunk)
Complete or cancel the batch run
Consumer Access
Consumers can request the last price record for a given instrument ID.
The "last" value is determined by the asOf timestamp.
Prices from a batch run become available only after completion, ensuring atomic visibility.
Cancelled batch runs are discarded safely.

Resilience
Handles incorrect producer call sequences gracefully.
Protects consumers from inconsistent data while a batch is being processed.

⚙️ Technical Details
Language: Java
Architecture: In-memory solution (no database dependency)
API: Defined as a Java interface, enabling producers and consumers to run in the same JVM
Concurrency: Designed to be thread-safe and resilient against misuse

🚀 Usage
Producers:
Start a batch run → Upload chunks → Complete or cancel
Consumers:
Query the latest price for a given instrument ID

📂 Repository Structure
src/main/java/... → Core service implementation
src/test/java/... → Unit tests and integration tests

✅ Goals
Provide a simple, reliable, and extensible service for managing last prices.

Ensure atomic visibility of batch uploads.

Demonstrate clean API design and robust handling of producer/consumer workflows.
