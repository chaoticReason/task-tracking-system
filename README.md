#**Login and register pages**

#### Developed with

Java 14, JavaFX 15, MySQL 8

#### Structure

The app has few scenes. 

When users launch it, 
firstly they see Login scene with the only one field of login.

They enter their login, and if it exists then they
move to the next scene with another lonely field of password.
If a password correspond with the email, if so, users achieve a message
with congratulations.

Also, it has register page
which users can reach from Login page 
clicking on the link.

WARNING: Russian language.

#### Settings

1) Download **Java**.

2) Download **IntelliJ**.

3) Download **JavaFX**. 
Here is a tutorial https://www.jetbrains.com/help/idea/javafx.html#add-javafx-lib .

4) Download  **MySQL**. Don't forget 
about **Java Driver**,
so you can connect to database.

5) Download **SceneBuilder** 
which is a drag and drop UI designer tool
that generates FXML for JavaFX.

6) Add src folder of the project to libraries: menu File/Project Structure.../Libraries.
That helped to fix problem "TaskTrackingApp module is not found".

7) Create a MySQL server. Then create a database with table _users_
(id PRIMARY KEY, email UNIQUE, name, surname, password). 
I've enveloped the script in a directory helping/SQL scripts. 
DataGrips is okay program to create and edit databases, by the way.

8) Install a plugin **Database Navigator** in IntelliJ. 
Use the plugin to connect with the database. 
In class DatabaseConnection edit database name, 
url, user, password.

#### Further developing

1) In a class PasswordController in a method onLogin()
find a comment //INSERT HERE FURTHER ACTIONS. 
 Add some actions, for example, transition 
 to the main app.

