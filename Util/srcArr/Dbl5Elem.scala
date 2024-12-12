/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 5 [[Double]]s. These are used in [[ArrDbl5]] Array[Double] based collections. */
trait Dbl5Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double

  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3); f(dbl4); f(dbl5) }
  override def dblBufferAppend(buffer: ArrayBuffer[Double]): Unit = buffer.append5(dbl1, dbl2, dbl3, dbl4, dbl5)
}

trait SeqLikeDbl5[A <: Dbl5Elem] extends Any with SeqLikeDblN[A]
{ def elemProdSize: Int = 5

  final override def setElemUnsafe(index: Int, newElem: A): Unit =
    arrayUnsafe.setIndex5(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[Dbl5Elem]]s. */
trait SeqSpecDbl5[A <: Dbl5Elem] extends Any with SeqLikeDbl5[A] with SeqSpecDblN[A]
{  /** Method for creating new specifying sequence elements from 5 [[Double]]s In the case of [[ArrDbl5]] this will be the type of the elements of the
   * sequence. */
  def ssElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A

  def ssIndex(index: Int): A = ssElem(arrayUnsafe(5 * index), arrayUnsafe(5 * index + 1), arrayUnsafe(5 * index + 2), arrayUnsafe(5 * index + 3),
    arrayUnsafe(5 * index + 4))

  override def ssElemEq(a1: A, a2: A): Boolean =
    (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4) & (a1.dbl5 == a2.dbl5)
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl5Elem]]s. */
trait ArrDbl5[A <: Dbl5Elem] extends Any with ArrDblN[A] with SeqLikeDbl5[A]
{ def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A
  final override def length: Int = arrayUnsafe.length / 5
  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def head3: Double = arrayUnsafe(2)
  def head4: Double = arrayUnsafe(3)
  def head5: Double = arrayUnsafe(4)

  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5)))

  @targetName("appendElem") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](arrayLen + 5)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex5(length, operand.dbl1, operand.dbl2, operand.dbl3, operand.dbl4, operand.dbl5)
    fromArray(newArray)
  }
}

trait BuilderSeqLikeDbl5[BB <: SeqLikeDbl5[?]] extends BuilderSeqLikeDblN[BB]
{ type BuffT <: BuffDbl5[?]
  final override def elemProdSize: Int = 5
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrDbl5]] final classes. Instances for the [[BuilderArrMap]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[Dbl5Elem]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderArrDbl5Map[B <: Dbl5Elem, ArrB <: ArrDbl5[B]] extends BuilderSeqLikeDbl5[ArrB] with BuilderArrDblNMap[B, ArrB]
{ type BuffT <: BuffDbl5[B]

  override def indexSet(seqLike: ArrB, index: Int, newElem: B): Unit =
    seqLike.arrayUnsafe.setIndex5(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrDbl5]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl5Elem]]. Instances for
 *  [[BuilderArrFlat] should go in the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B
 *  in ```map(f: A => B): ArrB``` function. */
trait BuilderArrDbl5Flat[ArrB <: ArrDbl5[?]] extends BuilderSeqLikeDbl5[ArrB] with BuilderArrDblNFlat[ArrB]

/** Helper class for companion objects of final [[SeqSpecDbl5]] classes. */
abstract class CompanionSeqLikeDbl5[A <: Dbl5Elem, ArrA <: SeqLikeDbl5[A]] extends CompanionSeqLikeDblN[A, ArrA]
{ override def numElemDbls: Int = 5

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val array = new Array[Double](length)
    var i: Int = 0
    while (i < length)
    { array.setIndex5(i, elems(i).dbl1, elems(i).dbl2, elems(i).dbl3, elems(i).dbl4, elems(i).dbl5)
      i += 1
    }
    fromArray(array)
  }
}

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl5Elem]]s collections. */
trait BuffDbl5[A <: Dbl5Elem] extends Any with BuffDblN[A]
{ type ArrT <: ArrDbl5[A]
  override def elemProdSize: Int = 5
  final override def length: Int = unsafeBuffer.length / 5
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A
  override def grow(newElem: A): Unit = unsafeBuffer.append5(newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)

  override def apply(index: Int): A = newElem(unsafeBuffer(index * 5), unsafeBuffer(index * 5 + 1), unsafeBuffer(index * 5 + 2),
    unsafeBuffer(index * 5 + 3), unsafeBuffer(index * 5 + 4))

  override def setElemUnsafe(i: Int, newElem: A): Unit =
    unsafeBuffer.setIndex5(i, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)
}