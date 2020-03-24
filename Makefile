build-docker-images:
	cd backend/ && docker build --rm -f "Dockerfile" -t ruiblaese/cdpu-backend:latest . 
run-docker-backend:
	docker run --rm --add-host="db:192.168.0.134" -p 8080:8080 -it ruiblaese/cdpu-backend:latest
run-docker-compose:
	cd backend && ./mvnw install
	cd backend && docker build --rm -f "Dockerfile" -t ruiblaese/cdpu-backend:latest .
	cd frontend && yarn && yarn build
	docker-compose up -d
push-heroku:
	git subtree push --prefix backend heroku master