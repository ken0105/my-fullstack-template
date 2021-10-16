base:
	cd db && docker-compose -p my_fullstack_template_db up --detach

base/stop:
	cd db && docker-compose stop

base/down:
	cd db && docker-compose down

run-client:
	cd client && npm start

run-server:
	cd server && SERVER_PORT=8081 ./gradlew bootRun

run-e2e:
	cd e2e && npm run cypress:run

create-dbs:
	cd server && psql -d postgres < src/main/resources/db/create_databases.sql && cd -

clear-test-db:
	cd server && psql -d toast_test < src/test/resources/test-clear_db.sql && cd -

seed-test-db:
	cd server && psql -d toast_test < src/test/resources/test-seed_db.sql && cd -

test:
	cd client && npm test && cd -
	cd server && ./gradlew test && cd -
	@$(MAKE) run-e2e

push:
	git pull -r
	cd client && npm install && cd -
	@$(MAKE) test
	git push