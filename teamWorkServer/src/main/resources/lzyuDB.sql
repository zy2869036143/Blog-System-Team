DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `user_id` INT NOT NULL,
    `content` TEXT NOT NULL,
    `blog_id` INT NOT NULL,
    `follow` INT NOT NULL,
    `time` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`));
