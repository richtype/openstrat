/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from a single [[Int]]. These are used in [[Int1Arr]] Array[Int] based collections. */
trait ElemInt1 extends Any with ElemIntN
{ def intValue: Int
  @inline def int1 : Int = intValue
  override def intForeach(f: Int => Unit): Unit = { f(int1) }
}

trait Int1SeqLike[A <: ElemInt1] extends Any with IntNSeqLike[A]
{ override def elemProdSize: Int = 1
  final override def unsafeSetElem(index: Int, elem: A): Unit = { unsafeArray(index) = elem.int1 }
  override def intBufferAppend(buffer: ArrayBuffer[Int], elem: A) : Unit = { buffer.append(elem.int1) }
}

/** A specialised immutable, flat Array[Int] based trait defined by a data sequence of a type of [[ElemInt1]]s. */
trait Int1SeqSpec[A <: ElemInt1] extends Any with Int1SeqLike[A] with IntNSeqSpec[A]
{ final override def ssIndex(index: Int): A = ssElem(unsafeArray(index))

  /** Constructs an element of the specifing sequence from an [[Int]] value. */
  def ssElem(intValue: Int): A
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[ElemInt1]]s. */
trait Int1Arr[A <: ElemInt1] extends Any with IntNArr[A] with Int1SeqLike[A]
{ final override def length: Int = unsafeArray.length

  /** Functionally appends the operand of type A. This alphanumeric method is not aliased by the ++ operator, to avoid confusion with numeric operators. */
  def append(op: A): ThisT =
  { val newArray = new Array[Int](length + 1)
    unsafeArray.copyToArray(newArray)
    newArray(length) = op.int1
    fromArray(newArray)
  }

  def newElem(intValue: Int): A
  final override def apply(index: Int): A = newElem(unsafeArray(index))
  override def elemEq(a1: A, a2: A): Boolean = a1.int1 == a2.int1
}

/** Trait for creating the ArrTBuilder type class instances for [[Int1Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to
 *  the B in ```map(f: A => B): ArrB``` function. */
trait Int1ArrBuilder[A <: ElemInt1, ArrT <: Int1Arr[A]] extends IntNArrBuilder[A, ArrT]
{ type BuffT <: Int1Buff[A]

  final override def elemProdSize: Int = 1
  def newArray(length: Int): Array[Int] = new Array[Int](length)
  final override def arrSet(arr: ArrT, index: Int, value: A): Unit =  arr.unsafeArray(index) = value.int1
  override def buffGrow(buff: BuffT, value: A): Unit = { buff.unsafeBuffer.append(value.int1); () }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Int1Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait Int1ArrFlatBuilder[A <: ElemInt1, ArrT <: Int1Arr[A]] extends IntNArrFlatBuilder[A, ArrT]
{ type BuffT <: Int1Buff[A]
  final override def elemProdSize: Int = 1
  def newArray(length: Int): Array[Int] = new Array[Int](length)
}

/** A specialised flat ArrayBuffer[Int] based trait for [[ElemInt1]]s collections. */
trait Int1Buff[A <: ElemInt1] extends Any with IntNBuff[A]
{ type ArrT <: Int1Arr[A]
  final override def length: Int = unsafeBuffer.length
  def intToT(value: Int): A
  def apply(i1: Int): A = intToT(unsafeBuffer(i1))
  override def elemProdSize: Int = 1
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1); () }

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  override def unsafeSetElem(i: Int, value: A): Unit = unsafeBuffer(i) = value.int1
}

/** Helper class for companion objects of final [[Int1SeqSpec]] classes. */
trait Int1SeqLikeCompanion[A <: ElemInt1, ArrA <: Int1SeqLike[A]] extends IntNSeqLikeCompanion[A, ArrA]
{
  override def elemNumInts: Int = 1

  /** Apply factory method */
  def apply(elems: A*): ArrA =
  { val arrLen: Int = elems.length
    val res = uninitialised(elems.length)
    var count: Int = 0

    while (count < arrLen)
    { res.unsafeArray(count) = elems(count).int1
      count += 1
    }
    res
  }
}