/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import reflect.ClassTag, collection.mutable.ArrayBuffer

/** A tileGrid is a collection of tiles, either hexs or squares. This is a fundamental trait. It is a specific case of a tiled area. I
 *  have reached the conclusion that the general case of completely irregular tiling, while interesting mathematically and useful for say
 *  representing a historical game like "Risk", has insufficient utility for the representations we want today. Tile rids can however be fully regular
 *  or partly irregular such as the grids for covering the Earth's surface. Grids can be simple just consisting of values for the tiles or complex
 *  containing values for the tiles and the tile sides. Rivers, straits, walls, ditches are examples of values commonly assigned to tile sides.  
 *  
 *  It is stored in an underlying array. It consists of a sequence of contiguous rows of tiles. Each row of tiles is itself contiguous,
 *  There are no breaks between the first tile of the row and the last tile of the row although a row can consist of a single tile. Every
 *  row shares at least one tile side with the row above and below. The grid includes all the sides of the tiles including the sides on
 *  the outer edges of the grid. This means to link two grids requires a Grid Bridge class. */
trait TileGrid[TileT <: Tile]
{  
  val arr: Array[TileT]
  def evTile: ClassTag[TileT]
  def xTileMin: Int
  def xTileMax: Int
  def yTileMin: Int
  def yTileMax: Int  
  def xArrLen: Int
  def yArrLen: Int
  def xToInd(x: Int): Int
  val yRatio: Double
  def xStep: Int
  def yStep: Int
  def tileNum: Int
  def rowForeachTileXY(y: Int, f: (Int, Int) => Unit): Unit
  def setTilesRectangle[A](bottomLeft: Cood, topRight: Cood, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit  
  /** Throws exception if Cood is not a valid Tile coordinate */
  def coodIsTile(x: Int, y: Int): Unit 
  
  final def arrLen = yArrLen * xArrLen  
  final def yToInd(y: Int): Int = y  - yTileMin
  def xyToInd(x: Int, y: Int) = xToInd(x) + yToInd(y) * xArrLen
  
  def optTile(x: Int, y: Int): Option[TileT]
  final def optTile(cood: Cood): Option[TileT] = optTile(cood.x, cood.y)
    
  /** Throws exception if Cood is not a valid Tile coordinate */
  final def coodIsTile(cood: Cood): Unit = coodIsTile(cood.x, cood.y)
  
  def getTile(x: Int, y: Int): TileT = { coodIsTile(x, y); arr(xyToInd(x, y)) }   
  def getTile(tc: Cood): TileT = { coodIsTile(tc); arr(xyToInd(tc.x, tc.y)) } 
 
  def setTile(x: Int, y: Int, value: TileT): Unit = { coodIsTile(x, y); arr(xyToInd(x, y)) = value  }  
  def setTile(cood: Cood, value: TileT): Unit = setTile(cood.x, cood.y, value)
  
  def fsetTile[A](cood: Cood, value: A)(implicit fTile: (Int, Int, A) => TileT): Unit = fSetTile[A](cood.x, cood.y, value)(fTile)
  def fSetTile[A](x: Int, y: Int, value: A)(implicit fTile: (Int, Int, A) => TileT): Unit =
  { coodIsTile(x, y)
    arr(xyToInd(x, y)) = fTile(x, y, value)
  }
  final def setAllTiles[A](value: A)(implicit fTile: (Int, Int, A) => TileT): Unit = forallTilesXY((x, y) => fSetTile(x, y, value)(fTile))
  
  /** For all Tiles call side effecting function on the Tile's XY Cood. */
  @inline final def forallTilesXY(f: (Int, Int) => Unit): Unit = forallTileRows(y => rowForeachTileXY(y, f))  
  
  /** For all Tiles call side effecting function on the Tile's Cood. */
  @inline final def forallTilesCood(f: Cood => Unit): Unit = forallTilesXY((x, y) => f(Cood(x, y)))
  
  /** For all Tiles call side effecting function on the Tile. */
  @inline final def forallTiles(f: TileT => Unit): Unit =  forallTilesCood{ tileCood => f(getTile(tileCood)) }  
  
  final def forallTileRows(f: Int => Unit): Unit =
  { var y: Int = yTileMin
    while(y <= yTileMax) { f(y); y += yStep }
  }
  
  /** Map all Tiles to Array[B] with function. */
  final def allTilesMap[B: ClassTag](f: TileT => B): Array[B] =
  {
    val acc: ArrayBuffer[B] = new ArrayBuffer(0)
    forallTilesCood{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: B = f(tile)
      acc += newRes
    }
    acc.toArray
  }
  
  /** Map all Tiles to an Array with function and flatten into Single Array. */
  def allTilesFlatMap[R: ClassTag](f: TileT => Array[R]): Array[R] =
  {
    val acc: ArrayBuffer[R] = new ArrayBuffer(0)
    forallTilesCood{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: Array[R] = f(tile)
      acc ++= newRes
    }
    acc.toArray
  }  
  
  /** Map all tiles' Cood to a List[B]. */
  final def allTilesCoodMapList[B](f: Cood => B): List[B] =
  { var acc: List[B] = Nil
    forallTilesCood{c => acc ::= f(c) }
    acc.reverse    
  }
  
  /** Map all tiles' XY Cood to List. */
  final def allTilesXYMapList[B](f: (Int, Int) => B): List[B] =
  { var acc: List[B] = Nil
    forallTilesXY{(x, y) => acc ::= f(x, y) }
    acc.reverse    
  }
  
  final def tileCoodsFold[R](f: Cood => R, fSum: (R, R) => R)(emptyVal: R): R =
  { var acc: R = emptyVal
    forallTilesCood { tileCood => acc = fSum(acc, f(tileCood)) }
    acc
  } 
  
  /** Not sure how useful this method is. */
  final def tileAndCoodsFold[R](f: (TileT, Cood) => R, fSum: (R, R) => R)(emptyVal: R): R =
  {
    var acc: R = emptyVal
    forallTilesCood{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: R = f(tile, tileCood)
      acc = fSum(acc, newRes)
    }
    acc   
  }
  
  /** Set tile row from the Cood. */
  final def setRow[A](cood: Cood, tileValues: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood = setRow(cood.y, cood.x, tileValues: _*)(f)
  /** Note set Row starts with the y (row) parameter. */ 
  final def setRow[A](yRow: Int, xStart: Int, tileValues: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
  {
    val tiles: List[A] = tileValues.toSingles      
    tiles.iForeach{(e, i) =>
      val x = xStart + i * xStep
      fSetTile(x, yRow, e)         
    }
    Cood(xStart + (tiles.length - 1) * xStep, yRow)   
  }
  
  /** Note set RowBack starts with the y (row) parameter */
  final def setRowBack[A](yRow: Int, xStart: Int, tileMakers: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
  {
    val tiles = tileMakers.toSingles      
    tiles.iForeach{(e, i) =>
      val x = xStart - i * xStep
      fSetTile(x, yRow, e)
    }
    Cood(xStart - (tiles.length - 1) * xStep, yRow)
  }
   
  final def setRowBack[A](cood: Cood, tileValues: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
    setRowBack(cood.y, cood.x, tileValues: _*)(f)  
}
