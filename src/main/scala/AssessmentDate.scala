import java.sql.{Date, ResultSet, Connection}
import java.text.SimpleDateFormat

import scala.collection.mutable.ListBuffer


object AssessmentDate extends Db.DbEntity[AssessmentDate] {
  def toDb(c: Connection)(ad: AssessmentDate) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setString(1, ad.id)
    pstmt.setDate(2, ad.from)
    pstmt.setDate(3, ad.to)
    pstmt.setString(4, ad.description)
    pstmt.setInt(5, ad.abbr)
    pstmt.setString(6, ad.classroom)
    pstmt.executeUpdate()
  }

  def fromDb(rs: ResultSet): List[AssessmentDate] = {
    val lb : ListBuffer[AssessmentDate] = new ListBuffer[AssessmentDate]()
    while (rs.next()) lb.append(AssessmentDate(rs.getString("assessment_date_id"), rs.getDate("date_start"),
                                               rs.getDate("date_end"), rs.getString("description"), rs.getInt("abbr"),
                                               rs.getString("classroom")))
    lb.toList
  }

  def insertSql: String = "insert into dbo.assessment_date (assessment_date_id, date_start, date_end, description, " +
                          "abbr, classroom) VALUES (?, ?, ?, ?, ? ,?)"

  def queryAll(con: Connection): ResultSet = query(con)("select * from dbo.assessment_date")
}



case class AssessmentDate(id:String, from:Date, to:Date, description:String, abbr:Int,
                          classroom:String) extends Db.DbEntity[AssessmentDate] {

  def toDb(c: Connection)(ad: AssessmentDate): Int = 0

  def fromDb(rs: ResultSet): List[AssessmentDate] = List()

  def insertSql: String = ""
}