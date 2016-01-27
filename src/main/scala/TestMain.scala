import java.io.IOException
import java.lang.RuntimeException
import java.net.URL
import java.util.ResourceBundle
import javafx.application.Application
import javafx.fxml.{Initializable, FXMLLoader}
import javafx.scene.{Scene, Parent}
import javafx.stage.Stage

import scala.util.control.NonFatal

object TestMain {
  def main(args: Array[String]) {
    Application.launch(classOf[TestMainApp], args: _*)
  }

}
class TestMainApp extends javafx.application.Application {


  override def start(stage: Stage): Unit = {

    val fxml = "/fxml/MainMenu.fxml"
    val cssMain = "/css/MainMenu.css"

    redir(stage, fxml, cssMain)
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
class TestMainAppController extends Initializable {

  override def initialize(location: URL, resources: ResourceBundle): Unit = {}

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


  // wenn Button gedrückt
  val cssMain = "/css/MainMenu.css"
  val fxml = "/fxml/TableViewClassroom.fxml"

  def subMenuClassroom(): Unit = {
    openWindow(fxml, cssMain)
  }
}

