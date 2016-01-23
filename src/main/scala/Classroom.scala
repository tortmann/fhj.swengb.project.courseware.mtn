import java.sql.{ResultSet, Connection}

/**
  * Created by ninas on 23.01.2016.
  */
object Classroom extends Db.DbEntity[Classroom] {
  def toDb(c: Connection)(cr: Classroom) : Int = ???
  def fromDb(rs: ResultSet): List[Classroom] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

case class Classroom() extends Db.DbEntity[Classroom] {
  def toDb(c: Connection)(cr: Classroom) : Int = ???
  def fromDb(rs: ResultSet): List[Classroom] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}
