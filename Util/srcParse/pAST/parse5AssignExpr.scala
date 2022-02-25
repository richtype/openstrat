/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package pAST

/** Function object to parse assignment expressions. */
object parse5AssignExpr
{ /** Function apply method parses assignment expressions. */
  def apply (implicit seg: Arr[StatementMem]): EMon[Expr] =
  {
    val leftAcc: Buff[AssignMem] = Buff()
    val rightAcc: Buff[AssignMem] = Buff()

    def leftLoop(rem: ArrOff[StatementMem]): EMon[Expr] = rem match
    { case ArrOff0() => parse6Clauses(leftAcc.toArr)

      case ArrOff1Tail(at@AsignToken(_), tail) => parse6Clauses(leftAcc.toArr).flatMap(gLs => rightLoop(tail).map { gRs =>
        AsignExpr(gLs, at, gRs)
      })

      case ArrOff1Tail(h: AssignMem, tail) => { leftAcc.append(h); leftLoop(tail) }
      case ArrOff1Tail(h, tail) => debexc(h.toString + " is not AssignMemExpr.")
    }

    def rightLoop(rem: ArrOff[StatementMem])(implicit seg: Arr[StatementMem]): EMon[AssignMemExpr] = rem match
    { case ArrOff0() => parse6Clauses(rightAcc.toArr)
      case ArrOffHead(at: AsignToken) => bad1(at, "Prefix operator not followed by expression")
      case ArrOff1Tail(am: AssignMem, tail) => { rightAcc.append(am); rightLoop(tail) }
    }

    leftLoop(seg.offset0)
  }
}