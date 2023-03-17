/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._, pgui._

/** A Civ scenario turn state. */
trait CivScen  extends HSysTurnScen
{ override def title: String = "Civ Scenario"

  /** tile terrain. */
  def terrs: HCenLayer[VTile]
  val corners: HCornerLayer
  val lunits: HCenArrLayer[Warrior]
}

/** A Civ scenario state at turn 0. */
trait CivScenStart extends CivScen
{ override def turn: Int = 0
}


/** Civ scenario 1. */
object Civ1 extends CivScenStart
{
  override implicit val gridSys: HGrid = HGridReg(2, 12, 4, 40)
  val terrs: HCenLayer[VTile] = gridSys.newHCenLayer[VTile](Land())
  terrs.setRowEnd(12, 20, Land(Hill), Land(Mountain) * 2, Land() * 3)
  terrs.setRowEnd(4, 4, Land(Hill) * 3, Land(Plain) * 7)
  val lunits: HCenArrLayer[Warrior] = gridSys.newHCenArrLayer[Warrior]
  lunits.set(10, 18, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))

  override val corners: HCornerLayer = gridSys.newHVertOffsetLayer
}

/** Civ scenario 2. */
object Civ2 extends CivScenStart
{ override val title: String = "CivRise Scen 2"
  override implicit val gridSys: HGrid = HGridReg(2, 12, 4, 40)
  val terrs: HCenLayer[VTile] = gridSys.newHCenLayer[VTile](Sea)
  terrs.setRowEndUnchecked(4, Land(Mountain) * 3, Land(Plain) * 2)
  terrs.setRow(8, Land(Plain) * 4, Head1Land(4), Sea * 5)
  terrs.setRow(6, Land(Plain) * 3, Sea * 2, Head2Land(2, Mountain), Sea * 3)
  val lunits: HCenArrLayer[Warrior] = gridSys.newHCenArrLayer[Warrior]
  lunits.set(8, 16, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))

  override val corners: HCornerLayer = gridSys.newHVertOffsetLayer
}