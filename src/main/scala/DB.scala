import java.sql._
import javafx.scene.{Scene, Parent}
import scala.collection.mutable.ListBuffer
import scala.util.Try


object Db {
  /**
    * A marker interface for datastructures which should be persisted to a jdbc database.
    *
    * @tparam T the type to be persisted / loaded
    */
  trait DbEntity[T] {
    /**
      * Recreates the table this entity is stored in
      *
      * @param stmt
      * @return
      */
    def reTable(stmt: Statement): Int = {
      stmt.executeUpdate(dropTableSql)
      stmt.executeUpdate(createTableSql)
    }
    /**
      * Saves given type to the database.
      *
      * @param c
      * @param t
      * @return
      */
    def toDb(c: Connection)(t: T): Int
    /**
      * Given the resultset, it fetches its rows and converts them into instances of T
      *
      * @param rs
      * @return
      */
    def fromDb(rs: ResultSet): List[T]
    /**
      * Queries the database
      *
      * @param con
      * @param query
      * @return
      */
    def query(con: Connection)(query: String): ResultSet = {
      con.createStatement().executeQuery(query)
    }
    /**
      * Sql code necessary to execute a drop table on the backing sql table
      *
      * @return
      */
    def dropTableSql: String
    /**
      * sql code for creating the entity backing table
      */
    def createTableSql: String
    /**
      * sql code for inserting an entity.
      */
    def insertSql: String

  }

  lazy val maybeConnection: Try[Connection] =
    Try(DriverManager.getConnection("jdbc:sqlite::memory:"))
}

/**
object DbTool {

  def main(args: Array[String]) {

    // parameters for the connection
    val driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver"
    val url="jdbc:microsoft:sqlserver://10.25.2.143:1433;databaseName=daent_g1;"
    val username = "wagm"
    val password = "wagenede14"

    // Connection --> NULL setzen
    var connection:Connection = null

    try {
      // connection connecten
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

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
*/


























