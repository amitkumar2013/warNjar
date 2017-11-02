# warNjar
This shows how to use profile to create a jar to be used in development
as well as a war to be used in a web application container.

So 
$ mvn clean package -P dev spring-boot:run 
-- package is optional -- creates a jar
-- -P dev is optional too -- its active by default in pom

$ mvn clean package -P qa spring-boot:run
-- works as its packaged with everything which makes it executable.
-- this will create a war + war.original

Deploy the war to the container and it works!