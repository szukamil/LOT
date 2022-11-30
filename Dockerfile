FROM maven:3.8.4-openjdk
WORKDIR /LOT
COPY . .
COPY wait-for-it.sh /usr/wait-for-it.sh
RUN chmod +x /usr/wait-for-it.sh
RUN mvn clean install -DskipTests
CMD mvn spring-boot:run