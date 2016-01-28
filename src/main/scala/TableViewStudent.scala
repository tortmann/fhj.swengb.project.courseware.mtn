import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.{Paths, Files}
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
import javafx.scene.control.{TextField, Label, TableColumn, TableView}
import javafx.scene.{Parent, Scene}
import javafx.util.Callback
import scala.collection.JavaConversions
import scala.io.Source
import scala.util.control.NonFatal

object TableViewStudent {
  def main(args: Array[String]) {
    Application.launch(classOf[TableViewStudentApp], args: _*)
  }

}
class TableViewStudentApp extends javafx.application.Application {

  override def start(stage: Stage): Unit = {

    val fxmlMain = "/fxml/TableViewStudent.fxml"
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
    ms.setBirthdate(s.birthdate)
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

  var student = new MutableStudent
}

class TableViewStudentAppController extends Initializable {

  import JfxUtilsStudent._

  type StudentTC[T] = TableColumn[MutableStudent, T]

  @FXML var window:BorderPane = _
  @FXML var tableView: TableView[MutableStudent] = _
  @FXML var errorLabel: Label = _

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

  def Create(): Unit = {openWindow(fxmlCreateStudent, cssMain)}
  def Edit(): Unit = {
    DataSourceStudent.student = tableView.getSelectionModel().getSelectedItem()
    openWindow(fxmlEditStudent, cssMain )
  }

  def ButtonClicked(): Unit = {
    val s: MutableStudent = tableView.getSelectionModel().getSelectedItem();
    val con = Db.Con

    try {
      if(s != null) {
        errorLabel.setText("")
        Student.delFromDb(con)(s.idProperty.get())
        con.close()
        mutableStudents.remove(s)
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

    def generateTable(data: List[Student]) = {

      val head = "<!DOCTYPE html><html lang=\"en\"><head>\n"
      val meta = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
      val link = "<link rel=\"stylesheet\" href=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\">"
      val script = "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js\"></script>\n<script src=\"http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js\"></script>\n"
      val begin = "<body>\n<div class=\"container\">\n<table class=\"table\">\n"
      val header = "<thead><tr>\n<th>firstname</th>\n<th>lastname</th>\n<th>id</th>\n</tr>\n</thead><tbody>"
      val middle = data.map(n => <tr><td>{n.firstname}</td><td>{n.lastname}</td><td>{n.id}</td></tr>).mkString(",")
      val end = "</tbody></table></div></body></html>"


      val html = head + meta + link + script + begin + header + middle + end
      val target = new File("/Users/Max/IMA14/3.Semester/SWENGB/workspace/fhj.swengb.project.courseware.mtn/src/main/resources/html/studentReport.html")
      writeToFile(target, html)
      errorLabel.setText("Copied to" + target.getAbsolutePath)
    }

    val con = Db.Con
    try {

      val students = Student.fromDb(Student.queryAll(con))
      con.close()

      generateTable(students)
    }
    catch {
      case e: Exception => errorLabel.setText("Not deleted due to primary key constraint!")
    }
  }
}






class CreateStudentAppController extends Initializable {

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
  @FXML var group:TextField = _
  @FXML var status:TextField = _
  @FXML var errorLabel:Label = _

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

  }

  def Exit(): Unit = {
    window.getScene.getWindow.hide()
    val tvsa = new TableViewStudentApp
    val stage = new Stage
    val fxmlMain = "/fxml/TableViewStudent.fxml"
    val cssMain = "/css/MainMenu.css"
    tvsa.redir(stage, fxmlMain, cssMain)
  }

  def ButtonCreated(): Unit = {
    try {
      val con = Db.Con

      val s = Student(id.getText(), title.getText(), firstname.getText(), lastname.getText(), birthdate.getText(),
        gender.getText(), address.getText(), zip.getText(), phone.getText(), email.getText(), group.getText(),
        status.getText().toInt)

      Student.toDb(con)(s)
      con.close()
      window.getScene.getWindow.hide()

      //val tvsa = new TableViewStudentApp
      //val stage = new Stage
      //val fxmlMain = "/fxml/TableViewStudent.fxml"
      //val cssMain = "/css/MainMenu.css"
      //tvsa.redir(stage, fxmlMain, cssMain)
    }
    catch {
      case e: Exception => errorLabel.setText("Could not be created!")
    }
  }
}

















class EditStudentAppController extends Initializable {

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
  @FXML var group:TextField = _
  @FXML var status:TextField = _
  @FXML var errorLabel:Label = _

  val student: MutableStudent = DataSourceStudent.student

  override def initialize(location: URL, resources: ResourceBundle): Unit = {

    id.setText(student.idProperty.get())
    title.setText(student.titleProperty.get())
    firstname.setText(student.firstnameProperty.get())
    lastname.setText(student.lastnameProperty.get())
    birthdate.setText(student.birthdateProperty.get())
    gender.setText(student.genderProperty.get())
    address.setText(student.addressProperty.get())
    zip.setText(student.zipProperty.get())
    phone.setText(student.phoneProperty.get())
    email.setText(student.emailProperty.get())
    group.setText(student.groupProperty.get())
    status.setText(student.statusProperty.get().toString)
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
      if (student != null) {
        val con = Db.Con

        val s = Student(id.getText(), title.getText(), firstname.getText(), lastname.getText(), birthdate.getText(), gender.getText(),
          address.getText(), zip.getText(), phone.getText(), email.getText(), group.getText(), status.getText().toInt)

        Student.editFromDb(con)(s, student.idProperty.get())
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