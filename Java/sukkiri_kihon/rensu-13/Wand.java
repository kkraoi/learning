public class Wand {
  private String name;
  private double power;

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    if (name.length() < 3){
      throw new IllegalArgumentException("3文字以上の入力が必要");
    }
  
    this.name = name;
  }

  public double getPower() {
    return this.power;
  }
  public void setPower(double power) {
    if (power < 0.5 || power > 100.0) {
      throw new IllegalArgumentException("0.5以上100.0以下の入力が必要");
    }
    this.power = power;
  }
}