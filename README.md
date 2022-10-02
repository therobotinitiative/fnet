# fnet
This is a file sharing and commenting application that I envisioned originally in early 2000's. Also works as testing ground for Weblectric project.

## Installing
- Install dos2unix
- Modify stage.sh and fnet.sh parameters to reflect your system
- Copy content /src/maub/resources/systemd/ to desired location, your home directory for example
- Run dos2unix prepare.sh
- Run sudo ./prepare.sh
- Copy boot jar to /var/lib/versions
- Create fnet.jar to link to copied jar file. for example: ln -s /var/lib/fnet/app/versins<jar file> /var/lib/fnet/app/fnet.jar

## Start parameters
### Required
- datasource.url
- datasource.userName
- datasource.password