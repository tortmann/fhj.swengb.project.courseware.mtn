import java.sql.{ResultSet, Connection}

import scala.collection.mutable.ListBuffer


object Assessment extends Db.DbEntity[Assessment] {
  def toDb(c: Connection)(a: Assessment) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setString(1, a.atype)
    pstmt.setInt(2, a.duration)
    pstmt.setString(3, a.lecture)
    pstmt.setString(4, a.description)
    pstmt.executeUpdate()
  }

  def fromDb(rs: ResultSet): List[Assessment] = {
    val lb : ListBuffer[Assessment] = new ListBuffer[Assessment]()
    while (rs.next()) lb.append(Assessment(rs.getString("type"), rs.getInt("duration"),
                                           rs.getString("lecture"), rs.getString("description")))
    lb.toList
  }

  def insertSql: String = "insert into dbo.assessment (type, duration, lecture, description) " +
                          "VALUES (?, ?, ?, ?)"

  def queryAll(con: Connection): ResultSet = query(con)("select * from dbo.assessment")
}



case class Assessment(atype:String, duration:Int, lecture:String, description:String) extends Db.DbEntity[Assessment] {

  def toDb(c: Connection)(a: Assessment) : Int = 0

  def fromDb(rs: ResultSet): List[Assessment] = List()

  def insertSql: String = ""
}