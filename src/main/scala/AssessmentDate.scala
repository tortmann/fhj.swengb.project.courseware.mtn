import java.sql.{ResultSet, Connection}
import java.text.SimpleDateFormat



object AssessmentDate extends Db.DbEntity[AssessmentDate] {
  def toDb(c: Connection)(ad: AssessmentDate) : Int = ???

  def fromDb(rs: ResultSet): List[AssessmentDate] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???

  def queryAll(con: Connection): ResultSet = ???
}



case class AssessmentDate(id:String, from:SimpleDateFormat, to:SimpleDateFormat, description:String, abbr:Int,
                          classroom:String) extends Db.DbEntity[AssessmentDate] {

  def toDb(c: Connection)(ad: AssessmentDate): Int = ???

  def fromDb(rs: ResultSet): List[AssessmentDate] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???
}