# 🕋 Manasik — Hajj & Umrah Step-by-Step Guide

> A desktop application built with JavaFX that serves as a digital, step-by-step guide for pilgrims performing Hajj and Umrah.

---

## 📖 About the Project

Many pilgrims — especially first-time visitors — get confused about the correct order of Hajj and Umrah rituals.
Existing solutions like YouTube videos, booklets, and tour guides are not interactive or step-by-step in real time.

**Manasik** solves this by guiding the user step-by-step from start to finish, with a clear checklist, the ability to jump to any step, and progress tracking.

---

## ✨ Features

### ✅ Implemented (v1.0,V2.0)

| # | Feature |                                    Description

| 1 | Ritual Selection | Home screen lets the user choose between Hajj or Umrah with a single tap.                             
| 2 | View All Steps (Roadmap screen) | A dedicated screen displaying every step of the selected ritual in order.                                    
| 3 | Start Journey | Begin the ritual from step 1 with a single tap and follow it through to the end.                             
| 4 | View Step Details | Tap any step to see its description and instructions.                                                        
| 5 | Jump to Specific Step | Tap any step in the roadmap to go straight to it useful if earlier steps are already completed in real life.

### 🚧 Planned

| # | Feature

| 6 | Complete Step & Progress Tracking
| 7 | Next / Previous Step Navigation
| 8 | Dark Mode / Light Mode toggle
| 9 | Multi-language support (Arabic / English)

---

## 🏗️  Design Patterns

The project uses a clean layered architecture with two design patterns from the GoF catalog:

### 1. Creational — Factory Pattern (Stage 1)

**File:** [`RitualFactory.java`](src/main/java/sa/edu/kau/fcit/cpit252/project/factory/RitualFactory.java)

Why we chose it:
We have two ritual types (Hajj and Umrah) that share the same Ritual interface,
so we needed a clean way to create the right object based on the user's choice.
The Factory keeps the UI decoupled from the concrete classes and makes it easy to add more ritual types later without touching the UI code.

### 2. Structural — Facade Pattern (Stage 2)

**File:** [`RitualFacade.java`](src/main/java/sa/edu/kau/fcit/cpit252/project/facade/RitualFacade.java)

Why we chose it:
The UI needs to coordinate two subsystems (the Factory that creates rituals, and the Progress Manager that tracks the user's current step) for every action,
which would make the UI complex and tightly coupled. The Facade gives the UI one simple entry point and hides all the internal coordination,
so changing any subsystem later won't affect the UI.


## 🚀 Build & Run

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
java -jar target/project-manasik-1.0.jar
```

### Download the pre-built binary

A ready-to-run JAR is available on the [Releases page](https://github.com/cpit252-spring-26-IT1/project-manasik/releases).

---

## 📸 Screenshots

Selection screen:
<img width="1919" height="1139" alt="Selection_Screen" src="https://github.com/user-attachments/assets/bfa40d7d-2491-4780-bad5-80b57ee5960b" />

RoadMap of Hajj:
<img width="1919" height="1141" alt="RoadMap_Hajj" src="https://github.com/user-attachments/assets/fdea9e41-e717-40f4-8b0a-4c7f832e3793" />

RoadMap of Umrah:
<img width="1919" height="1138" alt="Roadmap_Umrah" src="https://github.com/user-attachments/assets/bce736c7-6590-4d5b-b2cc-915e77779640" />

Step details:
<img width="1919" height="1143" alt="Step_Details" src="https://github.com/user-attachments/assets/5761e709-231c-4bd1-ab3d-099d1f9aa134" />

## 📄 License

This project is licensed under the MIT License — see the [LICENSE](LICENSE) file for details.

```
MIT License — Copyright (c) 2026 Manasik Team
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files.
```
