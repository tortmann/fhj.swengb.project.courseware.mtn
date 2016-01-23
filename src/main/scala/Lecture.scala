/**
  * Created by ninas on 23.01.2016.
  */
import java.sql.{Connection, Date, ResultSet}

/**
  * Created by ninas on 23.01.2016.
  */

object Lecture extends Db.DbEntity[Lecture] {
  def toDb(c: Connection)(l: Lecture) : Int = ???
  def fromDb(rs: ResultSet): List[Lecture] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

case class Lecture() extends Db.DbEntity[Lecture] {
  def toDb(c: Connection)(l: Lecture) : Int = ???
  def fromDb(rs: ResultSet): List[Lecture] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}

