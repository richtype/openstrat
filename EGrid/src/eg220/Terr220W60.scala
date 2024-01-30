/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 75° west to 45° west, centred on 60° west.  */
object Terr220W60 extends Long220Terrs
{
  override implicit val grid: EGrid220LongFull = EGrid220.w60(154, 164)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(162, taiga * 5),//Unchecked
      TRow(160, taiga * 3, hillyTaiga),//Unchecked
      TRow(158, hillyTaiga),//Unchecked
      TRow(156, hillyTaiga * 2),//Unchecked
      TRow(154, land),//Unchecked
    )
  }
  help.run
}