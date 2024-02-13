/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Helper trait for setting an [[LayerHcRefSys]], [[HSepLayer]] and a [[HCornerLayer]] at the same time. This allows the basic geometry of the
 *  terrain to be laid out in systematic row order. */
trait HSetter[TT <: AnyRef, ST, SST <: ST with HSepSome]
{ implicit def grid: HGrid

  def terrs: LayerHcRefSys[TT]

  def sTerrs: LayerHSOptSys[ST, SST]

  def corners: HCornerLayer

  trait IsleNBase
  { /** The tile terrain. typically land terrain. */
    def terr: TT

    /** The [[HSep]] separator terrain, Typically water terrain, */
    def sTerr: SST

    def magnitude: Int

    def run(row: Int, c: Int): Unit =
    { terrs.set(row, c, terr)
      corners.setNCornersIn(row, c, 6, 0, magnitude)
      iUntilForeach(6) { i =>
        val sep: HSep = HCen(row, c).sep(i)
        sTerrs.set(sep, sTerr)
      }
    }
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 10/16 of the radius of the hex. */
  trait IsleBase extends IsleNBase
  { override def magnitude: Int = 6
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 8/16 of the radius of the hex. */
  trait Isle8Base extends IsleNBase
  { override def magnitude: Int = 8
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 6/16 of the radius of the hex. */
  trait Isle6Base extends IsleNBase
  { override def magnitude: Int = 10
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 5/16 of the radius of the hex. */
  trait Isle5Base extends IsleNBase
  { override def magnitude: Int = 11
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 4/16 of the radius of the hex. */
  trait Isle4Base extends IsleNBase
  { override def magnitude: Int = 12
  }

  /** Sets the [[HSep]] terrain and corners for an Island, with a radius of 3/16 of the radius of the hex. */
  trait Isle3Base extends IsleNBase
  { override def magnitude: Int = 13
  }

  /** Sets an [[HSepB]] separator in the tile row. */
  trait SepBBase
  { /** The [[HSep]] separator terrain. */
    def sTerr: SST

    def run(row: Int, c: Int): Unit = sTerrs.set(row, c - 2, sTerr)
  }

  /** Base trait for capes / headlands / peninsulas. */
  trait CapeBase
  { /** The number of the first vertex to be indented. */
    def indentStartIndex: Int

    /** The number of indented vertices. */
    def numIndentedVerts: Int

    /** The terrain of the main tile, typically a type of land. */
    def terr: TT

    /** The terrain of the [[HSep]] separators, typically a type of water. */
    def sepTerrs: SST

    def run(row: Int, c: Int): Unit =
    { terrs.set(row, c, terr)
      corners.setNCornersIn(row, c, numIndentedVerts, indentStartIndex, 7)

      iUntilForeach(-1, numIndentedVerts) { i0 =>
        val i: Int = (indentStartIndex + i0) %% 6
        val sep: HSep = HCen(row, c).sep(i)
        sTerrs.set(sep, sepTerrs)
      }
    }
  }

  /** Base trait [Isthmus](https://en.wikipedia.org/wiki/Isthmus). Generally this will be used for Isthmuses, but it can be used for any [[HCen]] and
   * [[HSep]] terrain that fits the geometry. */
  trait IsthmusBase
  { /** The number of the first vertex to be indented. */
    def indentIndex: Int

    /** The number of the first vertex to be indented. */
    def oppositeIndex: Int = (indentIndex + 3) %% 6

    /** The terrain of the main tile, typically a type of land. */
    def terr: TT

    /** The terrain of the [[HSep]]s, next to the index vertex, typically a type of water. */
    def sepTerrs1: SST

    /** The terrain of the [[HSep]]s, next to the opposite vertex typically a type of water. */
    def sepTerrs2: SST

    /** Sets the [[HCen]] terrain. Sets the two opposite [[HCorner]]s and sets the four [[HSep]] terrains adjacent to the pulled in vertices. The
     * [[HSep]] terrains can be different on either [[HSep]] of the isthmus. For eample it could be sea on one [[HSep]] and fresh water lake on the
     * other. */
    def run(row: Int, c: Int): Unit =
    { terrs.set(row, c, terr)
      corners.setCornerIn(row, c, indentIndex, 7)
      corners.setCornerIn(row, c, oppositeIndex, 7)

      sTerrs.set(HCen(row, c).sep(indentIndex - 1), sepTerrs1)
      sTerrs.set(HCen(row, c).sep(indentIndex), sepTerrs1)
      sTerrs.set(HCen(row, c).sep(indentIndex - 4), sepTerrs2)
      sTerrs.set(HCen(row, c).sep(indentIndex + 3), sepTerrs2)
    }
  }

  trait VertSetBase
  { /** The C coordinate of the vertex. */
    def c: Int
  }

  /** Sets the mouth in the given direction and the [[HSep]] terrain in the opposite direction from the vertex. */
  trait MouthBase extends VertSetBase
  { /** The direction of the mouth. */
    def dirn: HVDirnPrimary

    /** The magnitude of the offsets. */
    def magnitude: Int

    /** The terrain of the left most [[HSep]] of the junction. */
    def sTerr: SST

    /** Sets the 3 corners for the [[HVert]] and sets the [[HSep]].  */
    def run(row: Int): Unit = dirn match
    { case HVUp =>
      { corners.setMouth3(row + 1, c, magnitude, magnitude)
        sTerrs.set(row - 1, c, sTerr)
      }

      case HVUR =>
      { corners.setMouth4(row + 1, c + 2, magnitude, magnitude)
        sTerrs.set(row, c - 1, sTerr)
      }

      case HVDR =>
      { corners.setMouth5(row - 1, c + 2, magnitude, magnitude)
        sTerrs.set(row, c - 1, sTerr)
      }

      case HVDn =>
      { corners.setMouth0(row - 1, c, magnitude, magnitude)
        sTerrs.set(row + 1, c, sTerr)
      }

      case HVDL =>
      { corners.setMouth1(row - 1, c - 2, magnitude, magnitude)
        sTerrs.set(row, c + 1, sTerr)
      }

      case HVUL =>
      { corners.setMouth2(row + 1, c - 2, magnitude, magnitude)
        sTerrs.set(row, c + 2, sTerr)
      }
    }
  }

  /** Sets the mouth in the given direction and the [[HSep]] terrain in the opposite direction from the vertex. */
  trait MouthLtBase extends VertSetBase
  { /** The direction of the mouth. */
    def dirn: HVDirnPrimary

    /** The magnitude of the offsets. */
    def magnitude: Int

    /** The terrain of the left most [[HSep]] of the junction. */
    def sTerr: SST

    def run(row: Int): Unit = dirn match
    { case HVUp =>
      { corners.setMouth3(row + 1, c, magnitude, 0)
        sTerrs.set(row - 1, c, sTerr)
      }

      case HVUR =>
      { corners.setMouth4(row + 1, c + 2, magnitude, 0)
        sTerrs.set(row, c - 1, sTerr)
      }

      case HVDR =>
      { corners.setMouth5(row - 1, c + 2, magnitude, 0)
        sTerrs.set(row, c - 1, sTerr)
      }

      case HVDn =>
      { corners.setMouth0(row - 1, c, magnitude, 0)
        sTerrs.set(row + 1, c, sTerr)
      }

      case HVDL =>
      { corners.setMouth1(row - 1, c - 2, magnitude, 0)
        sTerrs.set(row, c + 1, sTerr)
      }

      case HVUL =>
      { corners.setMouth2(row + 1, c - 2, magnitude, 0)
        sTerrs.set(row, c + 2, sTerr)
      }
    }
  }

  /** Sets the mouth in the given direction and the [[HSep]] terrain in the opposite direction from the vertex. This trait is provided to model real
   * world geographic / terrain features and is probably superfluous for created worlds / terrain. */
  trait MouthRtBase extends VertSetBase
  { /** The direction of the mouth. */
    def dirn: HVDirnPrimary

    /** The magnitude of the offsets. */
    def magnitude: Int

    /** The terrain of the left most [[HSep]] of the junction. */
    def sTerr: SST

    def run(row: Int): Unit = dirn match
    { case HVUp =>
      { corners.setMouth3(row + 1, c, 0, magnitude)
        sTerrs.set(row - 1, c, sTerr)
      }

      case HVUR =>
      { corners.setMouth4(row + 1, c + 2, 0, magnitude)
        sTerrs.set(row, c - 1, sTerr)
      }

      case HVDR =>
      { corners.setMouth5(row - 1, c + 2, 0, magnitude)
        sTerrs.set(row, c - 1, sTerr)
      }

      case HVDn =>
      { corners.setMouth0(row - 1, c, 0, magnitude)
        sTerrs.set(row + 1, c, sTerr)
      }

      case HVDL =>
      { corners.setMouth1(row - 1, c - 2, 0, magnitude)
        sTerrs.set(row, c + 1, sTerr)
      }

      case HVUL =>
      { corners.setMouth2(row + 1, c - 2, 0, magnitude)
        sTerrs.set(row, c + 2, sTerr)
      }
    }
  }

  /** Sets the mouth in the given direction and the [[HSep]] terrain in the opposite direction from the vertex. This trait is provided to model real
   * world geographic / terrain features and is probably superfluous for created worlds / terrain. */
  trait MouthLtRtBase extends VertSetBase
  { /** The direction of the mouth. */
    def dirn: HVDirnPrimary

    /** The terrain of the left most [[HSep]] of the junction. */
    def sTerr: SST

    /** The magnitude of the left offset. */
    def magLeft: Int

    /** The magnitude of the right offset. */
    def magRight: Int

    /** Sets the 3 corners for the [[HVert]] and sets the [[HSep]].  */
    def run(row: Int): Unit = dirn match
    { case HVUp =>
      { corners.setMouth3(row + 1, c, magLeft, magRight)
        sTerrs.set(row - 1, c, sTerr)
      }

      case HVUR =>
      { corners.setMouth4(row + 1, c + 2, magLeft, magRight)
        sTerrs.set(row, c - 1, sTerr)
      }

      case HVDR =>
      { corners.setMouth5(row - 1, c + 2, magLeft, magRight)
        sTerrs.set(row, c - 1, sTerr)
      }

      case HVDn =>
      { corners.setMouth0(row - 1, c, magLeft, magRight)
        sTerrs.set(row + 1, c, sTerr)
      }

      case HVDL =>
      { corners.setMouth1(row - 1, c - 2, magLeft, magRight)
        sTerrs.set(row, c + 1, sTerr)
      }

      case HVUL =>
      { corners.setMouth2(row + 1, c - 2, magLeft, magRight)
        sTerrs.set(row, c + 2, sTerr)
      }
    }
  }

  /** Sets the mouth in the given direction and the [[HSep]] terrain in the opposite direction from the vertex. */
  trait MouthSpecBase extends VertSetBase
  { /** The direction of the mouth. */
    def mouthDirn: HVDirnPrimary

    def dirn1: HVDirn

    def dirn2: HVDirn

    /** The terrain of the left most [[HSep]] of the junction. */
    def sTerr: SST

    /** The magnitude of the 1st offset of the mouth. */
    def magnitude1: Int

    def magnitude2: Int

    def run(row: Int): Unit = mouthDirn match
    { case HVUp =>
      { corners.setCorner(row - 1, c + 2, 5, dirn1, magnitude1)
        corners.setCorner(row - 1, c - 2, 1, dirn2, magnitude2)
        corners.setCornerPair(row + 1, c, 3, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row - 1, c, sTerr)
      }

      case HVUR =>
      { corners.setCorner(row - 1, c, 0, dirn1, magnitude1)
        corners.setCorner(row + 1, c - 2, 2, dirn2, magnitude2)
        corners.setCornerPair(row + 1, c + 2, 4, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row, c - 1, sTerr)
      }

      case HVDR =>
      { corners.setCorner(row - 1, c - 2, 1, dirn1, magnitude1)
        corners.setCorner(row + 1, c, 3, dirn2, magnitude2)
        corners.setCornerPair(row - 1, c + 2, 5, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row, c - 1, sTerr)
      }

      case HVDn =>
      { corners.setCorner(row + 1, c - 2, 2, dirn1, magnitude1)
        corners.setCorner(row + 1, c + 2, 4, dirn2, magnitude2)
        corners.setCornerPair(row - 1, c, 0, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row + 1, c, sTerr)
      }

      case HVDL =>
      { corners.setCorner(row + 1, c, 3, dirn1, magnitude1)
        corners.setCorner(row - 1, c + 2, 5, dirn2, magnitude2)
        corners.setCornerPair(row - 1, c - 2, 1, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row, c + 1, sTerr)
      }

      case HVUL =>
      { corners.setCorner(row + 1, c + 2, 4, dirn1, magnitude1)
        corners.setCorner(row - 1, c, 0, dirn2, magnitude2)
        corners.setCornerPair(row + 1, c - 2, 2, dirn1, dirn2, magnitude1, magnitude2)
        sTerrs.set(row, c + 2, sTerr)
      }
    }
  }

  /** Sets only the inside [[HCorner]] of Vertex for a bend [[HSep]] terrain, Sets the left most of the [[HSep]]s of this vertex. The orientation of
   *  the bend is specified by the direction of the inside of the bend. This trait is provided to model real world geographic / terrain features and
   *  is probably superfluous for created worlds / terrain. */
  trait BendBase extends VertSetBase
  { /** The direction of the [[HCen]] at the inside of the bend from the HVert. */
    def dirn: HVDirn

    /** The terrain of the left [[HSep]] of the junction as seen from from the inside of the bend. */
    def leftTerr: SST

    /** The terrain of the right [[HSep]] of the junction as seen from from the inside of the bend. */
    def rightTerr: SST

    final def run(row: Int): Unit =
    { setCorners(row)
      setSeparators(row)
    }

    def setCorners(row: Int): Unit

    def setSeparators(row: Int): Unit = dirn match
    { case HVUR =>
      { sTerrs.setIf(row + 1, c, leftTerr)
        sTerrs.setIf(row, c + 1, rightTerr)
      }

      case HVDR =>
      { sTerrs.set(row - 1, c, leftTerr)
        sTerrs.set(row, c + 1, rightTerr)
      }

      case HVDn =>
      { sTerrs.setIf(row, c - 1, leftTerr)
        sTerrs.setIf(row, c + 1, rightTerr)
      }

      case HVDL =>
      { sTerrs.set(row, c - 1, leftTerr)
        sTerrs.set(row - 1, c, rightTerr)
      }

      case HVUL =>
      { sTerrs.setIf(row, c - 1, leftTerr)
        sTerrs.setIf(row + 1, c, rightTerr)
      }

      case HVUp =>
      { sTerrs.setIf(row, c + 1, leftTerr)
        sTerrs.setIf(row, c - 1, rightTerr)
      }

      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** Sets all the corners of Vertex for a bend [[HSep]] terrain, Sets the left most of the [[HSep]]s of this vertex. The orientation of the bend is
   *  specified by the direction of the inside of the bend. This trait is provided to model real world geographic / terrain features and is probably
   *  superfluous for created worlds / terrain. */
  trait BendInOutBase extends BendBase
  { /** The magnitude of the offset for inside of the bend. */
    def magIn: Int

    /** The magnitude of the offset for the outside of bend. */
    def magOut: Int

    /** The direction of the inside of the bend [[HCen]] from the [[HVert]] of the bend. */
    def dirn: HVDirn

    override def setCorners(row: Int): Unit = dirn match
    { case HVUR => corners.setBend4All(row + 1, c + 2, magIn, magOut)
      case HVDR => corners.setBend5All(row - 1, c + 2, magIn, magOut)
      case HVDn => corners.setBend0All(row - 1, c, magIn, magOut)
      case HVDL => corners.setBend1All(row - 1, c - 2, magIn, magOut)
      case HVUL => corners.setBend2All(row + 1, c - 2, magIn, magOut)
      case HVUp => corners.setBend3All(row + 1, c, magIn, magOut)
      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  trait BendAllBase extends BendInOutBase
  { /** The magnitude of the offsets for both the inside and outside of the bend. */
    def magnitude: Int

    override def magIn: Int = magnitude
    override def magOut: Int = magnitude
  }

  /** Sets the 2 outer corners of the bend for [[HSep]] terrain, Also sets the left most of the [[HSep]]s of the bend vertex. The orientation of the bend is
   * specified by the direction of the inside of the bend. */
  trait BendOutBase extends BendBase
  { /** The magnitude of the offset for the 2 outer corners of the bend vertex. */
    def magnitude: Int

    override def setCorners(row: Int): Unit = dirn match
    { case HVUR => corners.setBend4Out(row + 1, c + 2, magnitude)
      case HVDR => corners.setBend5Out(row - 1, c + 2, magnitude)
      case HVDn => corners.setVert0Out(row - 1, c, magnitude)
      case HVDL => corners.setBend1Out(row - 1, c - 2, magnitude)
      case HVUL => corners.setBend2Out(row + 1, c - 2, magnitude)
      case HVUp => corners.setBend3Out(row + 1, c, magnitude)
      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** Sets only the inside [[HCorner]] of Vertex for a bend in [[HSep]]s terrain, Sets the left most of the [[HSep]]s of this vertex. The orientation
   *  of the bend is specified by the direction of the inside of the bend. This trait is provided to model real world geographic / terrain features
   *  and is probably superfluous for created worlds / terrain. */
  trait BendInBase extends BendBase
  { /** The magnitude of the offset on the inside [[HCorner]]. */
    def magnitude: Int

    override def setCorners(row: Int): Unit = dirn match
    { case HVUR => corners.setCornerIn(row + 1, c + 2, 4, magnitude)
      case HVDR => corners.setCornerIn(row - 1, c + 2, 5, magnitude)
      case HVDn => corners.setCornerIn(row - 1, c, 0, magnitude)
      case HVDL => corners.setCornerIn(row - 1, c - 2, 1, magnitude)
      case HVUL => corners.setCornerIn(row + 1, c - 2, 2, magnitude)
      case HVUp => corners.setCornerIn(row + 1, c, 3, magnitude)
      case HVLt | HVRt => excep("HVLt and HVRt not implemented")
    }
  }

  /** Used for setting a vertex where 3 [[HSep]] terrains meet. Also sets the left most [[HSep]]. */
  trait ThreeWayBase extends VertSetBase
  { def magnitude: Int

    /** The terrain of the left most [[HSep]] of the junction. */
    def sTerr: SST

    def run(row: Int): Unit =
    { corners.setVertEqual(row, c, magnitude)
      sTerrs.set(row, c - 1, sTerr)
    }
  }

  /** Used for setting a vertex where 3 [[HSep]] terrains meet. Also sets the left most [[HSep]]. This trait is provided to model real world
   *  geographic / terrain features and is probably superfluous for created worlds / terrain. */
  trait ThreeUpBase extends VertSetBase
  {
    def upTerr: SST

    def downRightTerr: SST

    /** The terrain of the left most [[HSep]] of the junction. */
    def downLeftTerr: SST

    /** The magnitude of the [[HVUR]] up-right offset. */
    def magUR: Int

    /** The magnitude of the [[HVDn]] down offset. */
    def magDn: Int

    /** The magnitude of the [[HVUL]] up-left offset. */
    def magUL: Int

    def run(row: Int): Unit =
    { grid.hCenExistsIfDo(row + 1, c + 2){ corners.setCornerIn(row + 1, c + 2, 4, magUR) }
      grid.hCenExistsIfDo(row - 1, c){ corners.setCornerIn(row - 1, c, 0, magDn) }
      grid.hCenExistsIfDo(row + 1, c - 2){ corners.setCornerIn(row + 1, c - 2, 2, magUL) }
      sTerrs.set(row + 1, c, upTerr)
      sTerrs.set(row, c + 1, downRightTerr)
      sTerrs.set(row, c - 1, downLeftTerr)
    }
  }
  
  /** Used for setting a vertex where 3 [[HSep]] terrains meet. Also sets the left most [[HSep]]. This trait is provided to model real world
   * geographic / terrain features and is probably superfluous for created worlds / terrain. */
  trait ThreeDownBase
  { def c: Int
    def st: SST
    def magUp: Int
    def magDR: Int
    def magDL: Int

    def run(row: Int): Unit =
    { grid.hCenExistsIfDo(row + 1, c) { corners.setCornerIn(row + 1, c, 3, magUp) }
      grid.hCenExistsIfDo(row - 1, c + 2) { corners.setCornerIn(row - 1, c + 2, 5, magDR) }
      grid.hCenExistsIfDo(row - 1, c - 2) { corners.setCornerIn(row - 1, c -2, 1, magDL) }
      sTerrs.set(row, c - 1, st)
    }
  }

  /** This is for setting [[HSep]]s on the edge of grids that sit within the hex area of the tile on the neighbouring grid. */
  trait SetSepBase
  { def c: Int
    def terr: SST
    def run(row: Int): Unit = sTerrs.set(row, c, terr)
  }

  /** Used for setting the a vertex on the left edge of a grid. Sets the vertex to the right on both hex tiles. */
  trait VertLeftsRightBase
  { /** The c coordinate of the vertex. */
    def c: Int

    /** The magnitude of the offset. */
    def magnitude: Int

    def run(row: Int): Unit = if (HVert.rcISHigh(row, c))
    { corners.setCorner(row + 1, c + 2, 4, HVRt, magnitude)
      corners.setCorner(row - 1, c, 0, HVRt, magnitude)
    }
    else
    { corners.setCorner(row + 1, c, 3, HVRt, magnitude)
      corners.setCorner(row - 1, c + 2, 5, HVRt, magnitude)
    }
  }

  /** Used for setting the a vertex on the right edge of a grid. Sets the vertex to the left on both hex tiles. */
  trait VertRightsLeftBase
  { /** The c coordinate of the vertex. */
    def c: Int

    /** The magnitude of the offset. */
    def magnitude: Int

    def run(row: Int): Unit = if (HVert.rcISHigh(row, c))
    { corners.setCorner(row + 1, c - 2, 2, HVLt, magnitude)
      corners.setCorner(row - 1, c, 0, HVLt, magnitude)
    }
    else
    { corners.setCorner(row + 1, c, 3, HVLt, magnitude)
      corners.setCorner(row - 1, c - 2, 1, HVLt, magnitude)
    }
  }
}