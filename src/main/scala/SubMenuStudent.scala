import javafx.application.Application
import javafx.fxml.{Initializable, FXMLLoader}
import javafx.stage.Stage
import java.net.URL
import java.util.ResourceBundle
import javafx.beans.property.{SimpleDoubleProperty, SimpleIntegerProperty, SimpleStringProperty}
import javafx.beans.value.ObservableValue
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml._
import javafx.scene.control.{TableColumn, TableView}
import javafx.scene.{Parent, Scene}
import javafx.util.Callback
import scala.collection.JavaConversions
import scala.util.Random
import scala.util.control.NonFatal

object SubMenuStudent {
  def main(args: Array[String]) {
    Application.launch(classOf[SubMenuStudentApp], args: _*)
  }

}
class SubMenuStudentApp extends javafx.application.Application {

  val fxmlMain = "/fxml/SubMenuStudent.fxml"
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
      stage.setTitle("Student Section")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(cssMain)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}

class SubMenuStudentAppController extends Initializable {

  override def initialize(location: URL, resources: ResourceBundle): Unit = {}

  val cssMain = "/css/MainMenu.css"
  val fxmlDbEntryMask = "/fxml/DbEntryMask.fxml"
  val fxmlTableViewStudent = "/fxml/TableViewStudent.fxml"

  val loadCreateStudent = new FXMLLoader(getClass.getResource(fxmlDbEntryMask))
  val loadTableViewStudent = new FXMLLoader(getClass.getResource(fxmlTableViewStudent))


  def openWindow(fxmlLoader: FXMLLoader, css: String):Unit = {
    try {
      val stage = new Stage
      stage.setTitle("Student")
      fxmlLoader.load[Parent]()
      val scene = new Scene(fxmlLoader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
  }


  def tableViewStudent(): Unit = {openWindow(loadTableViewStudent, cssMain)

  }

}

