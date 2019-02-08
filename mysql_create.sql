CREATE TABLE `Users` (
	`user_id` INT(10) NOT NULL AUTO_INCREMENT,
	`phone_number` varchar(15) NOT NULL UNIQUE,
	`display_name` varchar(30) NOT NULL,
	`email` varchar(30) NOT NULL UNIQUE,
	`password` varchar(30) NOT NULL,
	`gender` varchar(10) NOT NULL,
	`country` varchar(30) NOT NULL,
	`date_of_birth` DATE NOT NULL,
	`bio` varchar(1000) NOT NULL,
	`picture` blob,
	PRIMARY KEY (`user_id`)
);

CREATE TABLE `FriendShips` (
	`user_id` INT(10) NOT NULL,
	`friend_id` INT(10) NOT NULL,
	`category` VARCHAR(255) NOT NULL,
	`blocked` int(1) DEFAULT '0',
	`last_seen_message` INT(10),
	PRIMARY KEY (`user_id`,`friend_id`)
);

CREATE TABLE `Groups` (
	`group_id` INT(10) NOT NULL AUTO_INCREMENT,
	`admin_id` INT(10) NOT NULL,
	`display_name` VARCHAR(30) NOT NULL,
	`picture` blob,
	PRIMARY KEY (`group_id`)
);

CREATE TABLE `Group_Members` (
	`groub_id` INT(10) NOT NULL,
	`user_id` INT(10) NOT NULL,
	PRIMARY KEY (`groub_id`,`user_id`)
);

CREATE TABLE `Group_Messages` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`sender_id` INT(10) NOT NULL,
	`group_id` INT(10) NOT NULL,
	`message_type` VARCHAR(30) NOT NULL,
	`content` TEXT NOT NULL,
	`font_style` varchar(100),
	`time` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Requests` (
	`sender_id` INT(10) NOT NULL,
	`receiver_id` INT(10) NOT NULL,
	`time` TIMESTAMP NOT NULL,
	PRIMARY KEY (`sender_id`,`receiver_id`)
);

CREATE TABLE `Direct_Messages` (
	`id` INT(10) NOT NULL AUTO_INCREMENT,
	`min_id` INT(10) NOT NULL,
	`max_id` INT(10) NOT NULL,
	`sender_id` INT(10) NOT NULL,
	`message_type` VARCHAR(30) NOT NULL,
	`content` TEXT NOT NULL,
	`font_style` varchar(100),
	`time` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `FriendShips` ADD CONSTRAINT `FriendShips_fk0` FOREIGN KEY (`user_id`) REFERENCES `Users`(`user_id`);

ALTER TABLE `FriendShips` ADD CONSTRAINT `FriendShips_fk1` FOREIGN KEY (`friend_id`) REFERENCES `Users`(`user_id`);

ALTER TABLE `FriendShips` ADD CONSTRAINT `FriendShips_fk2` FOREIGN KEY (`last_seen_message`) REFERENCES `Direct_Messages`(`id`);

ALTER TABLE `Groups` ADD CONSTRAINT `Groups_fk0` FOREIGN KEY (`admin_id`) REFERENCES `Users`(`user_id`);

ALTER TABLE `Group_Members` ADD CONSTRAINT `Group_Members_fk0` FOREIGN KEY (`groub_id`) REFERENCES `Groups`(`group_id`);

ALTER TABLE `Group_Members` ADD CONSTRAINT `Group_Members_fk1` FOREIGN KEY (`user_id`) REFERENCES `Users`(`user_id`);

ALTER TABLE `Group_Messages` ADD CONSTRAINT `Group_Messages_fk0` FOREIGN KEY (`sender_id`) REFERENCES `Users`(`user_id`);

ALTER TABLE `Group_Messages` ADD CONSTRAINT `Group_Messages_fk1` FOREIGN KEY (`group_id`) REFERENCES `Groups`(`group_id`);

ALTER TABLE `Requests` ADD CONSTRAINT `Requests_fk0` FOREIGN KEY (`sender_id`) REFERENCES `Users`(`user_id`);

ALTER TABLE `Requests` ADD CONSTRAINT `Requests_fk1` FOREIGN KEY (`receiver_id`) REFERENCES `Users`(`user_id`);

ALTER TABLE `Direct_Messages` ADD CONSTRAINT `Direct_Messages_fk0` FOREIGN KEY (`min_id`) REFERENCES `Users`(`user_id`);

ALTER TABLE `Direct_Messages` ADD CONSTRAINT `Direct_Messages_fk1` FOREIGN KEY (`max_id`) REFERENCES `Users`(`user_id`);

ALTER TABLE `Direct_Messages` ADD CONSTRAINT `Direct_Messages_fk2` FOREIGN KEY (`sender_id`) REFERENCES `Users`(`user_id`);

