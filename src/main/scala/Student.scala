import java.sql.{Connection, Date, ResultSet}



object Student extends Db.DbEntity[Student] {
  def toDb(c: Connection)(s: Student) : Int = ???

  def fromDb(rs: ResultSet): List[Student] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???

  def queryAll(con: Connection): ResultSet = ???
}



case class Student(id:String, title:String, firstname:String, lastname:String, birthdate:Date, gender:String, address:String,
                   zip:String, phone:String, email:String, group:String, status:Int) extends Db.DbEntity[Student] {

  def toDb(c: Connection)(s: Student) : Int = ???

  def fromDb(rs: ResultSet): List[Student] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???
}