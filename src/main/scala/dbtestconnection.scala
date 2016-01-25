/**
 * Created by Max on 23.01.16.
 */

package jdbc

import java.sql.DriverManager
import java.sql.Connection



object dbtestconnection {

  def main(args: Array[String]) {
    // parameters for the connection
    val driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver"
    //val driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"


    // val url = "jdbc:microsoft:sqlserver://10.25.2.143:1433;DatabaseName=daent_g1"
    val url="jdbc:microsoft:sqlserver://10.25.2.143:1433;databaseName=daent_gr1;"
    //val url = "jdbc:msql://10.25.2.143:1114/daent_gr1"

    val username = "wagm"
    val password = "wagenede14"



    // Connection --> NULL setzen
    var connection:Connection = null

    try {
      // connection connecten
      Class.forName(driver);
      connection = DriverManager.getConnection(url, username, password)

       //connection = DriverManager.getConnection("jdbc:sqlserver://10.25.2.143:1433;" +
       //  "databaseName=daent_g1;user=wagm;password=wagenede14");



      // "SQL Statement"
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT firstname, lastname FROM dbo.student")
      while ( resultSet.next() ) {
        val firstname = resultSet.getString("firstname")
        val lastname = resultSet.getString("lastname")
        println("firstname, lastname = " + firstname + " " + lastname)
      }
    } catch {
      case e => e.printStackTrace
    }
    connection.close()
  }

}