/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, collection.mutable.ArrayBuffer

/** A polygon with the vertices defined by hex tile coordinates  [[HCoord]]s. */
class PolygonSqC(val arrayUnsafe: Array[Int]) extends AnyVal with SqCoordSeqSpec with PolygonLikeInt2[SqCoord]
{ override type ThisT = PolygonSqC
  override type SideT = LineSegSC
  override def typeStr: String = "PolygonSqC"
  override def fromArray(array: Array[Int]): PolygonSqC = new PolygonSqC(array)
  def vertNum: Int = arrayUnsafe.length / 2
  override def verts: SqCoordArr = new SqCoordArr(arrayUnsafe)

  /** Performs the side effecting function on the [[SqCoord]] value of each vertex. */
  override def vertsForeach[U](f: SqCoord => U): Unit =
  { var count = 0
    while (count < numVerts)
    { f(vert(count))
      count += 1
    }
  }

  override def vertsMap[B, ArrB <: Arr[B]](f: SqCoord => B)(implicit builder: BuilderArrMap[B, ArrB]): ArrB =
  { val res = builder.uninitialised(numVerts)
    var count = 0
    vertsForeach{ v =>
      builder.indexSet(res, count, f(v))
      count += 1
    }
    res
  }

  /** This method does nothing if the vertNum < 2. Foreach vertex applies the side effecting function to the previous vertex with each vertex. The
   * previous vertex to the first vertex is the last vertex of the [[PolygonLike]]. Note the function signature (previous, vertex) => U follows the
   * foreach based convention of putting the collection element 2nd or last as seen for example in fold methods'(accumulator, element) => B
   * signature. */
  override def vertsPrevForEach[U](f: (SqCoord, SqCoord) => U): Unit = ???

  /** This applies the index value in a circular manner. So the 6th index of a Sqexagon is applied at vertex 0, 7 at 1 and -1 at 5. */
  def circularIndex(inp: Int): Int = inp %% vertNum

  def toPolygon(f: SqCoord => Pt2): Polygon =
  {
    val res = PolygonGen.uninitialised(ssLength)
    ssIForeach((i, hv) => res.setElemUnsafe(i, f(hv)))
    res
  }

  def combine(operand: PolygonSqC): Option[PolygonSqC] =
  {
    var starts: Option[(Int, Int)] = None
    val a = ssIndex(0)
    ???
  }

  @inline override def side(index: Int): LineSegSC = LineSegSC(vert(index), vert(index + 1))
  override def sides: LineSegSCArr = new LineSegSCArr(arrayForSides)

  override def sidesForeach[U](f: LineSegSC => U): Unit =
  { var i = 0
    while (i < numVerts) { f(side(i)); i += 1 }
  }
}

/** Companion object for the polygon whose vertices are defined by hex tile coordinates [[PolygonSqC]] trait. */
object PolygonSqC extends CompanionSeqLikeInt2[SqCoord, PolygonSqC]
{ override def fromArray(array: Array[Int]): PolygonSqC = new PolygonSqC(array)

  implicit val arrBuildImplicit: BuilderArrMap[PolygonSqC, PolygonSqCArr] = new BuilderArrMap[PolygonSqC, PolygonSqCArr] {
    override type BuffT = PolygonSqCBuff
    override def newBuff(length: Int): PolygonSqCBuff = PolygonSqCBuff(length)
    override def uninitialised(length: Int): PolygonSqCArr = new PolygonSqCArr(new Array[Array[Int]](length))
    override def indexSet(seqLike: PolygonSqCArr, index: Int, newElem: PolygonSqC): Unit = seqLike.unsafeArrayOfArrays(index) = newElem.arrayUnsafe
    override def buffGrow(buff: PolygonSqCBuff, newElem: PolygonSqC): Unit = buff.unsafeBuffer.append(newElem.arrayUnsafe)
    override def buffToSeqLike(buff: PolygonSqCBuff): PolygonSqCArr = new PolygonSqCArr(buff.unsafeBuffer.toArray)
  }
}

class PolygonSqCArr(val unsafeArrayOfArrays:Array[Array[Int]]) extends Arr[PolygonSqC]
{ override type ThisT = PolygonSqCArr
  override def typeStr: String = "PolygonSqCArr"
  override def length: Int = unsafeArrayOfArrays.length
  override def setElemUnsafe(i: Int, newElem: PolygonSqC): Unit = unsafeArrayOfArrays(i) = newElem.arrayUnsafe
  override def fElemStr: PolygonSqC => String = _.toString
  override def apply(index: Int): PolygonSqC = new PolygonSqC(unsafeArrayOfArrays(index))
}

class PolygonSqCBuff(val unsafeBuffer: ArrayBuffer[Array[Int]]) extends AnyVal with ArrayIntBuff[PolygonSqC]
{ override type ThisT = PolygonSqCBuff
  override def typeStr: String = "PolygonSqCBuff"
  override def setElemUnsafe(i: Int, newElem: PolygonSqC): Unit = unsafeBuffer(i) = newElem.arrayUnsafe
  override def fElemStr: PolygonSqC => String = _.toString
  override def fromArrayInt(array: Array[Int]): PolygonSqC = new PolygonSqC(array)
}

object PolygonSqCBuff
{ def apply(initLen: Int = 4): PolygonSqCBuff = new PolygonSqCBuff(new ArrayBuffer[Array[Int]](initLen))
}
