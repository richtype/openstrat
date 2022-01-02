/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._, annotation.unchecked.uncheckedVariance

/** The UnShow type class produces an object in memory or an error sequence from RSON syntax strings. */
trait UnShow[+T] extends TypeStred
{
  def fromExpr(expr: Expr): EMon[T]
  
  /** Trys to build an object of type T from the statement. Not sure if this is useful. */
  final def fromStatement(st: Statement): EMon[T] = fromExpr(st.expr)

  /** Produces an ArrImut of the UnShow type from Statements (Refs[Statement]. */
  def valuesFromStatements[ArrT <: SeqImut[T] @uncheckedVariance](sts: Statements)(implicit arrBuild: ArrBuilder[T, ArrT] @uncheckedVariance): ArrT =
    sts.mapCollectGoods(fromStatement)(arrBuild)

  /** Produces a List of the UnShow type from List of Statements */
  def valueListFromStatements(l: Statements): List[T] = l.map(fromStatement(_)).collectList{ case Good(value) => value }

  /** Finds value of UnShow type, returns error if more than one match. */
  def findUniqueFromStatements(sts: Statements): EMon[T] = valueListFromStatements(sts) match
  { case Nil => TextPosn.emptyError("No values of type found")
    case h :: Nil => Good(h)
    case s3 => sts.startPosn.bad(s3.length.toString -- "values of" -- typeStr -- "found.")
  }

  /** Finds value of this UnShow type, returns error if more than one match. */
  def findUniqueTFromStatements[ArrT <: SeqImut[T] @uncheckedVariance](sts: Statements)(implicit arrBuild: ArrBuilder[T, ArrT] @uncheckedVariance):
    EMon[T] = valuesFromStatements(sts) match
  { case s if s.dataLength == 0 => TextPosn.emptyError("No values of type found")
    case s if s.dataLength == 1 => Good(s.head)
    case s3 => sts.startPosn.bad(s3.dataLength.toString -- "values of" -- typeStr -- "found.")
  }

  /** Finds a setting of the type of this UnShow instance from a [Statement]. */
  def settingTFromStatement(settingStr: String, st: Statement): EMon[T] = st match
  { case NonEmptyStatement(AsignExpr(IdentLowerToken(_, sym), _, rightExpr), _) if sym == settingStr => fromExpr(rightExpr)
    case _ => st.startPosn.bad(typeStr -- "not found.")
  }

  /** Finds a setting of the type of this UnShow instance from an Arr[Statement]. */
  def settingTFromStatements(sts: Arr[Statement], settingStr: String): EMon[T] = sts match
  { case Arr0() => TextPosn.emptyError("No Statements")
    case Arr1(st1) => settingTFromStatement(settingStr, st1)
    case s2 => sts.map(settingTFromStatement(settingStr, _)).collect{ case g @ Good(_) => g } match
    { case Arr1(t) => t
      case Arr0() => sts.startPosn.bad(settingStr -- typeStr -- "Setting not found.")
      case s3 => sts.startPosn.bad(s3.dataLength.toString -- "settings of" -- settingStr -- "of" -- typeStr -- "not found.")
    }
  }
}

/** Companion object for the [[UnShow]] type class trait, contains implicit instances for common types. */
object UnShow
{
  //implicit def tuple2Implicit[A1, A2](implicit ev1: Persist[A1], ev2: Persist[A2], eq1: EqT[A1], eq2: EqT[A2]): Persist[Tuple2[A1, A2]] =
  // Persist2[A1, A2, (A1, A2)]("Tuple2", "_1", _._1, "_2", _._2, (a1, a2) => (a1, a2))

  implicit val intImplicit: UnShow[Int] = new UnShow[Int]
  {
    override def typeStr: String = "Int"

    override def fromExpr(expr: Expr): EMon[Int] = expr match {
      case IntDeciToken(i) => Good(i)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toInt)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toInt)
      case _ => expr.exprParseErr[Int]
    }
  }

  implicit val doubleImplicit: UnShow[Double] = new UnShow[Double]
  {
    override def typeStr: String = "DFloat"
    //override def syntaxDepthT(obj: Double): Int = 1

    override def fromExpr(expr: Expr): EMon[Double] = expr match {
      case NatDeciToken(_, i) => Good(i.toDouble)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toDouble)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toDouble)
      case intok: IntNegToken => Good(intok.getInt.toDouble)
      case e => { debvar(e); expr.exprParseErr[Double] }
    }
  }

  implicit val longImplicit: UnShow[Long] = new UnShow[Long]
  {
    override def typeStr = "Long"
    def strT(obj: Long): String = obj.toString
    override def fromExpr(expr: Expr): EMon[Long] = expr match
    { case NatDeciToken(_, i) => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toLong)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-i.toLong)
      case  _ => expr.exprParseErr[Long]
    }
  }

  implicit val booleanImplicit: UnShow[Boolean] = new UnShow[Boolean]
  {
    override def typeStr: String = "Bool"

    override def fromExpr(expr: Expr): EMon[Boolean] = expr match
    { case IdentLowerToken(_, str) if str == "true" => Good(true)
      case IdentLowerToken(_, str) if str == "false" => Good(false)
      case _ => expr.exprParseErr[Boolean]
    }
  }

  implicit val stringImplicit: UnShow[String] = new UnShow[String]
  {
    override def typeStr: String = "Str"
    def strT(obj: String): String = obj.enquote

    override def fromExpr(expr: Expr): EMon[String] = expr match
    { case StringToken(_, stringStr) => Good(stringStr)
      case  _ => expr.exprParseErr[String]
    }
  }

  implicit val floatImplicit: UnShow[Float] = new UnShow[Float]
  {
    override def typeStr: String = "SFloat"

    override def fromExpr(expr: Expr): EMon[Float] = expr match
    { case NatDeciToken(_, i) => Good(i.toFloat)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "+" => Good(i.toFloat)
      case PreOpExpr(op, NatDeciToken(_, i)) if op.srcStr == "-" => Good(-(i.toFloat))
      /*  case FloatToken(_, _, d) => Good(d.toFloat)
        case PreOpExpr(op, FloatToken(_, _, d)) if op.srcStr == "+" => Good(d.toFloat)
        case PreOpExpr(op, FloatToken(_, _, d)) if op.srcStr == "-" => Good(-d.toFloat)
       */ case  _ => expr.exprParseErr[Float]
    }
  }

  implicit val charImplicit: UnShow[Char] = new UnShow[Char]
  {
    override def typeStr: String = "Char"

    override def fromExpr(expr: Expr): EMon[Char] = expr match
    { case CharToken(_, char) => Good(char)
      case  _ => expr.exprParseErr[Char]
    }
  }

  implicit val arrayIntImplicit: UnShow[Array[Int]] = new UnShow[Array[Int]]//(ShowT.intPersistImplicit)
  {
    def typeStr: String = "Array[Int]"

    override def fromExpr(expr: Expr): EMon[Array[Int]] = expr match
    { case SemicolonToken(_) => Good(Array[Int]())
      case AlphaBracketExpr(IdentUpperToken(_, "Seq"), Arr2(SquareBlock(ts, _, _), ParenthBlock(sts, _, _))) => ???
      //sts.eMap[Int](_.errGet[Int](evA)).map(_.array)
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }

  /** Implicit method for creating List[A: Persist] instances. */
  implicit def listImplicit[A](implicit evIn: UnShow[A]): UnShow[List[A]] = new UnShow[List[A]]// with ShowIterable[A, List[A]]
  {
    val evA: UnShow[A] = evIn
    override def typeStr: String = "Seq" + evA.typeStr.enSquare
    override def fromExpr(expr: Expr): EMon[List[A]] = expr match
    {
      case eet: EmptyExprToken => Good(List[A]())
      case AlphaSquareParenth("Seq", ts, sts) => ??? //sts.eMap(s => evA.fromExpr(s.expr)).toList
      case AlphaParenth("Seq", sts) => ??? // sts.eMap[A](_.errGet[A](evA))
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }

  /** Implicit method for creating List[A: Persist] instances. */
  implicit def vectorImplicit[A](implicit evIn: UnShow[A]): UnShow[Vector[A]] = new UnShow[Vector[A]]
  {
    val evA: UnShow[A] = evIn
    override def typeStr: String = "Seq" + evA.typeStr.enSquare
    override def fromExpr(expr: Expr): EMon[Vector[A]] = expr match
    {
      case eet: EmptyExprToken => Good(Vector[A]())
      case AlphaSquareParenth("Seq", ts, sts) => ??? //sts.eMap(s => evA.fromExpr(s.expr)).toList
      case AlphaParenth("Seq", sts) => ??? // sts.eMap[A](_.errGet[A](evA))
      case e => bad1(expr, "Unknown Exoression for Seq")
    }
  }

  //implicit def optionImplicit[A](implicit evIn: UnShow[A]): UnShow[Option[A]] = new UnShow[Option[A]]
}