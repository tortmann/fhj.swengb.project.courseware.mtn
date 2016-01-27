import java.sql.{Connection, Date, ResultSet}

import scala.collection.mutable.ListBuffer


object Teacher extends Db.DbEntity[Teacher] {
  def toDb(c: Connection)(t: Teacher) : Int = {
    val pstmt = c.prepareStatement(insertSql)
    pstmt.setString(1, t.id)
    pstmt.setString(2, t.title)
    pstmt.setString(3, t.firstname)
    pstmt.setString(4, t.lastname)
    pstmt.setString(5, t.birthdate)
    pstmt.setString(6, t.gender)
    pstmt.setString(7, t.address)
    pstmt.setString(8, t.zip)
    pstmt.setString(9, t.phone)
    pstmt.setString(10, t.email)
    pstmt.setString(11, t.ttype)
    pstmt.executeUpdate()
  }

  def delFromDb(c: Connection)(prop: String) : Int = {
    val pstmt = c.prepareStatement(deleteSql + prop + "'")
    pstmt.executeUpdate()
  }


  def editFromDb(c: Connection)(t: Teacher, id:String) : Int = {
    val sql = "update dbo.teacher set teacher_id = '" + t.id + "' set title = '" + t.title + "' set firstname = '" + t.firstname +
              "' set lastname = '" + t.lastname + "' set birthdate = '" + t.birthdate + "' set gender = '"  + t.gender
              "' set address = '" + t.address + "' set zip_code = '" + t.zip + "' set phone = '" + t.phone +
              "' set e_mail = '" + t.email + "' set type = '" + t.ttype + "'" + "where teacher_id = '" + id +"'"
    val pstmt = c.prepareStatement(sql)
    /*
    pstmt.setString(1, t.id)
    pstmt.setString(2, t.title)
    pstmt.setString(3, t.firstname)
    pstmt.setString(4, t.lastname)
    pstmt.setString(5, t.birthdate)
    pstmt.setString(6, t.gender)
    pstmt.setString(7, t.address)
    pstmt.setString(8, t.zip)
    pstmt.setString(9, t.phone)
    pstmt.setString(10, t.email)
    pstmt.setString(11, t.ttype)
    pstmt.setString(12, id)
    */
    pstmt.executeUpdate()
  }

  def fromDb(rs: ResultSet): List[Teacher] = {
    val lb : ListBuffer[Teacher] = new ListBuffer[Teacher]()
    while (rs.next()) lb.append(Teacher(rs.getString("teacher_id"), rs.getString("title"), rs.getString("firstname"),
                                        rs.getString("lastname"), rs.getString("birthdate"), rs.getString("gender"),
                                        rs.getString("address"), rs.getString("zip_code"), rs.getString("phone"),
                                        rs.getString("e_mail"), rs.getString("type")))
    lb.toList
  }

  def insertSql: String = "insert into dbo.teacher (teacher_id, title, firstname, lastname, birthdate, gender," +
                          "address, zip_code, phone, e_mail, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"

  def deleteSql: String = "delete from dbo.teacher where teacher_id = '"

  def updateSql: String =
    "update dbo.teacher " +
    "set teacher_id = '?' set title = '?' set firstname = '?' set lastname = '?' set birthdate = '?' set gender = '?' set address = '?' " +
    "set zip_code = '?' set phone = '?' set e_mail = '?' set type = '?'" +
    "where teacher_id = '?'"

  def queryAll(con: Connection): ResultSet = query(con)("select * from dbo.teacher")
}



case class Teacher(id:String, title:String, firstname:String, lastname:String, birthdate:String, gender:String,
                   address:String, zip:String, phone:String, email:String, ttype:String) extends Db.DbEntity[Teacher] {

  def toDb(c: Connection)(t: Teacher) : Int = 0

  def fromDb(rs: ResultSet): List[Teacher] = List()

  def delFromDb(c: Connection)(t: Teacher) : Int = 0

  def insertSql: String = ""
}