/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

object GeomPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Geom Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Geom Module"), main)
  def list: HtmlHeadOl = HtmlHeadOl("The Geom module contains".xCon, geomItme, colourItem, graphicItem, compound, trans)

  def geomItme: HtmlLi = HtmlLi.str("Geometry. Immutable classes for points, lines and shapes. These classes build on the Array based collections" +
    " from the Util module.")

  def colourItem: HtmlLi = HtmlLi.str("Colour class. A 32 bit integer class that can be built from rgba and named values.")
  def graphicItem: HtmlLi = HtmlLi.str("Graphic primitives. Immutable classes for fills, draws and active elements based on the geometry classes.")
  def compound: HtmlLi = HtmlLi.str("Compound Graphics. Again immutable classes. Useful for selection and placing.")
  def trans: HtmlLi = HtmlLi.str("Geometric transformations on both the geometric and graphical elements, preserving maximum type information.")
  def web = HtmlLi.str("Web library. Classes for XML, HTML, CSS and simple JavaScript functions. These pages have been enerated using this.")

  def main: HtmlDiv = HtmlDiv.classAtt("main", list, mainStr.xCon)

  def mainStr: String =
    """
      |<h2>Contents</h2>
      |  The Graphics module contains geometric and graphical software.
      |  <ol>
      |    <li>ostrat.geom A pure or near pure functional package.
      |        <ul>
      |          <li>A number of implementation Value classes of the Int and Double product classes defined in ostrat.</li>
      |          <li>2d graphical objects for generalised use. They are of particular use for the generic canvas based classes defined in pCanv but can be
      |            used in any display framework and for printing.</li>
      |
      |        </ul>
      |      </li>
      |    <li>ostrat.p3d Currently just a stub. I have included it because 3d is the preferred GUI. I have started with 2d, just because 3d development is
      |        highly time consuming and I want to focus on game play and what might might be described as the algebra of tiling. There is no "physics
      |        engine", although there is time and distance and other basic mechanics maths will probably be included later. I prefer 3d graphics, but as we
      |        are dealing with animations not a "physics engine", 2d and 3d are completely interchangeable.  There will also be a command line interface.
      |        This will be the canonical interface, although it obviously won't be the normal user interface.
      |      </li>
      |      <li> ostrat.pCanv depends on geom. This could be made into a separate module, but I don't see any great advantage.
      |        <ul>
      |          <li>Abstract canvas and classes for placing objects on that abstract canvas.</li>
      |          <li>classes for the manipulation and display of maps.</li>
      |          <li>Mouse and other abstract controls.</li>
      |          <li>An implementation of Canvas for Jvm using JavaFx.</li>
      |          <li>An implementation of Canvas for Html Canvas using JavaScript.</li>
      |          <li>There is no implementation for Native yet. I'm waiting for Scala-native to get up on 2.12 before experimenting. Running a game server in
      |            native should pose no problems. However there is no easily accessible canvas for native on Windows or Linux. The abstract canvas api could
      |            be implemented on DirectX or OpenGL, but this would require significantly more work than for the ScalaFx canvas or the Html Canvas.
      |          </li>
      |        </ul>
      |      </li>
      |
      |     </ul>
      |  </ol>
      |</div>
      |""".stripMargin
}
