# DigitalVaccination

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 8.3.20.

## Structure
Each page of the vaccination card is created using a separate
**angular component**.
This component must be included according to the angular conventions
in order to display it correctly
(e.g. adding the component to `app.component.ts`).
For each FHIR structure, such as a composition,
a separate **angular service** is used to parse the data. 
## Routing
To avoid unnecessary loading of data, the switching of the different
pages is realized with the help of on angular routings.
The corresponding routes can be created in the `app-routing.module.ts` file.
The maximum number of pages is limited by the variable `maxPage`
in the file `app.component.ts`, otherwise the route cannot be
reached via the corresponding buttons.
## Data parsing
The data of our document are enclosed in a composition.
More information about the composition of the data can be found
in the readme file of the instance creation. 
To parse the data, the JSON representation is loaded from the
relevant FHIR server. Since the amount of data is small,
we have decided to load the references of the FHIR structures directly
into the representation of the composition.
This has the advantage that the composition only needs to be viewed once.
The parsing of this document is realized in the file
`composition.service.ts`.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
