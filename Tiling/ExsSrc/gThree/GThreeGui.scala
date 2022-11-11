/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import pgui._, geom._, prid._, phex._, gPlay._

/** Graphical user interface for example game 3. A hex based game like game 1, that introduces multi turn directives. */
case class GThreeGui(canv: CanvasPlatform, scenStart: ThreeScen, viewIn: HGView) extends HGridSysGui("Game Three Gui")
{
  statusText = "Left click on Player to select. Right click on adjacent Hex to set move."
  var scen = scenStart
  var history: RArr[ThreeScen] = RArr(scen)
  implicit def gridSys: HGridSys = scen.gridSys
  def players: HCenOptLayer[Player] = scen.oPlayers
  cPScale = viewIn.cPScale
  focus = viewIn.vec
  implicit val proj: HSysProjection = gridSys.projection(mainPanel)
  proj.setView(viewIn)

  /** This is the planned moves or orders for the next turn. Note this is just a record of the planned moves it is not graphical display of those
   *  moves. This data is state for the Gui. */
  var moves: Map[Player, HDirnArr] = scen.playersData
  var movesNew: HDirnPathPairArr[Player] = scen.playerOrders

  val urect = Rect(1.4, 1)

  def units: GraphicElems = players.projSomeHcPtMap{ (p, hc, pt) =>
    val str = ptScale.scaledStr(170, p.toString + "\n" + hc.strComma, 150, p.charStr + "\n" + hc.strComma, 60, p.charStr)
    urect.scale(80).slate(pt).fillDrawTextActive(p.colour, HPlayer(hc, p), str, 24, 2.0)
  }

  /** [[TextGraphic]]s to display the [[HCen]] coordinate in the tiles that have no unit counters. */
  def hexStrs: RArr[TextGraphic] = players.projNoneHcPtMap{ (hc, pt) => TextGraphic(hc.strComma, 20, pt) }

  /** This makes the tiles active. They respond to mouse clicks. It does not paint or draw the tiles. */
  def actives: RArr[PolygonActive] = proj.tileActives

  /** Draws the tiles sides (or edges). */
  def sidesDraw: LinesDraw = proj.sidesDraw()

  /** This is the graphical display of the planned move orders. */
  def moveGraphics: RArr[LineSegDraw] = players.someHCFlatMap { (p, hc) =>
    val hss: HDirnArr = moves.withDefault(_ => HDirnArr())(p)
    val lps = movesNew.flatMap(p => p.path.segHCsMap(seg => LineSegHCPair(seg, p.a2)))
    //val lps2 = proj.transLineSegPairs(lps)
    hss.projLineSegs(hc, proj).map { ls => ls.draw(players.unSafeApply(hc).colour)
    }
  }

  def mg1a: RArr[LineSegPair[Colour]] =
    moves.flatMapArr { p => p._2.projLineSegs(scen.oPlayers.get(p._1), proj).map(ls => LineSegPair(ls, p._1.colour)) }

  def mg1b: LineSegPairArr[Colour] =
    moves.flatMapPairArr{p => p._2.projLineSegs(scen.oPlayers.get(p._1), proj).map(ls => LineSegPair(ls, p._1.colour)) }

  /** Creates the turn button and the action to commit on mouse click. */
  def bTurn: PolygonCompound = clickButton("Turn " + (scen.turn + 1).toString){_ =>
    scen = scen.endTurn(moves)
    moves = scen.playersData
    repaint()
    thisTop()
  }

  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(RArr(bTurn) ++ proj.buttons)

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (RightButton, AnyArrHead(HPlayer(hc1, p)), hits) => hits.findHCenForEach{ hc2 =>
      val newM: Option[HDirn] = gridSys.findStep(hc1, hc2)
      newM.fold[Unit]{ if (hc1 == hc2) moves = moves.replaceValue(p, HDirnArr()) } { m => moves = moves.replaceValue(p, HDirnArr(m)) }
      repaint()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }
  thisTop()

  def frame: GraphicElems = actives ++ units ++ hexStrs +% sidesDraw ++ moveGraphics
  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  repaint()
}