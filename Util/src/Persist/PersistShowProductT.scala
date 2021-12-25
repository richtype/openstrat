/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pParse._

/** The base trait for the persistence of algebraic product types, including case classes. Note the arity of the product, its size is based on the
 *  number of logical parameters. For example, a LineSeg is a product 2, it has a start point and an end point, although its is stored as 4 parameters
 *  xStart, yStart, xEnd, yEnd. */
trait PersistProduct[R] extends /*ShowProductT[R] with */Persist[R]
{
  override def fromExpr(expr: ParseExpr): EMon[R] = expr match
  {
    case AlphaBracketExpr(IdentLowerToken(_, typeName), Arr1(ParenthBlock(sts, _, _))) if typeStr == typeName => ??? // fromParameterStatements(sts)
    //case AlphaBracketExpr(IdentUpperToken(fp, typeName), _) => fp.bad(typeName -- "does not equal" -- typeStr)
    case _ => expr.exprParseErr[R](this)
  }
}

trait PersistShowProductT[R] extends PersistProduct[R] with ShowProductT[R]

trait PersistShowerProduct[R <: Show] extends PersistProduct[R]