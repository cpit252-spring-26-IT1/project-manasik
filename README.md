# 🕋 Manasik — Hajj & Umrah Step-by-Step Guide

> A desktop application built with JavaFX that serves as a digital, step-by-step guide for pilgrims performing Hajj and Umrah.

---

##  About the Project

Many pilgrims — especially first-time visitors — get confused about the correct order of Hajj and Umrah rituals.
Existing solutions like YouTube videos, booklets, and tour guides are not interactive or step-by-step in real time.

Manasik solves this by guiding the user step-by-step from start to finish, with a clear checklist, the ability to jump to any step, and progress tracking.

---

##  Features

###  Implemented (v1.0,v2.0,v2.1)

| # | Feature |                                    Description

| 1 | Ritual Selection | Home screen lets the user choose between Hajj or Umrah with a single tap.                             
| 2 | View All Steps (Roadmap screen) | A dedicated screen displaying every step of the selected ritual in order.                                    
| 3 | Start Journey | Begin the ritual from step 1 with a single tap and follow it through to the end.                             
| 4 | View Step Details | Tap any step to see its description and instructions.                                                        
| 5 | Jump to Specific Step | Tap any step in the roadmap to go straight to it useful if earlier steps are already completed in real life.
| 6 | Complete Step & Progress Tracking | mark steps as done, progress bar and checkmarks update in real time.
| 7 | Next / Previous Step Navigation | navigate between steps from the Step Detail screen with disabled at boundary buttons.
| 8 | Resume Progress / Save State | progress is automatically saved to disk and restored when the app is reopened.

###  Planned

| # | Feature |                                                      Description

| 9 | Tawaf and Sa'i Counter | At each step that involves Tawaf or Sa'i the user gets a counter that goes up to 7 to track how many rounds have been completed.
| 10 | Dark Mode / Light Mode toggle | Switch the entire app between a dark and a light theme from settings.
| 11 | Multi-language support (Arabic / English) |  (Arabic / English)Switch all step names, details, and UI labels between Arabic and English.

---

##   Design Patterns

The project uses a clean layered architecture with two design patterns from the GoF catalog.

### 1. Creational — Factory Pattern (Stage 1)

**File:** [`RitualFactory.java`](src/main/java/sa/edu/kau/fcit/cpit252/project/factory/RitualFactory.java)

Why we chose it:
We have two ritual types (Hajj and Umrah) that share the same Ritual interface,
so we needed a clean way to create the right object based on the user's choice.
The Factory keeps the UI decoupled from the concrete classes and makes it easy to add more ritual types later without touching the UI code.

### 2. Structural — Facade Pattern (Stage 2)

**File:** [`RitualFacade.java`](src/main/java/sa/edu/kau/fcit/cpit252/project/facade/RitualFacade.java)

Why we chose it:
Every user action in the app involves coordinating several subsystems creating the right ritual, validating whether the action is allowed,
updating the user's progress state, and saving that progress to disk.
Without a Facade, the UI would have to call all of these classes directly for every button press,
which would couple the UI tightly to the internals and make every feature change risky.
The RitualFacade solves this by sitting between the UI and four subsystems and exposing one simple method per user action.

The four subsystems coordinated by the Facade:

1- RitualFactory: creates the correct ritual object Hajj or Umrah based on the user's choice.
2- RitualProgressManager: stores the user's current step index and the set of completed steps computes the progress percentage.
3- decides whether actions are allowed canGoNext, canGoPrevious, isValidJump and canMarkDone.
4- saves and loads progress to a small properties file in the user's home directory so progress survives app restarts.


##  Build & Run

### Prerequisites

- Java 17 or higher (tested on Java 21)
- Apache Maven 3.8+
- JavaFX 21 (managed automatically by Maven)

### Clone the repository

```bash
git clone https://github.com/cpit252-spring-26-IT1/project-manasik.git
cd project-manasik
```

### Build the project

```bash
mvn clean install
```

### Run the application

```bash
mvn javafx:run
```

Or, after building, run the packaged JAR:

```bash
java -jar target/project-manasik-2.1.jar
```

### Download the pre-built binary

A ready-to-run JAR is available on the [Releases page](https://github.com/cpit252-spring-26-IT1/project-manasik/releases).

---

##  Screenshots

Selection screen:
<img width="1919" height="1139" alt="Selection_Screen" src="https://github.com/user-attachments/assets/bfa40d7d-2491-4780-bad5-80b57ee5960b" />

RoadMap of Hajj:
<img width="1919" height="1141" alt="RoadMap_Hajj" src="https://github.com/user-attachments/assets/fdea9e41-e717-40f4-8b0a-4c7f832e3793" />

RoadMap of Umrah:
<img width="1919" height="1138" alt="Roadmap_Umrah" src="https://github.com/user-attachments/assets/bce736c7-6590-4d5b-b2cc-915e77779640" />

Step details:
<img width="1917" height="1134" alt="Screenshot 2026-05-07 234740" src="https://github.com/user-attachments/assets/58fe2f06-8bd0-412b-8389-ee2234b852a7" />

Resume Progress:
<img width="1919" height="1141" alt="Screenshot 2026-05-07 234723" src="https://github.com/user-attachments/assets/cc251da9-f58b-4738-9de8-5a9aa7b2b88b" />



##  License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

```
MIT License — Copyright (c) 2026 Manasik Team
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files.
```
