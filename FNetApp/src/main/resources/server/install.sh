#!/bin/sh

# Folder structure
echo "Creating folder structure..."
mkdir /var/lib/fnet
chown fnet:fnet /var/lib/fnet
mkdir -p /var/lib/fnet/app/versions
mkdir -p /var/lib/fnet/pid
mkdir -p /var/lib/fnet/systemd
mkdir -p /var/lib/fnet/storage/production
mkdir -p /var/lib/fnet/storage/staging
mkdir -p /var/log/fnet
chown -R fnet:fnet /var/lib/fnet
chmod -R g+w /var/lib/fnet/
echo 'done!'

echo 'Installing fnet...'
cp $1 /var/lib/fnet/app/versions
ln -s /var/lib/fnet/app/versions/$1 /var/lib/fnet/app/fnet.jar