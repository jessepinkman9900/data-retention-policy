.PHONY: clean build run test clean-db help fmt clean-run setup-local docker-build docker-run

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
	@echo "  docker-build - Build Docker image"
	@echo "  docker-run   - Run application in Docker"

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
	sleep 2 && \
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

docker-build:
	docker build -t data-retention-policy:latest .

docker-run: clean-db docker-build
	docker run --rm -it \
		--network=host \
		-e SPRING_PROFILES_ACTIVE=local \
		--env-file .env.local \
		data-retention-policy:latest
