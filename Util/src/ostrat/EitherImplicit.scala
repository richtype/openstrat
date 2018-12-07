/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Extension methods for Either. This class can possible be eliminated. */
class EitherImplicit[A, B](val thisEither : Either[A, B]) extends AnyVal
{
   def filterElse[B1 >: B](p: (B) ⇒ Boolean, or:  ⇒ B): B1 = thisEither match
   {
      case Right(r) if p(r) => r
      case _ => or
   }
      //      def map[C](f: B => C): Either[A, C] = thisEither match
//      {
//         case Left(l) => Left[A, C](l)
//         case Right(r) => Right[A, C](f(r))
//      }
//      def flatMap[C](f: B => Either[A, C]): Either[A, C] = thisEither match
//      {
//         case Left(l) => Left[A, C](l)
//         case Right(r) => (f(r))
//      }      
}