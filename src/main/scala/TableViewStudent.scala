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

  case class Article(id: Int, name: String, price: Double)

  class MutableArticle {

    val idProperty: SimpleIntegerProperty = new SimpleIntegerProperty()
    val nameProperty: SimpleStringProperty = new SimpleStringProperty()
    val priceProperty: SimpleDoubleProperty = new SimpleDoubleProperty()

    def setId(id: Int) = idProperty.set(id)

    def setName(name: String) = nameProperty.set(name)

    def setPrice(price: Double) = priceProperty.set(price)
  }

  object MutableArticle {

    def apply(a: Article): MutableArticle = {
      val ma = new MutableArticle
      ma.setId(a.id)
      ma.setName(a.name)
      ma.setPrice(a.price)
      ma
    }

  }

  object JfxUtils {

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

  object DataSource {

    val data =
      (1 to 100) map {
        case i => Article(i, s"dummy entry", Random.nextDouble() * i)
      }

  }

  class TableViewStudentAppController extends Initializable {

    import JfxUtils._

    type ArticleTC[T] = TableColumn[MutableArticle, T]

    @FXML var tableView: TableView[MutableArticle] = _

    @FXML var columnId: ArticleTC[Int] = _
    @FXML var columnFirstName: ArticleTC[String] = _
    @FXML var columnLastName: ArticleTC[String] = _
    @FXML var columnAddress: ArticleTC[String] = _
    @FXML var columnPhone: ArticleTC[String] = _

    val mutableArticles = mkObservableList(DataSource.data.map(MutableArticle(_)))

    def initTableViewColumn[T]: (ArticleTC[T], (MutableArticle) => Any) => Unit =
      initTableViewColumnCellValueFactory[MutableArticle, T]

    override def initialize(location: URL, resources: ResourceBundle): Unit = {

      tableView.setItems(mutableArticles)

      initTableViewColumn[Int](columnId, _.idProperty)
      initTableViewColumn[String](columnFirstName, _.nameProperty)
      initTableViewColumn[String](columnLastName, _.nameProperty)
      initTableViewColumn[String](columnAddress, _.nameProperty)
      initTableViewColumn[String](columnPhone, _.nameProperty)

    }


}
