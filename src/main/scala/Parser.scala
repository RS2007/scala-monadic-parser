type Parser[T] = (String) => ParserResult[T]
type ParserResult[T] = List[(T, String)]

def result[T](in: T): String => ParserResult[T] = (inStr: String) =>
  List((in, inStr))
def zero[T](in: T): String => ParserResult[T] = (inStr: String) => List()
def item[char](in: char): String => ParserResult[char] = (inStr: String) =>
  inStr match {
    case u if inStr.startsWith(in.toString()) =>
      List((in, inStr.substring(1, inStr.length())))
    case _ => List[(char, String)]()
  }

  def bind[T, W](p: Parser[T], f: (T => Parser[W])): Parser[W] =
