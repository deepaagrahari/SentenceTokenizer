# Sentence Tokenizer Java Application

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Setup and Installation](#setup-and-installation)
  - [Clone the Repository](#clone-the-repository)
  - [Maven](#maven) 
- [Usage](#usage)
  - [Running the Application](#running-the-application) 
- [Logging](#logging)
  - [Configuration](#configuration)
  - [Log Files](#log-files)

## Introduction
The Sentence Tokenizer application is a Java program that reads input text, identifies sentence boundaries, and processes each sentence. It utilizes Log4j2 for logging purposes, ensuring that all operations are properly logged to both the console and a file.

## Features
- Reads input from various input streams.
- Identifies sentence boundaries based on custom logic.
- Logs activities and errors using Log4j2.
- Outputs processed sentences.

## Requirements
- Java 8 or higher
- Maven or Gradle for dependency management

# Setup and Installation

### Clone the Repository

```bash
  git clone https://github.com/yourusername/sentence-tokenizer.git
  cd sentence-tokenizer
```


## Maven
To build the project using Maven, 
run:

```bash
  mvn clean package
```

## Usage
### Running the Application
To run the application, use the following command:

```bash
  java -jar target\SentenceTokenizer-1.0-SNAPSHOT.jar xml/csv
```


Replace target/SentenceTokenizer-1.0-SNAPSHOT-shaded.jar with the path to your JAR file if it is different.

Paste the content into the console, then terminate the console by pressing Enter and Ctrl+Z to display the desired output.

## Logging
### Configuration
The logging configuration is defined in the log4j2.xml. The default configuration logs messages to a file named SentenceTokenizer.log.

## Log Files
The log file is created in the root directory of the project. You can change the logging configuration by modifying the log4j2.xml file.