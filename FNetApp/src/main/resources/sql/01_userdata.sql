CREATE TABLE `user_data` (
	`user_id` INT(11) NOT NULL,
	`first_name` TEXT NULL COLLATE 'latin1_swedish_ci',
	`last_name` TEXT NULL COLLATE 'latin1_swedish_ci',
	`email` TEXT NULL COLLATE 'latin1_swedish_ci',
	`last_login_ip` TEXT NULL COLLATE 'latin1_swedish_ci',
	`last_login` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	`created` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	`last_active_group` INT(11) NOT NULL DEFAULT '0',
	`password_changed` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	INDEX `userid_userdata` (`user_id`) USING BTREE,
	CONSTRAINT `userid_userdata` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
