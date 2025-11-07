A simple Book Library REST API that implements the Spring Boot and the Spring Security frameworks to manage users, books and loans
in a modern and secure way.
Done in Spring 3.5.7, Java 21 and Maven with a local h2 database.

### Business rules

1. Can’t borrow a loan if no copies of the wanted book are available. When books are borrowed, their copies get reduced. When
   books are returned, their copies increase.
2. Each user has a limit of 3 active loans

Users can get their user info and loans by `/users/me` and `/loans/me` respectively, but they can also see the list of all users
and all loans with the base route since there are no roles (on porpuse for simplicity).

![database-schema](database-schema.png)