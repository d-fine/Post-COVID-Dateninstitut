# Post-COVID Datenmodell
The application Post-COVID Datenmodell is the implementation of a cross-sectoral data ecosystem for 
post-COVID research.

Our understanding of diseases like Post-COVID crucially depends on data sets that are of high-quality 
and that can be linked. In this case, and generally for a lot of data-driven areas in academia and industry alike, 
the demand for data quickly comes in conflict with the equally justified societal demand for data privacy, 
data sovereignty and national laws for data protection. As an answer to this problem of data-sharing 
and the related technological, legal and procedural questions, we have developed an open-source 
data infrastructure as part of the Post-COVID challenge 
funded by the German government. Our approach combines Privacy Preserving Record Linkage (PPRL) 
with the advanced data trustee model of 
<a href="https://www.eurodat.org/">EuroDaT</a> 
which is centered around the data transaction principle. 
Thereby it automatically ensures security, data sovereignty, and legal compliance during the generation 
and delivery of integrated and linked data products, for the benefit of both academia and industry.

# Getting Started
- Setup EuroDaT clients and credentials, following the steps in the [developer information of 
  EuroDaT](https://eurodat.gitlab.io/trustee-platform/eurodat/provision/generate-client-secret/)
 Then copy the key files to the relevant folders and replace the placeholders (named \<tbd\>) in 
  the relevant files accordingly:
  - datenmodell-backend/src/main/resources
  - clientapp-backend/src/main/resources
  - e2e-tests/secrets
  - e2e-tests/postcovid-app-1/test-1-read-hard-coded-output.sh
  - e2e-tests/postcovid-app-2/test-2-write-input-to-output.sh
  - datenmodell-backend/src/main/resources/application.yaml
  - clientapp-backend/src/main/resources/application.yaml

- Setup a public image repository for the EuroDaT data analysis Python applications

# License

This project is free and open-source software licensed under the 
[BSD 3-Clause License](LICENSE.txt). 
Commercial use of this software is allowed.

# Contributions

Contributions are highly welcome. 
If you would like to contribute, please mail us at <a href="mailto:info.post-covid.dateninstitut@d-fine.com">info.post-covid.dateninstitut@d-fine.com</a> .
To avoid work on your side that we cannot integrate, please align with us upfront on your plans.


# Developer Remarks

In this section you find information that is intended for developers. 
This project consists of three independent stacks:
- A static website without backend, intended for information & resources about 
  the project, see the top-level directory
  - [datenmodell-info](datenmodell-info). 

  Our productive version of this website is  available at 
    https://post-covid.dateninstitut.d-fine.dev/
- The stack of the main application, i.e. the Datenmodell application. It consists of the top-level 
  modules/directories:
  - [datenmodell-frontend](datenmodell-frontend)
  - [datenmodell-backend](datenmodell-backend)
  - [datenmodell-keycloak](datenmodell-keycloak)
- The stack of the client application. It consists of the top-level modules/directorie
    - [clientapp-frontend](clientapp-frontend)
    - [clientapp-backend](clientapp-backend)
    - [clientapp-keycloak](clientapp-keycloak)

There are some curl-based end-to-end-tests for the communication with the data trustee EuroDaT 
in [e2e-tests](e2e-tests). 

## Overview of versions
The code base is developed based on the following versions for languages and tools:
- Kotlin 1.9.25
- Java 21
- Node v20.7.0
- npm 10.1.0
- Gradle 8.10.2


## Docker

Use Docker Desktop and run 
```sh
docker-compose up
```

For dis/enabling either the client stack or datenmodell stack, apply the following commands 
using Docker profiles:
-  ```docker-compose --profile datenmodell up -d```
-  ```docker-compose --profile clientapp up -d```

## Backend Remarks

Use IntelliJ or an IDE that understands Kotlin. For deployment, see Docker.

### Local execution and debugging:
In the IntelliJ run configuration, add the environment variable ` --spring.profiles.active=development`,
in order to apply the application properties from `application-development.yaml`


## Frontend Remarks


### Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [VueOfficial (previously called Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

### Type Support for `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for type checking. In editors, we need [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) to make the TypeScript language service aware of `.vue` types.

### Customize configuration

See [Vite Configuration Reference](https://vite.dev/config/).


### Setup
install packages

```sh
npm install
```

Generate backend api code if applicable (see package.json)

Requires java
```sh
npm run openapi-generator
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Type-Check, Compile and Minify for Production

```sh
npm run build
```