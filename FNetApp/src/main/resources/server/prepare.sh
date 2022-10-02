#!/bin/sh
uid="$(id -u)"
if [ "$uid" -ne 0 ];
then
        echo "Run the script as sudo user"
        exit
fi

####################
# Folder structure
echo "Creating folder structure..."
mkdir /var/lib/fnet
mkdir -p /var/lib/fnet/app/versions
mkdir -p /var/lib/fnet/pid
mkdir -p /var/lib/fnet/systemd
mkdir -p /var/lib/fnet/storage/production
mkdir -p /var/lib/fnet/storage/staging
mkdir -p /var/log/fnet
#########################
# Copying files
echo 'copying script files...'
cp systemd/run.sh /var/lib/fnet/systemd/
cp systemd/stage.sh /var/lib/fnet/systemd/
cp systemd/fnet.sh /var/lib/fnet/systemd/
cp services/stage.service /etc/systemd/system/
cp services/fnet.service /etc/systemd/system/
################
# Pemissions
echo 'Setting permissions....'
chown -R fnet:fnet /var/lib/fnet
chown -R fnet:fnet /var/log/fnet
chmod -R g+w /var/lib/fnet/
chmod -R g+w /var/log/fnet/
chmod ug+x /var/lib/fnet/systemd/stage.sh
chmod ug+x /var/lib/fnet/systemd/fnet.sh
chmod ug+x /var/lib/fnet/systemd/run.sh
############
# dos2unix
dos2unix /var/lib/fnet/systemd/stage.sh
dos2unix /var/lib/fnet/systemd/fnet.sh
dos2unix /var/lib/fnet/systemd/run.sh
dos2unix /etc/systemd/system/fnet.service
dos2unix /etc/systemd/system/stage.service
############
echo 'reloading systemctl daemons...'
systemctl daemon-reload
echo 'done!'
