CREATE TABLE `permission` (
	`user_Id` INT(11) NOT NULL,
	`permission` VARCHAR(50) NOT NULL COLLATE 'latin1_swedish_ci',
	UNIQUE INDEX `userId_permission` (`user_Id`, `permission`) USING BTREE,
	INDEX `userId` (`user_Id`) USING BTREE,
	CONSTRAINT `fk_permission_uid` FOREIGN KEY (`user_Id`) REFERENCES `user` (`user_id`) ON UPDATE CASCADE ON DELETE CASCADE
)
COLLATE='latin1_swedish_ci'
ENGINE=InnoDB
;
