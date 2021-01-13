test:
	cd client && npm test && cd -
	cd server && ./gradlew test && cd -

run-client:
	cd client && npm start

run-server:
	cd server && SERVER_PORT=8080 ./gradlew bootRun

create-dbs:
	cd server && psql -d postgres < src/main/resources/db/create_databases.sql && cd -

clear-test-db:
	cd server && psql -d toast_test < src/test/resources/test-clear_db.sql && cd -

seed-test-db:
	cd server && psql -d toast_test < src/test/resources/test-seed_db.sql && cd -

push:
	git pull -r
	cd client && npm install && cd -
	@$(MAKE) test
	git push