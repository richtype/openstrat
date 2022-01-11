/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Trait for Show for product types. This trait is implemented directly by the type in question, unlike the corresponding [[ShowProductT]] trait
 * which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 * [[ShowProductT]]. */
trait ShowProduct extends Any with Show
{
  /** A [[Strings]] Arr collection  of the show methods return values of the elements of this Show Product class. */
  def showElemStrs(way: ShowStyle, decimalPlaces: Int): Strings

  /** A [[Strings]] Arr of the element names of this Show Product class. */
  def elemNames: Strings

  /** A [[Strings]] Arr of the element type names of this Show Product class. */
  def elemTypeNames: Strings

  def showSemisNames(maxPlaces: Int = -1, minPlaces: Int = 0): String =
    elemNames.zipMap(showElemStrs(ShowStandard, maxPlaces))((n, s) => n + " = " + s).mkStr("; ")

  override def show(style: ShowStyle, maxPlaces: Int = -1, minPlaces: Int = 0): String =
  {
    def semisStr = showElemStrs(ShowCommas, maxPlaces).mkStr("; ")

    style match
    {
      case ShowSemis => semisStr
      case ShowCommas => showElemStrs(ShowStandard, maxPlaces).mkStr(", ")
      case ShowParamNames => typeStr + showSemisNames(maxPlaces, minPlaces).enParenth
      case ShowSemisNames => showSemisNames(maxPlaces, minPlaces)

      case ShowStdTypedFields =>
      {
        val inner = elemNames.zipMap2(elemTypeNames, showElemStrs(ShowStandard, maxPlaces))((n, t, s) => n + ": " + t + " = " + s).mkStr(", ")
        typeStr + inner.enParenth
      }

      case _ => typeStr.appendParenth(semisStr)
    }
  }
  override def str: String = show(ShowStandard, 1, 0)
}

/** Trait for Show for product types. This trait is implemented directly by the type in question, unlike the corresponding [[ShowProductT]] trait
 * which externally acts on an object of the specified type to create its String representations. For your own types ShowProduct is preferred over
 * [[ShowProductT]]. */
trait ShowProductPrec extends Any with ShowPrec with ShowProduct
{

}