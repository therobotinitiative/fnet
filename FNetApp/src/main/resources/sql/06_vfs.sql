CREATE TABLE `vfs` (
	`parent_id` INT(11) NOT NULL DEFAULT '0',
	`virtual_name` TEXT NOT NULL COLLATE 'latin1_swedish_ci',
	`original_name` TEXT NOT NULL COLLATE 'latin1_swedish_ci',
	INDEX `fk_vfs_iid` (`parent_id`) USING BTREE,
	PRIMARY KEY (`parent_id`),
	CONSTRAINT `fk_vfs_iid` FOREIGN KEY (`parent_id`) REFERENCES `item` (`item_id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
