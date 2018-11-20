.DEFAULT_GOAL := build-run

clean:
	./mvnw clean	
run:
	java -jar ./target/games-1.0-SNAPSHOT-jar-with-dependencies.jar

build-run: build run

build: 
	./mvnw package

update:
	./mvnw versions:update-properties
	./mvnw versions:display-plugin-updates