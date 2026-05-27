import java.io.IOException;

public class Main {
  // mainメソッドに例外を処理しないことを明示するため、throwsを使う
  public static void main(String[] args) throws IOException {
    /* 練習2 */
    // String s = null;
    // try {
    //   s.length();
    // } catch(NullPointerException e) {
    //   System.out.println("NullPointerException例外をcatchしました");
    //   System.out.println("== スタックトレース ここから ==");
    //   e.printStackTrace();
    //   System.out.println("== スタックトレース ここまで ==");
    // }

    /** 練習3 */
    // try {
    //   int i = Integer.parseInt("三");
    // } catch(NumberFormatException e) {
    //   System.out.println(e.getMessage());
    // }

    /** 練習4 */
    throw new IOException("お手上げ");
  }
}