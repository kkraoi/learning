public class Main {
  public static void main(String[] args) {
    // 武器の定義
    Sword s = new Sword();
    s.name = "炎の剣";
    s.damage = 10;

    // 勇者、この世界に生まれろ
    Hero h = new Hero();
    h.name = "猫";
    h.hp = 100;
    h.sword = s;
    System.out.println("勇者" + h.name + "を生み出しました");
    System.out.println("現在の武器は" + h.sword.name);

    // スーパーヒーロー
   

    // 武器
    Weapon w = new Weapon();

    // マタンゴ、この世界に生まれろ
    PoisonMatango mp = new PoisonMatango('A');
    mp.attack(h);

    // 冒険の始まり

    // 勇者、戦え

    // マタンゴ、逃げろ
  }
}