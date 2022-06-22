FROM openjdk:8-jre-slim
ADD ./target/*.jar /app/fic-kanban.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
EXPOSE 5656
ENTRYPOINT ["java","-jar","/app/fic-kanban.jar"]