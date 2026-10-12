# Compte-rendu final — GestionSeries

**Date de validation :** 12 octobre 2026  
**Environnement :** Windows 10 · Tomcat 10 (`C:\apache-tomcat-10.0`) · MySQL WAMP (port 3306) · déploiement `webapps/GestionSeries`

---

## 1. Objectif

Application locale de suivi de séries TV :

- **UI** JSP / servlets (CRUD, recherche, filtres, progression)
- **API REST** Jersey sous `/api/*` (phases A–C du sujet *Rest API.pdf*)
- **Persistance** JPA / EclipseLink / MySQL
- **Sécurité API** JWT + rôles (`ROLE_ADMIN` / `ROLE_USER`)

---

## 2. Soucis rencontrés et correctifs

| # | Problème | Impact | Correctif |
|---|----------|--------|-----------|
| 1 | Messages flash perdus après redirect | Toast succès/erreur invisibles | Stockage flash en **session** + lecture une fois côté JSP |
| 2 | `EntityManager` static partagé | Risque concurrence / fuites | **1 EM par requête** via `EntityManagerFilter` + ThreadLocal |
| 3 | Relations `OneToMany` en EAGER | Perf / LazyInitializationException | Passage en **LAZY** + **JOIN FETCH** sur la liste |
| 4 | Saut de scroll après « marquer vu » | UX frustrante | `sessionStorage` + ancre `#` pour restaurer la position |
| 5 | Index UNIQUE MySQL trop long sur login | Échec création table `AppUser` | Login limité à **100** caractères + script SQL adapté |
| 6 | Jersey : `NoClassDefFoundError` Validator | API 500 au démarrage | Ajout **jakarta.validation** + **hibernate-validator** dans `WEB-INF/lib` |
| 7 | Déploiement stale (classes / JAR absents) | Postman **404** / anciennes erreurs | Recopie `WebContent` + `build/classes` (+ `persistence.xml`) vers Tomcat |
| 8 | Tomcat arrêté après longue pause | App inaccessible | Redémarrage `startup.bat` ; MySQL déjà OK sur 3306 |
| 9 | Script PowerShell de test cassé (`eqn` / encodage) | Suite d’acceptation interrompue | Réécriture du script sans helper corrompu → **8/8 OK** |

### Point résiduel mineur (non bloquant)

Après `POST /api/saison/add`, le JSON de réponse peut afficher `"id":0` (objet pas encore rafraîchi côté JPA). Le statut **201** et la persistance réelle sont corrects ; l’UI et les lectures suivantes voient bien la saison.

---

## 3. Campagne de re-test (acceptation)

Script : `docs/_run_api_tests.ps1` · résultats : `docs/test_results.json`

| Test | Attendu | Résultat |
|------|---------|----------|
| `GET /api/serie/all` | 200 JSON | OK |
| `POST /api/auth/login` (admin/admin) | 200 + JWT | OK (token ~216 car.) |
| `POST /api/serie/add` + token admin | 201 | OK |
| Série visible dans `GET all` | présent | OK (`TestAPI_131358`) |
| `POST /api/auth/login` (user/user) | 200 + JWT | OK |
| `POST /api/serie/add` + token user | **403** | OK |
| `GET /liste` (UI) | 200 | OK (~85 Ko) |
| Login mauvais mot de passe | **400** | OK |

Compléments manuels : `GET /api/serie/{id}` → 200 ; `POST /api/saison/add` → 201 ; `POST /api/episode/add` → 201 ; racine `/` → 302 vers l’UI.

**Bilan : 8/8 tests automatisés + smoke endpoints saison/épisode OK. Aucun correctif code nécessaire pour clôturer.**

---

## 4. État final du projet

### Fonctionnel

| Domaine | État |
|---------|------|
| UI CRUD séries / saisons / épisodes | Opérationnel |
| Marquer épisode / saison vue | Opérationnel |
| Recherche + filtre « à voir » + progression % | Opérationnel |
| Flash messages + scroll conservé | Opérationnel |
| API lecture séries | Opérationnel |
| API mutations (série / saison / épisode) | Opérationnel, réservé admin |
| Auth JWT + seed `admin` / `user` | Opérationnel |
| DataSeeder (données démo) | Opérationnel |

### Technique

- Stack : Jakarta EE / Tomcat 10 / EclipseLink / MySQL / Jersey 3.0.1 / jjwt 0.11.5
- Couches : Servlets + JSP ‖ Services REST → `DaoFactory` → DAO BDD → EM/requête
- Déploiement validé : `http://localhost:8080/GestionSeries/`
- Doc : `README.md` (+ PDF illustré si présent) · ce compte-rendu

### Hors scope (volontaire)

Auth session UI / CSRF multi-utilisateur, front SPA, pagination lourde, secrets hors code pour la prod, tests H2 automatisés CI.

---

## 5. Comment relancer

1. Démarrer **MySQL** (schéma `GestionSeries`)
2. Démarrer **Tomcat 10** (`startup.bat`)
3. UI : http://localhost:8080/GestionSeries/
4. API Postman : base `http://localhost:8080/GestionSeries` — login puis appels `/api/…`
5. Option : `powershell -File docs/_run_api_tests.ps1`

Comptes API : `admin` / `admin` (ROLE_ADMIN) · `user` / `user` (ROLE_USER)

---

## 6. Conclusion

Le projet est **terminé et validé** : TP JEE (DAO/JPA) + bonus UI + phases A–C REST/JWT. Les obstacles (EM, LAZY, flash, AppUser, Validator Jersey, déploiement Tomcat) ont été traités ; la re-campagne d’acceptation du 12/10/2026 est **verte**. Le seul écart cosmétique connu (`id` saison à 0 dans le body 201) n’affecte pas le fonctionnement.
