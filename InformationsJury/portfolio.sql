-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 10 avr. 2026 à 11:33
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
  `name` varchar(50) NOT NULL
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
  `createdAt` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `profile`
--

INSERT INTO `profile` (`id`, `description`, `name`, `owner_id`, `createdAt`) VALUES
(0x0e7badab49714bed8949ba1084bf87ba, 'a sup bonjour', 'a sup', 0xf677918c50a84087a3d222276df138d7, '2026-01-28 16:52:26.000000'),
(0x689ed61c8de34f1c9ca8a987b7556ab2, 'Aime découvrir l\'utilisation de springboot', 'developpeur web (2)', 0xf677918c50a84087a3d222276df138d7, '2026-01-17 11:26:04.000000'),
(0x7b79127dbd7b458c930b9539395501b4, 'Je suis d\'admin mais je peux avoir un profil de test', 'Admin du site', 0xa8398d2da2784393a60e117a53a78f4f, '2026-01-17 12:12:37.000000'),
(0xd2ca546b4e5a41b9b2c7fcd45bf4e688, 'fabrication de meuble sur mesure', 'ebeniste', 0xf677918c50a84087a3d222276df138d7, '2026-01-28 10:49:55.000000'),
(0xec17808f2f7f4067864f3e32070d915a, '3 ans d\'experience en mécanique dans le garage automobile de caen', 'mecanicien', 0xf677918c50a84087a3d222276df138d7, '2026-01-27 18:59:42.000000');

-- --------------------------------------------------------

--
-- Structure de la table `rubric`
--

CREATE TABLE `rubric` (
  `id` binary(16) NOT NULL,
  `name` varchar(120) NOT NULL,
  `order_` int(11) NOT NULL,
  `category_id` binary(16) DEFAULT NULL,
  `profile_id` binary(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `firstname`, `lastname`, `password`, `username`, `role`) VALUES
(0xa8022aba052f4ff4982140458161caa1, 'max@max.fr', 'max', 'max', '$2a$10$mDuCXSAcXcXq/Z602isdR.OzjCZUZ7Zo3ZYVm8T.HGAzNSDXA5Xui', 'max', 'USER'),
(0xa8398d2da2784393a60e117a53a78f4f, 'admin', 'admin', 'admin', '$2a$10$OnBBrHe6UYdG49Lx.rPvKuyAysRuez.eBeUpx5cRXOlvoo.D/OE8e', 'admin', 'ADMIN'),
(0xe0a3a106aca9491bae1b75d43e8fb7dd, 'Estelle@mail.fr', 'Estelle', 'E', '$2a$10$5F5QOHh6RH.810eP8WTPkeGvZ7PD25NyoTue7oiRl6Jflb1x8CQZS', 'Estelle', 'USER'),
(0xf677918c50a84087a3d222276df138d7, 'ben', 'ben', 'ben', '$2a$10$KLCOYJ4A9HiL3DSbaJj8yueM0tIl3OVUNtuLi3FTEFMXZOBkzI32a', 'ben', 'USER');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `profile`
--
ALTER TABLE `profile`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK17d14ejcataae525pvcqllrl5` (`owner_id`);

--
-- Index pour la table `rubric`
--
ALTER TABLE `rubric`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt7ugwi3y4p0pfuaqdt06q2ckm` (`category_id`),
  ADD KEY `FK8oqdoygj47lqxpsyt913l7yw` (`profile_id`);

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
-- Contraintes pour la table `profile`
--
ALTER TABLE `profile`
  ADD CONSTRAINT `FK17d14ejcataae525pvcqllrl5` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`);

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
