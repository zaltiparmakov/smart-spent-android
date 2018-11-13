# Smart Spent Android application

## DEVELOPMENT
Whenever new feature or fix is going to be added, create new branch from development called FIX:fix-name 
or FEATURE:feature-name and after completing the task, and successfully testing, merge into the development 
branch. When everything is tested and works, merge development into staging branch, and CI/CD will deploy 
to the production if test is passed.

## Introduction
##### This project is in early development mode.
Goal of this project is developing Android application using Machine Learning and
Evolutionary Algorithms.  
Purpose is to develop application which will accept amount of
money user will spent on shopping through the day as an input, analyzing data about
category in which users spent money, make statistics about that, and notify users
on some event, for example spending too much money on unnecessary things, running low
on budget and similar events.  
Android application will also have interactive map, track user location, stores
he/she visited, etc.

## Description
This project has all necessary things you need to bring project to life in just a few
steps.  
This project is dockerized, meaning it runs in an isolated docker containers. There
are 4 Docker containers, and one Docker-Machine (VM) for Windows users.  
Containers:
* Nginx Web Server
* Python / Django
* PostgreSQL Database
* Redis Key-Value DB used for cache or as a Message Broker

Requirements:  
* Docker
* Docker Compose
* Docker Machine

##*nix users
In order to bring web backend to life, run ./setup.sh.
If can't run, execute chmod u+x setup.sh to gain access to run.  
On every change in docker configs execute ./setup.sh update  
When new model is added in django, execute ./setup.sh makemigrations and
./setup.sh migrate

##Windows users
**create new virtualbox docker machine**  
docker-machine create -d virtualbox smartspent-dev  

**update environment vars, in order docker client to talks to docker engine on
smartspent-dev VM instead of host OS**  
eval $(docker-machine env smartspent-dev)
  
**connect with VM via ssh**  
docker-machine ssh smartspent-dev  
After opening ssh connection to the VM, start

##IP Address  
In order Android application to communicate with the backend, nginx web server should 
be configured with the PC private IP address (e.g. 192.168.1.50) in nginx/conf.d/default.conf. 
IP addresses in upstreams also should be changed to that address. To get the IP address of 
your NIC, execute ip addr (on *nix) or ipconfig on Windows. 

##Additional docker commands
**List docker containers, view logs, shell to a docker container, shutdown all containers**  
docker ps  
docker-compose logs  
docker exec -it <container id> /bin/bash  
docker-compose down

**Docker VMs list, SSH to a VM, get the ip, stop VM**  
docker-machine ls  
docker-machine ssh smartspent-dev  
docker-machine ip smartspent-dev  
docker-machine stop smartspent-dev


## Deployment
For deployment, production.yml should be used  
docker-compose -f production.yml up -d
