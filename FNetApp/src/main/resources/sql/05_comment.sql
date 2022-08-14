CREATE TABLE `comment` (
	`comment_id` INT(11) NOT NULL AUTO_INCREMENT,
	`item_id` INT(11) NOT NULL DEFAULT '0',
	`group_id` INT(11) NOT NULL DEFAULT '0',
	`user_id` INT(11) NOT NULL DEFAULT '0',
	`timestamp` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	`comment` TEXT NOT NULL COLLATE 'latin1_swedish_ci',
	PRIMARY KEY (`comment_id`) USING BTREE,
	INDEX `fk_comment_iid` (`item_id`) USING BTREE,
	INDEX `fk_comment_gid` (`group_id`) USING BTREE,
	INDEX `fk_comment_uid` (`user_id`) USING BTREE,
	CONSTRAINT `fk_comment_gid` FOREIGN KEY (`group_id`) REFERENCES `user_group` (`group_id`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `fk_comment_iid` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT `fk_comment_uid` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
