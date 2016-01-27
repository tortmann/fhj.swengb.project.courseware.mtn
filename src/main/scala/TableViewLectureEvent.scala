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
import javafx.scene.control.{TextField, Label, TableColumn, TableView}
import javafx.scene.{Parent, Scene}
import javafx.util.Callback
import javafx.scene.input.MouseEvent

import scala.collection.JavaConversions
import scala.util.control.NonFatal

object TableViewLectureEvent {
  def main(args: Array[String]) {
    Application.launch(classOf[TableViewLectureEventApp], args: _*)
  }

}

class TableViewLectureEventApp extends javafx.application.Application {

  val fxmlMain = "/fxml/TableViewLectureEvent.fxml"
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
        stage.setTitle("LectureEvent Database")
        loader.load[Parent]() // side effect
        val scene = new Scene(loader.getRoot[Parent])
        stage.setScene(scene)
        stage.getScene.getStylesheets.add(cssMain)
        stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}


class MutableLectureEvent {

  val idProperty: SimpleStringProperty = new SimpleStringProperty()
  val fromProperty: SimpleStringProperty = new SimpleStringProperty()
  val toProperty: SimpleStringProperty = new SimpleStringProperty()
  val descriptionProperty: SimpleStringProperty = new SimpleStringProperty()
  val lectureProperty: SimpleStringProperty = new SimpleStringProperty()
  val groupProperty: SimpleStringProperty = new SimpleStringProperty()
  val classroomProperty: SimpleStringProperty = new SimpleStringProperty()

  def setId(id: String) = idProperty.set(id)

  def setFrom(from: String) = fromProperty.set(from)

  def setTo(to: String) = toProperty.set(to)

  def setDescription(description: String) = descriptionProperty.set(description)

  def setLecture(lecture: String) = lectureProperty.set(lecture)

  def setGroup(group: String) = groupProperty.set(group)

  def setClassroom(classroom: String) = classroomProperty.set(classroom)
}


object MutableLectureEvent {

  def apply(le: LectureEvent): MutableLectureEvent = {
    val mle = new MutableLectureEvent
    mle.setId(le.id)
    mle.setFrom(le.from)
    mle.setTo(le.to)
    mle.setDescription(le.description)
    mle.setLecture(le.lecture)
    mle.setGroup(le.group)
    mle.setClassroom(le.classroom)
    mle
  }
}

object JfxUtilsle {

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

object DataSourceLectureEvent {
    val con = Db.Con
    var data = LectureEvent.fromDb(LectureEvent.queryAll(con))
    con.close()
}

class TableViewLectureEventAppController extends Initializable {

  import JfxUtilsle._

  type LectureEventTC[T] = TableColumn[MutableLectureEvent, T]

  @FXML var window:BorderPane = _
  @FXML var tableView: TableView[MutableLectureEvent] = _
  @FXML var errorLabel: Label = _


  @FXML var columnId: LectureEventTC[String] = _
  @FXML var columnFrom: LectureEventTC[String] = _
  @FXML var columnTo: LectureEventTC[String] = _
  @FXML var columnDescription: LectureEventTC[String] = _
  @FXML var columnLecture: LectureEventTC[String] = _
  @FXML var columnGroup: LectureEventTC[String] = _
  @FXML var columnClassroom: LectureEventTC[String] = _


  val mutableLectureEvents = mkObservableList(DataSourceLectureEvent.data.map(MutableLectureEvent(_)))

  def initTableViewColumn[T]: (LectureEventTC[T], (MutableLectureEvent) => Any) => Unit =
    initTableViewColumnCellValueFactory[MutableLectureEvent, T]

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    tableView.setItems(mutableLectureEvents)

    initTableViewColumn[String](columnId, _.idProperty)
    initTableViewColumn[String](columnFrom, _.fromProperty)
    initTableViewColumn[String](columnTo, _.toProperty)
    initTableViewColumn[String](columnDescription, _.descriptionProperty)
    initTableViewColumn[String](columnLecture, _.lectureProperty)
    initTableViewColumn[String](columnGroup, _.groupProperty)
    initTableViewColumn[String](columnClassroom, _.classroomProperty)

  }
  val cssMain = "/css/MainMenu.css"
  val fxmlCreateLectureEvent = "/fxml/CreateLectureEvent.fxml"
  val fxmlEditLectureEvent = "/fxml/EditLectureEvent.fxml"


  val loadCreateLectureEvent = new FXMLLoader(getClass.getResource(fxmlCreateLectureEvent))
  val loadEditLectureEvent = new FXMLLoader(getClass.getResource(fxmlEditLectureEvent))


  def openWindow(fxmlLoader: FXMLLoader, css: String):Unit = {
    try {
      val stage = new Stage
      stage.setTitle("Lecture Event")
      fxmlLoader.load[Parent]()
      val scene = new Scene(fxmlLoader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
  }

  def Exit(): Unit = window.getScene.getWindow.hide()
  def Create(): Unit = {openWindow(loadCreateLectureEvent, cssMain)}
  def Edit(): Unit = {openWindow(loadEditLectureEvent, cssMain )}


  def ButtonClicked(): Unit = {
    val le: MutableLectureEvent = tableView.getSelectionModel().getSelectedItem();
    val con = Db.Con

    try {
      if(le != null) {
        errorLabel.setText("")
        LectureEvent.delFromDb(con)(le.idProperty.get())
        con.close()
        mutableLectureEvents.remove(le)
        tableView.refresh()
      }
    }
    catch {
      case e: Exception => errorLabel.setText("Not deleted due to primary key constraint!")
    }
  }
}















class CreateLectureEventAppController extends Initializable {

  @FXML var window:BorderPane = _

  @FXML var id:TextField = _
  @FXML var from:TextField = _
  @FXML var to:TextField = _
  @FXML var description:TextField = _
  @FXML var lecture:TextField = _
  @FXML var group:TextField = _
  @FXML var classroom:TextField = _
  @FXML var errorlabel:Label = _


  override def initialize(location: URL, resources: ResourceBundle): Unit = {

  }

  def Exit(): Unit = window.getScene.getWindow.hide()

  def ButtonCreated(): Unit = {
    val con = Db.Con
    try {
      val le = LectureEvent(id.getText(), from.getText(), to.getText(), description.getText(), lecture.getText(),
        group.getText(), classroom.getText())

      LectureEvent.toDb(con)(le)
      con.close()
      window.getScene.getWindow.hide()

      openWindow(loadLectureEvent, cssMain)
    }
    catch {
      case e: Exception => errorLabel.setText("Lecture Event could not be created!")
    }
  }


  val fxmlLectureEvent = "/fxml/TableViewLectureEvent.fxml"
  val cssMain = "/css/MainMenu.css"
  val loadLectureEvent = new FXMLLoader(getClass.getResource(fxmlLectureEvent))

  def openWindow(fxmlLoader: FXMLLoader, css: String):Unit = {
    try {
      val stage = new Stage
      stage.setTitle("Lecture Event")
      fxmlLoader.load[Parent]()
      val scene = new Scene(fxmlLoader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(css)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }
  }
}