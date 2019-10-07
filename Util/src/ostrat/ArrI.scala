package ostrat
import annotation.unchecked.uncheckedVariance, reflect.ClassTag

class ArrBuild

trait ArrN[+A] extends Any
{
  def array: Array[A] @uncheckedVariance
  @inline def length: Int = array.length
  @inline def apply(index: Int): A = array(index)

  protected[this] def internalAdd[AA >: A, B <: AA](opArray: Array[B], newArray: Array[AA]): Unit =
  {
    val opLength = newArray.length
    var count = 0
    while (count < length)
    { newArray(count) = apply(count)
      count += 1
    }
    var count2 = 0
    while (count2 < opLength)
    { newArray(count) = opArray(count2)
      count += 1; count2 += 1
    }
  }
}

class ArrR[+A <: AnyRef](val array: Array[A] @uncheckedVariance) extends AnyVal with ArrN[A]
{
  def ++[AA >: A <: AnyRef](operand: ArrR[AA] @uncheckedVariance)(implicit ct: ClassTag[AA]): ArrR[AA] =
  {
    val newArray: Array[AA] = new Array[AA](length + operand.length)
    var count = 0
    while (count < length)
    { newArray(count) = apply(count)
      count += 1
    }
    var count2 = 0
    while (count2 < operand.length)
    { newArray(count) = operand(count2)
      count += 1; count2 += 1
    }
    new ArrR(newArray)
  }
}

object ArrR
{ def apply[A <: AnyRef](inp: A*)(implicit ct: ClassTag[A]): ArrR[A] = new ArrR[A](inp.toArray)
}

trait ArrValue[A] extends Any with ArrN[A]
{ def newArr(length: Int): ArrValue[A]

  def ++(operand: ArrValue[A] ): ArrValue[A] =
  {
    val res: ArrValue[A] = newArr(length + operand.length)
    val resArray: Array[A] = res.array
    var count = 0
    while (count < length)
    { resArray(count) = apply(count)
      count += 1
    }
    var count2 = 0
    while (count2 < operand.length)
    { resArray(count) = operand(count2)
      count += 1; count2 += 1
    }
    res
  }
}


class ArrI(val array: Array[Int]) extends AnyVal with ArrValue[Int]
{ override def newArr(length: Int): ArrI = new ArrI(new Array[Int](length))
}

object ArrI
{
  def apply(inp: Int*): ArrI = new ArrI(inp.toArray)
}

class ArrD(val array: Array[Double]) extends AnyVal with ArrValue[Double]
{ override def newArr(length: Int): ArrD = new ArrD(new Array[Double](length))
}

/*
/** Using Att as temporary name, can be switched to Arr later to replace type alias for ArraySeq. */
class Att[+A](val array: Array[A] @scala.annotation.unchecked.uncheckedVariance) extends AnyVal
{
  @inline def foreach[U](f: A => U): Unit = array.foreach(f)

  def map[B](f: A => B)(implicit ct: ClassTag[B]): Att[B] =
  { var count = 0
    val newArray: Array[B] = new Array[B](length)
    while (count < length) { newArray(count) = f(array(count)); count += 1}
    new Att(newArray)
  }

  //def flatMap[B](f: A => B)(implicit ct: ClassTag[B]): Att[B] =

  /* Maps from A to B like normal map,but has an additional accumulator of type C that is discarded once the traversal is completed */
  def mapWithAcc[B, C](initC: C)(f: (A, C) => (B, C))(implicit ct: ClassTag[B]): Arr[B] =
  { val accB: Buff[B] = Buff()
    var accC: C = initC
    foreach { a =>
      val (newB, newC) = f(a, accC)
      accB += newB
      accC = newC
    }
    accB.toArr
  }

  /** Replaces all instances of the old value with the new value */
 // def replace(oldValue: A, newValue: A): Att[A] = map { it => if (it == oldValue) newValue else it }

  def reverseForeach(f: A => Unit): Unit =
  { var count = length - 1
    while(count >= 0){ f(apply(count)); count -= 1}
  }

  /*def ifAppendArr[B >: A](b: Boolean, newElems: => Att[B]): Att[B] = ife(b, this ++ newElems, this)
  def optAppend[B >: A](optElem: Option[B]): Att[B] = optElem.fold[Arr[B]](this)(b => this :+ b)
  def optAppends[B >: A](optElems: Option[Att[B]]): Arr[B] = optElems.fold[Arr[B]](this)(bs => this ++ bs)*/
}
*/