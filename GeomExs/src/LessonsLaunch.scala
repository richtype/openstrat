/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, pParse._, pgui._

object LessonsLaunch extends GuiLaunchMore
{
  override def settingStr: String = "lessons"

  override def default: (CanvasPlatform => Any, String) = (LsACircles.canv, "JavaFx" -- LsACircles.title)

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val code: String = sts.findSettingIdentifierElse("code", "A1")

    val res = theMap(code)
    (res.canv, "JavaFx" -- res.title)
  }

  def theMap(inp: String): LessonGraphics = inp match
  { case "A1" => LsACircles
    case "A2" => LsASquares
    case "A3" => LsACircles2
    case "A4" => LsAPolygons

    case "A6" => LsARotation
    case "A7" => LsAShapes2
    case "A8" => LsAShapes
    case "A9" => LsABeziers
    case "A10" => LsADiagram
    case "A11" => LsAReflect
    case "A12" => LsAHexEnum
    case "A13" => LsATiling
    case "A18" => LsAArcs
    case "A19" => LsAEllipses
    case "A20" => LsAInner
    
    case "B1" => LsTimer
    case "B2" => LsB2
    case "B3" => LsB3

    case "C1" => LsC1
    case "C2" => LsC2
    case "C3" => LsC3
    case "C3b" => LsC3b
    case "C4" => LsC4
    case "C5" => LsC5
    case "C6" => LsC6
    case "C7" => LsC7
    case "C8" => LsC8

    case "D1" => LsD1
    case "D2" => LsD2
    case "D3" => LsD3
    case "D4" => LsD4
//    case "D5" => (learn.LsD5(_), "JavaFx Demonstration Persistence 5")
//
//    case "E1" => (learn.LsE1(_), "JavaFx Demonstration Games 1") //Building turn based games.
//    case "E2" => (learn.LsE2(_), "JavaFx Demonstration Games 2")

    case _ => LsACircles
  }
}