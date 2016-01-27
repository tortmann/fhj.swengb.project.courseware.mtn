import java.net.URL
import java.util.ResourceBundle
import javafx.application.Application
import javafx.beans.property.{SimpleIntegerProperty, SimpleStringProperty}
import javafx.beans.value.ObservableValue
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.{FXML, Initializable, FXMLLoader}
import javafx.scene.control.{TableView, TableColumn}
import javafx.scene.layout.BorderPane
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage
import javafx.util.Callback

import scala.collection.JavaConversions
import scala.util.control.NonFatal

object TableViewCommissionalExam {
  def main(args: Array[String]) {
    Application.launch(classOf[TableViewCommissionalExamApp], args: _*)
  }

}

class TableViewCommissionalExamApp extends javafx.application.Application {

  override def start(stage: Stage): Unit = {

    val fxmlMain = "/fxml/TableViewCommissionalExam.fxml"
    val cssMain = "/css/MainMenu.css"
    redir(stage, fxmlMain, cssMain)
  }

  def redir(stage:Stage, fxml: String, css:String): Unit = {
    try {
      stage.setTitle("Commissional Exam")
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


class MutableCommissionalExam {

  val pers1Property: SimpleStringProperty = new SimpleStringProperty()
  val pers2Property: SimpleStringProperty = new SimpleStringProperty()
  val pers3Property: SimpleStringProperty = new SimpleStringProperty()
  val lectureProperty: SimpleStringProperty = new SimpleStringProperty()
  val fromProperty: SimpleStringProperty = new SimpleStringProperty()
  val toProperty: SimpleStringProperty = new SimpleStringProperty()
  val detailsProperty: SimpleStringProperty = new SimpleStringProperty()
  val markProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
  val studentProperty: SimpleStringProperty = new SimpleStringProperty()

  def setPers1(pers1: String) = pers1Property.set(pers1)

  def setPers2(pers2: String) = pers2Property.set(pers2)

  def setPers3(pers3: String) = pers3Property.set(pers3)

  def setLecture(lecture: String) = lectureProperty.set(lecture)

  def setFrom(from: String) = fromProperty.set(from)

  def setTo(to: String) = toProperty.set(to)

  def setDetails(details: String) = detailsProperty.set(details)

  def setMark(mark: Int) = markProperty.set(mark)

  def setStudent(student: String) = studentProperty.set(student)
}


object MutableCommissionalExam {

  def apply(ce: CommissionalExam): MutableCommissionalExam = {
    val mce= new MutableCommissionalExam
    mce.setPers1(ce.pers1)
    mce.setPers2(ce.pers2)
    mce.setPers3(ce.pers3)
    mce.setLecture(ce.lecture)
    mce.setFrom(ce.from.toString)
    mce.setTo(ce.to.toString)
    mce.setDetails(ce.details)
    mce.setMark(ce.mark)
    mce.setStudent(ce.student)
    mce
  }
}

object JfxUtilsce {

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

object DataSourceCommissionalExam {

  val con = Db.Con
  var data = CommissionalExam.fromDb(CommissionalExam.queryAll(con))
  con.close()

}

class TableViewCommissionalExamAppController extends Initializable {

  import JfxUtilsce._

  type CommissionalExamTC[T] = TableColumn[MutableCommissionalExam, T]

  @FXML var window:BorderPane = _
  @FXML var tableView: TableView[MutableCommissionalExam] = _

  @FXML var columnPers1: CommissionalExamTC[String] = _
  @FXML var columnPers2: CommissionalExamTC[String] = _
  @FXML var columnPers3: CommissionalExamTC[String] = _
  @FXML var columnFrom: CommissionalExamTC[String] = _
  @FXML var columnTo: CommissionalExamTC[String] = _
  @FXML var columnDetails: CommissionalExamTC[String] = _
  @FXML var columnMark: CommissionalExamTC[String] = _
  @FXML var columnStudent: CommissionalExamTC[String] = _



  val mutableCommissionalExams = mkObservableList(DataSourceCommissionalExam.data.map(MutableCommissionalExam(_)))

  def initTableViewColumn[T]: (CommissionalExamTC[T], (MutableCommissionalExam) => Any) => Unit =
    initTableViewColumnCellValueFactory[MutableCommissionalExam, T]

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    tableView.setItems(mutableCommissionalExams)

    initTableViewColumn[String](columnPers1, _.pers1Property)
    initTableViewColumn[String](columnPers2, _.pers2Property)
    initTableViewColumn[String](columnPers3, _.pers3Property)
    initTableViewColumn[String](columnFrom, _.fromProperty)
    initTableViewColumn[String](columnTo, _.toProperty)
    initTableViewColumn[String](columnDetails, _.detailsProperty)
    initTableViewColumn[String](columnMark, _.markProperty)
    initTableViewColumn[String](columnStudent, _.studentProperty)

  }

  def Exit(): Unit = {
    window.getScene.getWindow.hide()
    val tm = new MainMenuApp
    val stage = new Stage
    val fxml = "/fxml/MainMenu.fxml"
    val cssMain = "/css/MainMenu.css"
    tm.redir(stage, fxml, cssMain)
  }

}
