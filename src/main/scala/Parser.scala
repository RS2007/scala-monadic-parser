type Parser[T] = (String) => ParserResult[T]
type ParserResult[T] = List[(T, String)]

def result[T](in: T): Parser[T] = (inStr: String) => List((in, inStr))
def zero[T](): Parser[T] = (inStr: String) => List()
val item: Parser[Char] = (inStr: String) =>
  inStr match {
    case u if u.length == 0 => List()
    case u                  => List((u(0), u.substring(1, u.length())))
  }

def bind[T, W](p: Parser[T], f: T => Parser[W]): Parser[W] = { input =>
  val results = p(input)
  results
    .flatMap { case (value, remaining) =>
      f(value)(remaining)
    }
}

val sat = (p: Char => Boolean) =>
  (inStr: String) =>
    bind(
      item,
      (x: Char) =>
        if p(x) then result(x)
        else zero()
    )(inStr)

val parseDigit: Parser[Char] = sat(((x: Char) => x.isDigit))
val parseLower: Parser[Char] = sat(((x: Char) => x.isLower))
val parseUpper: Parser[Char] = sat(((x: Char) => x.isUpper))

def plus[T](p: Parser[T], q: Parser[T]): Parser[T] = (inStr: String) =>
  p(inStr) ++ q(inStr)

def or[T](p: Parser[T], q: Parser[T]): Parser[T] = { inStr =>
  plus(p, q)(inStr) match {
    case Nil     => List()
    case x :: xs => List(x)
  }
}

val char = or(parseLower, parseUpper)

val word: Parser[String] = (inStr: String) =>
  val initChar = char(inStr)
  if initChar.length == 0 then List(("", inStr))
  else {
    val rest = word(initChar(0)(1))
    if rest.length == 0 then List((initChar(0)(0).toString(), initChar(0)(1)))
    else List((initChar(0)(0).toString().concat(rest(0)(0)), rest(0)(1)))
  }

//def sat(condition: char => bool) = (inStr: String)=>
