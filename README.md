# Stop Smoking

Stop Smoking is an App that allows you to track your smoked cigarettes.

## Features

- Add smoked cigarettes.
- View current day smoked cigarettes.
- View daily smoked cigarettes.

## Arquitecture

#### This App is designed in Clean Arquitecture and MVVM.

[![N|Solid](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

It's composed of four modules:

- presentation - View implementation details. Views and ViewModels. Blue and green circles.
- domain - Business rules. It's where the UseCases and Repositories are located. Red and yellow
  circles.
- data - Data layer. This is where the repositories are implemented. They convert the domain DTOs
  into the format most convenient for the database. It's also where DAOs are defined. Green and Blue
  circles.
- app - It has only the Application class and some resources needed to create the APK.

##### These modules were defined this way to provide us some guarantees:

> The Dependency Rule states that source code dependencies can only point inwards. This means no
> inner circle can refer
> in any way anything in an outer circle. Using modules we can ensure circle dependencies are
> respected.

## Run

- Clone this repo.
- Open project in Android Studio.
- Run the App.

## Build

In order to build the project, run unit tests and assemble debug and release APKs execute the
following command:

```sh
./gradlew build
```

## Test

In order to run unit tests execute the following command:

```sh
./gradlew test
```

## Documentation

Domain module is documented with KDoc on all public interfaces.  
Remaining modules have no strict documentation rules. Documentation shall de provided as the
developer sees fit.
