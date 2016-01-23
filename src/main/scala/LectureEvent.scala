import java.sql.{ResultSet, Connection}

/**
  * Created by ninas on 23.01.2016.
  */
object LectureEvent extends Db.DbEntity[LectureEvent] {
  def toDb(c: Connection)(le: LectureEvent) : Int = ???
  def fromDb(rs: ResultSet): List[LectureEvent] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

case class LectureEvent() extends Db.DbEntity[LectureEvent] {
  def toDb(c: Connection)(le: LectureEvent) : Int = ???
  def fromDb(rs: ResultSet): List[LectureEvent] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}
