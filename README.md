# Clojure Interpreter in Java

This project provides a minimalistic Clojure-like interpreter implemented in Java. It is designed to enable users to execute a subset of Clojure's expressions directly from the command line or by providing a script file.

## Requirements

- JDK 17
- Maven

## Compilation

To compile the project, use the following Maven command:

```bash
mvn package
```
This command will compile the source code and package it into an executable JAR file.

## Usage

To start the REPL (Read-Eval-Print Loop), simply run the JAR file without any arguments:

```bash
java -jar LispInterpreter-1.0-SNAPSHOT.jar
```

If you have a script file (e.g., script.clj) that you want to execute, you can run the interpreter with the following command:

```bash
java -jar LispInterpreter-1.0-SNAPSHOT.jar -f script.clj
```

To exit the REPL, type \q and press Enter.

## Features

The current version of the Clojure Interpreter supports the following functions by default:
* Arithmetic operations: `+`, `-`, `*`, `/`
* Relational operations: `<`, `<=`, `>=`, `>`
* Logical operations: `and`, `or`, `not`
* Equality operation: `=`
* Definition operations: `defn`, `def`
* Conditional operation: `if`
* Output operations: `println`, `format`

## REPL Console
The `ReplConsole` class provides the main entry point to the interpreter. It handles the REPL loop and script execution logic.

## LispExecuter
The `LispExecuter` class is responsible for executing the provided SExpression. It initializes the context with the default functions and evaluates the given expression.

## Extensibility
The interpreter is designed to be easily extendable with additional functions. To add a new function, define it in the LispExecuter class, and add it to the context.

## Contributing
Contributions to the interpreter are welcome. Please ensure that any new features include corresponding test cases.

