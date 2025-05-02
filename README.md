# MVP für das Post-COVID-Datenmodell

Die Anwendung Post-COVID Datenmodell ist die Implementierung eines MVP für ein sektorübergreifendes Datenökosystem 
für die Post-COVID Forschung.

Unser Verständnis von Krankheiten wie Post-COVID hängt entscheidend von Datensätzen ab, 
die sowohl von hoher Qualität als auch miteinander verknüpfbar sein müssen. 
In diesem Fall, und generell für viele datengetriebene Bereiche in Wissenschaft und Industrie gleichermaßen, 
gerät die Nachfrage nach Daten schnell in Konflikt mit der ebenso berechtigten gesellschaftlichen Forderung nach 
Datenschutz, Datenhoheit und nationalen Datenschutzgesetzen. 
Als Antwort auf dieses Problem der gemeinsamen Datennutzung und den damit verbundenen technologischen, 
rechtlichen und prozessualen Fragen haben wir im Rahmen der von der deutschen Regierung geförderten 
Post-COVID-Challenge eine quelloffene Dateninfrastruktur entwickelt. 
Unser Ansatz kombiniert Privacy Preserving Record Linkage (PPRL) mit dem fortschrittlichen Datentreuhändermodell von
<a href="https://www.eurodat.org/">EuroDaT</a>, das auf dem Prinzip der Datentransaktion basiert.
Dadurch werden automatisch Sicherheit, Datensouveränität und Rechtskonformität bei der Erstellung und Bereitstellung 
von integrierten und verknüpften Datenprodukten gewährleistet, und damit zentrale Herausforderungen bei der 
Datennutzung sowohl in der Wissenschaft als auch in der Industrie adressiert.


# MVP for the Post-COVID Datenmodell
The application Post-COVID Datenmodell is the implementation of a MVP for a cross-sectoral data ecosystem for 
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
  - You need at least two different EuroDaT clients, one for the datenmodell, one for the client app
  - The datenmodell EuroDaT client keystore files have to be put here:
  `datenmodell-backend/src/main/resources/keystore.(jks, pem)`
  - The client app EuroDaT client keystore files have to be put here:
  `clientapp-backend/src/main/resources/keystore.(jks, pem)`
  - For the end-to-end tests, you may setup a third EuroDaT client or use the client app EuroDaT client.
  In either case, the keystore files  have to be put here: `e2e-tests/secrets`
- Setup a public image repository for the EuroDaT data analysis Python applications
  - For the end-to-end tests, Docker build and push the images  to your public image repository, using 
  the Dockerfiles `e2e-tests/postcovid-app-1/Dockerfile` and `e2e-tests/postcovid-app-2/Dockerfile`.
- Copy the following template files and replace the placeholders `<TO_BE_CHANGED>` by their respective values:
  - Set the backend secrets
    - Copy `datenmodell-backend/templates/*` to `datenmodell-backend/` 
    - Copy `clientapp-backend/templates/*` to `clientapp-backend/`
  - Set the Keycloak secrets
    - Copy `datenmodell-keycloak/templates/*` to `datenmodell-keycloak/`
    - Copy `clientapp-keycloak/templates/*` to `clientapp-keycloak/`
  - Set the application properties for the development profile 
      - In `datenmodell-backend/src/main/`, copy `resources/templates/application-development.yaml` to 
      `resources/application-development.yaml`
      - In `clientapp-backend/src/main/`, copy `resources/templates/application-development.yaml` to
        `resources/application-development.yaml`
  - Set the application properties for the test profile
    - In `datenmodell-backend/src/test/`, copy `resources/templates/application-test.yaml` to
      `resources/application-test.yaml`
    - In `clientapp-backend/src/test/`, copy `resources/templates/application-test.yaml` to
      `resources/application-test.yaml`
  - Set credentials for the end-to-end test, in `e2e-tests/postcovid-app-1` and  `e2e-tests/postcovid-app-2`:
    - Copy `templates/credentials.txt` to `credentials.txt`


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
- The stack of the client application. It consists of the top-level modules/directories
    - [clientapp-frontend](clientapp-frontend)
    - [clientapp-backend](clientapp-backend)
    - [clientapp-keycloak](clientapp-keycloak)

There are some curl-based end-to-end-tests for the communication with the data trustee EuroDaT 
in [e2e-tests](e2e-tests). 

## Docker

Use Docker Desktop and run 
```sh
docker compose --profile datenmodell --profile clientapp up -d
```
For running only the datenmodell stack or only the client app stack, select one of the following commands 
using Docker profiles:
-  ```docker compose --profile datenmodell up -d```
-  ```docker compose --profile clientapp up -d```

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