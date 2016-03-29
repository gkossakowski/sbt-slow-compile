// run with ammonite repl
load.ivy("commons-io" % "commons-io" % "2.4")

@

import java.io._
def writeToFile(fileName: String, append: Boolean = false)(write: java.io.PrintWriter => Unit): Unit = {
  val fileWriter = new java.io.FileWriter(fileName, append)
  val printer = new java.io.PrintWriter(fileWriter)
  write(printer)
  fileWriter.close()
}

val sourcesCount = 1000
val projectsCount = 5
val alphabet = 'A' to 'Z'

def singleClass(writer: PrintWriter, projectIndex: Int, classIndex: Int): Unit = {
  val className = s"${alphabet(projectIndex)}$classIndex"
  val deps = alphabet.take(projectIndex)
  val argNames = (1 to (deps.size)).map(i => s"x$i")
  val args = (deps zip argNames).map {
    case (d, an) => s"$an: ${d.toLower}.$d$classIndex"
  }
  writer.println(s"package ${alphabet(projectIndex).toLower}")
  writer.println(s"class $className${args.mkString("(", ", ", ")")}")
}

import org.apache.commons.io.FileUtils
for (i <- 0 to projectsCount-1) {
  val projectDirName = s"sub$i"
  val projectDir = new File(projectDirName)
  FileUtils.deleteDirectory(projectDir)
  projectDir.mkdir()
  for (j <- 0 to sourcesCount-1) {
    val className = s"${alphabet(i)}$j"
    writeToFile(s"$projectDirName/$className.scala")(singleClass(_, i, j))
  }
}
