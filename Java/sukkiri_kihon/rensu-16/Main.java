public class Main {
  public static void main(String[] args) {
    /* 問1 
    1. Set
    2. List
    3. Mao
    */

    /* 問2 */
    ArrayList<Hero> heroes = new ArrayList<>();
    String[] nameArray = {"斉藤", "鈴木"};
    for (String name : nameArray) {
      Hero h = new Hero(name);
      heroes.add(h);
    }
    for (Hero h : heroes) {
      System.out.println(h.getName());
    }

    /* 問3 */
    Map<String, Integer> brokenEnemys = new HashMap<String, Integer>();
    brokenEnemys.put("斉藤", 3);
    brokenEnemys.put("鈴木", 7);

    for (String key : brokenEnemys.keySet()) {
      int value = brokenEnemys.get(key);
      Ststem.out.println(key + "が倒した敵 = " + value);
    }
  }
}