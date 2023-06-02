/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import reflect.ClassTag

/** A data layer for an [[HGridSys]] where each tile's data is an [[Arr]] of the specified type. */
trait HCenArrLayer[A, ArrA <: Arr[A]]
{ /** The [[HGridSys]] hex grid system that is the key to this tile data layer */
  def gridSys: HGridSys

  /** Foreachs over each tile's [[Arr]]. */
  def foreach(f: ArrA => Unit): Unit

  def iApply(index: Int): ArrA

  /** The number of tile's in this data layer. */
  def numTiles: Int = gridSys.numTiles

  /** Maps from this [[HCenArrLayer]] to an [[HCenArrLayer]] type ArrB. */
  final def map[B, ArrB <: Arr[B], LayerT <: HCenArrLayer[B, ArrB]](f: ArrA => ArrB)(implicit builder: HCenArrLayerBuilder[B, ArrB, LayerT]): LayerT = {
    val res = builder.uninitialised(gridSys)
    iForeach { (i, a) => builder.iSet(res, i, f(a)) }
    res
  }

  /** Foreachs over each tile's [[Arr]] with a preceding index. */
  def iForeach(f: (Int, ArrA) => Unit): Unit

  /** Foreachs over each element of the respective [[Arr]]s with a preceding [[HCen]]. Applying the side effecting function. */
  def foreachHcForeach(f: (HCen, A) => Unit)(implicit gSys: HGridSys): Unit

  /** Maps each tile's [[HCen]] with its respective [[Arr]] to a new [[Arr]]. */
  final def hcMap[B, ArrB <: Arr[B], LayerT <: HCenArrLayer[B, ArrB]](f: (HCen, ArrA) => ArrB)(implicit builder: HCenArrLayerBuilder[B, ArrB, LayerT]): LayerT =
  { val res = builder.uninitialised(gridSys)
    gridSys.iForeach { (i, hc) => builder.iSet(res, i, f(hc, iApply(i))) }
    res
  }
}

object HCenArrLayer extends HCenArrLayerLowPrioity
{
  implicit def intNBuilderEv[B <: IntNElem, ArrB <: IntNArr[B]](implicit intNArrMapBuilder: IntNArrMapBuilder[B, ArrB]):
  HCenArrLayerBuilder[B, ArrB, HCenIntNArrLayer[B, ArrB]] = new HCenArrLayerBuilder[B, ArrB, HCenIntNArrLayer[B, ArrB]]
  { override val arrBBuild: IntNArrMapBuilder[B, ArrB] = intNArrMapBuilder
    override def uninitialised(gridSys: HGridSys): HCenIntNArrLayer[B, ArrB] =
      new HCenIntNArrLayer[B, ArrB](new Array[Array[Int]](gridSys.numTiles), gridSys)
    override def iSet(layer: HCenIntNArrLayer[B, ArrB], i: Int, arr: ArrB): Unit = layer.outerArrayUnsafe(i) = arr.unsafeArray
  }
}

trait HCenArrLayerLowPrioity
{
  implicit def RArrBuilderEv[B](implicit ct: ClassTag[B]): HCenArrLayerBuilder[B, RArr[B], HCenRArrLayer[B]] = new HCenArrLayerBuilder[B, RArr[B], HCenRArrLayer[B]]
  { override val arrBBuild: ArrMapBuilder[B, RArr[B]] = ArrMapBuilder.rMapImplicit
    override def uninitialised(gridSys: HGridSys): HCenRArrLayer[B] = new HCenRArrLayer(new Array[Array[B]](gridSys.numTiles), gridSys)
    override def iSet(layer: HCenRArrLayer[B], i: Int, arr: RArr[B]): Unit = layer.outerArrayUnsafe(i) = arr.unsafeArray
  }
}

trait HCenArrLayerBuilder[B, ArrB <: Arr[B], LayerB <: HCenArrLayer[B, ArrB]]
{ def arrBBuild: ArrMapBuilder[B, ArrB]
  def uninitialised(gridSys: HGridSys): LayerB
  def iSet(layer: LayerB, i: Int, arr: ArrB): Unit
}