ALTER TABLE `FriendShips` DROP FOREIGN KEY `FriendShips_fk0`;

ALTER TABLE `FriendShips` DROP FOREIGN KEY `FriendShips_fk1`;

ALTER TABLE `FriendShips` DROP FOREIGN KEY `FriendShips_fk2`;

ALTER TABLE `Groups` DROP FOREIGN KEY `Groups_fk0`;

ALTER TABLE `Group_Members` DROP FOREIGN KEY `Group_Members_fk0`;

ALTER TABLE `Group_Members` DROP FOREIGN KEY `Group_Members_fk1`;

ALTER TABLE `Group_Messages` DROP FOREIGN KEY `Group_Messages_fk0`;

ALTER TABLE `Group_Messages` DROP FOREIGN KEY `Group_Messages_fk1`;

ALTER TABLE `Requests` DROP FOREIGN KEY `Requests_fk0`;

ALTER TABLE `Requests` DROP FOREIGN KEY `Requests_fk1`;

ALTER TABLE `Direct_Messages` DROP FOREIGN KEY `Direct_Messages_fk0`;

ALTER TABLE `Direct_Messages` DROP FOREIGN KEY `Direct_Messages_fk1`;

ALTER TABLE `Direct_Messages` DROP FOREIGN KEY `Direct_Messages_fk2`;

DROP TABLE IF EXISTS `Users`;

DROP TABLE IF EXISTS `FriendShips`;

DROP TABLE IF EXISTS `Groups`;

DROP TABLE IF EXISTS `Group_Members`;

DROP TABLE IF EXISTS `Group_Messages`;

DROP TABLE IF EXISTS `Requests`;

DROP TABLE IF EXISTS `Direct_Messages`;

