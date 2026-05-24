public class Answer {
  Y[] ary = new Y[2];
  ary[0] = new A();
  ary[1] = new B();

  for (Y y : ary) {
    y.b();
  }
}