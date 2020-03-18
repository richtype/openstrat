/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, reflect.ClassTag

/** This represents a non-Simple square grid where the tile sides can have their own values. So for square the classic example is walls.
 *  The wall is too thin to occupy a whole tile or a line of tiles. For the time being all square grids are presumed to be regular grids */
abstract class SquareGridOld[TileT <: TileOld, SideT <: TileSideOld](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int,
  val turnNum: Int)(implicit val evTile: ClassTag[TileT], val evSide: ClassTag[SideT]) extends TileGridRegOld[TileT, SideT]
{
  override val xRatio = 1
  def coodToVec2(cood: Cood): Vec2 = Vec2(cood.c, cood.y)
  final def left: Double = xTileMin - margin
  final def right: Double = xTileMax + margin
  final def bottom: Double = yTileMin - margin
  final def top: Double = yTileMax + margin
  @inline override def rowTileXStart(y: Int): Int = xTileMin
  @inline override def rowTileXEnd(y: Int): Int = xTileMax

  final override def rowForeachTilesXY(y: Int, xStart: Int, xEnd: Int, f: (Int, Int) => Unit): Unit = for 
  {x <- xTileMin.max(xStart).roundUpTo(_.isEven) to xTileMax.min(xEnd).roundDownTo(_.isEven) by xStep} f(x, y)

  override def xArrLen: Int = xTileMax - xTileMin + 3
  override val yArrLen: Int = yTileMax - yTileMin + 3//+ 1 for lowersides +1 for zeroth tile, + 1 for upper side(s)
  override val arr: Array[TileT] = new Array[TileT](arrLen)
  override def vertCoodsOfTile(tileCood: Cood): Coods = SquareGrid.vertCoodsOfTile(tileCood)
  override def sideCoodsOfTile(tileCood: Cood): Coods = SquareGrid.sideCoodsOfTile(tileCood)
  override def xStep: Int = 2   
  def margin = 1.1  
  
  val sideArr: Array[SideT] = new Array[SideT](sideArrLen)
  override def xSideMin: Int = xTileMin - 1
  override def xSideMax: Int = xTileMax + 1  
  
  override def coodIsTile(x: Int, y: Int): Unit = ifNotExcep(x %% 2 == 0 & y %% 2 == 0,
    x.toString.appendCommas(y.toString) -- "is an invalid Square tile coordinate")
  
  override def coodIsSide(x: Int, y: Int): Unit = ifNotExcep(x.isOdd & y.isOdd,
    x.toString.appendCommas(y.toString) -- "is an invalid Squareside tile coordinate")

  override def foreachSidesXYAll(f: (Int, Int) => Unit): Unit =
  { ijToForeach(yTileMin, yTileMax, 2)(xTileMin + 1, xTileMax -1, 2)((y, x) => f(x, y))
    ijToForeach(yTileMin + 1, yTileMax - 1, 2)(xTileMin, xTileMax, 2)((y, x) => f(x, y))
  }
   
  /** Sets a rectangle of tiles to the same terrain type. */
  final override def setTiles[A](xFrom: Int, xTo: Int, yFrom: Int, yTo: Int, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit = for {
    y <- yFrom.max(yTileMin) to yTo.min(yTileMax) by 2
    x <- xFrom.max(xTileMin) to xTo.min(xTileMax) by 2
  } fSetTile(x, y, tileValue)
   
   
  final def setColumn[A](x: Int, yStart: Int, tileMakers: Multiple[A]*)(implicit f: (Int, Int, A) => TileT) : Cood =
  {
    val tiles = tileMakers.flatMap(_.singlesList)
    tiles.iForeach{(e, i) =>
      val y = yStart + i * 2
      fSetTile(x, y, e)      
    }
    Cood(x, yStart + (tiles.length - 1) * 2)
  }
  
  final def setColumn[A](cood: Cood, multis: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
    setColumn(cood.c, cood.y, multis: _*)(f)
   
  final def setColumnDown[A](x: Int, yStart: Int, tileMakers: Multiple[A]*)(implicit f: (Int, Int, A) => TileT) : Cood =
  {
    val tiles = tileMakers.flatMap(_.singlesList)
      
    tiles.iForeach{(e, i) =>
      val y = yStart - i * 2
      fSetTile(x, y, e)
    }
    Cood(x, yStart - (tiles.length - 1) * 2)
  }
  
  final def setColumnDown[A](cood: Cood, tileValues: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
    setColumnDown(cood.c, cood.y, tileValues: _*)(f)
   
  def fSetRow[A](y: Int, tileMakers: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Unit =
  { val tiles = tileMakers.flatMap(_.singlesList)
    tiles.iForeach((e , i) => fSetTile(xTileMin + i * 2, y, e))
  }
   
  def setTerrPath[A](value: A, startCood: Cood, dirns: Multiple[SquareGrid.PathDirn]*)(implicit f: (Int, Int, A) => TileT): Cood =
  {
    var cood = Cood(startCood.c, startCood.y)
    import SquareGrid._
    
    dirns.foreach 
    { case Multiple(Rt, i) => cood = setRow(cood, value * i)(f)
      case Multiple(Lt, i) => cood = setRowBack(cood, value * i)(f)
      case Multiple(Up, i) => cood = setColumn(cood, value * i)(f)
      case Multiple(Dn, i) => cood = setColumnDown(cood, value * i)(f)
    }
    cood
  }
  
  override def tileDestinguish[A](cood: Cood, v1: A, v2: A, v3: A, v4: A): A = cood match
  { case Cood(x, y) if x %% 4 == 0 & y.isEven => v1
    case Cood(x, y) if x %% 4 == 2 & y.isEven => v2
    case Cood(x, y) if x %% 4 == 0 => v3
    case _ => v4
  }
  
  override def vertCoodLineOfSide(x: Int, y: Int): CoodLine = SquareGrid.sideCoodToCoodLine(x, y)
  
  override def sidesTileCoods(x: Int, y: Int): (Cood, Cood) = if2Excep(
    x.isOdd & y.isEven, (Cood(x - 1, y), Cood(x + 1, y)),
    x.isEven & y.isOdd, (Cood(x, y - 1), Cood(x, y + 1)),
    "Invalid Square Coordinate")
 
  /** Warning needs Modification */
  override def adjTileCoodsOfTile(tileCood: Cood): Coods = SquareGrid.adjTileCoodsOfTile(tileCood)
  
  def tileRowLen: Int = ((xTileMax - xTileMin) / 2  + 1).min(0)
  def tileColumnLen: Int = tileRowLen * ((yTileMax - yTileMin) / 2 + 1).min(0)
  override def tileNum: Int = tileRowLen * tileColumnLen
  def sideRowLen: Int = ife(tileRowLen == 0, tileRowLen + 1, 0)
  def sideColumnLen: Int = ife(tileColumnLen == 0, tileColumnLen + 1, 0)
  override def sideNum: Int = sideRowLen  * sideColumnLen
}