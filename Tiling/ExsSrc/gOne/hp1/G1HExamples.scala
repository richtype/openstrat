/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne; package hp1
import prid._, phex._, gPlay._

/** 1st example Turn 0 scenario state for Game One hex. */
object G1HScen1 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = GSys.g1
  val players: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  players.setSomeMut(4, 4, PlayerA)
  players.setSomesMut((4, 8, PlayerB), (6, 10, PlayerC))
}

/** 2nd example Turn 0 scenario state for Game One hex. */
object G1HScen2 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = GSys.g2
  val players: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  players.setSomeMut(6, 38, PlayerA)
  players.setSomesMut((4, 40, PlayerB), (6, 42, PlayerC))
}

/** 3rd example Turn 0 scenario state for Game One hex. */
object G1HScen3 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridIrr = GSys.g3
  val players: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  players.setSomesMut((4, 4, PlayerA), (10, 6, PlayerB), (8, 8, PlayerC))
}

/** 3rd example Turn 0 scenario state for Game One. */
object G1HScen7 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(10, (1, 6), (2, 4), (3, 2), (2, 4), (1, 6))
  val players: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  players.setSomesMut((4, 4, PlayerA), (10, 6, PlayerB), (8, 8, PlayerC))
}

/** 2nd example Turn 0 scenario state for Game One. */
object G1HScen4 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 10, 4, 8)
  val players: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  players.setSomesMut((4, 4, PlayerA), (8, 4, PlayerB), (6, 6, PlayerC))
}

/** 8th example Turn 0 scenario state for Game One. An empty regular grid containing no hex tiles. */
object G1HScen8 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(4, 2, 4, 2)
  val players: HCenOptLayer[Player] = gridSys.newHCenOptLayer
}

/** 9th example Turn 0 scenario state for Game One. An empty irregular grid containing no hex tiles. */
object G1HScen9 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(2)
  val players: HCenOptLayer[Player] = gridSys.newHCenOptLayer
}

/** 10th example Turn 0 scenario state for Game One. Uses an [[HGridIrr]] */
object G1HScen10 extends G1HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(12, (12, 4), (4, 6), (1, 8), (4, 2), (2, 4), (1, 6))
  val players: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  players.setSomesMut((4, 4, PlayerA), (10, 6, PlayerB), (8, 8, PlayerC))
}