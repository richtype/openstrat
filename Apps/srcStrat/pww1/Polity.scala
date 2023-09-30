/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww1

trait Polity extends ShowSimpled
{ override def typeStr: String = "Polity"
  def colour: Colour
}

object Polity
{
  object PolityPersist extends PersistSingletons[Polity]("Polity")
  {
    override val singletons: RArr[Polity] = RArr(Germany, Britain, France)
  }
}

object Germany extends Polity
{ def str: String = "Germany"
  def colour = Colour.fromInts(128, 177, 179)//CadetBlue 60% shade
}

object Britain extends Polity
{ def str: String = "Britain"
  def colour = Colour.fromInts(255, 232, 184)
}

object France extends Polity
{ def str: String = "France"
  def colour = Colour.fromInts(125, 255, 255)
}