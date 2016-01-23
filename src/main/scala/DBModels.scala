import java.sql._
import javafx.scene.{Scene, Parent}
import scala.collection.mutable.ListBuffer
import scala.util.Try


object Teacher extends Db.DbEntity[Teacher] {
  def toDb(c: Connection)(t: Teacher) : Int = ???
  def fromDb(rs: ResultSet): List[Article] = ???
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





object AssessmentLogin extends Db.DbEntity[AssessmentLogin] {
  def toDb(c: Connection)(al: AssessmentLogin) : Int = ???
  def fromDb(rs: ResultSet): List[AssessmentLogin] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

// from, to -> Date, Time ???
case class AssessmentLogin(assessmentLoginId:Int, attempt:Int, description:String, assessmentDateId:String, student:String, mark:String) extends Db.DbEntity[AssessmentLogin] {
  def toDb(c: Connection)(al: AssessmentLogin) : Int = ???
  def fromDb(rs: ResultSet): List[AssessmentLogin] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}




object AssessmentDate extends Db.DbEntity[AssessmentDate] {
  def toDb(c: Connection)(ad: AssessmentDate) : Int = ???
  def fromDb(rs: ResultSet): List[AssessmentDate] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
  def queryAll(con: Connection): ResultSet = ???
}

// from, to -> Date, Time ???
case class AssessmentDate(assessmentDateId:String, description:String, abbr:Int, classroom:String) extends Db.DbEntity[AssessmentDate] {
  def toDb(c: Connection)(ad: AssessmentDate) : Int = ???
  def fromDb(rs: ResultSet): List[AssessmentDate] = ???
  def dropTableSql: String = ???
  def createTableSql: String = ???
  def insertSql: String = ???
}
