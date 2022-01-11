/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A sub trait of the [[ShowT]] sub class where the type parameter of ShowT extends Show. This allows the ShowT type class to delegate to the Show
 * class for the implementation of its strT and ShowT methods. It is better to use [[ShowPrec]] and ShowElemT for types you control than have the toString
 * method delegate to the [[ShowPrecisionT]] type class instance in the companion object. Potentially that can create initialisation order problems, but at the
 * very least it can increase compile times. */
trait ShowShowT[R <: Show] extends ShowT[R]
{
  /** Provides the standard string representation for the object. Its called ShowT to indicate this is a type class method that acts upon an object
   * rather than a method on the object being shown. */
  override def strT(obj: R): String = obj.str

  /** Simple values such as Int, String, Double have a syntax depth of one. A Tuple3[String, Int, Double] has a depth of 2. Not clear whether this
   * should always be determined at compile time or if sometimes it should be determined at runtime. */
  override def syntaxDepthT(obj: R): Int = obj.syntaxDepth
}

/** A sub trait of the [[ShowPrecisionT]] sub class where the type parameter of ShowT extends [[ShowPrec]]. This allows the ShowT type class to delegate to the Show
 * class for the implementation of its strT and ShowT methods. It is better to use [[ShowPrec]] and ShowElemT for types you control than have the toString
 * method delegate to the [[ShowPrecisionT]] type class instance in the companion object. Potentially that can create initialisation order problems, but at the
 * very least it can increase compile times. */
trait ShowPrecisionShowT[R <: ShowPrec] extends ShowPrecisionT[R] with ShowShowT[R]
{
  override def showT(obj: R, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = obj.show(way, maxPlaces, 0)
}

object ShowPrecisionShowT
{
  def apply[R <: ShowPrec](typeStrIn: String): ShowPrecisionShowT[R] = new ShowPrecisionShowT[R]
  { override def typeStr: String = typeStrIn
  }
}