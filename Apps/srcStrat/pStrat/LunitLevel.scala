/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pStrat
import geom._

/** Military land unit level of organisation. */
trait LunitLevel extends Int1Elem
{ def desigStr: String

  def drawables: RArr[Drawable]
}

trait LunitSoleLike extends LunitLevel
{ def dashDrawable: LineSeg = LineSeg(-0.25, 0, 0.25, 0)
}

object LunitSole extends LunitSoleLike
{ override val int1: Int = 1
  override def desigStr: String = """-"""
  override def toString: String = "Individual Soldier"
  override def drawables: RArr[Drawable] = RArr(dashDrawable)
}

/** Pair of soldiers including "Fire and Manouver teams. */
object FirePair extends LunitSoleLike
{ override val int1: Int = 2
  override def desigStr: String = """--"""
  override def toString: String = "FirePair"
  override def drawables: RArr[Drawable] = RArr(iToMap(-1, 1, 2){ i => dashDrawable.slateX(i) })
}

/** A fire team or squad where the squad is a sub unit of a platoon section. */
object FireTeam extends LunitLevel
{ override val int1: Int = 3
  override def desigStr: String = "•"
  override def toString: String = "FireTeam"
  override def drawables: RArr[Drawable] = RArr(Circle(0.5))
}

/** Platoon section, or infantry squad. Given the conflicting terms around this size of unit, a new term was created.  */
object ZugSect extends LunitLevel
{ override val int1: Int = 5
  override def desigStr: String = "••"
  override def toString: String = "Section"
  override def drawables: RArr[Drawable] = iToMap(-1, 1, 2){ i => Circle(0.5).scale(1.5).slateX(i) }
}

object Platoon extends LunitLevel
{ override val int1: Int = 6
  override def desigStr: String = "•••"
  override def toString: String = "Platoon"
  override def drawables: RArr[Drawable] = iToMap(-2, 2, 2){ i => Circle(0.5).scale(1.5).slateX(i) }
}

object Echelon extends LunitLevel
{ override val int1: Int = 7
  override def desigStr: String = "••••"
  override def toString: String = "Echelon"
  override def drawables: RArr[Drawable] = iToMap(-3, 3, 2){ i => Circle(0.5).scale(1.5).slateX(i) }
}

object Company extends LunitLevel
{ override val int1: Int = 8
  override def desigStr: String = "|"
  override def toString: String = "Company"
  override def drawables: RArr[Drawable] = RArr(LineSeg(0, -9.75, 0, 0.75))
}

object Battalion extends LunitLevel
{ override val int1: Int = 9
  override def desigStr: String = "||"
  override def toString: String = "Battalion"
  override def drawables: RArr[Drawable] = RArr[Drawable](LineSeg(0, -9.75, 0, 0.75))
}

object Regiment extends LunitLevel
{ override val int1: Int = 10
  override def desigStr: String = "|||"
  override def toString: String = "Regiment"
  override def drawables: RArr[Drawable] = RArr(iToMap(-2, 2, 2){ i => LineSeg(0, -9.75, 0, 0.75).slateX(i) })
}

object Brigade extends LunitLevel
{ override val int1: Int = 11
  override def desigStr: String = "X"
  override def toString: String = "Brigade"
  override def drawables: RArr[Drawable] = RArr(Cross.diag.scale(1.5))
}

object Division extends LunitLevel
{ override val int1: Int = 12
  override def desigStr: String = "XX"
  override def toString: String = "Division"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-1, 1, 2){ i => Cross.diag.scale(1.5).slateX(i) } )
}

object Corps extends LunitLevel
{ override val int1: Int = 13
  override def desigStr: String = "XXX"
  override def toString: String = "Corps"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-2, 2, 2){ i => Cross.diag.scale(1.5).slateX(i) } )
}

object FieldArmy extends LunitLevel
{ override val int1: Int = 14
  override def desigStr: String = "XXXX"
  override def toString: String = "Field Army"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-3, 3, 2){ i => Cross.diag.scale(1.5).slateX(i) } )
}

object ArmyGroup extends LunitLevel
{ override val int1: Int = 15
  override def desigStr: String = "XXXXX"
  override def toString: String = "Army Group"
  override def drawables: RArr[Drawable] = RArr(iToFlatMap(-4, 2, 4){ i => Cross.diag.scale(1.5).slateX(i) } )
}