import java.sql.{ResultSet, Connection}

/**
  * Created by ninas on 23.01.2016.
  */
object AssessmentDate extends Db.DbEntity[AssessmentDate] {
  def toDb(c: Connection)(ad: AssessmentDate) : Int = ???
  def fromDb(rs: ResultSet): List[AssessmentDate] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

// from, to -> Date, Time ???
case class AssessmentDate(assessmentDateId:String, description:String, abbr:Int, classroom:String) extends Db.DbEntity[AssessmentDate] {
  def toDb(c: Connection)(ad: AssessmentDate): Int = ???
  def fromDb(rs: ResultSet): List[AssessmentDate] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}