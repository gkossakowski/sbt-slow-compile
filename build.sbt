val sub0 = project
val sub1 = project.dependsOn(sub0)
val sub2 = project.dependsOn(sub0, sub1)
val sub3 = project.dependsOn(sub0, sub1, sub2)
val sub4 = project.dependsOn(sub0, sub1, sub2, sub3)

lazy val root = (project in file(".")).aggregate(sub0, sub1, sub2, sub3, sub4).settings(sourcesInBase := false)
