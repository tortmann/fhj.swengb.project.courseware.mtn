import java.sql.{Connection, Date, ResultSet}

/**
  * Created by ninas on 23.01.2016.
  */

object Student extends Db.DbEntity[Student] {
  def toDb(c: Connection)(s: Student) : Int = ???
  def fromDb(rs: ResultSet): List[Student] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

case class Student(studentId:String, title:String, firstname:String, lastname:String, birthdate:Date, gender:String,
                   address:String, zip:Int, phone:String, email:String, group:String, status:Int) extends Db.DbEntity[Student] {
  def toDb(c: Connection)(s: Student) : Int = ???
  def fromDb(rs: ResultSet): List[Student] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}
