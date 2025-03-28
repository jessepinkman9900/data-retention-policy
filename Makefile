.PHONY: clean build run test clean-db help fmt clean-run

help:
	@echo "Available commands:"
	@echo "  clean     - Clean build artifacts"
	@echo "  build     - Build the project"
	@echo "  run       - Run the application"
	@echo "  test      - Run tests"
	@echo "  clean-db  - Set up fresh local database using docker, liquibase and postgres"
	@echo "  fmt       - Format code"
	@echo "  clean-run - Clean database and run application"

clean:
	./mvnw clean

build:
	./mvnw package -DskipTests

run:
	./mvnw spring-boot:run

test:
	./mvnw test

fmt:
	./mvnw com.spotify.fmt:fmt-maven-plugin:format

clean-db:
	docker compose down && \
	docker compose up -d && \
	sleep 5 && \
	liquibase update \
		--changelog-file=./db-migrations/database.yaml \
		--url="jdbc:postgresql://localhost:5432/data-retention-policy" \
		--username="usr" \
		--password="pwd" \
		--log-level=INFO

clean-run: clean-db
	$(MAKE) run
