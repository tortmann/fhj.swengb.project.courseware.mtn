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
  val fxmlDbEntryMask = "/fxml/DbEntryMask.fxml"
  val fxmlSubMenuStudent = "/fxml/SubMenuStudent.fxml"
  val fxmlSubMenuTeacher = "/fxml/SubMenuLectureEvent.fxml"

  val loadCreateStudent = new FXMLLoader(getClass.getResource(fxmlDbEntryMask))
  val loadSubMenuStudent = new FXMLLoader(getClass.getResource(fxmlSubMenuStudent))
  val loadSubMenuTeacher = new FXMLLoader(getClass.getResource(fxmlSubMenuTeacher))

  def openWindow(fxmlLoader: FXMLLoader, css: String):Unit = {
    try {
      val stage = new Stage
      stage.setTitle("DbEntryMask")
      fxmlLoader.load[Parent]()
      val scene = new Scene(fxmlLoader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
  }

  def exit(): Unit = sys.exit()

  def subMenuStudent(): Unit = {openWindow(loadSubMenuStudent, cssMain)}
  def subMenuTeacher(): Unit = {openWindow(loadSubMenuTeacher, cssMain)}

}

