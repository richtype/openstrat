/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance

/** A type class for the building of efficient compact Immutable Arrays. Instances for this type class for classes / traits you control should go in
 * the companion object of B not the companion object of BB. This is different from the related ArrBinder[BB] type class where instance should go into
 * the BB companion object. The type parameter is named B rather than A, because normally this will be found by an implicit in the context of a
 * function from A => B or A => M[B]. The methods of this trait mutate and therefore must be used with care. Where ever possible they should not be
 * used directly by end users. */
trait PolygonBuilder[B <: ElemValueN, +BB <: PolygonLike[B]] extends DataBuilderCommon[BB @uncheckedVariance]
{ type BuffT <: SeqGen[B]

  /** Creates a new uninitialised class of type BB.  */
  def newPolygonT(length: Int): BB
  def arrSet(arr: BB @uncheckedVariance, index: Int, value: B): Unit

  /** A mutable operation that extends the ArrayBuffer by a single element of type B. */
  def buffGrow(buff: BuffT, value: B): Unit

  def buffContains(buff: BuffT, newElem: B): Boolean =
  { var res = false
    var count = 0
    while (!res & count < buff.dataLength) if (buff(count) == newElem) res = true else count += 1
    res
  }

  /** A mutable operation that extends the ArrayBuffer with the elements of the Iterable operand. */
  def buffGrowIter(buff: BuffT, values: Iterable[B]): Unit = values.foreach(buffGrow(buff, _))

  def iterMap[A](inp: Iterable[A], f: A => B): BB =
  { val buff = newBuff()
    inp.foreach(a => buffGrow(buff, f(a)))
    buffToBB(buff)
  }
}

/** Trait for creating the line path builder instances for the [[PolygonBuilder]] type class, for classes / traits you control, should go in the
 *  companion  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait PolygonValueNsBuilder[B <: ElemValueN, BB <: PolygonLike[B]] extends PolygonBuilder[B, BB]
{ def elemProdSize: Int
}

/** Trait for creating the builder type class instances for [[PolygonDblNs]] final classes. Instances for the [[PolygonBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonDblNsBuilder[B <: ElemDblN, BB <: PolygonDblNs[B] ] extends PolygonValueNsBuilder[B, BB]
{ type BuffT <: BuffDblNs[B]
  def fromDblArray(array: Array[Double]): BB
  def fromDblBuffer(inp: ArrayBuffer[Double]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromDblBuffer(new ArrayBuffer[Double](length * elemProdSize))
  final override def newPolygonT(length: Int): BB = fromDblArray(new Array[Double](length * elemProdSize))
  final override def buffToBB(buff: BuffT): BB = fromDblArray(buff.unsafeBuff.toArray)
  final override def buffGrowArr(buff: BuffT, arr: BB): Unit = { buff.unsafeBuff.addAll(arr.arrayUnsafe); () }
  final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)
}

/** Trait for creating the line path type class instances for [[PolygonDbl2s]] final classes. Instances for the [[PolygonDbl2sBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[ElemDbl2]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl2sBuilder[B <: ElemDbl2, BB <: PolygonDbl2s[B]] extends PolygonDblNsBuilder[B, BB]
{ type BuffT <: BuffDbl2s[B]
  final override def elemProdSize = 2
  override def arrSet(arr: BB, index: Int, value: B): Unit = { arr.arrayUnsafe(index * 2) = value.dbl1; arr.arrayUnsafe(index * 2 + 1) = value.dbl2}
}

/** Trait for creating the line path type class instances for [[PolygonDbl3s]] final classes. Instances for the [[PolygonDbl3sBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[ElemDbl3]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonDbl3sBuilder[B <: ElemDbl3, BB <: PolygonDbl3s[B]] extends PolygonDblNsBuilder[B, BB]
{ type BuffT <: BuffDbl3s[B]
  final override def elemProdSize = 3
  override def arrSet(arr: BB, index: Int, value: B): Unit =
  { arr.arrayUnsafe(index * 3) = value.dbl1; arr.arrayUnsafe(index * 3 + 1) = value.dbl2; arr.arrayUnsafe(index * 3 + 2) = value.dbl3
  }
}

/** Trait for creating the builder type class instances for [[PolygonDblNs]] final classes. Instances for the [[PolygonBuilder]] type class, for classes
 *  / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait PolygonIntNsBuilder[B <: ElemIntN, BB <: PolygonIntNs[B] ] extends PolygonValueNsBuilder[B, BB]
{ type BuffT <: BuffIntNs[B]
  def fromIntArray(array: Array[Int]): BB
  def fromIntBuffer(inp: ArrayBuffer[Int]): BuffT
  final override def newBuff(length: Int = 4): BuffT = fromIntBuffer(new ArrayBuffer[Int](length * elemProdSize))
  final override def newPolygonT(length: Int): BB = fromIntArray(new Array[Int](length * elemProdSize))
  final override def buffToBB(buff: BuffT): BB = fromIntArray(buff.unsafeBuff.toArray)
  final override def buffGrowArr(buff: BuffT, arr: BB): Unit = { buff.unsafeBuff.addAll(arr.arrayUnsafe); () }
  final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)
}

/** Trait for creating the line path type class instances for [[PolygonInt2s]] final classes. Instances for the [[PolygonInt2sBuilder]] type class,
 *  for classes / traits you control, should go in the companion object of type B, which will extend [[ElemInt2]]. The first type parameter is called
 *  B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait PolygonInt2sBuilder[B <: ElemInt2, BB <: PolygonInt2s[B]] extends PolygonIntNsBuilder[B, BB]
{ type BuffT <: BuffInt2s[B]
  final override def elemProdSize = 2
  override def arrSet(arr: BB, index: Int, value: B): Unit = { arr.arrayUnsafe(index * 2) = value.int1; arr.arrayUnsafe(index * 2 + 1) = value.int2}
}