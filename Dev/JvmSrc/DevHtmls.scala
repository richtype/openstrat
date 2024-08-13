/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*

object DevHtmls
{
  def main(args: Array[String]): Unit =
  { val names = StrArr("Diceless", "Discov", "WW1", "WW2", "BC305", "Zug", "Planets", "EG1300", "EG1000", "EG640", "EG460", "EG320")
    projPathProc { path => names.filter(name => args.exists(_ == name)).foreach(name => writeFastFull(path, name)) }
  }

  def writeFastFull(path: DirPathAbs, name: String): Unit =
  { writeFile(path, true, name)
    writeFile(path, false, name)
  }

  def writeFile(path: DirPathAbs, isFast: Boolean, name: String): Unit =
  { val scalaVersionStr = "3.4.2"
    val jsStr = ife(isFast, "fast", "")
    val htmlStr = ife(isFast, "Fast", "Full")

    val noCacheScript = s"""
    |  // aid local development in ensuring script not cached during a simple refresh
    |  var script = document.createElement("script");
    |  script.setAttribute("type", "text/javascript");
    |  script.setAttribute("src", "../../../AppsJs/target/scala-$scalaVersionStr/appsjs-${name}-${jsStr}opt/main.js?"+Date.now().toString());
    |  document.getElementsByTagName("head")[0].appendChild(script);
    |  script.addEventListener('load', function(e) { ${name}AppJs.main(); });
    |""".stripMargin

    val style = HtmlStyle(CssBody(DecMarg(0.px), DecOverflowHidden))
    val head = HtmlHead.title("OpenStrat: 460km All Longitudes full", HtmlNoCache, style)
    val script = HtmlScript.inlineJsStr(noCacheScript)
    val body = HtmlBody(HtmlCanvas.id("scanv"), HtmlNoScript(), script)
    val page = HtmlPage(head, body)
    val res = fileWrite(path / "Dev" / "target" / "DevPages", s"${name}Sbt${htmlStr}.html", page.out)
    deb(res.toString)
  }
}
