# HOME TASK

This service is used for validation purposes:
- This service enables the validation of account number through the help of external apis.

## Contents

- [Endpoints](#endpoints)
- [Configuration](#configuration)
- [Testing](#testing)
- [Dependencies](#dependencies)

## Endpoints

http://localhost:8080/v1/accounts/validate
- HTTP Method: POST
- Payload: ```{
  "accountNumber": "12345678",
  "sources": [
  "source1",
  "source2"
  ]
  }```

### Required software

- Java 11
- Recommend [SDK man](http://www.sdkman.io) for managing local Java versions

### Setup

Install Java if required.

```
sdk install java 11.0.6.hs-adpt
```

### Build locally

```
sdk use java 11.0.6.hs-adpt (if not default)
./mvnw clean build
```


## Configuration

The application uses different config files based on the environment. By default, the application runs with the `default` config file. To use the live profile
You can start the application with `SPRING_PROFILES_ACTIVE` set to `live` and it will be able to use the live configs.

```
SPRING_PROFILES_ACTIVE=live ./mvnw spring-boot:run
```
When you are running from the IntelliJ, then set the active profile to local in the `Run Configuration`.


## Testing
The application has unit tests as well as integration tests.
To run the test:
```
./mvnw test
```

## Dependencies
The application uses several third party dependencies. These include:
- spring cloud open-feign: used to build declarative rest clients to facilitate communication with the external APIs.
- Mockito: unit test mocking.
- WireMock: Mocking the external APIs.
- Lombok: Library to generate repetitive and verbose code.
