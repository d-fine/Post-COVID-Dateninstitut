

## datenmodell-backend

use IntelliJ for IDE that understands kotlin. For deployment, see docker

### Local execution and debugging:
In the IntelliJ run configuration, add the environment variable ` --spring.profiles.active=development`, 
in order to apply the application properties from `application-development.yaml`

## Docker

Use Docker Desktop and run 
```sh
docker-compose up
```

for dis/enabling either the client or datenmodell backend, add a profile to the docker-compose.

## frontends


### Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [VueOfficial (previously called Volar)](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

### Type Support for `.vue` Imports in TS

TypeScript cannot handle type information for `.vue` imports by default, so we replace the `tsc` CLI with `vue-tsc` for type checking. In editors, we need [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) to make the TypeScript language service aware of `.vue` types.

### Customize configuration

See [Vite Configuration Reference](https://vite.dev/config/).

### Setup

### datenmodell-info

Static website without any backend connection

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