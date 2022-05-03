/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import egrid._, geom._, pglobe._, prid.phex.HSide

trait EGrid80Sys extends EGridSys
{ override val cScale: Length = 20.kMetres
}

/** A main non-polar grid with a hex span of 80Km */
class EGrid80Main(rBottomCen: Int, rTopCen: Int, cenLong: Longitude, cOffset: Int) extends
  EGridMain(rBottomCen, rTopCen, cenLong, 20000.metres, 300, cOffset) with EGrid80Sys

/** object for creating 80km hex scale earth grids. */
object EGrid80Km
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def l0(rBottomCen: Int, rTopCen: Int = 540): EGrid80Main = new EGrid80Main(rBottomCen, rTopCen, 0.east, 512)

  /** Factory method for creating a main Earth grid centred on 30 degrees east of scale cScale 20Km or hex scale 80km. */
  def l30(rBottomCen: Int, rTopCen: Int = 540): EGrid80Main = new EGrid80Main(rBottomCen, rTopCen, 30.east,t"1G0"/* 1536*/)

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def l0b446: EGrid80Main = new EGrid80Main(446, 540, 0.east, 512)
  def l30b446: EGrid80Main = new EGrid80Main(446, 540, 30.east, 1536)

  def scen1: EScenBasic =
  { val grid: EGrid80Main = l0(446)
    EScenBasic(grid, EuropeNW80Terr())
  }

  def scen2: EScenBasic =
  { val grid: EGrid80Main = l30(446)
    EScenBasic(grid, EuropeNE80Terr())
  }
}

trait EGrid80MainMan extends EGridMainMan
{ override val grid: EGrid80Main
}

trait EGrid80MainMulti extends EGridMainMulti with EGrid80Sys
{ override val gridMans: Arr[EGrid80MainMan]
  override def sidesForeach(f: HSide => Unit): Unit = grids.foreach(_.sidesForeach(f))
  override def innerSidesForeach(f: HSide => Unit): Unit = grids.foreach(_.innerSidesForeach(f))
  override def outerSidesForeach(f: HSide => Unit): Unit = grids.foreach(_.outerSidesForeach(f))
  /** The top most point in the grid where the value of y is maximum. */
  def top: Double = ???

  /** The bottom most point in the grid where the value of y is minimum. */
  def bottom: Double = ???

  /** The left most point in the grid where x is minimum. */
  def left: Double = ???

  /** The right most point in the grid where the value of x is maximum. */
  def right: Double = ???
}