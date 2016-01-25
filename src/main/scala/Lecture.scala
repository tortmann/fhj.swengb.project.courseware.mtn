import java.sql.{Connection, Date, ResultSet}

import scala.collection.mutable.ListBuffer


object Lecture extends Db.DbEntity[Lecture] {
  def toDb(c: Connection)(l: Lecture) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setString(1, l.id)
    pstmt.setString(2, l.fullname)
    pstmt.setFloat(3, l.ects)
    pstmt.setString(4, l.teacher)
    pstmt.setString(5, l.description)
    pstmt.executeUpdate()
  }

  def fromDb(rs: ResultSet): List[Lecture] = {
    val lb : ListBuffer[Lecture] = new ListBuffer[Lecture]()
    while (rs.next()) lb.append(Lecture(rs.getString("lecture_id"), rs.getString("fullname"), rs.getFloat("ects"),
                                        rs.getString("teacher"), rs.getString("description")))
    lb.toList
  }

  def insertSql: String = "insert into dbo.lecture (lecture_id, fullname, ects, teacher, description)" +
                          "VALUES (?, ?, ?, ?, ?)"

  def queryAll(con: Connection): ResultSet = query(con)("select * from dbo.lecture")
}



case class Lecture(id:String, fullname:String, ects:Float, teacher:String,
                   description:String) extends Db.DbEntity[Lecture] {

  def toDb(c: Connection)(l: Lecture) : Int = 0

  def fromDb(rs: ResultSet): List[Lecture] = List()

  def insertSql: String = ""
}