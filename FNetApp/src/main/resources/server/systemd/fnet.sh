#!/bin/sh

set -a

APP=fnet.jar
PID_FILE=fnet.pid
OVERFLOW_LOG=/var/log/fnet/overflow-fnet.log

##############################################
# Define application and start up parameters
##############################################
PROFILES=production,enable-init
STORAGE_PATH=/var/lib/fnet/storage/production/

###############################
####### DB params #############
##############################
DB_USER=
DB_PWD=
DB_URL=

####################
###### SERVER ######
####################
SERVER_PORT=8080

source /var/lib/fnet/systemd/run.sh
