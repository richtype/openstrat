/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag

/** A [[HGrider]] data grid of [[Arr]]s of [[HCen]] data. */
class HCenArrDGrid[A](val unsafeArray: Array[Array[A]])
{
  def apply(hc: HCen)(implicit grider: HGrider): Arr[A] = new Arr(unsafeArray(grider.arrIndex(hc)))
  def apply(r: Int, c: Int)(implicit grider: HGrider): Arr[A] = new Arr(unsafeArray(grider.arrIndex(r, c)))

  def set(r: Int, c: Int, value: A)(implicit grider: HGrider, ct: ClassTag[A]): Unit = set(HCen(r, c), value)

  def set(hc: HCen, values: A*)(implicit grider: HGrider, ct: ClassTag[A]): Unit =
  { val newElem: Array[A] = new Array[A](values.length)
    values.iForeach((i, v) => newElem(i) = v)
    unsafeArray(grider.arrIndex(hc)) = newElem
  }

  def setSame(value: A, hcs: HCen*)(implicit grider: HGrider, ct: ClassTag[A]): Unit = hcs.foreach{ hc => set(hc, value) }

  def prepend(r: Int, c: Int, value: A)(implicit grider: HGrider, ct: ClassTag[A]): Unit = prepend(HCen(r, c), value)

  def prepend(hc: HCen, value: A)(implicit grider: HGrider, ct: ClassTag[A]): Unit =
  { val oldElem =  unsafeArray(grider.arrIndex(hc))
    val newElem: Array[A] = new Array[A](oldElem.length + 1)
    newElem(0) = value
    oldElem.copyToArray(newElem, 1)
    unsafeArray(grider.arrIndex(hc)) = newElem
  }
  //    def prepends(value : A, roords: Roord*)(implicit grid: TileGridOld): Unit = roords.foreach{ r =>  thisRefs.unsafeArr(grid.arrIndex(r)) ::= value }

  /** flatMaps over the the first element of each tile's data Array. Ignores empty arrays and subsequent elements. */
  def gridHeadsMap[B, BB <: SeqImut[B]](f: (HCen, A) => B)(implicit grider: HGrider, build: ArrBuilder[B, BB]): BB =
  {
    val buff = build.newBuff()
    grider.foreach { r =>
      val el:Arr[A] = apply(r)
      if (el.dataLength >= 1) build. buffGrow(buff, f(r, el(0)))
    }
    build.buffToBB(buff)
  }

  /** flatMaps over the the first element of each tile's data Array. Ignores empty arrays and subsequent elements. */
  def gridHeadsFlatMap[BB <: SeqImut[_]](f: (HCen, A) => BB)(implicit grider: HGrider, build: ArrFlatBuilder[BB]): BB =
  {
    val buff = build.newBuff()
    grider.foreach { r =>
      val el:Arr[A] = apply(r)
      if (el.dataLength >= 1) build.buffGrowArr(buff, f(r, el(0)))
    }
    build.buffToBB(buff)
  }
}