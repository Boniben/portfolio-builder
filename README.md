# 📁 Portfolio Builder

> Application web permettant de créer et gérer des portfolios professionnels en ligne.  
> Projet réalisé dans le cadre du **BTS SIO** — option SLAM.

---

## 🚀 Présentation

**Portfolio Builder** est une application web full-stack qui permet à chaque utilisateur de :
- Créer plusieurs **profils professionnels**
- Organiser son contenu en **rubriques** (Formation, Expérience, Compétences, Projets...)
- Ajouter des **éléments détaillés** dans chaque rubrique (titre, description, dates)
- Gérer son espace depuis un tableau de bord personnel

Un espace **administrateur** permet de gérer l'ensemble des utilisateurs de la plateforme.

---

## 🛠️ Stack technique

| Couche | Technologie |
|--------|------------|
| Langage | Java 17 |
| Framework backend | Spring Boot 4.0.0 |
| Sécurité | Spring Security |
| ORM | Spring Data JPA / Hibernate |
| Base de données | MySQL 8 |
| Templates | Mustache (rendu côté serveur) |
| Frontend | HTML5 / CSS3 |
| Build | Maven |

---

## ✅ Fonctionnalités implémentées

### 🔓 Accès public
- Page d'accueil
- Inscription d'un nouvel utilisateur
- Connexion / Déconnexion

### 👤 Espace utilisateur (connecté)
- Tableau de bord personnel
- **Profils** : créer, consulter, modifier, supprimer
- **Rubriques** : ajouter, modifier, supprimer (catégories : Formation, Expérience, Compétences, Projets, Autre)
- **Éléments** : ajouter, modifier, supprimer dans chaque rubrique (titre, description, dates)

### 🔐 Espace administrateur
- Liste de tous les utilisateurs
- Consulter le détail d'un utilisateur
- Supprimer un utilisateur

---

## 📐 Architecture

```
UTILISATEUR
    └── PROFIL (ex: "Développeur Full-Stack")
            └── RUBRIQUE (ex: "Formation")
                    └── ÉLÉMENT (ex: "BTS SIO - 2024/2026")
```

### Structure du projet

```
src/
└── main/
    ├── java/alt/portfolio/builder/
    │   ├── controller/       ← Gestion des routes HTTP
    │   ├── services/         ← Logique métier
    │   ├── repository/       ← Accès base de données
    │   ├── entity/           ← Modèles JPA (User, Profile, Rubric, Item...)
    │   ├── config/           ← Spring Security & MVC
    │   └── dtos/             ← Objets de transfert de données
    └── resources/
        ├── templates/        ← Pages Mustache (HTML)
        └── application.properties
```

---

## 🗺️ Routes disponibles

### Publiques
| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/` | Page d'accueil |
| GET | `/login` | Formulaire de connexion |
| GET/POST | `/users/create` | Inscription |

### Utilisateur connecté
| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/profiles` | Liste des profils |
| GET/POST | `/profiles/create` | Créer un profil |
| GET | `/profiles/{id}` | Détail d'un profil |
| GET/POST | `/profiles/{id}/edit` | Modifier un profil |
| POST | `/profiles/{id}/delete` | Supprimer un profil |
| GET/POST | `/profiles/{id}/rubrics/create` | Ajouter une rubrique |
| GET/POST | `/rubrics/{id}/edit` | Modifier une rubrique |
| POST | `/rubrics/{id}/delete` | Supprimer une rubrique |
| GET/POST | `/rubrics/{id}/items/create` | Ajouter un élément |
| GET/POST | `/items/{id}/edit` | Modifier un élément |
| POST | `/items/{id}/delete` | Supprimer un élément |

### Administrateur uniquement
| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/users` | Liste des utilisateurs |
| GET | `/users/show/{id}` | Détail d'un utilisateur |
| POST | `/users/delete` | Supprimer un utilisateur |

---

## ⚙️ Installation & Lancement

### Prérequis
- Java 17+
- Maven 3.8+
- MySQL 8+

### 1. Cloner le projet
```bash
git clone https://github.com/Boniben/portfolio-builder.git
cd portfolio-builder
```

### 2. Configurer la base de données
Créer une base de données MySQL :
```sql
CREATE DATABASE portfolio;
```

Vérifier `src/main/resources/application.properties` :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/portfolio
spring.datasource.username=root
spring.datasource.password=
```

### 3. Lancer l'application
```bash
./mvnw spring-boot:run
```

L'application démarre sur **http://localhost:8080**

> 💡 Les catégories de base (Formation, Expérience, Compétences, Projets, Autre) sont insérées automatiquement au premier démarrage.

---

## 🗃️ Modèle de données

```
User ──< Profile ──< Rubric ──< Item
              Category >──── Rubric
```

| Entité | Description |
|--------|-------------|
| `User` | Compte utilisateur (rôle USER ou ADMIN) |
| `Profile` | Portfolio d'un utilisateur |
| `Rubric` | Section du profil (ex: Formation) |
| `Category` | Type de rubrique (Formation, Expérience...) |
| `Item` | Élément dans une rubrique (ex: BTS SIO) |

---

## 🔒 Sécurité

- Authentification par formulaire (Spring Security)
- Mots de passe chiffrés avec **BCrypt**
- Accès aux pages protégé par rôle (`ROLE_USER`, `ROLE_ADMIN`)
- Protection **CSRF** configurée

---

## 📅 Historique des sprints

| Sprint | Fonctionnalités |
|--------|----------------|
| Sprint 1 | Mise en place du projet, authentification, gestion utilisateurs |
| Sprint 2 | CRUD profils, interface CSS |
| Sprint 3 | CRUD rubriques, CRUD éléments (EPIC 3) |

---

## 👤 Auteur

**Benjamin Boniface** — Étudiant BTS SIO option SLAM  
📧 benjamin.boniface@sts-sio-caen.info  
🔗 [GitHub](https://github.com/Boniben)
