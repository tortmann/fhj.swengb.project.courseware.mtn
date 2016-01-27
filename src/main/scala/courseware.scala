import scala.util.Try
import java.sql.{Connection, DriverManager, ResultSet, Statement}




object Db {
  /**
    * A marker interface for datastructures which should be persisted to a jdbc database.
    *
    * @tparam T the type to be persisted / loaded
    */
  trait DbEntity[T] {
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
      * sql code for inserting an entity.
      */
    def insertSql: String
  }

  def Con: Connection = {
    val driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver"
    val url = "jdbc:microsoft:sqlserver://10.25.2.143:1433;databaseName=daent_g1;"
    val username = "wagm"
    val password = "wagenede14"



    // Connection --> NULL setzen
    var connection: Connection = null

    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    connection
  }
}

object Courseware {


  def main(args: Array[String]) {


    // parameters for the connection
    val driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver"
    val url = "jdbc:microsoft:sqlserver://10.25.2.143:1433;databaseName=daent_g1;"
    val username = "wagm"
    val password = "wagenede14"



    // Connection --> NULL setzen
    var connection: Connection = null

    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)

    /**
    //QUERY ALL TABLES
      for (t <- Assessment.fromDb(Assessment.queryAll(connection))) {
        println(t)
      }
      for (t <- AssessmentDate.fromDb(AssessmentDate.queryAll(connection))) {
        println(t)
      }
      for (t <- AssessmentLogin.fromDb(AssessmentLogin.queryAll(connection))) {
        println(t)
      }
      for (t <- Classroom.fromDb(Classroom.queryAll(connection))) {
        println(t)
      }
      for (t <- CommissionalExam.fromDb(CommissionalExam.queryAll(connection))) {
        println(t)
      }
      for (t <- Lecture.fromDb(Lecture.queryAll(connection))) {
        println(t)
      }
      for (t <- LectureEvent.fromDb(LectureEvent.queryAll(connection))) {
        println(t)
      }
      for (t <- Student.fromDb(Student.queryAll(connection))) {
        println(t)
      }
      for (t <- Teacher.fromDb(Teacher.queryAll(connection))) {
        println(t)
      }

    for (t <- Lecture.fromDb(Lecture.queryAll(connection))) {
      println(t)}

    val l = new Lecture("MAXMAX","Maximilian is der coolste", 5.0,"LE-00001","")
    Lecture.toDb(connection)(l)

    for (t <- Lecture.fromDb(Lecture.queryAll(connection))) {
      println(t)}

    val a = Assessment("Prüfung", 120, "SWENGB", "")
    Assessment.toDb(connection)(a)

    Teacher.delFromDb(connection)("LE-00011")
    connection.close()


    val le = LectureEvent("DAENT_1", "2015-12-21 08:00:00", "2015-12-21 10:30:00", "", "DAENT", "grp_1", "G.AP147.220")
    LectureEvent.toDb(connection)(le)


      */

    for (t <- Student.fromDb(Student.queryReport(connection))) {
      println(t)



  }

}