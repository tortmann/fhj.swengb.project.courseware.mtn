import java.sql.{ResultSet, Connection}

import scala.collection.mutable.ListBuffer


object AssessmentLogin extends Db.DbEntity[AssessmentLogin] {
  def toDb(c: Connection)(al: AssessmentLogin) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setInt(1, al.id)
    pstmt.setInt(2, al.attempt)
    pstmt.setString(3, al.description)
    pstmt.setString(4, al.assessmentDate)
    pstmt.setString(5, al.student)
    pstmt.setInt(6, al.mark)
    pstmt.executeUpdate()
  }

  def fromDb(rs: ResultSet): List[AssessmentLogin] = {
    val lb : ListBuffer[AssessmentLogin] = new ListBuffer[AssessmentLogin]()
    while (rs.next()) lb.append(AssessmentLogin(rs.getInt("assessment_login_id"), rs.getInt("attempt"),
                                                rs.getString("description"), rs.getString("assessment_date"),
                                                rs.getString("student"), rs.getInt("mark")))
    lb.toList
  }

  def insertSql: String = "insert into dbo.assessment_login (assessment_login_id, attempt, description, " +
                          "assessment_date, student, mark) VALUES (?, ?, ?, ?, ? ,?)"

  def queryAll(con: Connection): ResultSet = query(con)("select * from dbo.assessment_login")
}



case class AssessmentLogin(id:Int, attempt:Int, description:String, assessmentDate:String, student:String,
                           mark:Int) extends Db.DbEntity[AssessmentLogin] {

  def toDb(c: Connection)(al: AssessmentLogin) : Int = 0

  def fromDb(rs: ResultSet): List[AssessmentLogin] = List()

  def insertSql: String = ""
}