PROJECT_DIR := demo

db: ; cd $(PROJECT_DIR) && docker-compose up -d db 

install:
	cd $(PROJECT_DIR) && docker-compose build 

start:
	cd $(PROJECT_DIR) && docker-compose up kotlinapp

stop:
	cd $(PROJECT_DIR) && docker-compose stop

clean: stop 
	cd $(PROJECT_DIR) && docker-compose down --rmi all -v --remove-orphans
