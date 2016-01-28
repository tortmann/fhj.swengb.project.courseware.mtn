import java.beans.EventHandler
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Paths, Files}
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

  override def start(stage: Stage): Unit = {

    val fxmlMain = "/fxml/TableViewLectureEvent.fxml"
    val cssMain = "/css/MainMenu.css"
    redir(stage, fxmlMain, cssMain)
  }

  def redir(stage:Stage, fxml: String, css:String): Unit = {
    try {
      stage.setTitle("Classroom")
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

object JfxUtilsLectureEvent {

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

    var lectureevent = new MutableLectureEvent
}

class TableViewLectureEventAppController extends Initializable {

  import JfxUtilsLectureEvent._

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

  def Exit(): Unit = {
    window.getScene.getWindow.hide()
    val mm = new MainMenuApp
    val stage = new Stage
    val fxml = "/fxml/MainMenu.fxml"
    val cssMain = "/css/MainMenu.css"
    mm.redir(stage, fxml, cssMain)
  }

  def Create(): Unit = {openWindow(fxmlCreateLectureEvent, cssMain)}
  def Edit(): Unit = {
    DataSourceLectureEvent.lectureevent = tableView.getSelectionModel().getSelectedItem()
    openWindow(fxmlEditLectureEvent, cssMain )
  }


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


  def ButtonReport(): Unit = {

    /*
    val target = new File("/Users/Max/IMA14/3.Semester/SWENGB/workspace/fhj.swengb.project.courseware.mtn/src/main/resources/html/teacherBootReport.html")
    def main() {
      val html = Source.fromInputStream(getClass.getResourceAsStream("teacherReport.html")).mkString
      writeToFile(target, html)
      println("Created " + target.getAbsolutePath)
    }
    */

    def writeToFile(file: File, content: String): File = {
      Files.write(Paths.get(file.toURI), content.getBytes(StandardCharsets.UTF_8)).toFile
    }

    def generateTable(data: List[LectureEvent]) = {

      val head = "<!DOCTYPE html><html lang=\"en\"><head>\n"
      val meta = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
      val link = "<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">"
      val script = "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\"></script>\n<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\"></script>\n"
      val begin = "<body>\n<div class=\"container\">\n<table class=\"table\">\n"
      val header = "<thead><tr>\n<th>id</th>\n<th>classroom</th>\n<th>from</th>\n<th>to</th>\n</tr>\n</thead><tbody>"
      val middle = data.map(n => <tr><td>{n.id}</td><td>{n.classroom}</td><td>{n.from}</td><td>{n.to}</td></tr>).mkString(",")
      val end = "</tbody></table></div></body></html>"


      val html = head + meta + link + script + begin + header + middle + end
      val target = new File("/Users/Max/IMA14/3.Semester/SWENGB/workspace/fhj.swengb.project.courseware.mtn/src/main/resources/html/lectureEventReport.html")
      writeToFile(target, html)
      errorLabel.setText("Copied to" + target.getAbsolutePath)
    }

    val con = Db.Con
    try {

      val lectureevent = LectureEvent.fromDb(LectureEvent.queryAll(con))
      con.close()

      generateTable(lectureevent)
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
  @FXML var errorLabel:Label = _


  override def initialize(location: URL, resources: ResourceBundle): Unit = {

  }

  def Exit(): Unit = {
    window.getScene.getWindow.hide()
    val tvlea = new TableViewLectureEventApp
    val stage = new Stage
    val fxmlMain = "/fxml/TableViewLectureEvent.fxml"
    val cssMain = "/css/MainMenu.css"
    tvlea.redir(stage, fxmlMain, cssMain)
  }

  def ButtonCreated(): Unit = {
    val con = Db.Con
    try {
      val le = LectureEvent(id.getText(), from.getText(), to.getText(), description.getText(), lecture.getText(),
        group.getText(), classroom.getText())

      LectureEvent.toDb(con)(le)
      con.close()
      window.getScene.getWindow.hide()

      //val tvlea = new TableViewLectureEventApp
      //val stage = new Stage
      //val fxmlMain = "/fxml/TableViewLectureEvent.fxml"
      //val cssMain = "/css/MainMenu.css"
      //tvlea.redir(stage, fxmlMain, cssMain)
    }
    catch {
      case e: Exception => errorLabel.setText("Could not be created!")
    }
  }
}








class EditLectureEventAppController extends Initializable {

  @FXML var window:BorderPane = _

  @FXML var id:TextField = _
  @FXML var from:TextField = _
  @FXML var to:TextField = _
  @FXML var description:TextField = _
  @FXML var lecture:TextField = _
  @FXML var group:TextField = _
  @FXML var classroom:TextField = _
  @FXML var errorLabel:Label = _

  val lectureevent: MutableLectureEvent = DataSourceLectureEvent.lectureevent

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    id.setText(lectureevent.idProperty.get())
    from.setText(lectureevent.fromProperty.get())
    to.setText(lectureevent.toProperty.get())
    description.setText(lectureevent.descriptionProperty.get())
    lecture.setText(lectureevent.lectureProperty.get())
    group.setText(lectureevent.groupProperty.get())
    classroom.setText(lectureevent.classroomProperty.get())
  }

  def Exit(): Unit = {
    window.getScene.getWindow.hide()
    val tvlea = new TableViewLectureEventApp
    val stage = new Stage
    val fxmlMain = "/fxml/TableViewLectureEvent.fxml"
    val cssMain = "/css/MainMenu.css"
    tvlea.redir(stage, fxmlMain, cssMain)
  }

  def ButtonEdited(): Unit = {
    try {
      if (lectureevent != null) {
        val con = Db.Con

        val le = LectureEvent(id.getText(), from.getText(), to.getText(), description.getText(), lecture.getText(),
          group.getText(), classroom.getText())

        LectureEvent.editFromDb(con)(le, lectureevent.idProperty.get())
        con.close()
        window.getScene.getWindow.hide()

        val tvlea = new TableViewLectureEventApp
        val stage = new Stage
        val fxmlMain = "/fxml/TableViewLectureEvent.fxml"
        val cssMain = "/css/MainMenu.css"
        tvlea.redir(stage, fxmlMain, cssMain)
      }
    }
     catch {
        case e: Exception => errorLabel.setText("Could not be edited!")
    }
  }
}