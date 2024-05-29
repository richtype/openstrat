/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._, Colour._

object OnlyCss  extends CssSpec
{ /** The CSS rules. */
  override def rules: RArr[CssRuleLike] = RArr(CssBody(DispFlex, DecMinHeight(98.vh), DecFlexDirnCol), CssButton(DecFontSize(1.5.em)),
    CssObjectRule("footer", DecAlignCen, DecMarg(0.8.em), DecColour(FireBrick)))

  override def endStr: String =
"""
  |#footer
  |{
  |	text-align: center;
  |	margin: 0.8em;
  |	color: FireBrick;
  |}
  |ul, ol, p
  |{
  |    max-width: 68em;
  |    margin-left: auto;
  |    margin-right: auto;
  |}
  |p
  |{
  |	margin-top: 0.5em;
  |    margin-bottom: 0.5em;
  |}
  |
  |h1 { text-align: center; }
  |
  |canvas {
  |  height: 100vh;
  |  width: 100vw;
  | display:block; }
  |
  |@media (min-width:50em)
  |{
  |	#topmenu li
  |	{
  |	   display: inline-block;
  |	   background-color: #dddddd;
  |	   padding: 0.2em;
  |	   border: 0.2em solid yellow;
  |
  |	}
  |	#topmenu { text-align: center; max-width: 100em;}
  |
  |   #bottommenu {display: none;}
  |}
  |
  |@media (max-width:50em)
  |{
  |	#topmenu { display: none; }
  |	#bottommenu li
  |	{
  |	   display: inline-block;
  |	   background-color: #dddddd;
  |	   padding: 0.2em;
  |	   border: 0.2em solid green;
  |	}
  |}
  |
|""".stripMargin
}
