#!/bin/bash

#
# @Author : Park Jun-Hong (parkjunhong77@gmail.com)
# @License: MIT. License
# @title  : SSH Tunneling Provider usign Remote Port Forwarding.
#           
# Tunneling using SSH Remote Port Forwarding.
# command: ssh -R {remote-port}:{service-host}:{service-port} {username)@{ssh-server-host} -p {ssh-server-port}

#
# @param $1 {string} (optional) error cause.
help(){
	echo 
	if [ ! -z "$1" ];
	then
		echo " !!! Caller: ${FUNCNAME[1]}, cause: $1"
		echo
	fi
	
	echo "Usage:"
	echo " ssh-tcm.sh <command> <options>"
	echo
	echo "[Examples]"
	echo " ssh-tcm.sh connect -r <rport>:<host>:<port> -s <svr-host>:<svr-port> -u <username> -p <userpwd> -v"
	echo " ssh-tcm.sh disconnect -t <rport>:<username>@<svr-host>:<svr-port>"
	echo " ssh-tcm.sh list"
	echo
	echo "[Command]"
	echo " connect   : connect to ssh server for remote port forwarding."
	echo " disconnect: cancel a remote port forwarding."
	echo " list      : provide remote port forwarding information."
	echo 
	echo "[Options]"
	echo " -p: Use to 'connect'."
	echo "     SSH Server user password"
	echo " -r: Use to 'connect'."
	echo "     Remote Port Forwarding(RPF) Information."
	echo "     + rport : remote port."
	echo "     + host  : RFP destination host."
	echo "     + port  : RFP destination port."
	echo " -s: Use to 'connect'."
	echo "     SSH Server Information."
	echo "     + svr-host: SSH Server host."
	echo "     + svr-port: SSH Server port."
	echo " -t: Use to 'disconnect'."
	echo "     Remote Port Forrwarding Unique Information."
	echo "     + rport: remote port"
	echo "     + username: account username."
	echo "     + svr-host: SSH Server host."
	echo "     + svr-port: SSH Server port."
	echo " -u: Use to 'connect'."
	echo "     SSH Server user name"
	echo " -v: verbose"
}

# @param $1 {number} port
# @param $2 {string} error message
check_port(){
	if [[ ! $1 =~ [0-9]+  ]];
	then
		help "$2. arg=$1"
		exit 1
	fi
}

declare -A __ARGUMENTS__
__command__=""
__verbose__=0
while [ ! -z "$1" ];
do
	case "$1" in
		-h | --help)
			help
			exit 0
			;;
		-p)
			shift
			__userpwd__=$1
			;;
		-r)
			shift
			#  -r <rport>:<host>:<port>
			IFS=":" read -a remote <<< "$1"
			if [ ${#remote[@]} -ne 3 ];
			then
				help "Invalid Remote Port Forwarding argument. arg=$1"
				exit 1
			fi
			
			check_port ${remote[0]} "Invalid remote port"
			check_port ${remote[2]} "Invalid port"
			
			__rport__=${remote[0]}
			__host__=${remote[1]}
			__port__=${remote[2]}

			;;
		-s)
			shift
			# -s <svr-host>:<svr-port>
			IFS="/@:" read -a server <<< "$1"
			if [ ${#server[@]} -ne 2 ];
			then
				help "Invalid Remote Port Forwarding argument. arg=$1"
				exit 1
			fi
			
			check_port ${server[1]} "Invalid SSH Server port"
			
			__svr_host__=${server[0]}
			__svr_port__=${server[1]}

			;;
		-t)
			shift
			# -t <rport>:<username>@<svr-host>:<svr-port>
			IFS="/@:" read -a server <<< "$1"
			if [ ${#server[@]} -ne 4 ];
			then
				help "Invalid Remote Port Forwarding argument. arg=$1"
				exit 1
			fi

			check_port ${server[0]} "Invalid remote port"			
			check_port ${server[3]} "Invalid SSH Server port"
			
			__rport__=${server[0]}
			__username__=${server[1]}
			__svr_host__=${server[2]}
			__svr_port__=${server[3]}
			;;
		-u)
			shift
			__username__=$1
			;;
		-v)
			__verbose__=1
			;;
		*)
			case "$1" in
				connect)
					__command__=$1
					;;
				disconnect)
					__command__=$1
					;;
				list)
					__command__=$1
					;;
				*)
					help "Unknown command. arg=$1"
					exit 1
					;;
			esac
			;;
	esac
	shift
done

if [ -z "${__command__}" ];
then
	help "Command IS REQUIRED !!!"
	exit 1
fi

# %s: HTTP Method
# %s: Header
# %s: Request Body
# %s: API
CURL_CMD="curl -X %s %s %s http://127.0.0.1:18080/sshtcm/connections%s?rtype=plain"
DATA_JSON=" \
-d '{ \
	\"tunneling\": { \
		\"username\": \"%s\", \
		\"password\": \"%s\", \
		\"sshServerHost\": \"%s\", \
		\"sshServerPort\": %s, \
		\"remotePort\": %s \
	} \
}'"
# @param $1 {string} arguments name
connect(){
	local header="-H 'Content-Type: application/json;charset=UTF-8'"
	local data=$(printf "${DATA_JSON}" "${__username__}" "${__userpwd__}" "${__svr_host__}" "${__svr_port__}" "${__rport__}" )
	
	if [ ${__verbose__} -eq 1 ];
	then
		printf "${CURL_CMD}\n" "PUT" "${header}" "${data}" "/${__host__}/${__port__}"
	fi
	
	eval $(printf "${CURL_CMD}\n" "PUT" "${header}" "${data}" "/${__host__}/${__port__}")
}

# @param $1 {string} arguments name
disconnect(){
	if [ ${__verbose__} -eq 1 ];
	then
		printf "${CURL_CMD}\n" "DELETE" "/${__username__}:${__svr_host__}:${__svr_port__}/${__rport__}"
	fi
	eval $(printf "${CURL_CMD}" "DELETE" " " " " "/${__username__}:${__svr_host__}:${__svr_port__}/${__rport__}")
}

# @param $1 {string} arguments name
list(){
	if [ ${__verbose__} -eq 1 ];
	then
		printf "${CURL_CMD}\n" "GET" ""
	fi
	
	eval $(printf "${CURL_CMD}" "GET")
}

case "${__command__}" in
	connect)
		connect
		;;
	disconnect)
		disconnect
		;;
	list)
		list
		;;
esac

echo
exit 0