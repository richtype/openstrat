/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package dless
import geom._, prid._, phex._, pgui._, egrid._

class DLessGame(var scen: DLessScen, guiNations: RArr[Nation])
{
  def endTurn(orderList: HCenStepPairArr[Army]): DLessScen = scen.endTurn(orderList)
}