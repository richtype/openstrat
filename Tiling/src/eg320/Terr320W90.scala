/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320W90 extends Warm320Terrs
{
  override implicit val grid: EGrid320WarmFull = EGrid320.w90(138)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](taiga)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, cStart, tileValues :_*); () }
    gs(160, 9728, sea * 2)
    gs(158, 9726, taiga * 3)
    gs(156, 9728, taiga * 2)
    gs(154, 9734, sea)
//    gs(152, 10760, ice)
    gs(150, 9730, sea * 2)
    gs(148, 9728, sea * 2, taiga)
    gs(146, 9730, sea * 2, taiga)
//    gs(144, 10744, taiga * 3, sea * 2)
//    gs(142, 10742, taiga * 4, sea * 2)
//    gs(140, 10744, taiga, sea * 2, taiga, sea * 2)
//    gs(138, 10742, taiga * 2, sea, taiga * 2, sea * 2)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts((152, 9730))//, (143, 507))
    res
  }
}