import java.sql.{Date, ResultSet, Connection}

import scala.collection.mutable.ListBuffer


object CommissionalExam extends Db.DbEntity[CommissionalExam] {
  def toDb(c: Connection)(ce: CommissionalExam) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setInt(1, ce.id)
    pstmt.setString(2, ce.pers1)
    pstmt.setString(3, ce.pers2)
    pstmt.setString(4, ce.pers3)
    pstmt.setString(5, ce.lecture)
    pstmt.setDate(6, ce.from)
    pstmt.setDate(7, ce.to)
    pstmt.setString(8, ce.details)
    pstmt.setInt(9, ce.mark)
    pstmt.setString(10, ce.student)
    pstmt.executeUpdate()
  }

  def fromDb(rs: ResultSet): List[CommissionalExam] = {
    val lb : ListBuffer[CommissionalExam] = new CommissionalExam[Lecture]()
    while (rs.next()) lb.append(CommissionalExam(rs.getInt("lecture_id"), rs.getString("comm_pers1"),
                                                 rs.getString("comm_pers2"), rs.getString("comm_pers3"),
                                                 rs.getString("lecture"), rs.getDate("date_start"),
                                                 rs.getDate("date_end"), rs.getString("details"), rs.getInt("mark"),
                                                 rs.getString("student")))
    lb.toList
  }

  def insertSql: String = "insert into dbo.commissional_exam (comm_exam_id, comm_pers1, comm_pers2, comm_pers3, " +
                          "lecture, date_Start, date_end, details, mark, student) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

  def queryAll(con: Connection): ResultSet = query(con)("select * from dbo.commissional_exam")
}



case class CommissionalExam(id:Int, pers1:String, pers2:String, pers3:String, lecture:String, from:Date, to:Date,
                            details:String, mark:Int, student:String) extends Db.DbEntity[CommissionalExam] {

  def toDb(c: Connection)(ce: CommissionalExam) : Int = 0

  def fromDb(rs: ResultSet): List[CommissionalExam] = List()

  def insertSql: String = ""
}