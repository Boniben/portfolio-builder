-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 09 mai 2026 à 10:46
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `portfolio`
--

-- --------------------------------------------------------

--
-- Structure de la table `category`
--

CREATE TABLE `category` (
  `id` binary(16) NOT NULL,
  `hasDates` bit(1) NOT NULL,
  `hasLink` bit(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  `has_dates` bit(1) NOT NULL,
  `has_link` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `category`
--

INSERT INTO `category` (`id`, `hasDates`, `hasLink`, `name`, `has_dates`, `has_link`) VALUES
(0x225dc0958e3e43399af51cbaf62c9484, b'0', b'0', 'Autre', b'0', b'0'),
(0x3c0eecb31a0d4908adf06da9be79b853, b'0', b'1', 'Projets', b'0', b'0'),
(0x5d8a7a4eb36d436b98b7f6a838a0daa8, b'0', b'0', 'Compétences', b'0', b'0'),
(0x9826e8a41ba3489c97d439f2eb456052, b'1', b'0', 'Formation', b'0', b'0'),
(0xa64797668fbf4195897d3cfe8f5b7a6d, b'1', b'0', 'Expérience', b'0', b'0');

-- --------------------------------------------------------

--
-- Structure de la table `item`
--

CREATE TABLE `item` (
  `id` binary(16) NOT NULL,
  `description` text DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `sort_order` int(11) NOT NULL,
  `start_date` date DEFAULT NULL,
  `title` varchar(150) NOT NULL,
  `location_id` binary(16) DEFAULT NULL,
  `rubric_id` binary(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `item`
--

INSERT INTO `item` (`id`, `description`, `end_date`, `sort_order`, `start_date`, `title`, `location_id`, `rubric_id`) VALUES
(0x42174d68daf94559a3f1f5f234e472bf, 'Réalisation de création sur tour à bois', '2022-03-01', 1, '2020-03-01', 'Création d\'art', NULL, 0x4f24fe4653e2453191f3ce40b43d12b1),
(0x5319dcff80894b17a15ff2c0d7e0bf01, '-Dessin sur sketshup\r\n-Monter les devis\r\n-Implantation de batiment\r\n-Réalisation de l\'ossature et de la charpente en bois (tennons-mortaise)\r\n-Couverture', '2023-08-15', 2, '2022-03-01', 'Travail de menuisier-charpentier dans un Zoo', NULL, 0xc62f811c5961415dac9da98c4c0f121f),
(0x59cf223fe2c149b6ba9f201a930087e5, '-Ponsage\r\n-micro-billage\r\n-', '2022-03-01', 2, '2020-03-01', 'Restauration de meuble', NULL, 0x4f24fe4653e2453191f3ce40b43d12b1),
(0x5c37cda00cc3499d891cc335f34f3917, 'Mise en place d\'un réseau selon les règle de cybersécurité', '2026-04-25', 1, '2026-04-10', 'Gerer un réseau', NULL, 0x7f1431d055da425f9b1c6cbecaaf1781),
(0xbcadeff2645f4930bfaf7cff3c52bc19, '-Création de pièces unique (plateau, assiettes, couverts à salade)\r\n-Restauration de meuble\r\n-Création de clostra\r\n-Création de tableau en résine époxy (table rivière)\r\n-Créations multiples de bijou en bois et epoxy', '2022-03-01', 1, '2020-03-01', 'Création d\'une micro-entreprise de fabrication d\'objet en bois', NULL, 0xc62f811c5961415dac9da98c4c0f121f),
(0xc17b3450196c448fad51d0344ef2dc1f, 'J\'aime apprendre et réparer tout ce qui touche à mon terrain et ma maison', NULL, 2, NULL, 'Le bricolage', NULL, 0x26858cf687544d2c92b0811aa20bdf54),
(0xd493af77f0ee41818ad65b9e25e4802e, 'Création de logicil informatique adapté aux personnes en situation de handicap', '2026-07-01', 1, '2024-08-15', 'BTS SIO', NULL, 0x7611c6fe0d3844b29cda6c3af0b6b5fe),
(0xf48419a32d554a6e810e04589b0a0897, 'J\'aime la mycologie, l\'observation des animaux sauvage, l\'écologie', NULL, 1, NULL, 'Nature', NULL, 0x26858cf687544d2c92b0811aa20bdf54);

-- --------------------------------------------------------

--
-- Structure de la table `location`
--

CREATE TABLE `location` (
  `id` binary(16) NOT NULL,
  `address` text DEFAULT NULL,
  `name` varchar(120) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `profile`
--

CREATE TABLE `profile` (
  `id` binary(16) NOT NULL,
  `description` varchar(10000) NOT NULL,
  `name` varchar(150) NOT NULL,
  `owner_id` binary(16) NOT NULL,
  `createdAt` datetime(6) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `template_id` binary(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `profile`
--

INSERT INTO `profile` (`id`, `description`, `name`, `owner_id`, `createdAt`, `created_at`, `template_id`) VALUES
(0x6f203e3d3fe04e01a927dac0c5bb5d31, 'Bonjour,\r\nJe suis Benjamin Boniface et je vous présentes mes qualités d\'informaticiens', 'Informaticien', 0xd7c80761e55f4e11a3b3ef94fc662343, NULL, '2026-04-25 16:34:59', NULL),
(0x7b79127dbd7b458c930b9539395501b4, 'Je suis d\'admin mais je peux avoir un profil de test', 'Admin du site', 0xa8398d2da2784393a60e117a53a78f4f, '2026-01-17 12:12:37.000000', '2026-04-25 15:31:53', NULL),
(0xc9d6171b6d25434a99776e2da697e76d, 'Faire monter en compétences les personnes en situation de handicap suivies', 'Moniteur d\'atelier', 0xd7c80761e55f4e11a3b3ef94fc662343, NULL, '2026-04-26 16:28:16', 0xd1218047f14b4d0b8fd9921fdc89fe26),
(0xe9bd54cd340646288de923c569f48589, 'Bonjour, Je me présente Benjamin Boniface est je vais vous présenter l\'ensemble de mes compétences et expériences en lien avec le métier de menuisier', 'Menuisier', 0xd7c80761e55f4e11a3b3ef94fc662343, NULL, '2026-04-26 16:13:59', 0x2dd46d49982748d48b35b806acff7a2d);

-- --------------------------------------------------------

--
-- Structure de la table `rubric`
--

CREATE TABLE `rubric` (
  `id` binary(16) NOT NULL,
  `name` varchar(120) NOT NULL,
  `order_` int(11) NOT NULL,
  `category_id` binary(16) DEFAULT NULL,
  `profile_id` binary(16) DEFAULT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `rubric`
--

INSERT INTO `rubric` (`id`, `name`, `order_`, `category_id`, `profile_id`, `visible`) VALUES
(0x1ad9652f7969408ebbe4c548b48bd24e, 'Mes compétences en informatique réseaux', 2, 0x5d8a7a4eb36d436b98b7f6a838a0daa8, 0x6f203e3d3fe04e01a927dac0c5bb5d31, 1),
(0x26858cf687544d2c92b0811aa20bdf54, 'Mes passions', 3, 0x225dc0958e3e43399af51cbaf62c9484, 0xe9bd54cd340646288de923c569f48589, 1),
(0x4f24fe4653e2453191f3ce40b43d12b1, 'Mes compétences de menuisier', 2, 0x5d8a7a4eb36d436b98b7f6a838a0daa8, 0xe9bd54cd340646288de923c569f48589, 1),
(0x7611c6fe0d3844b29cda6c3af0b6b5fe, 'Mes formations', 1, 0x9826e8a41ba3489c97d439f2eb456052, 0xc9d6171b6d25434a99776e2da697e76d, 1),
(0x7f1431d055da425f9b1c6cbecaaf1781, 'Mes compétences en informatique SLAM', 4, 0x5d8a7a4eb36d436b98b7f6a838a0daa8, 0x6f203e3d3fe04e01a927dac0c5bb5d31, 1),
(0xc62f811c5961415dac9da98c4c0f121f, 'Mes expériences de menuisier', 1, 0xa64797668fbf4195897d3cfe8f5b7a6d, 0xe9bd54cd340646288de923c569f48589, 1);

-- --------------------------------------------------------

--
-- Structure de la table `template`
--

CREATE TABLE `template` (
  `id` binary(16) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `filename` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `template`
--

INSERT INTO `template` (`id`, `description`, `filename`, `name`) VALUES
(0x2dd46d49982748d48b35b806acff7a2d, 'Coloré et dynamique, pour se démarquer.', 'fun', 'Fun'),
(0x70b2698746cb4b3db4d73bb3a348c9a1, 'Design coloré avec une barre latérale verte.', 'modern', 'Moderne'),
(0x75d8b44827eb45fb916ede7699f0816b, 'Mise en page document technique, structuré et numéroté.', 'manual', 'Manuel'),
(0xb0fa9a5ca7c841c3976fa96ad5c34cbc, 'Mise en page sobre et professionnelle, noir et blanc.', 'classic', 'Classique'),
(0xd1218047f14b4d0b8fd9921fdc89fe26, 'Ultra simple, juste l\'essentiel, très épuré.', 'minimal', 'Minimaliste'),
(0xfc283c325bb14bd88b46133354e50b58, 'Style terminal sombre, parfait pour les profils IT.', 'tech', 'Informatique');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` binary(16) NOT NULL,
  `email` varchar(150) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `role` varchar(10) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `firstname`, `lastname`, `password`, `username`, `role`, `address`, `phone`) VALUES
(0xa8022aba052f4ff4982140458161caa1, 'max@max.fr', 'max', 'max', '$2a$10$mDuCXSAcXcXq/Z602isdR.OzjCZUZ7Zo3ZYVm8T.HGAzNSDXA5Xui', 'max', 'USER', NULL, NULL),
(0xa8398d2da2784393a60e117a53a78f4f, 'admin', 'admin', 'admin', '$2a$10$OnBBrHe6UYdG49Lx.rPvKuyAysRuez.eBeUpx5cRXOlvoo.D/OE8e', 'admin', 'ADMIN', NULL, NULL),
(0xafb3d4262306459fa1bb6f98ac0c90d7, 'Jury@caen.info', 'Jury', 'Jury', '$2a$10$A6VhoK4MGrLqC9sRCk6sb.WD1v2p3gRpeDPyqYmx.HL/4j2vtoe0m', 'Jury', 'ADMIN', NULL, NULL),
(0xd7c80761e55f4e11a3b3ef94fc662343, 'ben@hotmail.fr', 'benjamin', 'boniface', '$2a$10$CvK9xC09iXdcY1PjUNs0QOJ.ADDeioJpfHvdJcBy3ZmUnOiVYsfdq', 'Boni', 'USER', '24 rue du pif à Perdu', '06303030'),
(0xe0a3a106aca9491bae1b75d43e8fb7dd, 'Estelle@mail.fr', 'Estelle', 'E', '$2a$10$5F5QOHh6RH.810eP8WTPkeGvZ7PD25NyoTue7oiRl6Jflb1x8CQZS', 'Estelle', 'USER', NULL, NULL),
(0xefe3571666d441c1bd106757858cfb8e, 'JuryUser@caen.info', 'JuryUser', 'JuryUser', '$2a$10$VMXbkNUisxWwNIyKEZ/M..nYP3yZccPOIZykVIdk6RVuoHqO95l3S', 'JuryUser', 'USER', NULL, NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKd490m4r8ekpyh5glux9jt7j3b` (`location_id`),
  ADD KEY `FKajq038ode3y4aiahm1vmyka0a` (`rubric_id`);

--
-- Index pour la table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK17d14ejcataae525pvcqllrl5` (`owner_id`),
  ADD KEY `FK5xem7ycs51mmf8bghji0x5dqo` (`template_id`);

--
-- Index pour la table `rubric`
--
ALTER TABLE `rubric`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt7ugwi3y4p0pfuaqdt06q2ckm` (`category_id`),
  ADD KEY `FK8oqdoygj47lqxpsyt913l7yw` (`profile_id`);

--
-- Index pour la table `template`
--
ALTER TABLE `template`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKoshmjvr6wht0bg9oivn75aajr` (`email`),
  ADD UNIQUE KEY `UKh74ord48otcajdi21yrl7k4d1` (`username`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `FKajq038ode3y4aiahm1vmyka0a` FOREIGN KEY (`rubric_id`) REFERENCES `rubric` (`id`),
  ADD CONSTRAINT `FKd490m4r8ekpyh5glux9jt7j3b` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`);

--
-- Contraintes pour la table `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `FK17d14ejcataae525pvcqllrl5` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK5xem7ycs51mmf8bghji0x5dqo` FOREIGN KEY (`template_id`) REFERENCES `template` (`id`);

--
-- Contraintes pour la table `rubric`
--
ALTER TABLE `rubric`
  ADD CONSTRAINT `FK8oqdoygj47lqxpsyt913l7yw` FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`),
  ADD CONSTRAINT `FKt7ugwi3y4p0pfuaqdt06q2ckm` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
