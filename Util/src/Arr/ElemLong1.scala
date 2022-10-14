/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from a single [[Long]]. These are used in [[Long1Arr]] Array[Int] based collections. */
trait ElemLong1 extends Any with ElemLongN
{ def long1: Long
  @inline def _1 : Long = long1
}

/** A specialised immutable, flat Array[Long] based collection of a type of [[ElemLong1]]s. */
trait Long1Arr[A <: ElemLong1] extends Any with LongNArr[A]
{ final override def elemProdSize: Int = 1
  def newElem(long1: Long): A
  final override def apply(index: Int): A = newElem(unsafeArray(index))
  final override def unsafeSetElem(index: Int, elem: A): Unit = unsafeArray(index) = elem.long1
  override def elemEq(a1: A, a2: A): Boolean = a1.long1 == a2.long1
}

/** A specialised flat ArrayBuffer[long] based trait for [[ElemLong1]]s collections. */
trait Long1Buff[A <: ElemLong1, ArrA <: Long1Arr[A]] extends Any with LongNBuff[A]
{ override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem._1); () }
}