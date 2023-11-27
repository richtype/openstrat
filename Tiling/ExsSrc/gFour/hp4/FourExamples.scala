/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gFour; package hp4
import prid._, phex._, gOne.h1p.GSys

object FourScen1 extends G4HScen(0)
{ override implicit val gridSys: HGridReg = GSys.g1
  override val terrs: HCenLayer[Terr] = HCenLayer[Terr](Plain)
  import terrs.{setRowPartSame => srp}
  srp(6, 2, 2, Water)

  srp(4, 4, 2, Woods)
  override val lunits: HCenRArrLayer[LunitState] = HCenRArrLayer()
  lunits.set1(4, 4, LunitState(Lunit(TeamA, 1), HexDR))
  lunits.setArr(4, 8, LunitState(Lunit(TeamB, 1), HexLt, HexDL), LunitState(Lunit(TeamB, 2), HexLt))//, (6, 10, LunitState(Lunit(TeamA, 2))))
}

object FourScen2 extends G4HScen(0)
{ override implicit val gridSys: HGrid = HGridReg(2, 8, 2, 22)
  override val terrs: HCenLayer[Terr] = HCenLayer[Terr](Plain)
  import terrs.{setRowPartSame => srp}
  srp(6, 2, 2, Water)
  srp(4, 4, 2, Woods)
  override val lunits: HCenRArrLayer[LunitState] = HCenRArrLayer()
  lunits.setArr(4, 12, LunitState(Lunit(TeamA, 1), HexDR), LunitState(Lunit(TeamA, 2), HexRt), LunitState(Lunit(TeamA, 3), HexUR))
  //lunits.setSomesMut((4, 8, LunitState(TeamB, HexLt, HexDL)), (6, 10, LunitState(TeamA)))
}

/** 2nd Scenario of Game Four. Has a larger number of hexs. */
object FourScen3 extends G4HScen(0)
{ override implicit val gridSys: HGrid = HGridReg(2, 20, 4, 60)
  override val terrs: HCenLayer[Terr] = HCenLayer[Terr](Plain)
  import terrs.{setRowPartSame => sr}
  sr(6,6, 4, Water)
  sr(8,4, 5, Water)
  sr(10, 6, 4, Water)
  sr(16, 4, 4, Water)
  sr(14, 42, 3, Woods)
  sr(16, 44, 3, Woods)
  sr(18, 42, 3, Woods)
  override val lunits: HCenRArrLayer[LunitState] = HCenRArrLayer()
 // lunits.setSomeMut(4, 4, LunitState(TeamA))

}

/** 3rd Scenario of Game Four. Has a larger number of hexs. */
object FourScen4 extends G4HScen(0)
{
  override implicit val gridSys: HGridReg = HGridReg(2, 6, 2, 10)
  override val terrs: HCenLayer[Terr] = HCenLayer[Terr](Plain)
  override val lunits: HCenRArrLayer[LunitState] = HCenRArrLayer()
//  import terrs.{setRowEnd => sr}

}