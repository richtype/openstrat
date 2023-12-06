/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15 West to 15 East. The Faroe Islands and the Shetland Islands are large enough to qualify as an island. The Orkney's are
 *  probably not, even with the mainland that comes into the hex, but for the sake of Scapa FLow they will be an [[Isle]].  */
object Terr120E0 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e0(300)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(344, sea * 9, Cape(4, 2, mtain)),
      TRow(342, sea * 10, mtain) ,
      TRow(340, sea * 10, mtain),
      TRow(338, sea * 9, hillyTundra * 2),
      TRow(336, sea * 9, hillyTaiga * 2, taiga),
      TRow(334, sea * 8, Cape(5, 2, mtain), hillyTaiga * 3),
      TRow(332, sea * 3, Isle(hilly), sea * 4, Cape(4, 2, mtain), mtain, hillyTaiga, taiga * 2),
      TRow(330, sea * 5, Isle(hilly), sea * 2, mtain * 4, hillyTaiga),
      TRow(328, sea * 6, sea * 3, mtain, hillyTaiga * 3),
      TRow(326, sea * 5, Isle(hilly), sea * 3, Cape(4, 2, hillyTaiga), hillyTaiga, taiga * 3),
      TRow(324, sea * 3, land, hilly, sea * 4, Cape(3, 2, hillyTaiga), Cape(2, 2, hillyTaiga), sea, land * 2),
      TRow(322, sea * 3, hilly, hilly * 2, sea * 5, Cape(5, 2), sea, land * 2),
      TRow(320, sea * 4, land * 2, sea * 5, Cape(4, 2), land, sea, land),
      VRow(319, Mouth(536, HVUp), Mouth(538, HVUL), Mouth(540, HVDR)),
      TRow(318, sea * 3, Cape(5, 3), Cape(4, 1, hilly), hilly, sea * 5, land, land, land, sea),
      VRow(317, Mouth(536, HVDn)),
      TRow(316, sea * 2, Cape(5, 2), land, Cape(1, 3), Cape(3, 2, hilly), land, sea * 5, land, sea * 3),
      VRow(315, Mouth(498, HVUL), Mouth(506, HVUR)),
      TRow(314, sea * 2, Cape(4, 2), land, Cape(1, 2), Cape(4, 3, hilly), land * 2, sea * 3, Cape(5, 2), land * 4),
      TRow(312, sea * 2, land * 2, sea, hilly, land * 2, Cape(0, 3), sea, land * 6),
      TRow(310, sea * 2, Cape(2, 4), sea * 2, Cape(3, 2, hilly), land * 2, Cape(2, 1), sea, land * 3, hilly * 2, land * 2),
      VRow(309, Mouth(504, HVUR)),
      TRow(308, sea * 5, Cape(4, 3, hilly), Cape(3, 1, hilly), Cape(3, 1), Cape(2, 2), Cape(5, 2), land * 2, hilly * 5),
      TRow(306, sea * 8, Cape(5, 3), Cape(5, 1), land * 2, hilly * 6),
      VRow(305, Mouth(512, HVDn)),
      TRow(304, sea * 6, land * 5, hilly * 4, land, hilly * 2),
      TRow(302, sea * 6, Cape(3, 3), land * 4, hilly * 2, hillyForest, hilly * 2, mtain * 2),
      TRow(300, sea * 8, land * 4, hilly * 2, mtain * 4, hilly),
    )
  }
  help.run
}

object BritReg120
{ implicit def britGrid: EGrid120Long = EGrid120Long.reg(138, 148, 0, 504, 520)
  def britTerrs: LayerHcRefSys[WTile] = Terr120E0.terrs.spawn(Terr120E0.grid)
  def britSTerrs: LayerHSOptSys[WSide, WSideSome] = Terr120E0.sTerrs.spawn(Terr120E0.grid, britGrid)
  def britCorners: HCornerLayer = Terr120E0.corners.spawn(Terr120E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid120Long = britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}