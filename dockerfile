# ---------- Build Stage ----------
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /build

COPY . .

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
