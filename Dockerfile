# Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jdk-jammy
# Create a non-root user (Required by Hugging Face)
RUN useradd -m -u 1000 user
USER user
ENV HOME=/home/user \
    PATH=/home/user/.local/bin:$PATH

WORKDIR $HOME/app

COPY --from=build --chown=user /target/taskflow-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 7860
ENTRYPOINT ["java","-jar","app.jar"]