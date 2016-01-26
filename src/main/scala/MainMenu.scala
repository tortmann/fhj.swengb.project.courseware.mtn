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

  val fxmlMain = "/fxml/MainMenu.fxml"
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
      stage.setTitle("Courseware")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(cssMain)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
}
class MainMenuAppController extends Initializable {

  override def initialize(location: URL, resources: ResourceBundle): Unit = {}

  val cssMain = "/css/MainMenu.css"
  val fxmlStudent = "/fxml/TableViewStudent.fxml"
  val fxmlLectureEvent = "/fxml/TableViewLectureEvent.fxml"

  val loadStudent = new FXMLLoader(getClass.getResource(fxmlStudent))
  val loadTeacher = new FXMLLoader(getClass.getResource(fxmlLectureEvent))

  def openWindow(fxmlLoader: FXMLLoader, css: String):Unit = {
    try {
      val stage = new Stage
      stage.setTitle("Courseware")
      fxmlLoader.load[Parent]()
      stage.setScene(new Scene(fxmlLoader.getRoot[Parent]))
      stage.getScene.getStylesheets.add(css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
  }

  def exit(): Unit = sys.exit()
  def subMenuStudent(): Unit = {openWindow(loadStudent, cssMain)}
  def subMenuTeacher(): Unit = {openWindow(loadTeacher, cssMain)}

}
//test