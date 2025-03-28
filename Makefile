.PHONY: clean build run test clean-db help fmt clean-run setup-local

help:
	@echo "Available commands:"
	@echo "  setup-local - Setup local environment"
	@echo "  clean-run - Clean database and run application"
	@echo "  fmt       - Format code"
	@echo "  clean-db  - Set up fresh local database using docker, liquibase and postgres"
	@echo "  clean     - Clean build artifacts"
	@echo "  build     - Build the project"
	@echo "  run       - Run the application"
	@echo "  test      - Run tests"

setup-local:
	mise install

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
	set -a && source .env.local && set +a && \
	env && \
	$(MAKE) run
