#!/bin/bash
echo "RUNNING DOCKER-ENTRYPOINT"
#set -e;

myip=
while IFS=$': \t' read -a line ;do
    [ -z "${line%inet}" ] && ip=${line[${#line[1]}>4?1:2]} &&
        [ "${ip#127.0.0.1}" ] && myip=$ip
  done< <(LANG=C /sbin/ifconfig eth0)


if [ -z "${myip}" ]; then
   myip=127.0.0.1
fi

export MYIP=${myip}

command="$1"; 
if [ "$command" != "java" ]; then 
   echo "ERROR: command must start with: java"; 
   exit 1; 
fi; 

exec "$@"
