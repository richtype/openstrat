/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** 160km terrain for 0 degrees east. Majorca is big enough at this scale to qualify as Island. Lesbos is not. */
object Terr160E0 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e0(262)

  /** Terrain for 160km 30East. Zealand has been moved north. 94GG has been left as Sea. */
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)

  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(310, sea * 6, Hland(3, 4, mtain)),
      TRow(308, sea * 7, mtain),
      TRow(306, sea * 7, Hland(2, 4, mtain)),
      TRow(304, sea * 7, mtain),
      TRow(302, sea * 6, Hland(2, 5, hillyTaiga), hillyTaiga, taiga),
      TRow(300, sea * 6, Hland(1, 5,  mtain), hillyTaiga * 2),
      TRow(298, sea * 6, Hland(2, 4, mtain), mtain, hillyTaiga * 2),
      TRow(296, sea * 6, hillyTaiga * 2, taiga * 2),
      VRow(295, Mouth(528, HVUp)),
      TRow(294, sea * 2, Isle(hills), Hland(3, 5, hills), sea * 3, hills, Hland(2, 2, hillyTaiga), taiga),
      TRow(292, sea * 3, hills, Hland(2, 1, hills), sea * 4, Hland(4, 5), plain),
      TRow(290, sea * 2, Hland(2, 4, hills), hills, sea * 4, plain, Isle(), plain),
      VRow(289, BendAll(502, HVDL)),
      TRow(288, sea * 2, plain, Hland(2, 3, hills), plain, sea * 4, plain, sea),
      TRow(286, sea * 2, plain, Hland(2, 1), plain * 2, sea * 3, plain * 3),
      TRow(284, sea, plain * 2, Hland(2, 4, hills), plain * 2, sea, plain * 5),
      TRow(282, sea * 4, hills, plain, Hland(3, 1), plain * 2, hills * 2, plain * 2),
      TRow(280, sea * 4, Hland(4, 2, hills), sea * 2, plain, hills * 5),
      TRow(278, sea * 4, plain * 5, hills, plain * 2, hills),
      TRow(276, sea * 5, plain * 4, hills * 2, mtain * 3),
      TRow(274, sea * 6, plain * 3, hills, mtain * 4),
      VRow(273, Mouth(538, HVUp)),
      TRow(272, sea * 6, plain, hills * 2, mtain, hills, mtain, plain, hills),
      VRow(271, BendAll(538, HVUR), Mouth(540, HVDR)),
      TRow(270, sea * 2, Hland(3, 4), Hland(1, 0, hills) * 3, plain * 2, hills * 2, mtain, sea, hills * 2, sea),
      TRow(268, sea * 3, hills, plain, hillyDesert * 2, mtain * 2, sea * 3, Isle(hills), hills * 2),
      TRow(266, sea * 2, hills * 2, desert, hillyDesert * 2, hills, sea * 3, hills, sea * 2, hills),
      VRow(265, Mouth(514, HVUp)),
      TRow(264, sea * 3, hills * 2, hillyDesert, hills, Hland(1, 2, hills), sea, Isle(hills), sea * 2, hills, sea * 2),
      TRow(262, sea * 3, plain * 2, hills * 3)
    )
  }
  help.run
}

/** 16okm terrain scenario for Britain */
object Brit160
{ def britTerrs: HCenLayer[WTile] = Terr160E0.terrs.spawn(Terr160E0.grid, EGrid160.britGrid)
  def britSTerrs: HSideOptLayer[WSide, WSideSome] =Terr160E0.sTerrs.spawn(Terr160E0.grid, EGrid160.britGrid)
  def britCorners: HCornerLayer = Terr160E0.corners.spawn(Terr160E0.grid, EGrid160.britGrid)

  def britScen: EScenBasic = new EScenBasic
  { override implicit val gridSys: EGrid160LongPart = EGrid160.britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideOptLayer[WSide, WSideSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
  }
}