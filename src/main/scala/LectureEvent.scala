import java.sql.{ResultSet, Connection}
import java.text.SimpleDateFormat



object LectureEvent extends Db.DbEntity[LectureEvent] {
  def toDb(c: Connection)(le: LectureEvent) : Int = ???

  def fromDb(rs: ResultSet): List[LectureEvent] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???

  def queryAll(con: Connection): ResultSet = ???
}



case class LectureEvent(id:String, from:SimpleDateFormat, to:SimpleDateFormat, description:String, lecture:String,
                        group:String, classroom:String) extends Db.DbEntity[LectureEvent] {

  def toDb(c: Connection)(le: LectureEvent) : Int = ???

  def fromDb(rs: ResultSet): List[LectureEvent] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???
}