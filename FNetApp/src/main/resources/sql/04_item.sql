CREATE TABLE `item` (
	`item_id` INT(11) NOT NULL AUTO_INCREMENT,
	`group_id` INT(11) NOT NULL DEFAULT '0',
	`user_id` INT(11) NOT NULL DEFAULT '0',
	`parent_id` INT(11) NOT NULL DEFAULT '0',
	`name` TEXT NOT NULL COLLATE 'latin1_swedish_ci',
	`item_type` TEXT NOT NULL COLLATE 'latin1_swedish_ci',
	`timestamp` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	PRIMARY KEY (`item_id`) USING BTREE,
	INDEX `fk_item_uid` (`user_id`) USING BTREE,
	INDEX `fk_item_gid` (`group_id`) USING BTREE,
	INDEX `fk_item_pid` (`parent_id`) USING BTREE,
	CONSTRAINT `fk_item_gid` FOREIGN KEY (`group_id`) REFERENCES `user_group` (`group_id`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `fk_item_uid` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
