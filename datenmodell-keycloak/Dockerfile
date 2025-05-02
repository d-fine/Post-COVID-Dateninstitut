FROM quay.io/keycloak/keycloak:26.0.5 AS builder

WORKDIR /opt/keycloak
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:26.0.5
COPY --from=builder /opt/keycloak/ /opt/keycloak/
COPY dateninstitut-realm.json /opt/keycloak/data/import/realm.json

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--import-realm"]