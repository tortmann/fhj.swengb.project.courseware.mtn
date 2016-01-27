import java.beans.EventHandler
import javafx.application.Application
import javafx.fxml.{Initializable, FXMLLoader}
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import java.net.URL
import java.util.ResourceBundle
import javafx.beans.property.{SimpleDoubleProperty, SimpleIntegerProperty, SimpleStringProperty}
import javafx.beans.value.ObservableValue
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml._
import javafx.scene.control.{Label, TableColumn, TableView}
import javafx.scene.{Parent, Scene}
import javafx.util.Callback
import javafx.scene.input.MouseEvent
import scala.collection.JavaConversions
import scala.util.control.NonFatal


object TableViewClassroom {
  def main(args: Array[String]) {
    Application.launch(classOf[TableViewClassroomApp], args: _*)
  }
}

class TableViewClassroomApp extends javafx.application.Application {

  val fxmlMain = "/fxml/TableViewClassroom.fxml"
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
      stage.setTitle("Classroom Database")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(cssMain)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}


class MutableClassroom {

  val idProperty: SimpleStringProperty = new SimpleStringProperty()
  val sizeProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val ctypeProperty: SimpleStringProperty = new SimpleStringProperty()
  val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()

  def setId(id: String) = idProperty.set(id)

  def setSize(size: Int) = sizeProperty.set(size)

  def setCtype(ctype: String) = ctypeProperty.set(ctype)

  def setDescription(description: String) = descriptionProperty.set(description)
}


object MutableClassroom {

  def apply(c: Classroom): MutableClassroom = {
    val mc = new MutableClassroom
    mc.setId(c.id)
    mc.setSize(c.size)
    mc.setCtype(c.ctype)
    mc.setDescription(c.description)
    mc
  }
}

object JfxUtilsC {

  type TCDF[S, T] = TableColumn.CellDataFeatures[S, T]

  import JavaConversions._

  def mkObservableList[T](collection: Iterable[T]): ObservableList[T] = {
    FXCollections.observableList(new java.util.ArrayList[T](collection))
  }

  private def mkCellValueFactory[S, T](fn: TCDF[S, T] => ObservableValue[T]): Callback[TCDF[S, T], ObservableValue[T]] = {
    new Callback[TCDF[S, T], ObservableValue[T]] {
      def call(cdf: TCDF[S, T]): ObservableValue[T] = fn(cdf)
    }
  }

  def initTableViewColumnCellValueFactory[S, T](tc: TableColumn[S, T], f: S => Any): Unit = {
    tc.setCellValueFactory(mkCellValueFactory(cdf => f(cdf.getValue).asInstanceOf[ObservableValue[T]]))
  }

}

object DataSourceClassroom {
  val con = Db.Con
  var data = Classroom.fromDb(Classroom.queryAll(con))
  con.close()
}

class TableViewClassroomAppController extends Initializable {

  import JfxUtilsC._

  type ClassroomTC[T] = TableColumn[MutableClassroom, T]

  @FXML var window:BorderPane = _
  @FXML var tableView: TableView[MutableClassroom] = _
  @FXML var errorLabel: Label = _


  @FXML var columnId: ClassroomTC[String] = _
  @FXML var columnSize: ClassroomTC[Int] = _
  @FXML var columnCtype: ClassroomTC[String] = _
  @FXML var columnDescription: ClassroomTC[String] = _


  val mutableClassrooms = mkObservableList(DataSourceClassroom.data.map(MutableClassroom(_)))

  def initTableViewColumn[T]: (ClassroomTC[T], (MutableClassroom) => Any) => Unit =
    initTableViewColumnCellValueFactory[MutableClassroom, T]

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    tableView.setItems(mutableClassrooms)

    initTableViewColumn[String](columnId, _.idProperty)
    initTableViewColumn[Int](columnSize, _.sizeProperty)
    initTableViewColumn[String](columnCtype, _.ctypeProperty)
    initTableViewColumn[String](columnDescription, _.descriptionProperty)

  }

  def Exit(): Unit = window.getScene.getWindow.hide()
}