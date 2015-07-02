#!/bin/bash
## Remove all docker containers and images on the host
docker rm $(docker ps -a -q)
docker rmi $(docker images -q)