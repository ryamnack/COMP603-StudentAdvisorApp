# COMP603 - Student Advisor App

A Java Swing desktop application that helps AUT students plan their BCIS degree. A student logs in, views the available papers, marks papers as completed and the app checks prerequisites and tracks progress towards the 120 points the programme requires each year. Data is stored in an embedded Apache Derby database accessed through JDBC.

Built for COMP603 PDC Project 2, extending the Project 1 console advisor with a GUI and database.

## Features

- New Swing GUI with a login screen, main menu and paper view
- Ability to browse all 13 seeded papers with code, name, semester and points
- Mark papers as completed, with prerequisite checking: a paper is refused if its prerequisites are not yet completed and the message also lists which ones are missing
- Completed papers and students persisted in Derby, so progress survives restarts
- Progress report export
- Papers loaded into the database from `Paper_Data/papers.txt` the first time the app runs

## Tech Used

- Java (NetBeans Ant project, JDK 25)
- Swing
- Apache Derby 10.14.2.0 (embedded) via JDBC
- JUnit 4.12 with Hamcrest 1.3

All libraries are in `StudentAdvisorApp/lib`.

## Design

- **Singleton**: `DBManager` holds the single database connection
- **DAO**: `PaperDAO`, `StudentDAO` and `CompletedDAO` are interfaces, each with a Derby implementation (`DerbyPaperDAO`, `DerbyStudentDAO`, `DerbyCompletedDAO`)
- **MVC-style split**: Swing panels (`MainFrame`, `LoginPanel`, `MenuPanel`, `PapersPanel`) are the view and `AdvisorController` handles user actions and talks to the DAOs
- Domain classes: `Paper`, `Programme`, `Pathway`, `Student`
- `PaperSeeder` fills an empty database from the papers file

## Project structure

```
StudentAdvisorApp/
  src/studentadvisorapp/   application source
  test/studentadvisorapp/  JUnit tests
  Paper_Data/papers.txt    paper definitions used to seed the database
  lib/                     Derby, JUnit and Hamcrest jars
  nbproject/               NetBeans project files
```

## Running the app

1. Open the `StudentAdvisorApp` folder as a project in NetBeans.
2. Make sure the project uses the default JDK 25 platform.
3. Press F6 to run. The Derby database is created and seeded automatically on first run.

Only one process can use the embedded database at a time, so close the app before running the tests.

## Running the tests

In NetBeans press Alt+F6 (Test Project). The tests cover:

- `StudentTest`: prerequisite logic (`canTake`, `getMissingPrerequisites`)
- `DerbyPaperDAOTest`: seeding and reading papers from the database
- `PathwayTest` and `ProgrammeTest`: pathway and programme behaviour

## Team

- Ryan Mackenzie: Swing GUI and controller.
- Brayden Pearce: Database layer (Derby, DBManager, DAOs, seeder) and prerequisite check.
- We both did our corresponding unit tests.


## Git workflow

Work was done on feature, testing and improvement branches and merged into `main` with merge commits. See `git log --graph --oneline`.
