/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320W60 extends WarmTerrs
{
  override implicit val grid: EGrid320Warm = EGrid320.w60(138)

  override val terrs: HCenDGrid[WTile] =
  { val res: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, cStart, tileValues :_*); () }
    gs(160, 10756, ice)
    gs(158, 10754, ice * 2)
    gs(156, 10748, tundra, sea, ice)
    gs(154, 10746, tundra * 2, sea, ice)
    gs(152, 10748, tundra, sea * 2, ice)
    gs(150, 10758, tundra)
    gs(148, 10744, taiga, sea * 4)
    gs(146, 10746, taiga * 2, sea * 3)
    gs(144, 10744, taiga * 3, sea * 2)
    gs(142, 10742, taiga * 4, sea * 2)
    gs(140, 10744, taiga, sea * 2, taiga, sea * 2)
    gs(138, 10742, taiga * 2, sea, taiga * 2, sea * 2)
    res
  }

  override val sTerrs: HSideBoolDGrid =
  { //implicit val grid: EGrid320Warm = EGrid320.e0(138)
    val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507))
    res
  }
}