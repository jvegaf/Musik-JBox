CREATE TABLE `songs` (
  `id` CHAR(36) NOT NULL,
  `title` VARCHAR(255),
  `artist` VARCHAR(255),
  `album` VARCHAR(255),
  `genre` VARCHAR(255),
  `bpm` INT,
  `genre` VARCHAR(10),
  `year` INT,
  `filename` VARCHAR(255) NOT NULL,
  `filepath` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `playlists` (
  `id` CHAR(36) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `playlists_has_songs` (
  `playlist_id` CHAR(36) NOT NULL,
  `song_id` CHAR(36) NOT NULL,
  `position` INT NOT NULL,
  FOREIGN KEY (`playlist_id`) REFERENCES `playlists`(`id`),
  FOREIGN KEY (`song_id`) REFERENCES `songs`(`id`),
  UNIQUE (`playlist_id`, `song_id`, `position`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;