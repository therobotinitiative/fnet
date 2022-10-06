CREATE TABLE `user` (
	`user_id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_name` VARCHAR(32) NOT NULL DEFAULT '0' COLLATE 'latin1_swedish_ci',
	`password` TEXT NOT NULL COLLATE 'latin1_swedish_ci',
	`salt` TEXT NOT NULL COLLATE 'latin1_swedish_ci',
	PRIMARY KEY (`user_id`) USING BTREE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;