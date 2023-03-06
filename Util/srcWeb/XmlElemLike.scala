/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML or an HTML element */
trait XmlElemLike extends XCon
{ /** The XML /HTML tag String. A tag is a markup construct that begins with < and ends with > */
  def tag: String
  def attribs: RArr[XmlAtt]
  def contents: RArr[XCon]

  def attribsOut: String = ife(attribs.empty, "", " " + attribs.foldStr(_.str, " "))
  def openAtts: String = "<" + tag + attribsOut
  def openUnclosed: String = openAtts + ">"

  def closeTag: String = "</" + tag + ">"
  def n1CloseTag: String = "\n" + closeTag
  def n2CloseTag: String = "\n\n" + closeTag
}

/** An XML element. */
trait XmlElem extends XmlElemLike
{
  override def out(indent: Int = 0, maxLineLen: Int = 150): String = if (contents.empty) openAtts + "/>"
    else openUnclosed.nli(indent + 2) + contents.foldStr(_.out(indent + 2, 150), "\n" + (indent + 2).spaces).nli(indent) + closeTag
}

/** Content for XML and HTML elements. */
trait XCon
{ /** Returns the XML source code, formatted according to the input. */
  def out(indent: Int, maxLineLen: Int): String
}

/** XConStr is a wrapper to convert [[String]]s to XCon, XML Element content. */
case class XConStr(value: String) extends XCon
{ override def out(indent: Int, maxLineLen: Int): String = value
}

object XConStr
{ implicit def StringToXConStr(value: String): XConStr = new XConStr(value)
}