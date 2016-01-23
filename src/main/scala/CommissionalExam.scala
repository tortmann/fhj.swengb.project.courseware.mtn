import java.sql.{ResultSet, Connection}

/**
  * Created by ninas on 23.01.2016.
  */
object CommissionalExam extends Db.DbEntity[CommissionalExam] {
  def toDb(c: Connection)(ce: CommissionalExam) : Int = ???
  def fromDb(rs: ResultSet): List[CommissionalExam] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

case class CommissionalExam() extends Db.DbEntity[CommissionalExam] {
  def toDb(c: Connection)(ce: CommissionalExam) : Int = ???
  def fromDb(rs: ResultSet): List[CommissionalExam] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}
