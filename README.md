# PrettyJDBC
[![](https://travis-ci.com/MarchenkoProjects/PrettyJDBC.svg?branch=master)](https://travis-ci.com/MarchenkoProjects/PrettyJDBC)
[![](https://img.shields.io/codecov/c/github/marchenkoprojects/prettyjdbc/master.svg?colorB=red&style=flat)](https://codecov.io/gh/MarchenkoProjects/PrettyJDBC)
[![](https://img.shields.io/github/license/marchenkoprojects/prettyjdbc.svg?style=flat)](https://github.com/MarchenkoProjects/PrettyJDBC/blob/master/LICENSE)
[![](https://img.shields.io/maven-central/v/com.github.marchenkoprojects/prettyjdbc.svg?style=flat&colorB=brightgreen)](https://search.maven.org/artifact/com.github.marchenkoprojects/prettyjdbc/0.3/jar)
[![](https://img.shields.io/badge/javadoc-v0.3-blue.svg?style=flat)](https://javadoc.io/doc/com.github.marchenkoprojects/prettyjdbc/0.3)

**PrettyJDBC** is a library that provides a simple and transparent way to work with a relational database. The library is a lightweight wrapper over JDBC technology.
## Getting started ##
### Installation ###
To use PrettyJDBC you just need to include the [**prettyjdbc-x.x.jar**](https://github.com/MarchenkoProjects/PrettyJDBC/releases/download/v0.3/prettyjdbc-0.3.zip) file in the classpath.

If you are using Maven just add the following dependency to your *pom.xml*:
```xml
<dependency>
  <groupId>com.github.marchenkoprojects</groupId>
  <artifactId>prettyjdbc</artifactId>
  <version>0.3</version>
</dependency>
```

### Creating a SessionFactory ###
Every application that uses *PrettyJDBC* is focused on an instance of **SessionFactory**.
SessionFactory represents the basic mechanism for getting a **Session** object. 

To work correctly you must register and configure [DataSource](https://docs.oracle.com/javase/7/docs/api/javax/sql/DataSource.html).
```java
DataSource dataSource = ...

SessionFactory sessionFactory = SessionFactory.create(() -> dataSource);
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
But this method is recommended for **informational purposes only**!