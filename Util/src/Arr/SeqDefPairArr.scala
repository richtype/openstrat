/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, unchecked.uncheckedVariance, reflect.ClassTag, collection.mutable.ArrayBuffer

/** An element that pairs a [[SeqDef]] with a second value. */
trait SeqDefPair[A2] extends SpecialT

/** A sequence of [[SeqDefPair]]s stored in 2 [[Array]]s for efficiency. */
trait SeqDefPairArr[A2, A <: SeqDefPair[A2]] extends SeqImut[A]
{ def a2Array: Array[A2]
  override def length: Int = a2Array.length
  override def sdLength: Int = a2Array.length
}

trait PairBuff[A2, A <: SeqDefPair[A2]] extends SeqGen[A]
{ def a2Buff: ArrayBuffer[A2]
  override def length: Int = a2Buff.length
  override def sdLength: Int = a2Buff.length
}