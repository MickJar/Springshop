# Springshop Warehouse

A simple warehouse implemented with Spring boot on Java 17.

## Development

Application requirements:
1. JAVA 17 SDK

To run the application:

`./gradlew bootRun`

The application has API documentation through swagger on the path: `/swagger/index.html`

The application is created with an N-Tier architecture.
The currently available tiers:
1. Controllers
2. Business
3. Integrations

The application is designed to house models within the application
layers. Application models are only to be shared upwards in the n-tier structure.

### Development guidelines

This application requires testing on all the business classes.

Useful defaults:

1. Default port: 8080
2. Gradle language: Groovy
3. SDK level: 17

## Features

The application supports:

1. Querying product definitions
2. Querying inventory status
3. Selling a product

The application has a modular design in order to support future
inventory microservice implementations.

## Issues

1. The application currently doesn't support transactions to sell products, 
which can result in selling the same inventory to several customers.
2. There is currently no way to add inventory or products to the application
without restarting.
3. There is currently no integration tests setup to test the static file reading and mapping.
4. There is currently no scaling support for this microservice, as there is no way to share inventory between services.
5. Error handling flow needs to improve with standardized codes and messages.