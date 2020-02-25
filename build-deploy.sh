#!/bin/bash

PROFILE=""
DEPLOY=0
while [ ! -z "$1" ];
do
	case "$1" in
		-p | --profile)
			shift
			PROFILE=$1
			;;
		-d)
			DEPLOY=1
			;;
		*)
			;;
	esac
	shift
done

if [ -z $PROFILE ];
then
	echo
	echo "Require PROFILE"
	echo
	echo "./build-run.sh -p <profile> -d"
	echo " -p: Profile"
	echo " -d: deploy after build."
	exit 1
fi

echo
echo " > mvn clean package -Dbuild.profile=$PROFILE -U -up"
mvn clean package -Dbuild.profile=$PROFILE -U -up

if [ $DEPLOY -ne 1 ];
then
	exit 0
fi

echo
echo " > cd ./deploy/$PROFILE"
cd ./deploy/$PROFILE
echo " > ./deploy.sh"
./deploy.sh
cd ..

exit 0
