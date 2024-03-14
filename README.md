# UniversitySystem - University Platform

UniversitySystem is a Java application with Swing GUI and MySQL database communication using JDBC. Its purpose is to provide a comprehensive platform for managing university activities, catering to both students and professors.

## Key Features:

### 1. Authentication and Redirection:
- The application features an authentication system allowing users to log in using a username and password.
- Upon authentication, users are redirected to functionalities specific to their role (student or professor).

### 2. Student Management:
- Students can view personal information such as grades, course schedules, and activity timetables.
- They can also access and update personal details like email address or phone number.

### 3. Gradebook:
- Professors can add, edit, and delete grades for individual students.
- Students can view the grades obtained in their various academic activities.

### 4. Activity Planning:
- The platform provides functionalities for planning and managing academic activities, such as creating courses, setting tasks, and deadlines for projects.

### 5. Role-based Redirection:
- The application features logic that directs users to relevant pages and functionalities based on their role (student or professor).

## Usage Instructions:

1. Clone or download the project from GitHub.
2. Import the project into a Java development environment such as IntelliJ IDEA or Eclipse.
3. Configure the connection to the MySQL database in the `database.properties` file.
4. Run the SQL scripts provided in the `database/` directory to set up the database schema and populate it with initial data.
5. After running the scripts, it is recommended to change the MySQL password to your own.
6. Run the application and authenticate using a valid username and password.
---

UniversitySystem is an essential tool for efficiently managing university activities, offering an intuitive interface and comprehensive functionalities for both students and professors.

For questions or issues, please create an issue on GitHub.

---

**Author:** Blotor Raul Flavius