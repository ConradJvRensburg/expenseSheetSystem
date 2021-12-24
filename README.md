# expenseSheetSystem

Description:
A web application used to track the expenses made by a user. The application makes use of scaffolding to create users, these users can then log in to make full use of the app's capabilites. They can create new transactions given that the user has enough money in their account, the user can also view all the transaction which they have made and they have the option to export their transactions as a csv file.

Technologies used in the development of the application:
- Intellij IDEA IDE
- Grails framework (Scaffolding and GSP)
- Groovy (Domain classes and controllers)
- H2 Database (Creates tables for each of the groovy domain classes)
- SQL (Insert, Update, and Select data from the H2 database)

How to run the project:
(Recommended)
The simplest way to run the project would be to open it in Intellij IDEA as a new project and to simply run the application. Intellij will get everything ready for you and you can just wait as the IDE builds the application and gets everything ready for you.

You can also run the application through the command line by going to the directory of the file on your device via the Command Prompt once in the directory you can run the application using the "run-app" command.

How to use the application:
- The landing page asks the user if they are new or have an account
  New Users
    - Will click on "Yes", this will redirect you to a page where you can input your name and balance.
    - Once you have inserted the relevant infromation click on "Create" located at the bottom of the form.
    - This will add a new user to the database.
    - To use the functionalities of the application a login is required, to do this either navigate to the homepage and click on         the "Go to Login" link located above the paragraph. Or click on "Users List" and then on "Login".
   
   Users that already have an account
    - Will input their usernames on the Login page and click on submit.
    - They will then be directed to the homepage where they can click on the "New Transaction" link to make a transaction, or the       "Show Transactions" link to show all the transaction linked to the account.
    
- Creating a transaction
  - Click on the "New Transaction" link (If you are not logged in you will be returned to the login page)
  - Insert the cost of your transaction
  - Click on submit to make a transaction
  - Click on the "Go to Homepage" link to return to the homepage
  
- Show transactions
  - Click on the "Show transactions" link (If you are not logged in you will be returned to the login page)
  - This will display a table with all the transactions linked to the logged in account
  - To export all the transactions of the user to a csv file click on the "export" button, this will download the file to the         device.
    
    
    
