FROM amazoncorretto:11-alpine-jdk

COPY target/MyTelegramBot-0.0.1-VERSION.jar first-telegrambot-0.0.1.jar

ENTRYPOINT ["java","-jar","/first-telegrambot-0.0.1.jar"]
