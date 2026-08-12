# Build from repo root:
#   docker build -f app/admin/Dockerfile -t hei-boot-admin .
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY . .
# Production image: exclude sample biz module (-P'!with-biz')
RUN mvn -B -pl app/admin -am -DskipTests -P'!with-biz' package

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
RUN useradd --system --create-home --uid 10001 hei \
  && mkdir -p /app/logs /app/storage \
  && chown -R hei:hei /app
COPY --from=build /workspace/app/admin/target/admin.jar /app/app.jar
USER hei
EXPOSE 8000 9999
ENV SPRING_PROFILES_ACTIVE=prod \
    JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
