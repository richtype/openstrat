/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

trait ElemPair[A1, A2] extends Any
{ def a1: A1
  def a2: A2
}

/** An element that pairs a [[SeqSpec]] with a second value. */
trait ElemSeqLikePair[A1E, A1 <: SeqLike[A1E], A2] extends ElemPair[A1, A2] with SpecialT
{ def a1: A1
  def a2: A2
}

trait PairArr[A1, A1Arr <: Arr[A1], A2, A <: ElemPair[A1, A2]] extends Arr[A]
{
  def a1Arr: A1Arr
  def a2Array: Array[A2]
  def a2Arr: RArr[A2] = new RArr[A2](a2Array)
  override final def length: Int = a2Array.length
}

trait PairArrBuilder[B1, ArrB1 <: Arr[B1], B2, B <: ElemPair[B1, B2], ArrB <: Arr[B]] extends ArrBuilder[B, ArrB]
{
  /** Builder for an Arr of the first element of the pair. */
  def b1ArrBuilder: ArrBuilder[B1, ArrB1]

  /** Builder for the sequence of pairs, takes the results of the other two builder methods to produce the end product. Pun intended */
  def pairArrBuilder(polygonArr: ArrB1, a2s: Array[B2]): ArrB
}

/** A sequence of [[ElemSeqLikePair]]s stored in 2 [[Array]]s for efficiency. */
trait SeqLikePairArr[A1E, A1 <: SeqSpec[A1E], A1Arr <: Arr[A1], A2, A <: ElemSeqLikePair[A1E, A1, A2]] extends PairArr[A1, A1Arr, A2, A]
{
  /** Maps the first component of the pairs, dropping the second. */
  def a1Map[B, ArrB <: Arr[B]](f: A1 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = a1Arr.map(f)

  /** Maps the second component of the pairs, dropping the first. */
  def a2Map[B, ArrB <: Arr[B]](f: A2 => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = a2Arr.map(f)

  /** Needs rewriting. */
  def pairMap[B, ArrB <: Arr[B]](f: (A1, A2) => B)(implicit builder: ArrBuilder[B, ArrB]): ArrB = map(p => f(p.a1, p.a2))
}

/** A buffer of [[ElemSeqLikePair]]s stored in 2 [[ArrayBuffer]]s for efficiency. */
trait SeqSpecPairBuff[A1E, A1 <: SeqSpec[A1E], A2, A <: ElemSeqLikePair[A1E, A1, A2]] extends Sequ[A]
{ def a2Buff: ArrayBuffer[A2]
  override def length: Int = a2Buff.length
}

trait SeqSpecPairArrBuilder[B1E, B1 <: SeqSpec[B1E], ArrB1 <: Arr[B1], B2, B <: ElemSeqLikePair[B1E, B1, B2], ArrB <: Arr[B]] extends
  PairArrBuilder[B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def b1Builder: SeqLikeImutBuilder[B1E, B1]
}


/** Helper trait for Companion objects of [[DblNArr]] classes. */
/*
trait DblNPairCompanion[A <: ElemDblN, AA <: DblNSeqLike[A]] extends SeqLikeCompanion[A, AA]
{ /** The number of [[Double]] values that are needed to construct an element of the defining-sequence. */
  def elemNumDbls: Int

  /** Method to create the final object from the backing Array[Double]. End users should rarely have to use this method. */
  def fromArray(array: Array[Double]): AA

  /** returns a collection class of type ArrA, whose backing Array is uninitialised. */
  override def uninitialised(length: Int): AA = fromArray(new Array[Double](length * elemNumDbls))

  def empty: AA = fromArray(new Array[Double](0))

  /** Factory method for creating the sequence defined object from raw double values. This will throw if the number of parameter [[Doubles]] is
   *  incorrect. */
  def fromDbls(elems: Double*): AA =
  { val arrLen: Int = elems.length
    if (arrLen % elemNumDbls != 0) excep(
      s"$arrLen Double values is not a correct number for the creation of this objects defining sequence, must be a multiple of $elemNumDbls")

    val array = Array[Double](elems.length)
    var count: Int = 0

    while (count < arrLen)
    { array(count) = elems(count)
      count += 1
    }
    fromArray(array)
  }
}*/
