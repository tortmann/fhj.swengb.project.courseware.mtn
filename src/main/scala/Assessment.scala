import java.sql.{ResultSet, Connection}



object Assessment extends Db.DbEntity[Assessment] {
  def toDb(c: Connection)(a: Assessment) : Int = ???

  def fromDb(rs: ResultSet): List[Assessment] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???

  def queryAll(con: Connection): ResultSet = ???
}



case class Assessment(id:Int, atype:String, duration:Int, lecture:String, description:String) extends Db.DbEntity[Assessment] {

  def toDb(c: Connection)(a: Assessment) : Int = ???

  def fromDb(rs: ResultSet): List[Assessment] = ???

  def dropTableSql: String = ???

  def createTableSql: String = ???

  def insertSql: String = ???
}