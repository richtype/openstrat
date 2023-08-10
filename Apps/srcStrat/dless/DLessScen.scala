/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import prid._, phex._, eg320._, egrid._

/** Scenario trait for Diceless. */
trait DLessScen extends HSysTurnScen
{ ThisScen =>
  def title: String = "DLessScen"
  override implicit val gridSys: EGridSys
  val terrs: HCenLayer[WTile]
  val sTerrs: HSideOptLayer[WSide, WSideSome]
  val corners: HCornerLayer
  val armies: HCenRArrLayer[Army]

  def endTurn(orderList: HCenStepPairArr[Army]): DLessScen =
  { val targets: HCenBuffLayer[HCenPair[Army]] = gridSys.newHCenArrOfBuff

    orderList.foreach { pair =>
      val optTarget: Option[HCen] = pair.startHC.stepOpt(pair.step)
      optTarget.foreach { target => if (terrs(target).isLand) targets.appendAt(target, HCenPair(pair.startHC, pair.a2)) }
    }

    val armiesNew: HCenRArrLayer[Army] = armies.copy
    targets.foreach { (hc2, buff) => buff.foreachLen1(hcPair => if (armies.emptyTile(hc2)) armiesNew.moveUnsafe(hcPair.a1, hc2, hcPair.a2)) }

    new DLessScen
    { override implicit val gridSys: EGridSys = ThisScen.gridSys
      override val terrs: HCenLayer[WTile] = ThisScen.terrs
      override val sTerrs: HSideOptLayer[WSide, WSideSome] = ThisScen.sTerrs
      override val corners: HCornerLayer = ThisScen.corners
      override val armies: HCenRArrLayer[Army] = armiesNew
      override def turn: Int = ThisScen.turn + 1
    }
  }
}

/** The main scenario for Diceless. */
object DLessScen1 extends DLessScen
{
  override def turn: Int = 0

  override implicit val gridSys: EGrid320LongMulti = EGrid320.multi(2, 0, 124)

  override val terrs: HCenLayer[WTile] = fullTerrsHCenLayerSpawn
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = fullTerrsSideLayerSpawn
  override val corners: HCornerLayer = fullTerrsCornerLayerSpawn
  implicit val nations: RArr[Nation] = RArr(Britain, France, Germany, Austria, Russia, Ottoman, Italy, Spain)

  override val armies: HCenRArrLayer[Army] =
  { val res = HCenRArrLayer[Army]()
    implicit val counters: ArrCounters[Nation] = ArrCounters(nations)
    res.setFSomesMut(Britain.armyNext, 142,514,  144,508)
    res.setFSomesMut(Germany.armyNext, 140,520,  144,1528,  144,520,  142,1526)
    res.setFSomesMut(France.armyNext, 138,514,  140,516)
    res.setFSomesMut(Russia.armyNext, 142,1534,  148,1536)
    res.setFSomesMut(Italy.armyNext, 134,526,  136,524)
    res.setFSomesMut(Austria.armyNext, 136,1528,  138,526)
    res.setFSomesMut(Ottoman.armyNext, 132,1532, 132,1548)
    res.setFSomesMut(Spain.armyNext, 132,508,  130,510)
    res
  }

  val nationMap: HCenLayer[NationOpt] = HCenLayer[NationOpt](NationLess)
  terrs.hcForeach{ (hc, terr) => if(terr.isLand) nationMap.set(hc, Neutral) }
  //nationMap.setRowPartSame(138, 510, 3, France)
}

/** 2nd scenario for Diceless. Might have some use. */
object DLessScen2 extends DLessScen
{ deb("Diceless Scen 2")
  override def turn: Int = 0

  override implicit val gridSys: EGrid320Long = BritReg.britGrid

  override val terrs: HCenLayer[WTile] = BritReg.britTerrs
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = BritReg.britSTerrs
  override val corners: HCornerLayer = HCornerLayer()
  override val armies: HCenRArrLayer[Army] = HCenRArrLayer()
}