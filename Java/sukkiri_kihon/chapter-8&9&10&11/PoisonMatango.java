public class PoisonMatango extends Matango {
  int numberOfPoisonAttack = 5;

  public PoisonMatango(char suffix) {
    super(suffix);
  }

  public void attack(Hero h) {
    super.attack(h);
    if (numberOfPoisonAttack == 0) return;

    System.out.println("さらに毒胞子をばら撒いた!");
    int damage = Math.max(1, h.hp / 5);
    h.hp -= damage;
    System.out.println(damage + "ポイントのダメージ！");
    this.numberOfPoisonAttack --;
  }
}