#!/bin/bash

#
# @Author : Park Jun-Hong (parkjunhong77@gmail.com)
# @License: Apache License 2.0
# @title  : SSH Tunneling Request & Manage by using Remote Port Forwarding.
#           
# Tunneling using SSH Remote Port Forwarding.
# command: ssh -R {remote-port}:{service-host}:{service-port} {username)@{ssh-server-host} -p {ssh-server-port}

help-add-forwarding(){
	if [ "$1" == "1" ];
	then
		echo "[Example]"
	fi

	echo " ADD Tunneling"
	echo " e.g.) ssh-cmd --add-forwarding=<rport>:<dhost>:<dport>/<username>@<shost>:<sport>"
	echo "       ssh-cmd --add-forwarding=1234:127.0.0.1:1234/user@192.168.0.2:22"
	echo " [options]"
	echo " rport: remote port."
	echo " dhost: destination host."
	echo " dport: destination port."
	echo " user : SSH Server username."
	echo " shost: SSH Server host."
	echo " sport: SSH Server port."
}

help-del-forwarding(){
	if [ "$1" == "1" ];
	then
		echo "[Example]"
	fi

	echo " DELete Tunneling"
	echo " e.g.) ssh-cmd --del-forwarding=<rport>/<username>@<shost>:<sport>"
	echo "       ssh-cmd --del-forwarding=1234/user@192.168.0.2:22"
	echo " [options]"
	echo " rport: remote port."
	echo " user : SSH Server username."
	echo " shost: SSH Server host."
	echo " sport: SSH Server port."
}

help-list-all(){
	if [ "$1" == "1" ];
	then
		echo "[Example]"
	fi

	echo " LIST"
	echo " provide remote port forwarding information."
	echo " e.g.) ssh-cmd --list-all"
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
	echo " ssh-cmd <command> <options>"
	echo
	echo "[Command]"
	help-add-forwarding
	echo
	help-del-forwarding
	echo
	help-list-all
	echo
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
__api_context__="${server.servlet.contextpath}"
__api_svr_port__="${server.port}"
while [ ! -z "$1" ];
do
	case "$1" in
		--add-*)
				_cmd_=$(cut -d'=' -f1 <<< "$1")
				case ${_cmd_:6} in
					forwarding)
						# ssh-cmd --add-forwarding=<rport>:<dhost>:<dport>/<username>@<shost>:<sport>"
						IFS=":/@" read -a _add_args_ <<< "$(cut -d'=' -f2 <<< "$1")"
						if [ ${#_add_args_[@]} -ne 6 ];
						then
							help-add-forwarding "Invalid --add-forwarding argument. arg=$1"
							exit 1
						fi

						check_port ${_add_args_[0]} "Invalid remote port"
						check_port ${_add_args_[2]} "Invalid destination port"
						check_port ${_add_args_[5]} "Invalid SSH server port"

						# <rport>:<dhost>:<dport>
						__rport__=${_add_args_[0]}
						__dhost__=${_add_args_[1]}
						__dport__=${_add_args_[2]}

						# <username>@<shost>:<sport>
						__username__=${_add_args_[3]}
						__svr_host__=${_add_args_[4]}
						__svr_port__=${_add_args_[5]}

						__command__="add-forwarding"
						;;
					*)
						help "Unknown option! arg=$1"
						exit 1
						;;
				esac
			;;
		--del-*)
				_cmd_=$(cut -d'=' -f1 <<< "$1")
				case ${_cmd_:6} in
					forwarding)
						# ssh-cmd --del-forwarding=<rport>/<username>@<shost>:<sport>"
						IFS="/@:" read -a _del_args_ <<< "$(cut -d'=' -f2 <<< "$1")"
						if [ ${#_del_args_[@]} -ne 4 ];
						then
							help-del-forwarding "Invalid --del-forwarding argument. arg=$1"
							exit 1
						fi

						check_port ${_del_args_[0]} "Invalid remote port"
						check_port ${_del_args_[3]} "Invalid SSH server port"

						# <rport>
						__rport__=${_del_args_[0]}

						# <username>@<shost>:<sport>
						__username__=${_del_args_[1]}
						__svr_host__=${_del_args_[2]}
						__svr_port__=${_del_args_[3]}

						__command__="del-forwarding"
						;;
					*)
						help "Unknown option! arg=$1"
						exit 1
					;;
				esac
			;;
		-h)
			help
			exit 0
			;;
		--help-*)
			case "${1:7:${#1}}" in
				add-forwarding)
					help-add-forwarding 1
					;;
				del-forwarding)
					help-del-forwarding 1
					;;
				list)
					help-list-all 1
					;;
				*)
					;;
			esac
			exit 0
			;;
		--list-all)
			__command__="list-all"
			;;
		-v)
			__verbose__=1
			;;
		*)
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
CURL_CMD="curl -X %s %s %s http://127.0.0.1:${__api_svr_port__}/${__api_context__}/connections%s"
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
add-forwarding(){
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
	echo

	local header="-H 'Content-Type: application/json;charset=UTF-8' -H 'Accept: text/plain'"
	local data=$(printf "${DATA_JSON}" "${__username__}" "${__userpwd__}" "${__svr_host__}" "${__svr_port__}" "${__rport__}" )
	
	if [ ${__verbose__} -eq 1 ];
	then
		printf "${CURL_CMD}\n" "PUT" "${header}" "${data}" "/${__dhost__}/${__dport__}"
	fi
	
	eval $(printf "${CURL_CMD}\n" "PUT" "${header}" "${data}" "/${__dhost__}/${__dport__}")
}

# @param $1 {string} arguments name
del-forwarding(){
	local header="-H 'Accept: text/plain'"
	if [ ${__verbose__} -eq 1 ];
	then
		printf "${CURL_CMD}\n" "DELETE" "${header}" " " "/${__username__}:${__svr_host__}:${__svr_port__}/${__rport__}"
	fi
	eval $(printf "${CURL_CMD}" "DELETE" "${header}" " " "/${__username__}:${__svr_host__}:${__svr_port__}/${__rport__}")
}

# @param $1 {string} arguments name
list-all(){
	local header="-H 'Accept: text/plain'"
	if [ ${__verbose__} -eq 1 ];
	then
		printf "${CURL_CMD}\n" "GET" "${header}"
	fi
	
	eval $(printf "${CURL_CMD}" "GET" "${header}")
}

case "${__command__}" in
	add-forwarding)
		add-forwarding
		;;
	del-forwarding)
		del-forwarding
		;;
	list-all)
		list-all
		;;
esac

echo
exit 0
