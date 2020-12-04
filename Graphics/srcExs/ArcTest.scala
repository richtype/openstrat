package learn
import ostrat._, geom._, pCanv._, Colour._
import scala.math.{Pi}

/* This is a tempory lesson: whilst Arcs get fixed*/ 

case class ArcTest(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A4")
{
  val radius = 20.0

  var myStuff: GraphicElems = ijToMap(1, 1)(0,15) { (i, j) =>
    val x = -400 + 25 + j * 50; // x coordinate
    val y = 25 + i * 50; // y coordinate
    val arcAngle = 0.1 + Pi/8*j; // angle of arc
    //val origin = x pp y
    val startPoint = x+radius pp y
    val apex = x+radius*math.cos(arcAngle/2) pp y+radius*math.sin(arcAngle/2)
    val endPoint = x+radius*math.cos(arcAngle) pp y+radius*math.sin(arcAngle)

    CArc(startPoint, apex, endPoint).draw(DeepSkyBlue)
  }

  repaint(myStuff)
}

//def getPointOnCircle(origin: pt2, radius: Angle)
// CArcDrawOld(x pp y, 0 pp 0, 0 pp 250)
// CArc3(x pp y, -141.421356237 pp 141.421356237, 0 pp 200).draw(Crimson)
// Arr(CArcDraw(CArc(x pp y, x+radius pp y, endAngle), 2, Blue))
