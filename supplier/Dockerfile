FROM openjdk:21
WORKDIR /app
COPY /target/supplier-0.0.1-SNAPSHOT.jar supplier.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/supplier.jar"]


# docker image build -t lmurta94/supplier:1.0.0  GERA A IMAGEM DA APPLICACAO

#  docker push lmurta94/supplier:1.0.0    SOBE A IMAGEM PRO REPOSITORY