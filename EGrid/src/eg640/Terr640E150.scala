/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.
 * [[Isle8]] 77942.286km² => 100112.536km². Hokkaido 83423.84km².
 * [[Isle7]] 58543.317km² => 77942.286km². Tasmania 68401 km².
 * [[Isle6]] 41915.629km² => 58543.317km². New Britain 35144km² + New Ireland 8990km² = 44134km².
 * [[Isle4]] 16974.097km² => 28059.223km². New Caledonia 18353km².
 * [[Isle3]] 8660.254km² => 16974.097km². Solomon south east 12999km². Solomon middle 6379km² + Bougainville 9581km² = 15960km² */
object Terr640E150 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e150(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(130, siceWin),
    VRow(129, OrigMin(5632, HVDR, 3, siceWin), BendOut(5634, HVUp, 7, siceWin), BendIn(5636, HVDn, 13, siceWin)),
    TRow(128, tundra),
    TRow(126, mtainTundra, hillyTaiga),
    TRow(124, hillyTaiga, hillyTaiga),

    VRow(123, OrigRt(5628, HVUR, 7, siceWin), BendIn(5630, HVDn, 13, siceWin), ThreeDown(5632, 11, 13, 13), BendMin(5636, HVDR, 3, siceWin),
      OrigRt(5638, HVDL, 7, siceWin)),

    TRow(122, hillyTaiga, hillyTaiga),
    VRow(121, BendOut(5630, HVDR), ThreeUp(5632, 13, 0, 13), OrigMin(5634, HVUL), OrigLt(5636, HVUp, 7, siceWin)),
    TRow(120, taiga),
    VRow(119, Bend(5628, HVDR, 7, 1), ThreeUp(5630, 0, 13, 6), BendIn(5632, HVDL, 13)),
    TRow(118, hillyContForest),
    VRow(117, Bend(5626, HVDR, 10, 7), Bend(5628, HVUL, 8, 3), BendOut(5630, HVDR), BendIn(5632, HVUL, 13)),
    TRow(116, hillyContForest),
    VRow(115, BendOut(5628, HVDR, 7), BendIn(5630, HVUL, 13)),
    TRow(114, hillySub),
    VRow(113, OrigLt(5626, HVUR, 7), BendIn(5628, HVUL, 13)),
    VRow(99, OrigMin(5622, HVUp)),
    TRow(98, hillyJungle * 2, Isle6(mtainJungle)),
    VRow(97, OrigRt(5624, HVDL, 7), ThreeDown(5622, 13, 13, 0), BendOut(5632, HVUR, 7), BendIn(5634, HVDL, 13)),
    TRow(96, jungle, jungle, mtainJungle, Isle3(mtainJungle), Isle3(mtainJungle)),

    VRow(95, BendIn(5622, HVUR, 13), BendIn(5624, HVUp, 13), BendOut(5626, HVDn, 7), BendIn(5628, HVUp, 13), BendMax(5630, HVDn), ThreeDown(5632, 13, 0, 13),
      BendIn(5634, HVUL, 13)),

    TRow(94, savannah, hillySavannah),
    VRow(93, Orig(5630, HVUR, 4, 4), ThreeUp(5632, 0, 13, 13), BendIn(5634, HVDL, 13)),
    TRow(92, savannah, hillySavannah, sea, Isle4(hillyJungle)),
    VRow(91, BendOut(5634, HVUR, 7), BendIn(5636, HVDL, 13)),
    TRow(90, deshot, savannah, hillySavannah),
    VRow(89, OrigLt(5636, HVUp, 7)),
    TRow(88, deshot, hillySavannah),
    VRow(87, OrigRt(5624, HVDn, 7)),
    TRow(86, savannah, hillySavannah),
    VRow(85, BendIn(5624, HVUR), BendOut(5626, HVDL, 7)),
    TRow(84, hillyOce),
    VRow(83, BendIn(5626, HVUR, 13), BendIn(5628, HVUp, 13), BendInLt(5630, HVDn, 9, 7)),
    TRow(82, Isle7(hillyOce)),
    TRow(72, SeaIcePerm),
    TRow(70, SeaIcePerm)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(116, "Honshu north")
    str(114, "Honshu middle")
    str(118, "Hokkaido")
    str(98, "New Guinee\ncentral", "New Guinee NE", "New Britain")
    str(96, "New Guinee SW", "New Guinee SM", "new Guinee SE", "Solomon main", "Guadalcanal")
    str(92, "" * 3, "New Caledonia")
  }
}