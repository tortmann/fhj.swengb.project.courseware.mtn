import javafx.application.Application
import javafx.fxml.{Initializable, FXMLLoader}
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import java.net.URL
import java.util.ResourceBundle
import javafx.beans.property.{SimpleIntegerProperty, SimpleStringProperty}
import javafx.beans.value.ObservableValue
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml._
import javafx.scene.control.{TableColumn, TableView}
import javafx.scene.{Parent, Scene}
import javafx.util.Callback
import scala.collection.JavaConversions
import scala.util.control.NonFatal

object TableViewStudent {
  def main(args: Array[String]) {
    Application.launch(classOf[TableViewStudentApp], args: _*)
  }

}
class TableViewStudentApp extends javafx.application.Application {

  val fxmlMain = "/fxml/TableViewStudent.fxml"
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
      stage.setTitle("Student Database")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(cssMain)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}

class MutableStudent {

  val idProperty: SimpleStringProperty = new SimpleStringProperty()
  val titleProperty: SimpleStringProperty = new SimpleStringProperty()
  val firstnameProperty: SimpleStringProperty = new SimpleStringProperty()
  val lastnameProperty: SimpleStringProperty = new SimpleStringProperty()
  val birthdateProperty: SimpleStringProperty = new SimpleStringProperty()
  val genderProperty: SimpleStringProperty = new SimpleStringProperty()
  val addressProperty: SimpleStringProperty = new SimpleStringProperty()
  val zipProperty: SimpleStringProperty = new SimpleStringProperty()
  val phoneProperty: SimpleStringProperty = new SimpleStringProperty()
  val emailProperty: SimpleStringProperty = new SimpleStringProperty()
  val groupProperty: SimpleStringProperty = new SimpleStringProperty()
  val statusProperty: SimpleIntegerProperty = new SimpleIntegerProperty()

  def setId(id: String) = idProperty.set(id)

  def setTitle(title: String) = titleProperty.set(title)

  def setFirstname(firstname: String) = firstnameProperty.set(firstname)

  def setLastname(lastname: String) = lastnameProperty.set(lastname)

  def setBirthdate(birthdate: String) = birthdateProperty.set(birthdate)

  def setGender(gender: String) = genderProperty.set(gender)

  def setAddress(address: String) = addressProperty.set(address)

  def setZip(zip: String) = zipProperty.set(zip)

  def setPhone(phone: String) = phoneProperty.set(phone)

  def setEmail(email: String) = emailProperty.set(email)

  def setGroup(group: String) = groupProperty.set(group)

  def setStatus(status: Int) = statusProperty.set(status)
}

object MutableStudent {

  def apply(s: Student): MutableStudent = {
    val ms = new MutableStudent
    ms.setId(s.id)
    ms.setTitle(s.title)
    ms.setFirstname(s.firstname)
    ms.setLastname(s.lastname)
    ms.setBirthdate(s.birthdate.toString)
    ms.setGender(s.gender)
    ms.setAddress(s.address)
    ms.setZip(s.zip)
    ms.setPhone(s.phone)
    ms.setEmail(s.email)
    ms.setGroup(s.group)
    ms.setStatus(s.status)
    ms
  }
}

object JfxUtilsStudent {

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

object DataSourceStudent {
  val con = Db.Con
  var data = Student.fromDb(Student.queryAll(con))
  con.close()

}

class TableViewStudentAppController extends Initializable {

  import JfxUtilsStudent._

  type StudentTC[T] = TableColumn[MutableStudent, T]

  @FXML var window:BorderPane = _
  @FXML var tableView: TableView[MutableStudent] = _

  @FXML var columnId: StudentTC[String] = _
  @FXML var columnTitle: StudentTC[String] = _
  @FXML var columnFirstName: StudentTC[String] = _
  @FXML var columnLastName: StudentTC[String] = _
  @FXML var columnBirthdate: StudentTC[String] = _
  @FXML var columnGender: StudentTC[String] = _
  @FXML var columnAddress: StudentTC[String] = _
  @FXML var columnZipcode: StudentTC[String] = _
  @FXML var columnPhone: StudentTC[String] = _
  @FXML var columnEmail: StudentTC[String] = _
  @FXML var columnGroup: StudentTC[String] = _
  @FXML var columnStatus: StudentTC[Int] = _

  val mutableStudents = mkObservableList(DataSourceStudent.data.map(MutableStudent(_)))

  def initTableViewColumn[T]: (StudentTC[T], (MutableStudent) => Any) => Unit =
    initTableViewColumnCellValueFactory[MutableStudent, T]

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    tableView.setItems(mutableStudents)

    initTableViewColumn[String](columnId, _.idProperty)
    initTableViewColumn[String](columnTitle, _.titleProperty)
    initTableViewColumn[String](columnFirstName, _.firstnameProperty)
    initTableViewColumn[String](columnLastName, _.lastnameProperty)
    initTableViewColumn[String](columnBirthdate, _.birthdateProperty)
    initTableViewColumn[String](columnGender, _.genderProperty)
    initTableViewColumn[String](columnAddress, _.addressProperty)
    initTableViewColumn[String](columnZipcode, _.zipProperty)
    initTableViewColumn[String](columnPhone, _.phoneProperty)
    initTableViewColumn[String](columnEmail, _.emailProperty)
    initTableViewColumn[String](columnGroup, _.groupProperty)
    initTableViewColumn[Int](columnStatus, _.statusProperty)
  }
  val cssMain = "/css/MainMenu.css"
  val fxmlCreateStudent = "/fxml/CreateStudent.fxml"
  val fxmlEditStudent = "/fxml/EditStudent.fxml"


  val loadCreateStudent = new FXMLLoader(getClass.getResource(fxmlCreateStudent))
  val loadEditStudent = new FXMLLoader(getClass.getResource(fxmlEditStudent))


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

  def Exit(): Unit = window.getScene.getWindow.hide()
  def Create(): Unit = {openWindow(loadCreateStudent, cssMain)}
  def Edit(): Unit = {openWindow(loadEditStudent, cssMain )}

}