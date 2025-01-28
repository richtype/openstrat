/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** 1300km [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex area 1463582.932km².
 * [[Isle13]] 893300.129km² => 1041945.271km². (Borneo 748168km²) + (Sulawesi 180681km²) = 928849km²
 * [[Isle9]] 413061.979km² => 515970.154km². Sumatra 443065kmm².
 * [[Isle8]] 321588.046km² => 413061.979km². Japan combined 377973 km².
 * [[Isle7]] 241548.355km² => 321588.046km². Most of Philippines 300000km².
 * [[Isle6]] 172942.905km² => 243930.488km².
 * [[Isle5]] 115771.696km² => 172942.905km². Java 138794km² + Bali 5780km² + Lambok 4607.68km² and Sumbawa 15414km².
 * [[Isle4]] 70034.730km² => 115771.696km². Most of Luzon (109,965 km2) excluding the south plus Taiwan, Lesser Sunda Islands estimate 80000km, */
object Terr13E120 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e120(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(115, OrigRt(4610, HVDR, 7, SeaIceWinter), BendOut(4612, HVUp, 7, SeaIceWinter)),
    TRow(114, hillyTundra),
    TRow(112, taiga),
    VRow(111, Orig(4612, HVDn, 7, 3)),
    TRow(110, hillyTaiga),
    VRow(109, BendIn(4610, HVDR, 8), ThreeUp(4612, 13, 8, 0)),
    TRow(108, hillyContForest),
    VRow(107, BendIn(4608, HVDR, 13), BendIn(4610, HVUL)),
    TRow(106, hillySub, hillySubForest),
    VRow(105, BendIn(4608, HVUR, 13), ThreeDown(4610, 13, 0, 6), BendOut(4612, HVDn, 7)),
    TRow(104, hillyJungle, sea),
    VRow(103, OrigLt(4606, HVDR, 7), ThreeDown(4608, 13, 9, 0), ThreeUp(4610, 0, 9, 13)),
    TRow(102, sea, Isle7(hillyJungle)),
    VRow(101, BendInLt(4604, HVDn, 7, 4), ThreeUp(4606, 0, 7, 9), ThreeDown(4610, 9, 13,3), BendIn(4612, HVDn, 13), BendIn(4614, HVDL, 9)),
    TRow(100, hillyJungle, hillyJungle),
    VRow(99, ThreeDown(4604, 7, 11, 0), ThreeUp(4606, 1, 11, 6), ThreeDown(4608, 0, 12, 11), ThreeUp(4610, 7, 12, 7), Bend(4612, HVDL, 12, 2)),
    TRow(98, Isle5(hillyJungle), Isle4(hillyJungle)),
    VRow(97, ThreeDown(4606, 11, 7, 0), ThreeUp(4608, 12, 9, 11), BendIn(4612, HVUp, 13), Bend(4614, HVDn, 13, 7)),
    TRow(96, SepB(), deshot, sahel),
    VRow(95, BendIn(4604, HVDR, 13), BendOut(4606, HVUL)),
    TRow(94, savannah, deshot),
    VRow(93, BendIn(4604, HVUR, 13), OrigRt(4606, HVUL, 7)),
    VRow(91, OrigLt(4610, HVDR, 7), BendIn(4612, HVUp, 13)),
    VRow(87, BendOut(4606, HVDn, 7, siceWin), BendIn(4608, HVUp, 13, siceWin), Bend(4610, HVDn, 6, 7, siceWin), BendIn(4612, HVUp, 13, siceWin)),
    TRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(110, "Manchuria north")
    str(106, "", "Japan south")
    str(104, "", "Guandong")
    str(102, "", "Philippines")
    str(100, "Borneo", "Guinee west")
    str(98, "Java", "Lesser Sunda")
    str(96, "Australia north west", "Australia north midst")
    str(94, "Australia south west", "Australia south midst")
  }
}