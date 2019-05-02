/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import pParse._

/** The essential persistence type class. it implements both a Show style type class interface, the production of a String representation of the value
  * but also produces an EMon[T] from a String. It Persists and builds objects of type T from CRON syntax. So for example the IntImplicit object in the
  * Persist companion object persists Integers and constructs Integers from Strings. */
trait Persist[T] extends Show[T] with UnShow[T]

/** Companion object for the persistence type class. Contains the implicit instances for Scala standard library types. */
object Persist
{  
  /** Implicit method for creating List[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def listToPersist[A](implicit ev: Persist[A]): Persist[List[A]] = new PersistListImplicit[A](ev)  
  /** Implicit method for creating Seq[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def seqToPersist[T](implicit ev: Persist[T]): Persist[Seq[T]] = new PersistSeqImplicit[T](ev)
  /** Implicit method for creating Vector[A: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def vectorToPersist[T](implicit ev: Persist[T]): Persist[Vector[T]] = new PersistVectorImplicit[T](ev)  
  
  /** Implicit method for creating Array[A <: Persist] instances. This seems to have to be a method rather directly using an implicit class */
  implicit def arrayRefToPersist[A <: AnyRef](implicit ev: Persist[A]): Persist[Array[A]] = new ArrayRefPersist[A](ev) 
  
  implicit def seqToPersistDirect[A](thisSeq: Seq[A])(implicit ev: Persist[A]): PersistSeqDirect[A] = new PersistSeqDirect[A](thisSeq, ev)
  
  implicit def optionToPersist[A](implicit ev: Persist[A]): Persist[Option[A]] = new OptionPersist[A](ev)  
  
  class OptionPersist[A](val ev: Persist[A]) extends Persist[Option[A]]  
  {    
    override def typeStr: String = "Option" + ev.typeStr.enSquare
    override def syntaxDepth: Int = ev.syntaxDepth
    override def show(obj: Option[A]) = obj.fold("")(ev.show(_))
    def showComma(obj: Option[A]): String = show(obj)
    def showSemi(obj: Option[A]): String = show(obj)
    override def showTyped(obj: Option[A]): String = obj.fold("None")(typeStr + ev.show(_).enParenth)
    
    override def fromClauses(clauses: Seq[ostrat.pParse.Clause]): ostrat.EMon[Option[A]] = ???
    override def fromExpr(expr: ostrat.pParse.Expr): ostrat.EMon[Option[A]] = ???
    override def fromStatement(st: ostrat.pParse.Statement): ostrat.EMon[Option[A]] = ???
  }
  
  implicit val NonePersistImplicit: Persist[None.type] = new PersistSimple[None.type]("None")
  {   
    override def show(obj: None.type) = ""   
    def fromExpr(expr: Expr): EMon[None.type] = expr match
    {
      case eet : EmptyExprToken => Good(None)
      case e => bad1(e, "None not found")
    }
  }
  
  implicit val ArrayIntPersistImplicit: Persist[Array[Int]] = new PersistSeqLike[Int, Array[Int]]('Seq, Persist.IntPersist)
  {       
    override def showSemi(thisArray: Array[Int]): String = thisArray.map(ev.showComma(_)).semiFold
    override def showComma(thisArray: Array[Int]): String = thisArray.map(ev.show(_)).commaFold
    override def fromParameterStatements(sts: List[Statement]): EMon[Array[Int]] = bad1(FilePosn.empty, "ArrayInt from statements")
    override def fromClauses(clauses: Seq[Clause]): EMon[Array[Int]] = ???
  
    override def fromExpr(expr: Expr): EMon[Array[Int]] = expr match
    { case SemicolonToken(_) => Good(Array[Int]())
      case AlphaBracketExpr(AlphaToken(_, "Seq"), Seq(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) =>
        sts.eMonMap[Int](_.errGet[Int](ev)).map(_.toArray)
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }
  
  implicit val IntPersist: Persist[Int] = new PersistSimple[Int]("Int")
  { def show(obj: Int): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Int] = expr match      
    { case IntToken(_, _, i) => Good(i)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-i)
      case  _ => expr.exprParseErr[Int]
    }
  }
  
  implicit val CharPersist: Persist[Char] = new PersistSimple[Char]("Char")
  { def show(obj: Char): String = obj.toString.enqu1
    override def fromExpr(expr: Expr): EMon[Char] = expr match      
    { case CharToken(_, char) => Good(char)        
      case  _ => expr.exprParseErr[Char]
    }
  }
   
  implicit val StringPersist: Persist[String] = new PersistSimple[String]("Str")
  { def show(obj: String): String = obj.enqu
    override def fromExpr(expr: Expr): EMon[String] = expr match      
    { case StringToken(_, stringStr) => Good(stringStr)        
      case  _ => expr.exprParseErr[String]
    }
  }
   
  implicit val LongPersist: Persist[Long] = new PersistSimple[Long]("Long")
  { def show(obj: Long): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Long] = expr match      
    { case IntToken(_, _, i) => Good(i.toLong)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i.toLong)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-i.toLong)
      case LongIntToken(_, _, li) => Good(li)
      case PreOpExpr(op, LongIntToken(_, _, li)) if op.str == "+" => Good(li)
      case PreOpExpr(op, LongIntToken(_, _, li)) if op.str == "-" => Good(-li)
      case  _ => expr.exprParseErr[Long]
    }
  }   
   
  implicit val FloatPersist: Persist[Float] = new PersistSimple[Float]("SFloat")
  { def show(obj: Float): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Float] = expr match      
    { case IntToken(_, _, i) => Good(i.toFloat)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i.toFloat)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-(i.toFloat))
      case FloatToken(_, _, d) => Good(d.toFloat)
      case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "+" => Good(d.toFloat)
      case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "-" => Good(-d.toFloat)
      case  _ => expr.exprParseErr[Float]
    }
  }
  
  implicit val DoublePersist: Persist[Double] = new PersistSimple[Double]("DFloat")
  { def show(obj: Double): String = obj.toString      
    override def fromExpr(expr: Expr): EMon[Double] = expr match      
    { case IntToken(_, _, i) => Good(i.toDouble)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "+" => Good(i.toDouble)
      case PreOpExpr(op, IntToken(_, _, i)) if op.str == "-" => Good(-(i.toDouble))
      case FloatToken(_, _, d) => Good(d)
      case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "+" => Good(d)
      case PreOpExpr(op, FloatToken(_, _, d)) if op.str == "-" => Good(-d)
      case  _ => expr.exprParseErr[Double]
    } 
  }   
   
  implicit val BooleanPersist: Persist[Boolean] = new PersistSimple[Boolean]("Bool")
  { override def show(obj: Boolean): String = obj.toString  
    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case AlphaToken(_, str) if str == "true" => Good(true)
      case AlphaToken(_, str) if str == "false" => Good(false)
       case _ => expr.exprParseErr[Boolean]
     }
  }
}
