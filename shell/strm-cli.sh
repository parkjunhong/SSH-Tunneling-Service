#!/bin/bash

#
# @Author : Park Jun-Hong (parkjunhong77@gmail.com)
# @License: Apache License 2.0
# @title  : SSH Tunneling Request & Manage by using Remote Port Forwarding.
#           
# Tunneling using SSH Remote Port Forwarding.
# command: ssh -R {remote-port}:{service-host}:{service-port} {username)@{ssh-server-host} -p {ssh-server-port}


help-connect(){
	if [ "$1" == "1" ];
	then
		echo "[Example]"
	fi

	echo " connect   : connect to ssh server for remote port forwarding."
	echo " e.g.) strm-cli.sh connect -r <rport>:<host>:<port> -s <username>@<svr-host> -p <svr-port> -v"
	echo " [options]"
	echo " -p: SSH Server port."
	echo " -r: Remote Port Forwarding(RPF) Information."
	echo "     + rport : remote port."
	echo "     + host  : RFP destination host."
	echo "     + port  : RFP destination port."
	echo " -s: SSH Server Information."
	echo "     + username: SSH Server Account username."
	echo "     + svr-host: SSH Server host."
}

help-disconnect(){
	if [ "$1" == "1" ];
	then
		echo "[Example]"
	fi
	
	echo " disconnect: cancel a remote port forwarding."
	echo " e.g.) strm-cli.sh disconnect -t <rport>:<username>@<svr-host>:<svr-port>"
	echo " [options]"
	echo " -t: Remote Port Forrwarding Unique Information."
	echo "     + rport: remote port."
	echo "     + username: account username."
	echo "     + svr-host: SSH Server host."
	echo "     + svr-port: SSH Server port."
}

help-list(){
	if [ "$1" == "1" ];
	then
		echo "[Example]"
	fi

	echo " list      : provide remote port forwarding information."
	echo " e.g.) strm-cli.sh list"
}

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
	echo " strm-cli.sh <command> <options>"
	echo
	echo "[Command]"
	help-connect
	echo
	help-disconnect
	echo
	help-list
	echo
	echo " -c: Context Path of RESTful API. Default: sshtrm. (SSH Tunneling Request & Manage)"
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

declare -A __args__
# Confirm to arguments.
__command__=""
# show log
__verbose__=0
# Default SSH port
__svr_port__=22
__args__["svr-port"]=1
# Default context path
__context__="sshtrm"
while [ ! -z "$1" ];
do
	case "$1" in
		-c)
			shift
			if [ ! -z "$1" ];
			then
				__context__="$1"
			fi
			;;
		-h)
			help
			exit 0
			;;
		--help-*)
			case "${1:7:${#1}}" in
				connect)
					help-connect 1
					;;
				disconnect)
					help-disconnect 1
					;;
				list)
					help-list 1
					;;
				*)
					;;
			esac
			exit 0
			;;
		-p)
			shift
			
			check_port $1 "Invalid SSH Server port"

			__svr_port__=$1
			__args__["svr-port"]=1
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

			__args__["remote"]=1

			;;
		-s)
			shift
			# -s <username>@<svr-host>
			IFS="/@" read -a server <<< "$1"
			if [ ${#server[@]} -ne 2 ];
			then
				help "Invalid Remote Port Forwarding argument. (<username>@<svr-host>) arg=$1"
				exit 1
			fi
			
			__username__=${server[0]}
			__svr_host__=${server[1]}

			while [ 1 ];
			do
				read -s -p " > ${__username__}@${__svr_host__}'s password: " __userpwd__

				if [ -z $"{__userpwd__}" ];
				then
						echo
						echo " > Please, enter a password !!!"
				else
						break
				fi
			done

			__args__["server"]=1
			;;
		-t)
			shift
			# -t <rport>:<username>@<svr-host>:<svr-port>
			IFS="/@:" read -a server <<< "$1"
			if [ ${#server[@]} -ne 4 ];
			then
				help "Invalid Remote Port Forwarding argument. (<rport>:<username>@<svr-host>:<svr-host>) arg=$1"
				exit 1
			fi

			check_port ${server[0]} "Invalid remote port"			
			check_port ${server[3]} "Invalid SSH Server port"
			
			__rport__=${server[0]}
			__username__=${server[1]}
			__svr_host__=${server[2]}
			__svr_port__=${server[3]}

			__args__["target"]=1
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
CURL_CMD="curl -X %s %s %s http://127.0.0.1:18080/${__context__}/connections%s"
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
	local header="-H 'Content-Type: application/json;charset=UTF-8' -H 'Accept: text/plain'"
	local data=$(printf "${DATA_JSON}" "${__username__}" "${__userpwd__}" "${__svr_host__}" "${__svr_port__}" "${__rport__}" )
	
	if [ ${__verbose__} -eq 1 ];
	then
		printf "${CURL_CMD}\n" "PUT" "${header}" "${data}" "/${__host__}/${__port__}"
	fi
	
	eval $(printf "${CURL_CMD}\n" "PUT" "${header}" "${data}" "/${__host__}/${__port__}")
}

# @param $1 {string} arguments name
disconnect(){
	local header="-H 'Accept: text/plain'"
	if [ ${__verbose__} -eq 1 ];
	then
		printf "${CURL_CMD}\n" "DELETE" "${header}" " " "/${__username__}:${__svr_host__}:${__svr_port__}/${__rport__}"
	fi
	eval $(printf "${CURL_CMD}" "DELETE" "${header}" " " "/${__username__}:${__svr_host__}:${__svr_port__}/${__rport__}")
}

# @param $1 {string} arguments name
list(){
	local header="-H 'Accept: text/plain'"
	if [ ${__verbose__} -eq 1 ];
	then
		printf "${CURL_CMD}\n" "GET" "${header}"
	fi
	
	eval $(printf "${CURL_CMD}" "GET" "${header}")
}

case "${__command__}" in
	connect)
		if [ -z ${__args__['remote']} ] || [ -z ${__args__['server']} ] ;
		then
			help-connect 1
			exit 1
		fi

		connect
		;;
	disconnect)
		if [ -z ${__args__['target']} ]; 
		then
			help-disconnect 1
			exit 1
		fi

		disconnect
		;;
	list)
		list
		;;
esac

echo
exit 0
