FROM openjdk:8
MAINTAINER Clivens Petit <peclevens@magicsoftbay.com>
ADD target/app365*.jar /app365/app365.jar
WORKDIR /app365
CMD ["java", "-jar", "/app365/app365.jar"]