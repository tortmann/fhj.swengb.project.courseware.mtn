
import java.io.File
import java.net.URL
import java.nio.charset.StandardCharsets
import java.nio.file.{Paths, Files}

import scala.io.Source

object SwengbUtil {

  def mkParent(file : File) : Unit = {
    if (!file.getParentFile.exists()) {
      file.getParentFile.mkdirs()
    }
  }
  def writeToFile(file: File, content: String): File = {
    Files.write(Paths.get(file.toURI), content.getBytes(StandardCharsets.UTF_8)).toFile
  }

  def fetch(url: URL): String = {
    Source.fromURL(url).mkString
  }

}

/**
 * have a look into target/site dir
 *
 * interesting: http://www.webjars.org/
 */
object Report {




  val target = new File("C:\\workspace\\fhj.swengb.project.courseware.mtn\\example.html")

  def main(args: Array[String]) {
    //val studentreport: String = Student.fromDb(Student.queryAll(Db.Con)).toString
    val studentlist: String = List(Student("1","Dr","MAX","WAGENEDER","1995-07-29","m","STREET1","8010","","","grp_1",1),Student("1","Mag","THOMAS","ORTMANN","2015-07-29","m","STREET1","8010","","","grp_1",1)).toString()
    val html1 = Source.fromString(studentlist).mkString
    //val html = Source.fromIterable(studentreport).mkString
    SwengbUtil.writeToFile(target, html1)
    println("Created " + target.getAbsolutePath)
  }

}
