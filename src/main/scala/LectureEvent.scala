import java.sql.{Date, ResultSet, Connection}
import scala.collection.mutable.ListBuffer


object LectureEvent extends Db.DbEntity[LectureEvent] {
  def toDb(c: Connection)(le: LectureEvent) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setString(1, le.id)
    pstmt.setString(2, le.from)
    pstmt.setString(3, le.to)
    pstmt.setString(4, le.description)
    pstmt.setString(5, le.lecture)
    pstmt.setString(6, le.group)
    pstmt.setString(7, le.classroom)
    pstmt.executeUpdate()
  }

  def delFromDb(c: Connection)(prop: String) : Int = {
    val pstmt = c.prepareStatement(deleteSql + prop + "'")
    pstmt.executeUpdate()
  }

  def fromDb(rs: ResultSet): List[LectureEvent] = {
    val lb : ListBuffer[LectureEvent] = new ListBuffer[LectureEvent]()
    while (rs.next()) lb.append(LectureEvent(rs.getString("lecture_event_id"), rs.getString("date_start"),
                                             rs.getString("date_end"), rs.getString("description"),
                                             rs.getString("lecture"), rs.getString("group_nr"), rs.getString("classroom")))
    lb.toList
  }

  def insertSql: String = "insert into dbo.lecture_event (lecture_event_id, date_start, date_end, description, " +
                          "lecture, group_nr, classroom) VALUES (?, ?, ?, ?, ?, ?, ?)"

  def deleteSql: String = "delete from dbo.lecture_event where lecture_event_id = '"

  def queryAll(con: Connection): ResultSet = query(con)("select * from dbo.lecture_event")
}



case class LectureEvent(id:String, from:String, to:String, description:String, lecture:String, group:String,
                        classroom:String) extends Db.DbEntity[LectureEvent] {

  def toDb(c: Connection)(le: LectureEvent) : Int = 0

  def fromDb(rs: ResultSet): List[LectureEvent] = List()

  def delFromDb(c: Connection)(le: LectureEvent) : Int = 0

  def insertSql: String = ""
}