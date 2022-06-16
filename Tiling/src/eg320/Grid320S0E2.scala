/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._

object Grid320S0E2 extends EGrid320WarmMulti
{ ThisSys =>

  override val grids: Arr[EGridWarm] = Arr(EGrid320.e0(), EGrid320.e30(), EGrid320.e60())
  override def headGridInt: Int = 0
  override def cGridDelta: Double = 40
  override val gridMans: Arr[EGridWarmMan] = iToMap(0, 2)(EGridWarmMan(_, ThisSys))

  override def adjTilesOfTile(tile: HCen): HCenArr = ???

  //override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???
}

object Scen320S0E2 extends EScenWarmMulti
{ override val gridSys: EGrid320WarmMulti = Grid320S0E2
  override def warms: Arr[WarmTerrs] = Arr(Terr320E0, Terr320E30, Terr320E60)
  override val title: String = "320km 0E - 60E"
}