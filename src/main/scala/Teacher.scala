import java.sql.{Connection, Date, ResultSet}

/**
  * Created by ninas on 23.01.2016.
  */
object Teacher extends Db.DbEntity[Teacher] {
  def toDb(c: Connection)(t: Teacher) : Int = ???
  def fromDb(rs: ResultSet): List[Teacher] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

case class Teacher(teacherId:String, title:String, firstname:String, lastname:String, birthdate:Date, gender:String,
                   address:String, zip:Int, phone:String, email:String, ttype:String) extends Db.DbEntity[Teacher] {
  def toDb(c: Connection)(t: Teacher) : Int = ???
  def fromDb(rs: ResultSet): List[Teacher] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}