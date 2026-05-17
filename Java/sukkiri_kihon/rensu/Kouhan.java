package javaapp.koumon.comment;
import javaapp.koumon.comment.Zenhan;

public class Kouhan {
  public static void callDeae() {
    System.out.println("ええい、小癪な。曲者だ。出会えい");
  }

  public static void showMondokoro() throws Exception {
    System.out.println("飛車さん、角さん、もういいでしょう。");

    Thread.sleep(3000);

    System.out.println("このもんどころが目に入らぬか。");
    Zenhan.doTogame();
  }
}