import java.sql.{Connection, Date, ResultSet}



object Teacher extends Db.DbEntity[Teacher] {
  def toDb(c: Connection)(t: Teacher) : Int = ???

  def fromDb(rs: ResultSet): List[Teacher] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???

  def queryAll(con: Connection): ResultSet = ???
}



case class Teacher(id:String, title:String, firstname:String, lastname:String, birthdate:Date, gender:String, address:String,
                   zip:String, phone:String, email:String, ttype:String) extends Db.DbEntity[Teacher] {

  def toDb(c: Connection)(t: Teacher) : Int = ???

  def fromDb(rs: ResultSet): List[Teacher] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???
}