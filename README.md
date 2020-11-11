# Final Delivery
​
## Points repartition (Group of 5) :
​
- BISEGNA David     : 125
- HAENLIN Jason     : 140
- JRAD Yassine      : 110
- MARCELLIN Betsara : 125
- VASSEUR Adrien    : 0
​
## Running the project
​
When running the ```prepare.sh``` file, the script will compile all microservices, generate .jar files, then, the docker images will be built with these files.
Once the docker images are built, the command ```docker-compose up -d``` will run, creating a container for each webservices, and some other container running the database, setting up the Kafka topics, and running the Kafka broker and zookeeper.
​
Then, when running the ```run.sh```, the script will wait for all the containers to be healthy before running the tests with Cucumber.
This script doesn't turn off the containers (in case you want to run the tests multiple times to check if the system is idempotent), running manually ```docker-compose down``` will be needed if you want to turn them off.
​
## Scenario :
​
Ce scénario va d'abord créer les éléments de base, c'est à dire 2 boosters, une fusée, un payload ainsi qu'une mission (à laquelle on donnera les éléments créer précédemment pour qu'elle les gère).
​
Puis le déroulement sera :
​
- Richard demande aux services de météo et de la fusée leur Go/NoGo
- Un des deux service n'a pas donné le Go, Richard n'envoi pas de Go à Elon pour lancer la fusée
- Une fois que la météo et le statut du département de la fusée a changé, Richard redemande un Go/NoGo.
- Les deux services ont bien répondu par "Go", Richard notifie le service de préparation de mission pour la préparer. Il notifie aussi le service de la fusée pour la préparer.
- Elon peux voir que la fusée est prête à décoller, et que l'étape de décollage est actuellement "En préparation"
- Elon envoi la requête pour lancer la fusée afin de commencer le compte à rebours de 1 minute (Réduit à 3 secondes pour des raisons pratique).
- La fusée est en état "Démarrage", le payload "En mission", le 1er booster de la fusée "En marche" et la mission "Débutée".
- Lorsque le compte à rebours est terminé, la fusée doit démarré son booster principal.
- Après quelques secondes, la fusée décolle du sol
- Lorsque la fusée atteint une altitude d'environ 14km, elle entre en MaxQ et sa vitesse est diminuée
- Puis au bout de quelques kilomètres, elle sort de la zone de MaxQ
- Une fois que la fusée atteint une certaines distance, elle éteint son moteur principal, pour ainsi pouvoir se séparer de son booster principal (à améliorer car il faudrait s'en séparer en fonction de l'essence restante).
- Le booster démarre sa séquence d'atterissage.
- Une fois que la fusée est arrivée à son objectif, Richard regarde les logs de la mission, et voit des logs généré par la séquence d'atterissage du booster

# Service-oriented Architecture Lab

project name : **Blue Galactic X**

NB : all deliverables not concerning code for the project is in deliverable

## Web Services Architecture

[architecture](./docs/Architecture.md)

## Project Strategy

Project Policies for the team

### Branching

| branch name  | goal                                                                                     |
| ------------ | ---------------------------------------------------------------------------------------- |
| master       | main branch, for the stable version only, must be on develop and tested on develop first |
| develop      | develop branch, each completed task are merged with it                                   |
| feature/BG-X | User Story branch                                                                        |
| hotfix/BG-X  | Hotfix or production critical error from master                                          |
| bugfix/BG-X  | Unexpected Bug that need to be fixe on develop                                           |
| rework/BG-X  | when a rework (or refactor) need to be done                                              |

> BG-X : Blue Galactic with the ID of the issue

### Git

Never push to develop, after finishing a task, make a PR (Pull Request) and wait for at least 2 members of the team to approve.
Each commit must have a link to the ID of the task at the begin of the commit and please, try to make a consistent commit message.

> #X (e.g. #15 with 15 the number of the issue)
>
> GH-X (e.g. GH-15 with 15 the number of the issue)

When working on local, never merge when you want to update your local branch with the latest modification from the remote branch. Use instead *git pull --rebase* to avoid making a mess in the git history when it is not necessary.

### Task

each task must be a US like

#### Title :

**[Pts: x] I am a short description**

the **Pts** correspond to the story points

#### Body :

*User Story*

**AS** ...

**I WANT** ...

**SO THAT** ...

*Acceptance Criteria*

**GIVEN** ...

**WHEN** ...

**THEN** ...

(others : *BUT*, *END*)

also (Description, Scope)

>Acceptance Criteria are here to validate the US with User Acceptance Test (e.g. Gherkin testing framework and maybe E2E test or integration test)

## Technology stack and Interface

[check the technology stack](./docs/TechnologyStack.md)

## Serializer and Deserializer Format

[check the format](./docs/ParserFormat.md)
