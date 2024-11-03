FROM gradle:jdk21-graal-jammy AS build

WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew clean build

FROM registry.access.redhat.com/ubi8/openjdk-21-runtime:1.20 AS run

ENV LANGUAGE='en_US:en'

COPY --from=build --chown=185 /app/build/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 /app/build/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 /app/build/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 /app/build/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080

USER 185

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "keytool -importcert -file /usr/local/share/ca-certificates/mongodb-cert.crt -alias mongodb-ca -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -noprompt" ]
CMD [ "/opt/jboss/container/java/run/run-java.sh" ]