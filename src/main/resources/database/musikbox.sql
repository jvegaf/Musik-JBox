CREATE TABLE `tracks` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `playlists` (
  `id` CHAR(36) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
