#!/bin/sh

##########################
# Spring Boot parameters #
##########################
APP_PARAMS="-Ddatasource.user=$DB_USER -Ddatasource.password=$DB_PWD -Ddatasource.url=$DB_URL -Dstorage.path=$STORAGE_PATH -Dspring.profiles.active=$PROFILES -Dserver.port=$SERVER_PORT"

################
# Define paths
################
APP_DIR=/var/lib/fnet/app/
PID_DIR=/var/lib/fnet/pid/

#############################
# Define execution commands #
#############################
APP_EXEC="java $MEMORY_PARAMS $APP_PARAMS -jar $APP_DIR$APP > $OVERFLOW_LOG"

start_fnet()
{
        ## start FNet
        echo 'Starting FNet Server'
        $APP_EXEC &
        echo $! > $PID_DIR$PID_FILE
}

stop_fnet()
{
        ## stop FNet
        echo 'Stopping FNet Server'
        stop_process $PID_DIR$PID_FILE
}

restart_fnet()
{
        stop_fnet
        start_fnet
}

status_fnet()
{
        check_status $PID_DIR$PID_FILE
}

help()
{
        echo 'Accepted commands start,stop,restart,status'
}

stop_process()
{
        PIDFILE=$1
        if [ -f $PIDFILE ];
        then
                PIDTOKILL="$(cat $PIDFILE)"
                kill $PIDTOKILL
                while [ -e /proc/$PIDTOKILL ];
                do
                        sleep .6
                done
                rm $PIDFILE
                echo 'stopped'
        else
                echo 'Process not running'
        fi
}

check_status()
{
        PIDFILE=$1
        if [ -f $PIDFILE ];
        then
                echo 'Running'
        else
                echo 'Stopped'
        fi
}

case $1 in
        start)
                start_fnet
                ;;
        stop)
                stop_fnet
                ;;
        restart)
                restart_fnet
                ;;
        status)
                status_fnet
                ;;
        help)
                help
                ;;
        dryrun)
                echo $APP_EXEC
                ;;
        *)
                echo 'Try help'
                ;;
esac
