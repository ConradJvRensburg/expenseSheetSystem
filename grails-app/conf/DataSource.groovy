dataSource{
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}

environments{
    production{
        dataSource{
            dbCreate = "update"
            url = "jdbc:h2:./data/expenseSheetDB;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
            pooled = true
        }
    }
}