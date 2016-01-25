import java.sql.{ResultSet, Connection}

import scala.collection.mutable.ListBuffer


object Classroom extends Db.DbEntity[Classroom] {
  def toDb(c: Connection)(cr: Classroom) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setString(1, cr.id)
    pstmt.setInt(2, cr.size)
    pstmt.setString(3, cr.ctype)
    pstmt.setString(4, cr.description)
    pstmt.executeUpdate()
  }

  def fromDb(rs: ResultSet): List[Classroom] = {
    val lb : ListBuffer[Classroom] = new ListBuffer[Classroom]()
    while (rs.next()) lb.append(Classroom(rs.getString("classroom_id"), rs.getInt("size"), rs.getString("type"),
                                          rs.getString("description")))
    lb.toList
  }

  def insertSql: String = "insert into dbo.classroom (classroom_id, size, type, description)" +
                          "VALUES (?, ?, ?, ?)"

  def queryAll(con: Connection): ResultSet = query(con)("select * from dbo.classroom")
}



case class Classroom(id:String, size:Int, ctype:String, description:String) extends Db.DbEntity[Classroom] {

  def toDb(c: Connection)(cr: Classroom) : Int = 0

  def fromDb(rs: ResultSet): List[Classroom] = List()

  def insertSql: String = ""
}