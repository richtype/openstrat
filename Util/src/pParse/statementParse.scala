/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse

/** Parses String (with RSON syntax) searching for the String terminator. Returns error if end of file found first. Function object to parse a raw
 *  Statement of statement members, where sub blocks have already been parsed into Statement Blocks. */
object statementParse
{
  /** Parses a sequence of Statement members into a Statement. Statement members are either nonBracketTokens or parsed BracketBlocks.  */
  def apply(memsIn: Arr[StatementMember], optSemi: OptRef[SemicolonToken]): EMon[Statement] =
  {
    implicit val inp = memsIn
    val acc: Buff[StatementMember] = Buff()

    def loop(rem: ArrOff[StatementMember]): EMon[Statement] =
      rem.headFold(getExpr(acc.toArr).map(g => NonEmptyStatement(g, optSemi))){ (em, tail) =>
        acc.append(em)
        loop(tail)
      }


    loop(inp.offset0)
  }
}