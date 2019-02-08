# PrettyJDBC Library
[![](https://travis-ci.com/MarchenkoProjects/PrettyJDBC.svg?branch=master)](https://travis-ci.com/MarchenkoProjects/PrettyJDBC)
[![](https://img.shields.io/codecov/c/github/marchenkoprojects/prettyjdbc/master.svg?colorB=red&style=flat)](https://codecov.io/gh/MarchenkoProjects/PrettyJDBC)
[![](https://img.shields.io/librariesio/github/marchenkoprojects/prettyjdbc.svg?style=flat)](https://libraries.io/github/MarchenkoProjects/PrettyJDBC)
[![](https://img.shields.io/github/license/marchenkoprojects/prettyjdbc.svg?style=flat)](https://github.com/MarchenkoProjects/PrettyJDBC/blob/master/LICENSE)
[![](https://img.shields.io/maven-central/v/com.github.marchenkoprojects/prettyjdbc.svg?style=flat&colorB=brightgreen)](https://search.maven.org/artifact/com.github.marchenkoprojects/prettyjdbc/0.3/jar)
[![](https://img.shields.io/badge/javadoc-v0.3-blue.svg?style=flat)](https://javadoc.io/doc/com.github.marchenkoprojects/prettyjdbc/0.3)

**PrettyJDBC** is a Java library that provides a simple and transparent way to work with a relational database.
The library introduces a lightweight level of abstraction over *JDBC technology*.
Using *PrettyJDBC* protects you from working with low-level statements and multiple checked exceptions.
Since the library is a wrapper then you can always go back to a lower level to solve specific problems.

## Getting started ##
### Installation ###
To use PrettyJDBC you just need to include the [**prettyjdbc-x.x.jar**](https://github.com/MarchenkoProjects/PrettyJDBC/releases/download/v0.3/prettyjdbc-0.3.zip) file in the classpath.

If you are using automated build systems just add the following dependency to your project:

**Maven:**
```xml
<dependency>
  <groupId>com.github.marchenkoprojects</groupId>
  <artifactId>prettyjdbc</artifactId>
  <version>0.3</version>
</dependency>
```
**Gradle:**
```groovy
compile("com.github.marchenkoprojects:prettyjdbc:0.3")
```

### Creating a SessionFactory ###
Every application that uses *PrettyJDBC* is focused on an instance of **SessionFactory**.
SessionFactory represents the basic mechanism for getting a **Session** object to work with database. 

To work correctly you must configure and register [javax.sql.DataSource](https://docs.oracle.com/javase/7/docs/api/javax/sql/DataSource.html)
into *SessionFactory*.
```java
DataSource dataSource = ...

SessionFactory sessionFactory = SessionFactory.create(() -> dataSource);
// Work with sessionFactory instance
```

### Working with Session ###
**Session** represents the physical connection between Java application and relational database. Session is a lightweight object, so it is always 
created (`open session`) when you need to execute a query. After working with the session it must be destroyed (`close session`). 
Right way to work with session is to wrap it in `try-with-resources` because then there is no need to worry about its closure.

To work with a **Session** it is enough to get instance from **SessionFactory**:
```java
try(Session session = sessionFactory.openSession()) {
    // Working with session
}
```

If you don`t want to create an instance of a *SessionFactory*, 
you can use the static method of creating a *Session* from [java.sql.Connection](https://docs.oracle.com/javase/7/docs/api/java/sql/Connection.html):
```java
Connection connection = ...

try(Session session = SessionFactory.newSession(connection)) {
    // Working with session
}
```
But this method is recommended for `informational purposes only`!

A typical transaction should use the following idiom:
```java
try (Session session = sessionFactory.openSession()) {
    Transaction tx = session.beginTransaction();
    try {
        // Working with transaction in the session
        tx.commit();
    } catch (Exception e) {
        tx.rollback();
    }
}
```
But to simplify working with transactions, you can use the `lambda expression`.
```java
session.doInTransaction(currentSession -> {
    // Work in transaction
});
```
and to execute the transaction with returning the result:
```java
R result = session.doInTransaction(currentSession -> {
    // Work in transaction with return of the result
    return ...;
});
```