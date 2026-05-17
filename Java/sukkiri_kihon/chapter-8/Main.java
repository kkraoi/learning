public class Main {
  public static void main(String[] args) {
    // 勇者、この世界に生まれろ
    Hero h = new Hero();
    h.name = "猫";
    h.hp = 100;
    System.out.println("勇者" + h.name + "を生み出しました");

    // マタンゴ、この世界に生まれろ
    Matango m1 = new Matango();
    m1.hp = 50;
    m1.suffix = "A";

    Matango m2 = new Matango();
    m2.hp = 48;
    m2.suffix = "B";

    // 冒険の始まり
    h.sit(5);
    h.slip();
    h.sit(25);
    h.run();

    // 勇者、戦え

    // マタンゴ、逃げろ
  }
}