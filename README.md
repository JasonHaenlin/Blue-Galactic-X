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
