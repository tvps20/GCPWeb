# Gerenciador de Coleções Pessoais 
![](https://imgur.com/4m0NxB6b.png) ![](https://imgur.com/X9uwJYZ.png)

## Sobre o Projeto
O projeto consiste em ser um gerenciador de coleções pessoais, onde ele
conterá várias funções como: gerenciamento de empréstimos de itens, relações dos itens cadastrados, quais estão faltando para completar a coleção, sistema de ranking, itens repetidos e que podem ser vendidos, itens com defeito.

## Dependências 
- Java JDK 1.8 ou maior
- Maven
- Spring Boot
- Hibernate
- MySQL Server
- Java [Lombok](https://projectlombok.org/)
- JUnit
- Mockito
- Apache Tomcat
- Eclipse IDE

## Application.Properties 
> src/main/resources/application.properties

##### Datasource
A aplicação possui duas urls de conexão com banco de dados. 
- Produção
- Testes

```
# Connection url for the database "gcp_database"
# spring.datasource.url=jdbc:mysql://localhost:3306/gcp_database?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.url=jdbc:mysql://localhost:3306/gcp_database_test?createDatabaseIfNotExist=true&serverTimezone=UTC
```
##### Username e Password
    spring.datasource.username=root
    spring.datasource.password=root

## Importar Projeto no Eclipse

#### 1. Instalar o Lombok 
- Baixe e execute o .JAR no site do  [Lombok](https://projectlombok.org/)
- Reinicie o Eclipse

#### 2. Clone ou baixe o projeto 
#### 3. Import um projeto Maven

![](https://imgur.com/pu7JCpI.png)

#### 4. Atualize as dependências do Maven
- Clique com o botão direito sobre o projeto

![](https://imgur.com/IsnVnq0.png)

#### 5. Adiciona a configuração do Maven Build
- Seleciona a opção *Run Configurations...* no menu *Run*
- Dê dois clicks na opção *Maven Build* para criar uma nova configuração
- Na opção *Goals* coloque o comando *spring-boot:run*

![](https://imgur.com/6fEp6fI.png)

#### 6. Execute a aplicação
> http://localhost:8080

## Publicando no Tomcat

#### 1. Crie um novo Maven Build
- Na opção *Goals* coloque a configuração *-e clean package*

![](https://imgur.com/JiPvoQw.png)

#### 2. Execute o build para gerar o arquivo .war
#### 3. Copie o arquivo war gerado na pasta *target* para o tomcat
> tomcat/webapps

#### 7. Logar no sistema
> Realiar um cadastro de conta na opção 'Criar uma conta nova'