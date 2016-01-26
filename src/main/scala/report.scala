
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

  /**
   * function to measure execution time of first function, optionally executing a display function,
   * returning the time in milliseconds
   */
  def time[A](a: => A, display: Long => Unit = s => ()): A = {
    val now = System.nanoTime
    val result = a
    val micros = (System.nanoTime - now) / 1000000
    display(micros)
    result
  }
}

/**
 * have a look into target/site dir
 *
 * interesting: http://www.webjars.org/
 */
object Report {


  val target = new File("/Users/Max/IMA14/3.Semester/SWENGB/workspace/fhj.swengb.project.courseware.mtn/example.html")

  def main(args: Array[String]) {
    val studentreport = Student.fromDb(Student.queryAll(Db.Con)).toString()
    val html1 = Source.fromString(studentreport).mkString
    //val html = Source.fromIterable(studentreport).mkString
    SwengbUtil.writeToFile(target, html1)
    println("Created " + target.getAbsolutePath)
  }

}
