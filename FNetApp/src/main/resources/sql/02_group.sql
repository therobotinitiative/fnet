CREATE TABLE `user_group` (
	`group_id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` TEXT NOT NULL COLLATE 'latin1_swedish_ci',
	PRIMARY KEY (`group_id`) USING BTREE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
