import java.sql.{Connection, Date, ResultSet}
import java.net.URL
import java.util.ResourceBundle
import javafx.application.Application
import javafx.fxml.{Initializable, FXMLLoader}
import javafx.scene.{Scene, Parent}
import javafx.stage.Stage

import scala.util.control.NonFatal

/**
  * Created by Thomas on 07.12.2015.
  */

object CreateStudent {
  def main(args: Array[String]) {
    Application.launch(classOf[CreateStudentApp], args: _*)
  }

}
class CreateStudentApp extends javafx.application.Application {

  val fxmlMain = "/fxml/CreateStudent.fxml"
  val cssMain = "/css/MainMenu.css"

  val loader = new FXMLLoader(getClass.getResource(fxmlMain))

  def setSkin(stage: Stage, fxml: String, css: String): Boolean = {
    val scene = new Scene(loader.load[Parent]())
    stage.setScene(scene)
    stage.getScene.getStylesheets.clear()
    stage.getScene.getStylesheets.add(css)
  }

  override def start(stage: Stage): Unit =
    try {
      stage.setTitle("Create Student")
      loader.load[Parent]()
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(cssMain)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}
class CreateStudentAppController extends Initializable {

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
  }
}

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
