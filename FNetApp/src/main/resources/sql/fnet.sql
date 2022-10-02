-- Clear old
drop database fnet;
drop database fnetstage;

-- Create fnetstage
create database fnetstage;
use fnetstage;
source 00_user.sql;
source 01_userdata.sql;
source 02_group.sql;
source 03_user_group_mapping.sql;
source 04_item.sql;
source 05_comment.sql;
source 06_vfs.sql;
source 07_permission.sql;

-- permissions
grant all on fnetstage.* to 'fnetstage'@'localhost';

-- Create fnet
create database fnet;
use fnet;
source 00_user.sql;
source 01_userdata.sql;
source 02_group.sql;
source 03_user_group_mapping.sql;
source 04_item.sql;
source 05_comment.sql;
source 06_vfs.sql;
source 07_permission.sql;

-- permissions
grant all on fnet.* to 'fnetstage'@'localhost';

flush privileges;