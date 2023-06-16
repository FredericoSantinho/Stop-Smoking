# Expenses Register 3.0

Expenses Register 3.0 is an App thas intends to facilitate the register of regular expenses.

## Features

- Automatic Place selection based on current location with previously registered products
  sugestions.
- Manual expense register.
- Place location shown on maps.
- Search of Places and Place Products.
- Bills that represent groups of expenses in the same place at a given time.

> This App uses your phone location to provide sugestions of Places and Products to register.

## Arquitecture

#### This App is designed in Clean Arquitecture and MVVM.

[![N|Solid](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

It's composed of eight modules:

- presentation - View implementation details. Blue circle.
- viewmodel - ViewModels. Green circle.
- domain - Application specific business rules. It's where the UseCases, Services and Repositories
  are located. Red circle.
- entity - Enterprise wide business rules. It's where main entity controllers are located. This
  module is the least prone to change, as only changes in enterprise business rules should affect
  it.
- data - Data layer. This is where the repositories are implemented. They convert the domain DTOs
  into the format most convenient for the database. It's also where DAOs are defined. Green and Blue
  circles.
- test - Essencially a couple of classes to help design Rx tests.
- app - It has only the AndroidManifest and some build configurations.
- acceptance-tests - This module contains only acceptance / integration tests.

##### These modules were defined this way to provide us some guarantees:

> The Dependency Rule states that source code dependencies can only point inwards. This means no
> inner circle can refer in any way anything in an outer circle. Using modules we can ensure circle
> dependencies are respected.

Starting with the entity module, it has no dependencies outwards. Being the innermost circle, it has
no dependencies at all.

The domain module is the only module that knows about the entity module, and this is the only module
it knows. This way we ensure no entities ever leave the domain layer. UseCases expose DTOs to
communicate with the green circle.

The data and viewmodel modules only know about domain module.

The presentation module knows about the data and the viewmodel modules.

Finally, the app module only needs to know about the presentation module.

acceptance-tests module exists in order to run Acceptance / Integration tests in a different context
of the application's one.

## Test coverage rules

Modules entity and domain have a minimum test coverage ration of 100%. Being the modules where all
business logic lives, we want to make sure every line of code is covereded by the tests.  
In order to run the test coverage verification task execute the following command:

```
./gradlew test jacocoTestCoverageVerification
```

This task will fail if code coverage ratio is below 1.0.  
It is to be used in the CI configuration in order to ensure no build is made without proper business
logic testing.

## Run

- Clone this repo.
- Open project in Android Studio.
- Run the App.

***note**: this App uses Google Maps API to show the Places on the map. Although you can run the
project
without a Google Maps API Key, you will not be able to see the maps.*

## Build

***note:** You will need a valid signing key in order to build the project.*

In order to build the project, run unit tests and assemble debug and release APKs execute the
following command:

```sh
./gradlew build
```

## Test

In order to run unit tests, instrumented test and generate coverage reports run the following
command:

```sh
./gradlew test connectedAndroidTest jacocoTestReport
```

***note**: On android 13 (API 33) compose instrumented tests
fail. (https://issuetracker.google.com/issues/240993946)*

Test coverage reports:

| Module    | Report                                                               |
|-----------|----------------------------------------------------------------------|
| data      | data/build/reports/jacoco/jacocoTestReportDebug/html/index.html      |
| viewmodel | viewmodel/build/reports/jacoco/jacocoTestReportDebug/html/index.html |
| domain    | domain/build/reports/jacoco/test/html/index.html                     |
| entity    | entity/build/reports/jacoco/test/html/index.html                     |

*presentation module has no tests yet.*

## Documentation

entity and domain modules are documented with KDoc on all public interfaces.  
Remaining modules have no strict documentation rules. Documentation shall de provided as the
developer sees fit.

## Development

In order to be able to make a first release we need:

- Finish Bills screen.
- Implement remaining viewmodel module tests.
- Implement remaining data module tests.
- Implement presentation module tests.
- Define and implement remaining screens acceptance / integration tests.
- Code review.
