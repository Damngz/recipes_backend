FROM eclipse-temurin:22-jdk AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
COPY src /app/src
COPY Wallet_FullStack3 /app/wallet

ENV TNS_ADMIN=/app/wallet

RUN mvn clean package

FROM eclipse-temurin:22-jdk

COPY --from=buildstage /app/target/recipes_backend-0.0.1-SNAPSHOT.jar /app/recipes_backend.jar

COPY Wallet_FullStack3 /app/wallet

ENV TNS_ADMIN=/app/wallet
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/recipes_backend.jar"]