import java.sql.{Connection, Date, ResultSet}



object Lecture extends Db.DbEntity[Lecture] {
  def toDb(c: Connection)(l: Lecture) : Int = ???

  def fromDb(rs: ResultSet): List[Lecture] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???

  def queryAll(con: Connection): ResultSet = ???
}



case class Lecture(id:String, fullname:String, ects:Float, teacher:String, description:String) extends Db.DbEntity[Lecture] {

  def toDb(c: Connection)(l: Lecture) : Int = ???

  def fromDb(rs: ResultSet): List[Lecture] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???
}