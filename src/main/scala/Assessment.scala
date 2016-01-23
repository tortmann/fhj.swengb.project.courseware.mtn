import java.sql.{ResultSet, Connection}

/**
  * Created by ninas on 23.01.2016.
  */
object Assessment extends Db.DbEntity[Assessment] {
  def toDb(c: Connection)(a: Assessment) : Int = ???
  def fromDb(rs: ResultSet): List[Assessment] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

case class Assessment() extends Db.DbEntity[Assessment] {
  def toDb(c: Connection)(a: Assessment) : Int = ???
  def fromDb(rs: ResultSet): List[Assessment] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}