***************CHALLENGE CON SPRING BOOT Y JPA***************
LITERALURA

Creado por Saint-Martin

Descripcion: Este es un un proyecto que consulta una API externa (https://gutendex.com) para buscar y guardar libros en una base de datos local con postgres.

El menu consta de opciones donde podra elegir el usuario que acciones tomar basandonos en el numero que aparece en consola.

Se utilizo Java, Spring Boot con las siguientes dependencias:

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>

		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.17.1</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
  
  Espero que sea de tu agrado :)
  ATTE. Saint_
