/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs
import geom._
import pDisp._
import org.scalajs.dom._

object CanvasJs extends CanvasTopLeft
{
   selfJs =>
   val can: html.Canvas = document.getElementById("scanv").asInstanceOf[html.Canvas] 
   override def width = can.width
   override def height = can.height 
   def setup() =
   {
      can.width = (window.innerWidth).max(200).toInt //-20
      can.height = (window.innerHeight).max(200).toInt //-80
   }
   setup
   
   def getButton(e: MouseEvent): MouseButton = e.button match
   {
      case 0 => LeftButton
      case 1 => MiddleButton
      case 2 => RightButton
   } 
   can.onmouseup = (e: MouseEvent) =>
      {
         val rect: ClientRect = can.getBoundingClientRect()
         mouseUpTopLeft(e.clientX - rect.left, e.clientY -rect.top, getButton(e))
      }
      
   can.onmousedown = (e: MouseEvent) =>
      {
         val rect = can.getBoundingClientRect()         
         mouseDownTopLeft(e.clientX - rect.left, e.clientY  -rect.top, getButton(e))
      }  

   can.asInstanceOf[scalajs.js.Dynamic].onwheel = (e: WheelEvent) => 
      {
         e.deltaY match
         {
            case 0 => 
            case d if d < 0 => onScroll(true)
            case _ => onScroll(false)
         }
         e.preventDefault() //Stops the page scrolling when the mouse pointer is over the canvas
      }
      
   can.oncontextmenu = (e: MouseEvent) => e.preventDefault()
   window.onresize = (e: UIEvent) =>
   {
      setup
      resize()
   }   
     
   override def getTime: Double = new scala.scalajs.js.Date().getTime()      
   override def timeOut(f: () => Unit, millis: Integer): Unit = window.setTimeout(f, millis.toDouble) 
   
   val gc = can.getContext("2d").asInstanceOf[raw.CanvasRenderingContext2D]   
   
   override def tlFillPoly(fp: FillPoly): Unit =    
   {      
      gc.beginPath()
      gc.moveTo(fp.xHead, fp.yHead)      
      fp.foreachVertPairTail(gc.lineTo)
      gc.closePath()
      gc.fillStyle = fp.colour.str            
      gc.fill()               
   }
   override def tlPolyDraw(verts: Vec2s, lineWidth: Double, lineColour: Colour): Unit =    
   {      
      gc.beginPath()
      gc.moveTo(verts.head1, verts.head2)
      verts.foreachPairTail(gc.lineTo)
      gc.closePath()      
      gc.strokeStyle = lineColour.str
      gc.lineWidth = lineWidth
      gc.stroke            
   }
   override def tlPolyFillDraw(verts: Vec2s, colour: Colour, lineWidth: Double, lineColour: Colour): Unit =    
   {      
      gc.beginPath()
      gc.moveTo(verts.head1, verts.head2)
      verts.foreachPairTail(gc.lineTo)
      gc.closePath()
      gc.fillStyle = colour.str            
      gc.fill()
      gc.strokeStyle = lineColour.str
      gc.lineWidth = lineWidth
      gc.stroke            
   }
   protected def tlLineSegsDraw(lineSegs: Seq[Line2], lineWidth: Double, linesColour: Colour): Unit =
   {           
      gc.beginPath
      lineSegs.foreach(ls => { gc.moveTo(ls.x1, ls.y1);  gc.lineTo(ls.x2, ls.y2)})
      gc.strokeStyle = linesColour.str
      gc.stroke()      
   }
   
   private def segsPath(segs: Seq[ShapeSeg]): Unit =
   {
      gc.beginPath()           
      var p1 = segs.last.endPt
      gc.moveTo(p1.x, p1.y)      
      segs.foreach(s =>
         {
            s match
            {
               case LineSeg(pt2) => gc.lineTo(pt2.x, pt2.y)
               case as: ArcSeg => as.fArcTo(p1, gc.arcTo) 
            }
            p1 = s.endPt
         })
         gc.closePath
   }
   
   override def tlShapeFill(segs: Seq[ShapeSeg], fillColour: Colour): Unit =
   {
      segsPath(segs)  
      gc.fillStyle = fillColour.str
      gc.fill
   }   
   
   override def tlShapeDraw(segs: Seq[ShapeSeg], lineWidth: Double, lineColour: Colour): Unit =
   {
      segsPath(segs)      
      gc.strokeStyle = lineColour.str
      gc.lineWidth = lineWidth
      gc.stroke   
   }     
   override def tlShapeFillDraw(segs: Seq[ShapeSeg], fillColour: Colour, lineWidth: Double, lineColour: Colour): Unit =
   {
      segsPath(segs)  
      gc.fillStyle = fillColour.str
      gc.fill
      gc.strokeStyle = lineColour.str
      gc.lineWidth = lineWidth
      gc.stroke   
   }
   override def tlArcDraw(arc: Arc, lineWidth: Double, lineColour: Colour): Unit =
   {
      gc.beginPath
      gc.moveTo(arc.startPt.x, arc.startPt.y)
      arc.fArcTo(gc.arcTo)
   }
   
   override def tlTextFill(x: Double, y: Double, str: String, fontSize: Int, colour: Colour, align: TextAlign): Unit = 
   {      
      gc.textAlign = align.jsStr
      gc.textBaseline = "middle"
      gc.font = fontSize.toString + "px Arial"
      gc.fillStyle = colour.str
      gc.fillText(str, x, y)      
   }
   override def tlTextDraw(x: Double, y: Double, str: String, fontSize: Int, colour: Colour): Unit = 
   {
      gc.strokeStyle = colour.str
      gc.lineWidth = 1
      gc.textAlign = "center"
      gc.textBaseline = "middle"
      gc.font = fontSize.toString + "px Arial"      
      gc.strokeText(str, x, y)
   }   

   override def clear(colour: Colour): Unit =
   {
      gc.fillStyle = colour.str
      gc.fillRect(0, 0, width, height)
   }
   override def tlClip(pts: Vec2s): Unit =
   {
      gc.beginPath     
      gc.moveTo(pts.head1, pts.head2)
      pts.foreachPairTail(gc.lineTo)
      gc.closePath()
      gc.clip()
   }
   override def gcRestore(): Unit = gc.restore() 
   override def gcSave(): Unit = gc.save()
   
   override def saveFile(fileName: String, output: String): Unit = window.localStorage.setItem(fileName, output)     
   override def loadFile(fileName: String): EMon[String] =
   {
      val nStr = window.localStorage.getItem(fileName)
      if (nStr == null) bad1(FilePosn(1, 1, "Js Error"), "File not found") else Good(nStr)
   }       
}
