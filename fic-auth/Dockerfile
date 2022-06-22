FROM openjdk:8-jre-slim
ADD ./target/*.jar /app/fic-auth.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
EXPOSE 4011
ENTRYPOINT ["java","-jar","/app/fic-auth.jar"]