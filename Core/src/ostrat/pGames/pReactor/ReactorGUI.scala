/* Copyright 2018 w0d. Licensed under Apache Licence version 2.0 */

package ostrat
package pGames.pRodHello

import geom._, pCanv._, Colour._

case class RodGUI (canv: CanvasPlatform) extends CanvasSimple {
  deb("Console Debugging On..")
  val title = "reactor.."
  val size = 40
  val rows = 8
  val cols = 10
  var players = Array(Red, Green, Yellow, Blue)
  var playersTurnIndex = 0

  //Array.fill[Byte](5)(0)
  var cellCounts = Array.fill[Int](rows*cols)(0)
  var cellColors = Array.fill[Colour](rows*cols)(Black)
  var cellNeighbours = new Array[Array[Int]](80)

  canv.polyFill(Rectangle(width, height, 0 vv 0).fill(Colour(0xFF161616)))

  for( r <- 0 to rows-1; c <- 0 to cols-1){
    val index = c+cols*r
    cellNeighbours(index) = Array[Int]()

    canv.polyFill(Rectangle.fromBL(size-1, size-1, size*c vv size*r).fill(cellColors(index)))

    if (c>0) cellNeighbours(index) = (index-1) +: cellNeighbours(index)
    if (r>0) cellNeighbours(index) = (index-cols) +: cellNeighbours(index)
    if (c<(cols-1)) cellNeighbours(index) = (index+1) +: cellNeighbours(index)
    if (r<(rows-1)) cellNeighbours(index) = (index+cols) +: cellNeighbours(index)
  }
  
  canv.polyFill(Rectangle.fromBL(size/2, size/2, -size vv -size).fill(players(playersTurnIndex)))

  mouseUp = (v, but: MouseButton, clickList) => (v, but, clickList) match
    {
      case (v, LeftButton, cl) =>
      {
        if(v._1 >= 0  &&  v._1 < (size*cols)  &&  v._2 >= 0  &&  v._2 < (size*rows)){
          val r = (v._2/size).toInt
          val c = (v._1/size).toInt
          val index = c+cols*r
          if (players(playersTurnIndex) == cellColors(index) || Black  == cellColors(index)) {
            canv.polyFill(Rectangle.fromBL(size-1, size-1, size*c vv size*r).fill(players(playersTurnIndex)))
            cellColors(index) = players(playersTurnIndex)
            cellCounts(index) += 1
            playersTurnIndex = playersTurnIndex + 1
            if (playersTurnIndex >= players.length) playersTurnIndex = 0
            canv.polyFill(Rectangle.fromBL(size/2, size/2, -size vv -size).fill(players(playersTurnIndex)))
            deb("cellCounts(index) "+cellCounts(index).toString)
            canv.textGraphic((size*c+size/2) vv (size*r+size/2), cellCounts(index).toString, 12, players(playersTurnIndex))
          }
        }
     }
      case _ => deb("Mouse other")
    }   
}

//  var renderings = List[PolyFill]()
//  renderings = Rectangle.fromBL(size-1, size-1, size*c vv size*r).fill(Orange) :: renderings
//  repaint(renderings)
//  deb("renderings.length = " + renderings.length)
  // deb("myGrid.length = " + myGrid.length)
  // for ( i <- 0 to (myGrid.length - 1) ) {
  //   deb("myGrid(" + i + ") = " + myGrid(i))
  // } 
/*  mouseUp = (v, b, s) =>
  { // mouseLoc, whichBtn, HitList
    deb(v.toString -- b.toString -- s.length.toString)
    if (s.length>0) deb(s(0).toString)
  } 
*/
 //  val rndY = new scala.util.Random().nextInt(300) 
 //  val arr = Array.apply(4,5,6,7)
 //  deb(arr.apply(3).toString)
 //  val rndX = - new scala.util.Random().nextInt(300)  
 //  var ptStart: Vec2 = 200 vv rndY
 //  val t1 = TextGraphic(ptStart, title, 18, Colour.Blue)
 //  ptStart = rndX vv new scala.util.Random().nextInt(300)  
 //  val t2 = TextGraphic(ptStart, canv.getTime.toString, 18, LightBlue)
  
 //  val t3 = TextGraphic(ptStart + Vec2.apply(100, 100), canv.getTime.toString, 18, LightBlue) //dont need the new Vec2(100,100) as has factory method which Vec2.apply(100,100) == Vec()
 //  //val t3 = TextGraphic(ptStart.addXY(100, 100), canv.getTime.toString, 18, LightBlue)
 //  val t4 = TextGraphic(Vec2(new scala.util.Random().nextInt(300), new scala.util.Random().nextInt(300)), canv.getTime.toString, 18, LightBlue)
 
 //  val recty = Rectangle(300, 100, 0 vv 0).fillSubj("I am a rectangle", Green)
 
 //  val l1 = List(t1, t2)
 //  val l3 = l1 :+ t3
 //  val l4 = recty :: l1
 //  //repaints(t1, t2)
 //  repaint(l4)
 // deb("debuggin the console")

  
 // val timesUp = () => {
 //   repaint(Nil)
 //   ///    refresh
   
 //  }
 
 // canv.timeOut(timesUp, 30000)
 
 // val clicky = () => {
 //   deb("clicky")
 //   repaints(recty, TextGraphic(Vec2(-300, -300), "you chose wisely..", 18, Yellow))
 // }
 
 // mouseUp = (v, b, s) =>
 //   {
 //     clicky()
 //     deb(v.toString -- b.toString -- s.toString)} 


// case class RodGUI (canv: CanvasPlatform) extends CanvasSimple {
//   val title = "now you see it.."
//   val rndY = new scala.util.Random().nextInt(300) 
//   val arr = Array.apply(4,5,6,7)
//   deb(arr.apply(3).toString)
//   val rndX = - new scala.util.Random().nextInt(300)  
//   var ptStart: Vec2 = 200 vv rndY
//   val t1 = TextGraphic(ptStart, title, 18, Colour.Blue)
//   ptStart = rndX vv new scala.util.Random().nextInt(300)  
//   val t2 = TextGraphic(ptStart, canv.getTime.toString, 18, LightBlue)
  
//   val t3 = TextGraphic(ptStart + Vec2.apply(100, 100), canv.getTime.toString, 18, LightBlue) //dont need the new Vec2(100,100) as has factory method which Vec2.apply(100,100) == Vec()
//   //val t3 = TextGraphic(ptStart.addXY(100, 100), canv.getTime.toString, 18, LightBlue)
//   val t4 = TextGraphic(Vec2(new scala.util.Random().nextInt(300), new scala.util.Random().nextInt(300)), canv.getTime.toString, 18, LightBlue)
 
//   val recty = Rectangle(300, 100, 0 vv 0).fillSubj("I am a rectangle", Green)
 
//   val l1 = List(t1, t2)
//   val l3 = l1 :+ t3
//   val l4 = recty :: l1
//   //repaints(t1, t2)
//   repaint(l4)
//  deb("debuggin the console")

  
//  val timesUp = () => {
//    repaint(Nil)
//    ///    refresh
   
//   }
 
//  canv.timeOut(timesUp, 30000)
 
//  val clicky = () => {
//    deb("clicky")
//    repaints(recty, TextGraphic(Vec2(-300, -300), "you chose wisely..", 18, Yellow))
//  }
 
//  mouseUp = (v, b, s) =>
//    {
//      clicky()
//      deb(v.toString -- b.toString -- s.toString)} 
// }


