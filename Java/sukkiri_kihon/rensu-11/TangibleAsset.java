public abstract class TangibleAsse extends Asset implements Thing{
  String color;
  double weight;

  public TangibleAsset (tring name, int price, String color, double weight){
    super(name, price);
    this.color = color;
    this.weight = weight;
  }
  
  public String getColor() { return this.color }

  public double getWeight() { return this.weight }
  
  double setWeight(double weight) {
    this. weight = weight;
    return this.weight;
  }
}