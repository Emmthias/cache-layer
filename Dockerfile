# Download maven with jdk version 11
FROM maven:3-openjdk-11-slim as app_build_image

# Application build steps
RUN mkdir -p /tmp/src

# change the working directory
WORKDIR /tmp/src

# copy source code
COPY . /tmp/src

# create the jar application
RUN mvn clean install

# apply multi stage to execute java jar application
FROM amazoncorretto:11-alpine

# create folder to hold the build
RUN mkdir -p /app/cache-layer

WORKDIR /app/cache-layer/

#copy jar release to a new jar without version
COPY --from=app_build_image /tmp/src/target/cache*.jar /app/cache-layer/cache-layer.jar

# Copy entrypoint script≈
COPY --from=app_build_image /tmp/src/entrypoint.sh /app/cache-layer/entrypoint.sh

# add executable permissions to entrypoint.sh
RUN chmod +x /app/cache-layer/entrypoint.sh

# Exposing http port
EXPOSE 8080
EXPOSE 9010

# Executing application JAR
ENTRYPOINT ["/bin/sh","./entrypoint.sh"]
