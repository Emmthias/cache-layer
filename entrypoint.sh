
  JVM_ARGS="-Dcom.sun.management.jmxremote.rmi.port=9010"
  JVM_ARGS="$JVM_ARGS -Dcom.sun.management.jmxremote=true"
  JVM_ARGS="$JVM_ARGS -Dcom.sun.management.jmxremote.port=9010"
  JVM_ARGS="$JVM_ARGS -Dcom.sun.management.jmxremote.ssl=false"
  JVM_ARGS="$JVM_ARGS -Dcom.sun.management.jmxremote.authenticate=false"
  JVM_ARGS="$JVM_ARGS -Dcom.sun.management.jmxremote.local.only=false"
  JVM_ARGS="$JVM_ARGS -Djava.rmi.server.hostname=0.0.0.0"
  JVM_ARGS="$JVM_ARGS -XX:+ExitOnOutOfMemoryError"

  echo "Executing jar with JVM arguments: $JVM_ARGS"

  java -jar $JVM_ARGS /app/cache-layer/cache-layer.jar
