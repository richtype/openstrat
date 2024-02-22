/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75 East to 105 East. 1300km per hex tile. */
object Terr13W120 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w120(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, tundra),
      TRow(112, mtain),
      TRow(110, hillyTaiga),
      VRow(109, MouthOld(8704, HVUR)),
      TRow(108, Cape(4, 2, hillyForest)),
      VRow(107, BendAllOld(8704, HVDL)),
      TRow(106, sea, Cape(4, 1, hillyDesert)),
      VRow(105, BendOut(8706, HVDL)),
      TRow(104, sea, Cape(3, 2, hillySahel)),
      VRow(91, BendIn(8708, HVDR, 13)),
      VRow(89, BendIn(8708, HVUR, 13)),
      VRow(87, MouthOld(8704, HVDL, 3, wice), MouthOld(8708, HVDR, 3, wice)),
      TRow(86, Cape(0, 1, ice, wice))
    )
  }
  help.run
}