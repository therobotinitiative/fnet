CREATE TABLE `user_group_mapping` (
	`user_id` INT(11) NOT NULL,
	`group_id` INT(11) NOT NULL,
	PRIMARY KEY (`user_id`, `group_id`) USING BTREE,
	INDEX `fk_ugm_gid` (`group_id`) USING BTREE,
	CONSTRAINT `fk_ugm_gid` FOREIGN KEY (`group_id`) REFERENCES `user_group` (`group_id`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `fk_ugm_uid` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
