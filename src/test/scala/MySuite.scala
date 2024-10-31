// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  test("parse start array token") {
    assert(item("done") == List(('d', "one")))
    assert(parseDigit("3c") == List(('3', "c")))
    assert(parseDigit("c3") == List())
    assert(parseLower("c3") == List(('c', "3")))
    assert(parseUpper("C3") == List(('C', "3")))
    var parseTwoLower = bind(
      parseLower,
      (x: Char) => bind(parseLower, (y: Char) => result(x, y))
    )
    assert(parseTwoLower("cdE") == List((('c', 'd'), "E")))
    assert(or(parseLower, parseUpper)("Cd") == List(('C', "d")))
    assert(or(parseLower, parseUpper)("dC") == List(('d', "C")))
    assert(char("abcd") == List(('a', "bcd")))
    assert(word("abcd") == List(("abcd", "")))
  }
}
