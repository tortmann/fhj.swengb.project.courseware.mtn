import java.net.URL
import java.util.ResourceBundle
import javafx.application.Application
import javafx.fxml.{Initializable, FXMLLoader}
import javafx.scene.{Scene, Parent}
import javafx.stage.Stage

import scala.util.control.NonFatal

object MainMenu {
  def main(args: Array[String]) {
    Application.launch(classOf[MainMenuApp], args: _*)
  }

}
class MainMenuApp extends javafx.application.Application {

  override def start(stage: Stage): Unit = {

    val fxmlMain = "/fxml/MainMenu.fxml"
    val cssMain = "/css/MainMenu.css"
    redir(stage, fxmlMain, cssMain)
  }

  def redir(stage:Stage, fxml: String, css:String): Unit = {
    try {
      stage.setTitle("Courseware")
      var loader = new FXMLLoader(getClass.getResource(fxml))
      loader.setRoot(null)
      loader.load[Parent]()

      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
  }
}
class MainMenuAppController extends Initializable {

  override def initialize(location: URL, resources: ResourceBundle): Unit = {}

  val cssMain = "/css/MainMenu.css"
  val fxmlStudent = "/fxml/TableViewStudent.fxml"
  val fxmlTeacher = "/fxml/TableViewTeacher.fxml"
  val fxmlLectureEvent = "/fxml/TableViewLectureEvent.fxml"
  val fxmlCommissionalExam = "/fxml/TableViewCommissionalExam.fxml"
  val fxmlClassroom = "/fxml/TableViewClassroom.fxml"

  def openWindow(fxml: String, css:String):Unit = {
    try {
      val stage = new Stage
      stage.setTitle("Courseware")
      var loader = new FXMLLoader(getClass.getResource(fxml))
      loader.load[Parent]()
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
  }

  def exit(): Unit = sys.exit()
  def subMenuStudent(): Unit = {openWindow(fxmlStudent, cssMain)}
  def subMenuTeacher(): Unit = {openWindow(fxmlTeacher, cssMain)}
  def subMenuLectureEvent(): Unit = {openWindow(fxmlLectureEvent, cssMain)}
  def subMenuCommissionalExam(): Unit = {openWindow(fxmlCommissionalExam, cssMain)}
  def subMenuClassroom(): Unit = {openWindow(fxmlClassroom, cssMain)}

}

