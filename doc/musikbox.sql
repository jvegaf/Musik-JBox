create TABLE IF NOT EXISTS `tracks` (
  `id` CHAR(36) NOT NULL,
  `title` VARCHAR(255)NOT NULL,
  `location` VARCHAR(255)NOT NULL,
  `duration` INT NOT NULL,
  `artist` VARCHAR(255),
  `album` VARCHAR(255),
  `genre` VARCHAR(255),
  `year` VARCHAR(4),
  `bpm` INT,
  `key` VARCHAR(10),
  `comments` VARCHAR(255),
  PRIMARY KEY (`id`)
);

create TABLE IF NOT EXISTS `playlists` (
  `id` CHAR(36) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
);

create TABLE IF NOT EXISTS `track_playlist`(
    `id` CHAR(36) NOT NULL,
  `playlist_id` VARCHAR(36) NOT NULL,
  `track_id` VARCHAR(36) NOT NULL,
  `position` INT NOT NULL,
  PRIMARY KEY (`id`)
);
