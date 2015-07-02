#!/bin/bash
#S skeliton script to build deploy and run the clearAvenue myMedications docker images

## Assumes internet facing linux host with docker running and git
## Assumes user has correct permissions to run docker
## Assume user has the IP or hostname of the host running docker

## Make and use a working directory
mkdir clearavenue
cd clearavenue
## Get the clearAvenue myMedications Food and Drug Administration Drug Interactions, FDADI project from github
git clone https://github.com/clearavenue/FDADI.git
## change to the docker directory
cd FDADI/docker
## Build the clearAvenue myMedications image based on the adjacent Dockerfile
docker build -t clearavenue/mymeds .
## Run the docker hub official MongoDB image
docker run --name clearavenue-mymeds-mongo -d mongo
## Run the clearAvenue myMedications image and make the Mongo image available to it and bind the image port 8080 to the host port 8080
docker run -p 8080:8080 --link clearavenue-mymeds-mongo:mongo --name clearavenue-mymeds -d clearavenue/mymeds
## here you can access and use the clearAvenue myMedications application at http://your.docker.host:8080/FDADI


## Stop the myMedications containers
## docker stop clearavenue-mymeds
## docker stop clearavenue-mymeds-mongo
## Remove all docker containers and images on the host
## docker rm $(docker ps -a -q)
## docker rmi $(docker images -q)