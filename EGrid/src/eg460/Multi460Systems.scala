/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._

/** Scenario for 2 Grid system for 0E and 30E */
object Scen460S0E1 extends EScenLongMulti
{ override val title: String = "460km 0E - 30E"
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(2, 0, 116)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 3 460km grid system for 90°E, 120°E and 150°E */
object Scen460ChinaJapan extends EScenLongMulti
{ override val title: String = "460km 90°E - 150°E"
  implicit override val gridSys: EGrid460LongMulti = EGrid460.multi(3, 3, 118, 122)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Terrain only scenario for North America. 4 460km grid system for 150°W, 120°W, 90°W and 60°W */
object Scen460NorthAmerica extends EScenLongMulti
{ override val title: String = "460km 150°W - 6°0W"
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(4, 7, 118, 120)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Scenario for 4 460km grid system for 30°W, 0°E, 30°E and 60°E. */
object Scen460S11E2 extends EScenLongMulti
{ override val title: String = "460km 30°W - 60°E"
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(4, 11, 118, 120)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}

/** Just terrain scenario for all longitudes grid system. */
object Scen460All extends EScenLongMulti
{ override val title: String = "460km all longitude terrain only scenario."
  override implicit val gridSys: EGrid460LongMulti = EGrid460.multi(12, 0, 118, 120)
  override val terrs: LayerHcRefSys[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
}