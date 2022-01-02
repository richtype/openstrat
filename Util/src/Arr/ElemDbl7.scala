/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from 7 [[Double]]s. These are used in [[DataDbl7s]] classes including [[ArrDbl7s]] sequence collections. */
trait ElemDbl7 extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
  def dbl6: Double
  def dbl7: Double
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[ElemDbl7]]s. */
trait DataDbl7s[A <: ElemDbl7] extends Any with DataDblNs[A]
{ def elemProdSize: Int = 7
  def dataElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double, d7: Double): A

  def indexData(index: Int): A =
  { val offset = 7 * index

    dataElem(arrayUnsafe(offset), arrayUnsafe(offset + 1), arrayUnsafe(offset + 2), arrayUnsafe(offset + 3), arrayUnsafe(offset + 4),
      arrayUnsafe(offset + 5), arrayUnsafe(offset + 6))
  }

  override def unsafeSetElem(index: Int, elem: A): Unit =
  { val offset = 7 * index;
    arrayUnsafe(offset) = elem.dbl1; arrayUnsafe(offset + 1) = elem.dbl2; arrayUnsafe(offset + 2) = elem.dbl3; arrayUnsafe(offset + 3) = elem.dbl4
    arrayUnsafe(offset + 4) = elem.dbl5; arrayUnsafe(offset + 5) = elem.dbl6; arrayUnsafe(offset + 6) = elem.dbl7
  }
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[ElemDbl7]]s. */
trait ArrDbl7s[A <: ElemDbl7] extends Any with ArrDblNs[A] with DataDbl7s[A]
{ def head1: Double = arrayUnsafe(0); def head2: Double = arrayUnsafe(1); def head3: Double = arrayUnsafe(2); def head4: Double = arrayUnsafe(3)
  def head5: Double = arrayUnsafe(4); def head6: Double = arrayUnsafe(5); def head7: Double = arrayUnsafe(6)
  final override def length: Int = arrayUnsafe.length / 7
  def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5, el.dbl6, el.dbl7)))
}

/** Helper class for companion objects of final [[DataDbl7s]] classes. */
abstract class DataDbl7sCompanion[A <: ElemDbl7, ArrA <: DataDbl7s[A]] extends DataDblNsCompanion[A, ArrA]
{ override def elemProdSize: Int = 7
  def apply(length: Int): ArrA = uninitialised(length)

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = uninitialised(length)
    var count: Int = 0

    while (count < length)
    { val offset = count * 7
      res.arrayUnsafe(offset) = elems(count).dbl1; res.arrayUnsafe(offset + 1) = elems(count).dbl2; res.arrayUnsafe(offset + 2) = elems(count).dbl3
      res.arrayUnsafe(offset + 3) = elems(count).dbl4; res.arrayUnsafe(offset + 4) = elems(count).dbl5
      res.arrayUnsafe(offset + 5) = elems(count).dbl6; res.arrayUnsafe(offset + 6) = elems(count).dbl7
      count += 1
    }
    res
  }

  def doubles(elems: Double*): ArrA =
  { val arrLen: Int = elems.length
    val res = uninitialised(elems.length / 7)
    var count: Int = 0
    while (count < arrLen) { res.arrayUnsafe(count) = elems(count); count += 1 }
    res
  }

   def fromList(list: List[A]): ArrA =
   { val res = uninitialised(list.length)
     var count: Int = 0
     var rem = list
     
     while (count < list.length)
     { val offset = count * 7      
       res.arrayUnsafe(offset) = rem.head.dbl1; res.arrayUnsafe(offset +  1) = rem.head.dbl2; res.arrayUnsafe(offset +  2) = rem.head.dbl3;
       res.arrayUnsafe(offset + 3) = rem.head.dbl4; res.arrayUnsafe(offset + 4) = rem.head.dbl5; res.arrayUnsafe(offset + 5) = rem.head.dbl6;
       res.arrayUnsafe(offset + 7) = rem.head.dbl7
       count += 1
       rem = rem.tail
     }
     res
  }
}