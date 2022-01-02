/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from a single [[Double]]. These are used in [[ArrDbl1s]] Array[Int] based collections. */
trait ElemDbl1 extends Any with ElemDblN
{ def dbl1: Double
  def dblsEqual(that: ElemDbl1): Boolean = dbl1 == that.dbl1
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[ElemDbl1]]s. */
trait ArrDbl1s[A <: ElemDbl1] extends Any with ArrDblNs[A]
{ final override def elemProdSize: Int = 1
  def newElem(dblValue: Double): A
  final override def length: Int = arrayUnsafe.length
  final override def indexData(index: Int): A = newElem(arrayUnsafe(index))
  final override def unsafeSetElem(index: Int, elem: A): Unit = arrayUnsafe(index) = elem.dbl1
}