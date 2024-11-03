FROM gradle:jdk21-graal-jammy AS BUILD

WORKDIR /app

COPY . .

RUN ./gradlew clean build

FROM registry.access.redhat.com/ubi8/openjdk-21:1.20 AS PROD

ENV LANGUAGE='en_US:en'

COPY --from=BUILD --chown=185 /app/build/quarkus-app/lib/ /deployments/lib/
COPY --from=BUILD --chown=185 /app/build/quarkus-app/*.jar /deployments/
COPY --from=BUILD --chown=185 /app/build/quarkus-app/app/ /deployments/app/
COPY --from=BUILD --chown=185 /app/build/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]

