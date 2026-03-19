# Build stage
FROM maven:3.9-eclipse-temurin-21 as builder

WORKDIR /app

# Copy the backend project
COPY backend/ayubot ./

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built JAR from builder
COPY --from=builder /app/target/ayubot-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 10000

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:10000/api || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
