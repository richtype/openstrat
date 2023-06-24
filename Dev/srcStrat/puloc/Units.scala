/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pglobe._, pEarth._, pEurope._

object PruCp1 extends CorpsNumbered
{ override val corpsNum: Int = 1
  override val polity: Polity = Prussia
  override val startDate: MTime = MTime(1820)
  override val endDate: MTime = MTime(1871, 1, 18)
  override def locStarts: RArr[LocStart] = RArr(LocStart.date(1820))

  override def locPosns: MTimeSeries[LatLongOpt] = MTimeSeries(LLNone)
}

object DeuCp1 extends CorpsNumbered
{ override val polity: Polity = Germany
  override val corpsNum: Int = 1
  override val locStarts: RArr[LocStart] = RArr(LocStart(Baltland.konigsberg, 1934, 10), LocStart.date(1939, 9, 2))
  override val startDate = MTime(1934, 10)
  override val endDate: MTime = MTime(1945, 5, 8)
  //override implicit val startEnd: MTime2 = MTime2(startDate, endDate)
  override val locPosns: MTimeSeries[LatLongOpt] = MTimeSeries(Baltland.konigsberg, (MTime(1939, 9, 2), LLNone))
}

object DeuCp2 extends CorpsNumbered
{ override val polity: Polity = Germany
  override val corpsNum: Int = 2
  override val locStarts: RArr[LocStart] = RArr(LocStart.date(1935, 4))
  override val startDate = MTime(1935, 4)
  override val endDate: MTime = MTime(1945, 5, 8)
  override val locPosns: MTimeSeries[LatLongOpt] = MTimeSeries(LLNone)
}

object DeuCp4 extends CorpsNumbered
{ override val polity: Polity = Germany
  override val corpsNum: Int = 4
  override val locStarts: RArr[LocStart] = RArr(LocStart(Germania.dresden.latLong, 1934, 10), LocStart.date(1939, 11))
  override val startDate = MTime(1935, 4)
  override val endDate: MTime = MTime(1945, 5, 8)
  override val locPosns: MTimeSeries[LatLongOpt] = MTimeSeries(Germania.dresden.latLong, (MTime(1939, 10), LLNone))
}

object FraCp1 extends CorpsNumbered
{ override val polity: Polity = France
  override val corpsNum: Int = 1
  override val locStarts: RArr[LocStart] = RArr(LocStart(Frankia.calais, 1939, 8, 27), LocStart.date(1939, 11, 15))
  override val startDate: MTime = MTime(1939, 8, 27)
  override val endDate: MTime = MTime(1945, 5, 8)
  override val locPosns: MTimeSeries[LatLongOpt] = MTimeSeries(LLNone, (MTime(1939, 8, 27), Frankia.calais))
}

object FraCp2 extends CorpsNumbered
{ override val polity: Polity = France
  override val corpsNum: Int = 2
  override val locStarts: RArr[LocStart] = RArr(LocStart.date(1939, 8, 23))
  override val startDate = MTime(1939, 8, 23)
  override val endDate: MTime = MTime(1940, 5, 26)
  override val locPosns: MTimeSeries[LatLongOpt] = MTimeSeries(LLNone)
}