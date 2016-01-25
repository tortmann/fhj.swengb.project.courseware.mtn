import java.sql.{ResultSet, Connection}



object AssessmentLogin extends Db.DbEntity[AssessmentLogin] {
  def toDb(c: Connection)(al: AssessmentLogin) : Int = ???

  def fromDb(rs: ResultSet): List[AssessmentLogin] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???

  def queryAll(con: Connection): ResultSet = ???
}



case class AssessmentLogin(id:Int, attempt:Int, description:String, assessmentDate:String, student:String,
                           mark:Int) extends Db.DbEntity[AssessmentLogin] {

  def toDb(c: Connection)(al: AssessmentLogin) : Int = ???

  def fromDb(rs: ResultSet): List[AssessmentLogin] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???
}