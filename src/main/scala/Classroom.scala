import java.sql.{ResultSet, Connection}



object Classroom extends Db.DbEntity[Classroom] {
  def toDb(c: Connection)(cr: Classroom) : Int = ???

  def fromDb(rs: ResultSet): List[Classroom] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???

  def queryAll(con: Connection): ResultSet = ???
}



case class Classroom(id:String, size:Int, ctype:String, description:String) extends Db.DbEntity[Classroom] {

  def toDb(c: Connection)(cr: Classroom) : Int = ???

  def fromDb(rs: ResultSet): List[Classroom] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???
}