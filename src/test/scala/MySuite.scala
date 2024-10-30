// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  test("parse start array token") {
    assert(item('d')("done") == List(('d', "one")))
  }
}
