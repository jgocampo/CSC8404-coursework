# CSC8404 Coursework - Quiz System

## Overview
This project is part of the CSC8404 Advanced Programming in Java coursework. It implements a **Quiz System** designed for student assessments. The system allows students to take quizzes, track performance, and generate statistics. The focus is on object-oriented design principles, including interface-based hierarchies, late binding, immutability, collections, and unit testing.

## Features
- **Quiz Management**: Generates regular and revision quizzes.
- **Student Management**: Tracks student attempts, scores, and assessment results.
- **Question Types**: Supports both multiple-choice and free-response questions.
- **Scoring System**: Determines pass/fail verdicts based on quiz scores.
- **Statistics Generation**: Provides insights into student performance.
- **Unit Testing**: Ensures correctness using JUnit.

## System Design
### **Main Components**
- **Question Interface & Implementations**:
  - `Question`: Base interface for quiz questions.
  - `FreeResponseQuestion`: Represents open-ended questions.
  - `MultipleChoiceQuestion`: Handles multiple-choice questions.
- **Quiz System**:
  - `Quiz`: Represents a quiz with a list of questions.
  - `RegularQuiz`: A randomly generated quiz with unique questions.
  - `RevisionQuiz`: Contains only questions the student answered incorrectly or hasn’t seen before.
  - `QuizFactory`: Ensures correct instantiation of quiz objects.
- **Student and Statistics**:
  - `Student`: Stores student details and quiz history.
  - `Statistics`: Tracks student performance metrics.
- **Utility Classes**:
  - `QuizGenerator`: Manages quiz creation.
  - `QuizEvaluator`: Processes answers and calculates scores.

## Installation & Usage
### **1. Clone the Repository**
```sh
 git clone https://github.com/YOUR_GITHUB/CSC8404-QuizSystem.git
 cd CSC8404-QuizSystem
```
### **2. Compile and Run**
```sh
 javac -d bin src/**/*.java
 java -cp bin Main
```
### **3. Run Unit Tests**
```sh
 javac -cp .:junit-4.13.2.jar -d bin test/**/*.java
 java -cp .:bin:junit-4.13.2.jar org.junit.runner.JUnitCore test.QuizTest
```

## Testing
- **Unit Testing**: Each component is tested with JUnit.
- **Test Cases Cover**:
  - Normal scenarios.
  - Boundary conditions.
  - Exception handling.

## UML Diagram
The UML diagram illustrating class relationships and hierarchy.

![image](https://github.com/user-attachments/assets/3c734ec5-a51e-4032-b957-850e798cdd2e)

