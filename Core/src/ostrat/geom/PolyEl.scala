/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait ValsVec2s extends Any
{
   def arr: Array[Double]
   def valsLength: Int
   def ptsLength: Int = (arr.length - valsLength) / 2   
   def xDouble(index: Int): Double = arr(index * 2 + valsLength)
   def yDouble(index: Int): Double = arr(index * 2 + valsLength + 1)
   def xHead: Double = arr(valsLength)
   def yHead: Double = arr(valsLength + 1)   
   def vert(index: Int): Vec2 = Vec2(xDouble(index), yDouble(index))  
   def vertsIndexForeach(f: Int => Unit): Unit = (0 until ptsLength).foreach(f)
   def vertsForeach(f: Vec2 => Unit): Unit = vertsIndexForeach(i => f(vert(i)))
   def arrTrans(f: Vec2 => Vec2): Array[Double]
   def foreachVertPairTail[U](f: (Double, Double) => U): Unit =
   {
      var count = 1      
      while(count < ptsLength) { f(xDouble(count), yDouble(count)); count += 1 }
   }
   
   /** Not sure about this name */
   def xVertsArr: Array[Double] =
   {
      val xArr: Array[Double] = new Array[Double](ptsLength)
      vertsIndexForeach{i => xArr(i) = xDouble(i) }      
      xArr
   }   

   /** Not sure about this name */
   def yVertsArr: Array[Double] =
   {
      val yArr: Array[Double] = new Array[Double](ptsLength)
      vertsIndexForeach{i => yArr(i) = yDouble(i) }      
      yArr
   }
   def arrVertsTrans(f: Vec2 => Vec2): Array[Double] =
   {
      val newArr: Array[Double] = new Array[Double](arr.length)
      var count = valsLength
      vertsForeach { oldVert =>
         val newVert = f(oldVert)
         newArr(count) = newVert.x
         newArr(count + 1) = newVert.y
         count += 2
      }
      newArr
   }
}

trait Val1Vec2s[Val1T] extends Any with ValsVec2s
{
   def valsLength: Int = 1
   def val1Func: Double => Val1T
   def val1: Val1T = val1Func(arr(0).toInt)   
   
   def arrTrans(f: Vec2 => Vec2): Array[Double] =
   {
      val newArr: Array[Double] = arrVertsTrans(f)
      newArr(0) = arr(0)//copies initial Int1 value      
      newArr
   }   
}

trait Val2Vec2s[Val1T, Val2T] extends Any with ValsVec2s
{
   override def valsLength: Int = 2
   def val1Func: Double => Val1T
   def val1: Val1T = val1Func(arr(0).toInt)
   def val2Func: Double => Val2T
   def val2: Val2T = val2Func(arr(1).toInt)
   override def arrTrans(f: Vec2 => Vec2): Array[Double] =
   {
      val newArr: Array[Double] = arrVertsTrans(f)//new Array[Double](arr.length)
      newArr(0) = arr(0)//copies initial Int1 value      
      newArr(1) = arr(1)//copies initial Int2 value 
      newArr
   }      
}

/** An efficient class to fill polygon based on Array[Double] */
class FillPoly(val arr: Array[Double]) extends AnyVal with Val1Vec2s[Colour] with CanvEl[FillPoly]
{ 
   override def val1Func: Double => Colour = d => Colour(d.toInt)
   @inline def colour: Colour = val1
   override def fTrans(f: Vec2 => Vec2): FillPoly =  new FillPoly(arrTrans(f))
}

object FillPoly
{
   def apply(colour: Colour, verts: Vec2s): FillPoly =
   {
      val arr: Array[Double] = new Array[Double](1 + verts.arr.length)
      arr(0) = colour.argbValue//copies colour
      verts.arr.copyToRight(arr, 1)
      new FillPoly(arr)
   }
}

/** An efficient class to draw polygon based on Array[Double] */
class DrawPoly(val arr: Array[Double]) extends AnyVal with Val2Vec2s[Double, Colour] with CanvEl[DrawPoly]
{
   override def val1Func: Double => Double = d => d
   @inline def lineWidth: Double = val1
   override def val2Func: Double => Colour = d => Colour(d.toInt)
   @inline def lineColour: Colour = val2
   override def fTrans(f: Vec2 => Vec2) = new DrawPoly(arrTrans(f))   
}

object DrawPoly
{
   def apply(lineWidth: Double, colour: Colour, verts: Vec2s): DrawPoly =
   {
      val arr: Array[Double] = new Array[Double](2 + verts.arr.length)
      arr(0) = lineWidth
      arr(1) = colour.argbValue//copies colour
      verts.arr.copyToRight(arr, 2)
      new DrawPoly(arr)
   }
}
