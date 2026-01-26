FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app
COPY . .
RUN mvn test -Punit-tests
CMD ["mvn", "test", "-Punit-tests"]
