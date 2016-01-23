import java.sql.{ResultSet, Connection}

/**
  * Created by ninas on 23.01.2016.
  */
object AssessmentLogin extends Db.DbEntity[AssessmentLogin] {
  def toDb(c: Connection)(al: AssessmentLogin) : Int = ???
  def fromDb(rs: ResultSet): List[AssessmentLogin] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

// from, to -> Date, Time ???
case class AssessmentLogin(assessmentLoginId:Int, attempt:Int, description:String, assessmentDateId:String, student:String, mark:String) extends Db.DbEntity[AssessmentLogin] {
  def toDb(c: Connection)(al: AssessmentLogin) : Int = ???
  def fromDb(rs: ResultSet): List[AssessmentLogin] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}
