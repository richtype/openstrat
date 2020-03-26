package ostrat
package gOne
import pCanv._, geom._

/** Graphical user interface for GOne example game. */
case class GOneGui(canv: CanvasPlatform) extends CmdBarGui("Game One Gui")
{ var statusText = "Stuff"
  val grid = OneGrid.grid
  val scale = grid.fullDisplayScale(mainWidth, mainHeight)
  val uts = grid.tilesSomeMap(OneGrid.arr, scale){ (p, v) => Rectangle(120, 80, v).fillDrawTextActive(p.colour, p, p.toString, 24, 2.0) }
  def thisTop(): Unit = reTop(Refs(status))
  thisTop()
  mainMouseUp = (v, b, l) => (v, b, l) match
  {
    case (v, LeftButton, cl) =>
    { selected = l
      statusText = selected.headToStringElse("Nothing Selected")
      thisTop()
    }
    case _ => deb("Hi")
  }
  mainRepaint(cenSideVertCoodText(grid, scale) ++ uts)
}