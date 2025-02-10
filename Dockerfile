FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
FROM tomee:latest
RUN rm -rf /usr/local/tomee/webapps/ROOT
COPY --from=builder /app/target/EEstack-1.0-SNAPSHOT.war /usr/local/tomee/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]