/**
  * Created by Thomas on 07.12.2015.
  */
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
object SubMenuAdmin {
  def main(args: Array[String]) {
    Application.launch(classOf[SubMenuAdminApp], args: _*)
  }

}
class SubMenuAdminApp extends javafx.application.Application {

  val fxmlMain = "/fxml/SubMenuAdmin.fxml"
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
      stage.setTitle("Administrative Section")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(cssMain)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}
class SubMenuAdminAppController extends Initializable {

  override def initialize(location: URL, resources: ResourceBundle): Unit = {
  }
}

