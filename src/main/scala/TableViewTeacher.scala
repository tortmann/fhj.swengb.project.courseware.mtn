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
import scala.util.control.NonFatal

object TableViewTeacher {
  def main(args: Array[String]) {
    Application.launch(classOf[TableViewLectureEventApp], args: _*)
  }

}

class TableViewTeacherApp extends javafx.application.Application {

  val fxmlMain = "/fxml/TableViewTeacher.fxml"
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
      stage.setTitle("Teacher Database")
      loader.load[Parent]() // side effect
      val scene = new Scene(loader.getRoot[Parent])
      stage.setScene(scene)
      stage.getScene.getStylesheets.add(cssMain)
      stage.show()
    } catch {
      case NonFatal(e) => e.printStackTrace()
    }

}

class MutableTeacher {

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
  val ttypeProperty: SimpleStringProperty = new SimpleStringProperty()

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

  def setTtype(ttype: String) = ttypeProperty.set(ttype)
}

object MutableTeacher {

  def apply(t: Teacher): MutableTeacher = {
    val mt = new MutableTeacher
    mt.setId(t.id)
    mt.setTitle(t.title)
    mt.setFirstname(t.firstname)
    mt.setLastname(t.lastname)
    mt.setBirthdate(t.birthdate.toString)
    mt.setGender(t.gender)
    mt.setAddress(t.address)
    mt.setZip(t.zip)
    mt.setPhone(t.phone)
    mt.setEmail(t.email)
    mt.setTtype(t.ttype)
    mt
  }
}

object JfxUtilsTeacher {

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

object DataSourceTeacher {

  val con = Db.Con
  var data = Teacher.fromDb(Teacher.queryAll(con))
  con.close()

}

class TableViewTeacherAppController extends Initializable {

  import JfxUtilsTeacher._

  type TeacherTC[T] = TableColumn[MutableTeacher, T]

  @FXML var tableView: TableView[MutableTeacher] = _

  @FXML var columnId: TeacherTC[String] = _
  @FXML var columnTitle: TeacherTC[String] = _
  @FXML var columnFirstName: TeacherTC[String] = _
  @FXML var columnLastName: TeacherTC[String] = _
  @FXML var columnBirthdate: TeacherTC[String] = _
  @FXML var columnGender: TeacherTC[String] = _
  @FXML var columnAddress: TeacherTC[String] = _
  @FXML var columnZipcode: TeacherTC[String] = _
  @FXML var columnPhone: TeacherTC[String] = _
  @FXML var columnEmail: TeacherTC[String] = _
  @FXML var columnTtype: TeacherTC[String] = _

  val mutableTeachers = mkObservableList(DataSourceTeacher.data.map(MutableTeacher(_)))

  def initTableViewColumn[T]: (TeacherTC[T], (MutableTeacher) => Any) => Unit =
    initTableViewColumnCellValueFactory[MutableTeacher, T]

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    tableView.setItems(mutableTeachers)

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
    initTableViewColumn[String](columnTtype, _.ttypeProperty)
  }

}