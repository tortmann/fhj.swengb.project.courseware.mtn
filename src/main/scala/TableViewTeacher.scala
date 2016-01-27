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
import scala.collection.JavaConversions
import scala.util.control.NonFatal


object TableViewTeacher {
  def main(args: Array[String]) {
    Application.launch(classOf[TableViewTeacherApp], args: _*)
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
      stage.setTitle("Teacher")
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
    mt.setBirthdate(t.birthdate)
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


  var teacher = new MutableTeacher

  def getTeacher(): MutableTeacher = {
    return teacher
  }

  def setTeacher(t: MutableTeacher) = {
    teacher = t
  }

}

class TableViewTeacherAppController extends Initializable {

  import JfxUtilsTeacher._

  type TeacherTC[T] = TableColumn[MutableTeacher, T]

  @FXML var window:BorderPane = _
  @FXML var tableView: TableView[MutableTeacher] = _
  @FXML var errorLabel: Label = _

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

  val cssMain = "/css/MainMenu.css"
  val fxmlCreateTeacher = "/fxml/CreateTeacher.fxml"
  val fxmlEditTeacher = "/fxml/EditTeacher.fxml"

  val loadCreateTeacher = new FXMLLoader(getClass.getResource(fxmlCreateTeacher))
  val loadEditTeacher = new FXMLLoader(getClass.getResource(fxmlEditTeacher))


  def openWindow(fxmlLoader: FXMLLoader, css: String):Unit = {
    try {
      val stage = new Stage
      stage.setTitle("Teacher")
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
  def Create(): Unit = {openWindow(loadCreateTeacher, cssMain)}

  var teacher = new MutableTeacher

  def Edit(): Unit = {
    DataSourceTeacher.setTeacher(tableView.getSelectionModel().getSelectedItem())
    openWindow(loadEditTeacher, cssMain )
  }

  def ButtonClicked(): Unit = {
    val t: MutableTeacher = tableView.getSelectionModel().getSelectedItem();
    val con = Db.Con

    try {
      if(t != null) {
        errorLabel.setText("")
        Teacher.delFromDb(con)(t.idProperty.get())
        con.close()
        mutableTeachers.remove(t)
        tableView.refresh()
      }
    }
    catch {
      case e: Exception => errorLabel.setText("Not deleted due to primary key constraint!")
    }
  }
}











class CreateTeacherAppController extends Initializable {

  @FXML var window:BorderPane = _

  @FXML var id:TextField = _
  @FXML var title:TextField = _
  @FXML var firstname:TextField = _
  @FXML var lastname:TextField = _
  @FXML var birthdate:TextField = _
  @FXML var gender:TextField = _
  @FXML var address:TextField = _
  @FXML var zip:TextField = _
  @FXML var phone:TextField = _
  @FXML var email:TextField = _
  @FXML var ttype:TextField = _

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

  }

  def Exit(): Unit = window.getScene.getWindow.hide()

  def ButtonCreated(): Unit = {
    val con = Db.Con

    val t = Teacher(id.getText(), title.getText(), firstname.getText(), lastname.getText(), birthdate.getText(), gender.getText(),
                    address.getText(), zip.getText(), phone.getText(), email.getText(), ttype.getText())

    Teacher.toDb(con)(t)
    con.close()
    window.getScene.getWindow.hide()
  }
}






class EditTeacherAppController extends Initializable {

  import MutableTeacher._

  @FXML var window:BorderPane = _

  @FXML var id:TextField = _
  @FXML var title:TextField = _
  @FXML var firstname:TextField = _
  @FXML var lastname:TextField = _
  @FXML var birthdate:TextField = _
  @FXML var gender:TextField = _
  @FXML var address:TextField = _
  @FXML var zip:TextField = _
  @FXML var phone:TextField = _
  @FXML var email:TextField = _
  @FXML var ttype:TextField = _

  val teacher: MutableTeacher = DataSourceTeacher.getTeacher()

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    id.setText(teacher.idProperty.get())
    title.setText(teacher.titleProperty.get())
    firstname.setText(teacher.firstnameProperty.get())
    lastname.setText(teacher.lastnameProperty.get())
    birthdate.setText(teacher.birthdateProperty.get())
    gender.setText(teacher.birthdateProperty.get())
    address.setText(teacher.addressProperty.get())
    zip.setText(teacher.zipProperty.get())
    phone.setText(teacher.phoneProperty.get())
    email.setText(teacher.emailProperty.get())
    ttype.setText(teacher.ttypeProperty.get())
  }

  def Exit(): Unit = window.getScene.getWindow.hide()

  def ButtonEdited(): Unit = {
    val con = Db.Con

    val t = Teacher(id.getText(), title.getText(), firstname.getText(), lastname.getText(), birthdate.getText(), gender.getText(),
                    address.getText(), zip.getText(), phone.getText(), email.getText(), ttype.getText())

    Teacher.editFromDb(con)(t, teacher.idProperty.get())
    con.close()
    window.getScene.getWindow.hide()


  }
}