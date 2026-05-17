import java.util.*;

public class Cleric {
  String name;
  int hp;
  final int MAX_HP = 50;
  int mp;
  final int MAX_MP = 10;

  public void selfAid() {
    this.mp -= 5;
    this.hp = this.MAX_HP;
  }

  public int pray(int sec) {
    int recover = new Random().nextInt(3) + sec;
    int recoverActual = Math.min(this.MAX_MP - this.mp, recover);
    this.mp += recoverActual;
    return recoverActual;
  }
}