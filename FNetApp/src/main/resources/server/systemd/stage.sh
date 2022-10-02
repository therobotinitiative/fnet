#!/bin/sh

set -a

APP=stage.jar
PID_FILE=stage.pid
OVERFLOW_LOG=/var/log/fnet/overflow-stage.log

##############################################
# Define application and start up parameters
##############################################
PROFILES=staging,enable-init
STORAGE_PATH=/var/lib/fnet/storage/staging/

###############################
####### DB params #############
##############################
DB_USER=
DB_PWD=
DB_URL=

####################
###### SERVER ######
####################
SERVER_PORT=8081

source /var/lib/fnet/systemd/run.sh
