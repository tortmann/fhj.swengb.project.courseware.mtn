import java.sql.{ResultSet, Connection}
import java.text.SimpleDateFormat



object CommissionalExam extends Db.DbEntity[CommissionalExam] {
  def toDb(c: Connection)(ce: CommissionalExam) : Int = ???

  def fromDb(rs: ResultSet): List[CommissionalExam] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???

  def queryAll(con: Connection): ResultSet = ???
}



case class CommissionalExam(id:Int, pers1:String, pers2:String, pers3:String, lecture:String, from:SimpleDateFormat,
                            to:SimpleDateFormat, details:String,mark:Int, student:String) extends Db.DbEntity[CommissionalExam] {

  def toDb(c: Connection)(ce: CommissionalExam) : Int = ???

  def fromDb(rs: ResultSet): List[CommissionalExam] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???
}