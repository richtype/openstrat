/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait Length extends Any with Ordered[Length]
{ /** The number of metres in this [[Length]]. */
  def metresNum: Double

  /** The number of kilometres in this [[Length]]. */
  def kiloMetresNum: Double

  /** The negative of this [[Lenght]] */
  def unary_- : Length

  /** Add a length. Normally use the + operator for add [[Length]]s within the same measurmenet unit system. An alphanumeric name is used here to alert that the
   * developer that they are mixing different measurement unit systems. */
  def addLength(operand: Length): Length

  /** Subtract a length. Normally use the - operator for add [[Length]]s within the same measurmenet unit system. An alphanumeric name is used here to alert that the
   * developer that they are mixing different measurement unit systems. */
  def subLength(operand: Length): Length

  /** Multiply by the given scalar. */
  def *(operand: Double): Length

  /** Converts this [[Length]] to [[Metres]]. */
  def toMetres: Metres = Metres(metresNum)
}

trait MetricUnits extends Any

trait MetricLength extends Any with Length with MetricUnits
{ /** Add a [[Length]] defined in [[MetricLength]] units. Use addLength method if you wish to mix units from different unit measurement systems. */
  def +(operand: MetricLength): MetricLength

  /** Subtract a [[Length]] defined in [[MetricLength]] units. Use subLength method if you wish to mix units from different unit measurement systems. */
  def -(operand: MetricLength): MetricLength

  override def addLength(operand: Length): MetricLength
  override def subLength(operand: Length): MetricLength
  override def unary_- : MetricLength
  override def *(operand: Double): MetricLength
}

/** Length can be negative. The underlying data is stored in metres. */
final class Metres(val metresNum: Double) extends AnyVal with MetricLength
{ def typeStr: String = "Metres"

  override def toString: String = metresNum.str + "m"
  def str: String = "Length".appendParenth(metresNum.toString)
  override def +(operand: MetricLength): Metres = Metres(metresNum + operand.metresNum)
  override def addLength(operand: Length): Metres = Metres(metresNum + operand.metresNum)
  override def -(operand: MetricLength): Metres = Metres(metresNum - operand.metresNum)
  override def subLength(operand: Length): Metres = Metres(metresNum - operand.metresNum)
  override def unary_- : Metres = Metres(-metresNum)
  def *(operand: Double): Metres = Metres(metresNum * operand)
  def /(operand: Double): Metres = Metres(metresNum / operand)
  @inline def yardsNum: Double = metresNum * 1.09361
  @inline def milesNum: Double = metresNum / 1609.34
  @inline def mMilesNum: Double = metresNum / 1609340000

  /** Returns the max length of this and the operand length in [[Metres]]. */
  def max(operand: Metres): Metres = new Metres(metresNum.max(operand.metresNum))

  def min(operand: Metres): Metres = ife(metresNum < operand.metresNum, this, operand)
  def kmStr2 = (metresNum / 1000).str2 + "km"

  override def compare(that: Length): Int = metresNum.compare(that.metresNum)

  def pos: Boolean = metresNum >= 0
  def neg: Boolean = metresNum < 0

  @inline def kiloMetresNum: Double = metresNum / 1000
  @inline def mMetresNum: Double = metresNum / 1000000
  @inline def gMetresNum: Double = metresNum / 1000000000
}

/** Companion object for the [[Metres]] class. */
object Metres
{ def apply(metres: Double): Metres = new Metres(metres)

  implicit class MetreExtensions(thisMetres: Metres)
  { def * (operand: MetricLength): MetresSq = new MetresSq(thisMetres.metresNum * operand.metresNum)
    def / (operand: MetricLength): Double = thisMetres.metresNum / operand.metresNum
  }
}

/** Measurement of [[Length]] in Kilometres. can be negative. */
final class KiloMetres(val kiloMetresNum: Double) extends AnyVal with MetricLength
{ def typeStr: String = "Metres"

  override def compare(that: Length): Int = kiloMetresNum.compare(that.kiloMetresNum)

  override def metresNum: Double = kiloMetresNum * 1000

  override def +(operand: MetricLength): KiloMetres = KiloMetres(kMetresNum = operand.kiloMetresNum)
  override def addLength(operand: Length): KiloMetres = KiloMetres(kiloMetresNum + operand.kiloMetresNum)
  override def -(operand: MetricLength): KiloMetres = KiloMetres(kiloMetresNum - operand.kiloMetresNum)
  override def subLength(operand: Length): KiloMetres = KiloMetres(kiloMetresNum - operand.kiloMetresNum)
  override def unary_- : KiloMetres = KiloMetres(-kiloMetresNum)
  override def *(operand: Double): KiloMetres = KiloMetres(kiloMetresNum * operand)
}

object KiloMetres
{
  def apply(kMetresNum: Double): KiloMetres = new KiloMetres(kMetresNum)
}