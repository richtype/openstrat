/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from a single [[Int]]. These are used in [[ArrInt1s]] Array[Int] based collections. */
trait ElemInt1 extends Any with ElemIntN
{ def intValue: Int
  @inline def int1 : Int = intValue
}

/** A specialised immutable, flat Array[Int] based trait defined by a data sequence of a type of [[ElemInt1]]s. */
trait Int1SeqDef[A <: ElemInt1] extends Any with IntNSeqDef[A]
{
  override def elemProdSize: Int = 1
  final override def indexData(index: Int): A = dataElem(unsafeArray(index))
  def dataElem(intValue: Int): A
  final override def unsafeSetElem(index: Int, elem: A): Unit = { unsafeArray(2 * index) = elem.int1 }
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[ElemInt1]]s. */
trait ArrInt1s[A <: ElemInt1] extends Any with IntNArr[A] with Int1SeqDef[A]
{
  final override def length: Int = unsafeArray.length

  /** This method could be made more general. */
  def findIndex(value: A): OptInt =
  {
    var count = 0
    var acc: OptInt = NoInt
    var continue = true
    while (continue == true & count < dataLength)
    {
      if (value.intValue == unsafeArray(count))
      { acc = SomeInt(count)
        continue = false
      }
      count += 1
    }
    acc
  }

  /** Functionally appends the operand of type A. This alphanumeric method is not aliased by the ++ operator, to avoid confusion with numeric operators. */
  def append(op: A): ThisT =
  { val newArray = new Array[Int](dataLength + 1)
    unsafeArray.copyToArray(newArray)
    newArray(dataLength) = op.int1
    unsafeFromArray(newArray)
  }
}

/** Trait for creating the ArrTBuilder type class instances for [[Int1Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to
 *  the B in ```map(f: A => B): ArrB``` function. */
trait ArrInt1sBuilder[A <: ElemInt1, ArrT <: ArrInt1s[A]] extends IntNArrBuilder[A, ArrT]
{ type BuffT <: Int1Buff[A]

  final override def elemProdSize: Int = 1
  def newArray(length: Int): Array[Int] = new Array[Int](length)
  final override def arrSet(arr: ArrT, index: Int, value: A): Unit =  arr.unsafeArray(index) = value.int1
  override def buffGrow(buff: BuffT, value: A): Unit = { buff.unsafeBuffer.append(value.int1); () }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Int1Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait ArrInt1sFlatBuilder[A <: ElemInt1, ArrT <: ArrInt1s[A]] extends IntNArrFlatBuilder[A, ArrT]
{ type BuffT <: Int1Buff[A]

  final override def elemProdSize: Int = 1
  def newArray(length: Int): Array[Int] = new Array[Int](length)
  //final override def arrSet(arr: ArrT, index: Int, value: A): Unit =  arr.arrayUnsafe(index) = value.int1
  //override def buffGrow(buff: BuffT, value: A): Unit = { buff.buffer.append(value.int1); () }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[ElemInt1]]s collections. */
trait Int1Buff[A <: ElemInt1] extends Any with IntNBuff[A]
{ type ArrT <: ArrInt1s[A]
  final override def length: Int = unsafeBuffer.length
  def intToT(value: Int): A
  def indexData(i1: Int): A = intToT(unsafeBuffer(i1))
  override def elemProdSize: Int = 1
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1); () }

  /** Sets / mutates an element in the Arr. This method should rarely be needed by end users, but is used by the initialisation and factory
   * methods. */
  override def unsafeSetElem(i: Int, value: A): Unit = unsafeBuffer(i) = value.int1
}