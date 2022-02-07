/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Common super trait for [[ShowPrec]], [[ShowT]] and [[Unshow]]. All of which inherit the typeStr property. */
trait TypeStr extends Any
{ /** The RSON type of T. This the only data that a ShowT instance requires, that can't be implemented through delegation to an object of type
    * Show. */
  def typeStr: String
}

trait Show extends Any with TypeStr
{ /** The most basic Show method, paralleling the strT method on ShowT type class instances. */
  def str: String

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  def show(style: ShowStyle = ShowStandard, maxPlaces: Int = -1): String = show(style, maxPlaces, maxPlaces)

  /** Intended to be a multiple parameter comprehensive Show method. Intended to be paralleled by showT method on [[ShowT]] type class instances. */
  def show(style: ShowStyle, maxPlaces: Int, minPlaces: Int): String

  def syntaxDepth: Int

  override def toString: String = str
}

/** A trait for providing an alternative to toString. USing this trait can be convenient, but at some level of the inheritance the type must provide a
 *  ShowT type class instance. It is better for the [[ShowT]] type class instance to delegate to this trait than have the toString method delegate to
 *  the [[ShowT]] type class instance in the companion object. Potentially that can create initialisation order problems, but at the very least it
 *  can increase compile times. The capabilities of decimal place precision and explicit typing for numbers are placed defined here and in the
 *  corresponding [[SHowT]] type class although they have n meaning / purpose for many types, as seperating them adds enormous complexity for very
 *  little gain. */
trait ShowPrec extends Any with Show
{ def str: String = show(ShowStandard, -1, 0)
  def str0: String = show(ShowStandard, 0, 0)
  def str1: String = show(ShowStandard, 1, 1)
  def str2: String = show(ShowStandard, 2, 2)
  def str3: String = show(ShowStandard, 3, 3)
}

/** Currently can't think of a better name for this trait */
sealed trait ShowStyle

/** Show the object just as its comma separated constituent values. */
object ShowCommas extends ShowStyle

/** Show the object as semicolon separated constituent values. */
object ShowSemis extends ShowStyle

/** Show the object in the standard default manner. */
object ShowStandard extends ShowStyle

/** Show the object in the standard default manner, with parameter names. */
object ShowParamNames extends ShowStyle

/** Show the object as semicolon separated constituent values preceded b y their parameter names. */
object ShowSemisNames extends ShowStyle

/** Show the object in the standard default manner, with field names and their types. */
object ShowStdTypedFields extends ShowStyle

/** Show the object with the type of the object even if the string representation does not normally states its type. Eg Int(7). */
object ShowTyped extends ShowStyle

/** Represents the object with an underscore. */
object ShowUnderScore extends ShowStyle