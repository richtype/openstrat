/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, collection.mutable.ArrayBuffer

/** Base trait for [[PersistBase2]] and [[PersistBase3Plus]] classes. it declares the common properties of name1, name2, opt1 and opt2. It is not a base trait
 *  for [[Show2]], as [[ShowTell2]] classes do not need this data, as they can delegate to the [[Tell2]] object to implement their interfaces. */
trait PersistBase2Plus[A1, A2] extends Any with PersistBaseN
{ /** 1st parameter name. */
  def name1: String

  /** 2nd parameter name. */
  def name2: String

  /** The optional default value for parameter 1. */
  def opt1: Option[A1]

  /** The optional default value for parameter 2. */
  def opt2: Option[A2]

  /** The declaration here allows the same field to cover [[Show]][A1], [[UnShow]][A1] and [[Persist]][A1]. */
  def persist1: Show[A1] | Unshow[A1]

  /** The declaration here allows the same field to be to cover [[Show]][A2] [[UnShow]][A2] and [[Persist]][A2]. */
  def persist2: Show[A2] | Unshow[A2]
}

/** A base trait for [[Tell2]] and [[UnShow2]]. It is not a base trait for [[Show2]], as [[ShowTell2]] classes do not need this data, as they can
 *  delegate to the [[Tell2]] object to implement their interfaces. */
trait PersistBase2[A1, A2] extends Any with PersistBase2Plus[A1, A2]
{ override def paramNames: StrArr = StrArr(name1, name2)
  override def numParams: Int = 2
}

/** [[Tell]] trait for classes with 2+ Show parameters. */
trait Show2Plused[A1, A2] extends Any with TellN with PersistBase2Plus[A1, A2]
{ /** The optional default value for parameter 1. */
  override def opt1: Option[A1] = None

  /** The optional default value for parameter 2. */
  override def opt2: Option[A2] = None

  /** Element 1 of this Show 2+ element product. */
  def show1: A1

  /** Element 2 of this Show 2+ element product. */
  def show2: A2

  override def persist1: Show[A1]

  override def persist2: Show[A2]
}

/** Show type class for 2 parameter case classes. */
trait Show2[A1, A2, R] extends ShowN[R]
{ def fArg1: R => A1
  def fArg2: R => A2
  implicit def persist1: Show[A1]
  implicit def persist2: Show[A2]

  override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr =
    StrArr(persist1.showDecT(fArg1(obj), way, maxPlaces, 0), persist2.showDecT(fArg2(obj), way, maxPlaces, 0))
}

/** Companion object for the [[Show2]] type class trait that shows object with 2 logical fields. */
object Show2
{
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, opt2: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit persist1: Show[A1], persist2: Show[A2]): Show2[A1, A2, R] =
    new Show2Imp[A1, A2, R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1In)

  /** Implementation class for the general cases of [[Show2]] trait. */
  class Show2Imp[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val opt2: Option[A2] = None,
    opt1In: Option[A1] = None)(implicit val persist1: Show[A1], val persist2: Show[A2]) extends Show2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
    override def syntaxDepthT(obj: R): Int = persist1.syntaxDepthT(fArg1(obj)).max(persist2.syntaxDepthT(fArg2(obj))) + 1
  }
}

/** Extension methods for [[Show2]] type class instances. */
class Show2Extensions[A1, A2, -T](ev: Show2[A1, A2, T], thisVal: T)
{
  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[Show]] type class instances. */
  def show2(way: ShowStyle = ShowStandard, way1: ShowStyle = ShowStandard, places1: Int = -1, way2: ShowStyle = ShowStandard, places2: Int = -1):
    String = ???
}

/** [[Show]] type class trait for types with 2 [[Int]] Show components. */
trait ShowInt2[R] extends Show2[Int, Int, R]
{ override def persist1: Persist[Int] = Show.intPersistEv
  override def persist2: Persist[Int] = Show.intPersistEv
  override def syntaxDepthT(obj: R): Int = 2
}

object ShowInt2
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, opt2: Option[Int] = None, opt1In: Option[Int] = None):
    ShowInt2[R] = new ShowInt2Imp[R](typeStr, name1, fArg1, name2, fArg2, opt2, opt1In)

  /** Implementation class for the general cases of [[ShowInt2]] trait. */
  class ShowInt2Imp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int, val opt2: Option[Int] = None,
    opt1In: Option[Int] = None) extends ShowInt2[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** UnShow type class trait for a 2 element Product. */
trait Unshow2[A1, A2, R] extends UnshowN[R] with PersistBase2[A1, A2]
{ /** The UnShow type class instance for type A1. */
  def persist1: Unshow[A1]

  /** The UnShow type class instance for type A2. */
  def persist2: Unshow[A2]

  def newT: (A1, A2) => R

  protected def fromSortedExprs(sortedExprs: RArr[Expr], pSeq: IntArr): EMon[R] =
  { val len: Int = sortedExprs.length
    val r0: EMon[A1] = ife(len > pSeq(0), persist1.fromSettingOrExpr(name1, sortedExprs(pSeq(0))), opt1.toEMon)
    def e2: EMon[A2] = ife(len > pSeq(1), persist2.fromSettingOrExpr(name2,sortedExprs(pSeq(1))), opt2.toEMon)
    r0.map2(e2)(newT)
  }
}

object Unshow2
{
  def apply[A1, A2, R](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R, opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit
    ev1: Unshow[A1], ev2: Unshow[A2]): Unshow2[A1, A2, R] = new Unshow2Imp[A1, A2, R](typeStr, name1, name2, newT, opt2, opt1)

  case class Unshow2Imp[A1, A2, R](typeStr: String, name1: String, name2: String, newT: (A1, A2) => R, val opt2: Option[A2], opt1In: Option[A1])(implicit
    val persist1: Unshow[A1], val persist2: Unshow[A2]) extends Unshow2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/** Persistence class for product 2 type class. It ShowTs and UnShows objects with 2 logical parameters. */
trait Persist2[A1, A2, R] extends Show2[A1, A2, R] with Unshow2[A1, A2, R] with PersistN[R]
{ override def persist1: Persist[A1]
  override def persist2: Persist[A2]
}

/** Factory object for Persist product 2 type class that persists objects with 2 parameters. */
object Persist2
{ /** apply facory method for [[Persist2]] type class instances. */
  def apply[A1, A2, R](typeStr: String, name1: String, fArg1: R => A1, name2: String, fArg2: R => A2, newT: (A1, A2) => R,
    opt2: Option[A2] = None, opt1: Option[A1] = None)(implicit ev1: Persist[A1], ev2: Persist[A2]): Persist2[A1, A2, R] =
    new Persist2Imp(typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1)(ev1, ev2)

  class Persist2Imp[A1, A2, R](val typeStr: String, val name1: String, val fArg1: R => A1, val name2: String, val fArg2: R => A2, val newT: (A1, A2) => R,
    val opt2: Option[A2] = None, opt1In: Option[A1] = None)(implicit val persist1: Persist[A1], val persist2: Persist[A2]) extends Persist2[A1, A2, R]
  { val opt1: Option[A1] = ife(opt2.nonEmpty, opt1In, None)

    override def strDecs(obj: R, way: ShowStyle, maxPlaces: Int): StrArr = ???

    /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
     * should always be determined at compile time or if sometimes it should be determined at runtime. */
    override def syntaxDepthT(obj: R): Int = ???
  }
}

trait PersistInt2[R] extends Persist2[Int, Int, R] with ShowInt2[R]

object PersistInt2
{
  def apply[R](typeStr: String, name1: String, fArg1: R => Int, name2: String, fArg2: R => Int, newT: (Int, Int) => R, opt2: Option[Int] = None,
    opt1In: Option[Int] = None): ShowInt2[R] = new PersistInt2Imp[R](typeStr, name1, fArg1, name2, fArg2, newT, opt2, opt1In)

  /** Implementation class for the general cases of [[ShowInt2]] trait. */
  class PersistInt2Imp[R](val typeStr: String, val name1: String, val fArg1: R => Int, val name2: String, val fArg2: R => Int,
    val newT: (Int, Int) => R, val opt2: Option[Int] = None, opt1In: Option[Int] = None) extends PersistInt2[R]
  { val opt1: Option[Int] = ife(opt2.nonEmpty, opt1In, None)
  }
}

/**  Class to persist [[Int2Arr]] collection classes. */
abstract class PersistArrInt2s[A <: Int2Elem, M <: Int2Arr[A]](val typeStr: String) extends IntNSeqLikePersist[A, M]
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: A): Unit =
  { buf += value.int1
    buf += value.int2
  }

  override def syntaxDepthT(obj: M): Int = 3
}