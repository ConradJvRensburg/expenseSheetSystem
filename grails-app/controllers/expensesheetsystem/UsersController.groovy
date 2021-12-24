package expensesheetsystem

import java.sql.*
import org.h2.jdbcx.JdbcConnectionPool

class UsersController {
    static String loggedInUser;
    static String uID;
    JdbcConnectionPool conPool
    Connection con
    Statement state
    ResultSet resSet

    def scaffold = Users //generates scaffolding for users

    def defaultLanding() { //defines defaultLanding, this enables grails to the display the view

    }

    def loadUser() {
        conPool = JdbcConnectionPool.create("jdbc:h2:./data/expenseSheetDB","sa","") //used to connect to the database
        con = conPool.getConnection()
        def username = params.usernameInput //gets username inserted in findUser.gsp
        loggedInUser = String.valueOf(username)
        state = con.createStatement()
        resSet = state.executeQuery("SELECT ID FROM USERS WHERE username='"+loggedInUser+"'")
        if (resSet.next()){ //checks if the username exists in the database
            uID = resSet.getString(1)
            def user = session["user"]
            session["user"] = uID //creates a session variable from the ID of the logged in user
            redirect(controller: 'users', action: 'defaultLanding')
        }else{
            redirect(controller: 'landingPage', action:'userNotFound')
        }
        //closes database connections
        con.close()
        conPool.dispose()
    }




}
