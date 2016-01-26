import java.sql.{Connection, Date, ResultSet}

import scala.collection.mutable.ListBuffer


object Teacher extends Db.DbEntity[Teacher] {
  def toDb(c: Connection)(t: Teacher) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setString(1, t.id)
    pstmt.setString(2, t.title)
    pstmt.setString(3, t.firstname)
    pstmt.setString(4, t.lastname)
    pstmt.setDate(5, t.birthdate)
    pstmt.setString(6, t.gender)
    pstmt.setString(7, t.address)
    pstmt.setString(8, t.zip)
    pstmt.setString(9, t.phone)
    pstmt.setString(10, t.email)
    pstmt.setString(11, t.ttype)
    pstmt.executeUpdate()
  }

  def delFromDb(c: Connection)(t: Teacher) : Int = {
    val pstmt = c.prepareStatement(deleteSql)
    pstmt.setString(1, t.id)
    pstmt.executeUpdate()
  }

  def fromDb(rs: ResultSet): List[Teacher] = {
    val lb : ListBuffer[Teacher] = new ListBuffer[Teacher]()
    while (rs.next()) lb.append(Teacher(rs.getString("teacher_id"), rs.getString("title"), rs.getString("firstname"),
                                        rs.getString("lastname"), rs.getDate("birthdate"), rs.getString("gender"),
                                        rs.getString("address"), rs.getString("zip_code"), rs.getString("phone"),
                                        rs.getString("e_mail"), rs.getString("type")))
    lb.toList
  }

  def insertSql: String = "insert into dbo.teacher (teacher_id, title, firstname, lastname, birthdate, gender," +
                          "address, zip_code, phone, e_mail, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

  def deleteSql: String = "delete from dbo.teacher where teacher_id = '?'"

  def queryAll(con: Connection): ResultSet = query(con)("select * from dbo.teacher")
}



case class Teacher(id:String, title:String, firstname:String, lastname:String, birthdate:Date, gender:String,
                   address:String, zip:String, phone:String, email:String, ttype:String) extends Db.DbEntity[Teacher] {

  def toDb(c: Connection)(t: Teacher) : Int = 0

  def fromDb(rs: ResultSet): List[Teacher] = List()

  def delFromDb(c: Connection)(t: Teacher) : Int = 0

  def insertSql: String = ""
}