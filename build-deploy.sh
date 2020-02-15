#!/bin/bash

if [ -z $1 ];
then
	echo "No profile."
	exit 1
fi

echo
echo " > mvn clean package -Dbuild.profile=$1 -U -up"
mvn clean package -Dbuild.profile=$1 -U -up
echo
echo " > cd ./deploy/$1"
cd ./deploy/$1
echo " > ./deploy.sh"
./deploy.sh
cd ..

exit 0
