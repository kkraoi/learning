# Javaのメモ書き
## 開発の流れ
1. ソースコード（.java）：高等言語
2. コンパイル（.class）：バイトコード
3. 実行：インタプリタがバイトコードの変換と実行を依頼。JVMがバイトコードを少しずつ読み、CPUが解釈できるマシンコードに翻訳し、CPUに処理を実行させる。

### コラム
javaはスパコンでも使える。最終的できるバイトコードが特定のCPUに依存しないため。

## コメント
```java
/*
複数行
*/

// １行
```

## 変数
### 整数型
- byte：1バイト：+- 128
- short：2バイト：+- 3.2万
- int：4バイト：+- 21億
- long：8バイト：+- 900京
※intの方が高速に処理できるコンピュータも多いのでintで基本はOK

### 不動小数点の型
- float < double がよく使われる。
- doubleの方が厳密な計算ができる。
- 金額などの重要な計算は小数型は使っていけない

### 文字の型
- char：１文字（全角・半角問わない）、シングルクオート
- String：ダブルクオート

### 定数の宣言
```java
final 型 定数名 = 初期値;
```

## 計算の文
### オペランドと演算子の違いがわかる？
- オペランド：変数や数値
- 演算子：+, - とか

### リテラル
- 0x → 16進数（int a = 0x11;）
- 0b → 2進数（int b = 0b0011;）
- 2_000_000 → 2,000,000

### エスケープシーケンス
- `¥"` → 「"」
- `¥'` → 「'」
- `¥¥` → 「¥」
- `¥n` → 改行
※LinuxやmacOSは「¥」が使えないかも。「\」を使うべきかも。

### テキストブロック
```java
"""
へー
　そうなんだ
"""
```
２つの`"""`とそれに挟まれた各行のうち、最も左に文字を記述した部分を、複数行リテラルの「左端」とみなす。

### 評価の3原則
1. 評価結果への置換
  - 演算子は周囲のオペランドの情報を使って計算を行い、それらオペランドを巻き込んで結果に化ける（置き換わる）。
  - 「1 + 5 - 3」の場合
    1. 1 + 5 が化けて 「6」
    2. 6 - 3 が化けて 「3」
2. 優先順位
  - 式に演算子が複数ある場合は、Javaで定められた優先順位の高い演算子から順に評価される。
  - 代入が最後
3. 結合規則
  - 式の中に同じ優先順位グループに属する演算子が複数ある場合、演算子ごとに決められた「方向」から順に評価される。
  - `+` は左から評価される
  - `=` は右から評価される。→ a = b = 10。代入演算子という。

### 演算子
- `/`：商 → 9 / 2 = 4 となるので注意。（9.0 / 2 = 4.5）
- `%`：余り → 9 % 2 = 1
- `+=`：複合代入演算子
- `a++`：単行演算子、aの値が1増える
  - `a=10, a++ + 50` => 計算してから1増やす => 60を返して、a=11となる
- `++a`：1増やしてから計算する
  - `a=10, ++a + 50` => 1増やしてから計算する => a=11となって61を返す

### 代入時の自動型変換
- 意味的に「小さな型」の値を「大きな型」の箱に代入する場合、代入する値を代入先の変数の肩に自動的に変換してから代入が行われる。
- double > float なので、 doubleにfloat型を代入できる。
- 大きな型を小さな型に代入することはできない。
- double > float > long > int > short > byte
```java
float f = 3; // 3 => 3.0F に自動変換
double d = f; // 3.0F => doubleの3.0 に自動変換
```

### キャスト演算子
小さい型に大きい型を無理やり代入する方法。
```Java
int age = (int)3.2; // 強制的にintにして、3となる
```

### 演算時の自動型変換
```java
double a = 5.0;
int b = 2;
/* 
a / b = 2.5 となる。
異なる型で演算を行った場合、「意味的に大きな型」に統一されて演算が行われる。
つまり、bもdoubleに自動的に変換されて演算される。。
*/
```

## メソッド
### 基礎編
- `System.out.print()`：改行せずに画面に文字を表示する
- `int m = Math.max(a, b)`：aとbの数値を比較し、大き方の数値を返す
- `int n = Integer.parseInt("31")`：文字列を整数値に変換する
- `int r = new java.util.Random().nextInt(乱数の上限値+1)`：乱数を発生させる
  - 0~2を出したい場合は3を引数にする
- `String s = new java.util.Scanner(System.in).nextLine()`：キーボードから1行の文字列の入力を受け付ける
- `int input = new java.util.Scanner(System.in).nextInt()`：キーボードから1つの整数の入力を受け付ける

## 文字列の比較
```java
if(str == "スッキリ") {...} // これは比較できない
if(str.equal("スッキリ")){...} // こうしなければいけない
```

## 短絡評価
```java
if(aga >= 18 && month == 5) {...}
```
この場合、aga >= 18 が false の場合、以降の month == 5　は無視して評価しなくなる。

## [テク]
```java
// x は 10 より大きく、20 より小さい の表現は
boolean bool = 10 < x && x < 20
// boolean bool = 10 < x < 20 とは書かない
```

## break文
```java
for (int i = 1; i < 10; i++) {
  if (i == 3) break;
  System.out.println(i);
}
```
この場合、3回目でループが止まる。

## continue文
```java
for (int i = 1; i < 10; i++) {
  if (i == 3) continue;
  System.out.println(i);
}
```
この場合、3回目はスキップする。

## switch構文
```java
String s = switch (条件)  {
  case 結果 -> 戻り値;
  case 結果 -> 戻り値;
  default -> 戻り値;
};
```

## 配列
配列変数と配列要素はメモリの観点からだと下記となる。
配列変数は最初の配列要素のアドレスを保持する。

### 宣言
```java
// 要素の型[] 配列変数名
int[] scores;

// 5の大きさの配列を作成
scores = new int[5]

// まとめて書ける
int[] scores = new int[5];

// 配列の大きさ
scores.length;

// ちなみにStringの大きさはメソッド
String text = "ほげほげ"
text.length();
```

### 初期化
初期化されず、参照してもエラーは起きない
```java
int[] test1 = new int[5];
System.out.print(test1[0]); // 0 が表示される
boolean[] test2 = new boolean[3];
System.out.print(test2[0]); // false が表示される
String[] test3 = new String[3];
System.out.print(test3[0]); // null が表示される
```

### 代入
```java
int[] scores = new int[] {1, 2, 3, 4};
int[] scores1 = {1, 2, 3};
```

### 配列とfor
```java
int[] scores = {1, 2, 3};
for(int i = 0; i < scores.length; i++) {
  System.out.print(i);
}
```

### テク： 塩基配列をランダムで出力
switch文を使わない
```java
int[] seq = new int[10];
char[] base = {'A', 'T', 'G', 'C'};

for (int i = 0; i < seq.length; i++) {
  seq[i] = new java.util.Random().nextInt(4);
}

for (int i = 0; i < seq.length; i++) {
  System.out.print(base[seq[i]] + "");
}
```

### 拡張for文
```java
int[] scores = {20, 30, 40, 50};

for (int value : scores) {
  System.out.print(value);
}
```

### 多次元配列
```java
// 2列3行の配列を作る
int[][] scores = new int[2][3];
int[][] scores = {{1, 2, 3}, {1, 3, 5}};
```

## 参照変数とプリミティブ変数
- 参照変数：他のメモリ領域のアドレスを保存する。配列変数、インスタンス変数（クラス型）、String
  - String型は、実はクラス型。
    - java.lang.Stringクラス
    - `java.lang.String s;`の宣言を本来する必要があるが、標準クラスのため省略している。
    - new の代わりに `""` でインスタンスを作っている
- プリミティブ変数：値そのものを保存する。int, boolean

## new演算子
- メモリ（ヒープ領域）の容量を確保する
- Javaプログラム実行時は、数百MB~数GB
- new を用いてインスタンスを生み出すと、ヒープの一部の領域（数十~数百バイト）が確保され、インスタンスの情報が格納される


## 変数の寿命
宣言されたブロック内が生きる範囲。
ブロックから出るとメモリから消滅する。

## ガベージコレクション
- ブロックから出ても残る変数がある（newで確保された配列要素など）。
- それはどの変数からも参照されなくなり、メモリ上に残り続ける。
- javaには常に、実行中のプログラムが生み出したゴミ（どの変数からも参照されなくなったメモリ領域）を自動的に探し出して排除する機能がある。

## null
- 参照変数のアドレスを000...にする。
- 参照を切る
- 参照を切ると、参照先はどこからも参照されなくなるので、ガベージコレクションに処理される
- プリミティブ変数はnullにできない。

## メソッドの定義方法
```java
public static void メソッド名() {
  メソッドが呼び出された時の処理
}
```

## シンボル
唯一無二である"単体"の識別票。
単体で唯一無二となるもの。

## シグネチャ
唯一無二の"組み合わせ"の識別票。
組み合わせた結果、唯一無二となるもの。
```java
public static int add(int x, inty) {}
```
の`add(int x, inty)`の部分のこと

## オーバーロード
1つのメソッドに複数パターンの戻り値を用意しておける。
シグネチャが重複しない場合のみ許される。
引数は同じで、戻り値の方だけが異なるものは定義できない。

### パターン1
仮引数が異なれば同じ名前のメソッドを複数定義することが許される。
```java
  public static int add(int x, int y) {
      return x + y;
  }
  
  public static double add(double x, double y) {
      return x + y;
  }
  
  public static String add(String x, String y) {
      return x + y;
  }
```

### パターン2
もしくは、引数の個数が異なれば同じメソッドを複数定義することが許される。
```java
  public static int add(int x, int y) {
      return x + y;
  }

  public static int add(int x, int y, int z) {
      return x + y + z;
  }
```

## オーバーライド

## 値渡しと参照渡し
`int x = 100;`のような基本型の変数を引数に渡すと、その値はコピーして扱われ、別の存在となるため、仮引数xにどんな操作をしようとも、元の引数のxにはなんの影響も及ぼさない。

しかし、下記のようにメソッドに配列型変数（参照型）を渡すときは、配列の実態を示すメモリのアドレスを渡しているため、
仮引数arrayに何らかの操作を加えると、元のarrayにも影響が及ぶ。
```java
public class Main {
  public static void printArray(int[] array) {
      for (int element : array) {
          array[1] = 100;
          System.out.println(element);
      }
  }
  i
  public static void main(String[] args) {
      int[] array = {1, 2, 3};
      System.out.println(array[1]);
      printArray(array);
      System.out.println(array[1]);
  }
}
```

## mainメソッド
```java
public static void main(String[] arg) {} 
```

javaのプログラムを起動するときに、追加情報「コマンドライン引数」を指定して起動することができる。
Java Development Kit（JDK）でプログラムを実行する場合、下記のコマンド。
```sh
java プログラム名 引数リスト
# java Main 引数A 引数B
```

## コマンドライン引数
プログラム起動時に指定したコマンドライン引数がJVM（JavaVirtualMachine）によって配列変換され、mainメソッド起動時に渡される。

## JARファイル
プログラム完成品が複数になった場合、複数のクラスファイルを一つにまとめるファイル形式のことをJARファイルという。ZIPファイルみたいなもの。
```sh
jar 
```

## パッケージ
- 各クラスをグループに所属させ、分類・管理できる仕組み。
- パッケージの中にパッケージを入れることはできない。
- 親子関係や階層関係はない。

```java
package 所属させたいパッケージ名;
package calcapp.logics;
package calcapp.main;
```
実は
- calcapp > logics
- calcapp > main
という関係はない。
`calcapp.logics`・`calcapp.main`という名のパッケージ

### デフォルトパッケージ
- どのパッケージにも所属していない状態を「デフォルトパッケージ（無名パッケージ）」と表現する。
- デフォルトパッケージに属するクラスはimportすることはできない。

### パッケージを含まれているクラスを指定する
完全限定クラス名（完全就職クラス名, FQCN）
```java
パッケージ名.クラス名;
calcapp.logics.CalcLogic.tasu(a, b);
```

### import文
```java
import パッケージ名.クラス名;
import パッケージ名.*; // パッケージの全部のクラスをインポート
```

### クラスローダー
- FQCNを指定されたら、その名を持つクラスのクラスファイルをPCないから検索し、JVMに読み込んで利用する。
- クラスファイルのパスを指定しないのがすごい
  - -> クラスパスというヒント情報を使って高速に目的のファイルを探す。

### クラスパスの指定方法
1. 起動時にjavaコマンドで指定する
  - `-cp`または`-classpath`オプションを指定する
  - `java -cp c:¥work Calc`
  - `java -cp ./work Calc`
2. 検索場所をOSに登録しておく
  - 環境変数にクラスパスを設定する
  - javaコマンドは、この環境変数を自動的に読み込んでクラスファイルの検索に利用する
3. 特に指定しない
  - 環境変数もなく、特に指定しない場合、
  - javaコマンドが実行されたフォルダがクラスパスとなる。

### クラスパスで指定できる対象
1. フォルダの場所
2. JARファイルやクラスが入っているZIPファイル
3. フォルダとJAR（ZIP）の組み合わせ
  - `./work/jars/calcapp.jar`
  - `c:¥work¥jars¥calcapp.jar`

### パッケージ利用でのプログラム実行
1. パッケージに則ったディレクトリ構造を作る
  - `mkdir -p calcapp/main calcapp/logics` などで一括で作るとよい
  - `-p` はparent 
2. コンパイルし、classファイルを1に適宜格納する
3. `java FQCN`を実行

### プログラム動作までの流れ
1. JVMは起動させるクラス名（以降の例：calcapp.main.Calc）を受け取る
2. JVMはクラスローダーに`calcapp.main.Calc`の読み込みを指示する
3. クラスローダーはクラスパスを確認する
4. クラスローダーはクラスパスを基準として、calcapp > main　とフォルダを降りていき、Calc.classを見つける
5. クラスローダーは発見したCalc.classを読み込む
6. JVMは読み込んだCalcクラスのmainメソッドを実行する

### パッケージ名の衝突予防
- A社のパッケージ名とB社のパッケージ名が被る、というようなケースを考える
- 自社が保有するインターネットドメインを前後逆順にしたものから始まるパッケージ名の推奨をjavaはしている。
- 例：foo.example.com のインターネットドメインの場合、`com.example.foo`としてパッケージ名を使う。

## API
組み込みのクラスやメソッド
- java.lang：プログラミングに欠かせない重要なクラス群
  - import文を使わなくても自動importされるもの
  - System, Integer, Math, Object, String, Runtimeなど
- java.util：プログラミングを便利にするクラス群
- java.math：数学に関するクラス群
- java.net：ネットワーク通信などを行うためのクラス群
- java.io：ファイルの読み書きなどデータの逐次処理に関するクラス群

### APIの調べ方
- `Java API 仕様` で検索
- https://docs.oracle.com/javase/jp/21/docs/api/index.html

## オブジェクト指向_クラスの書き方
### TIPS
- オブジェクト指向に基づいて作るメソッドには、基本的にstaticをつけない

### フィールド
属性のこと
```java
public class Matango {
  int hp;
  // int level = 10;
  final int LEVEL = 10; // 定数フィールド：書き換え禁止
}
```

### this
自分自身のインスタンスを表す
```java
public void sleep() {
  this.hp = 100;
  System.out.println(this.name + "は、眠って回復した");
}
```
※省略しても機能するが、ローカル変数や引数に同名があると、そちらが優先されるので、基本的に省略しない

### クラスとメンバの命名ルール
- クラス名：名詞、先頭大文字
- フィールド名：名詞、キャメルケース
- メソッド名：同士、キャメルケース

### インスタンス生成
```java
クラス名 変数名 = new クラス名();
Hero h = new Hero();
```

### 参照の解決（アドレス解決）
参照変数から番地情報を取り出し、次にその番地にアクセスするというJVMの動作のこと。
```java
Hero h;
h = new Hero();
h.hp = 100; // アドレス解決
```

## コンストラクタ
- newされた直後自動的に実行される
- コンストラクはJVMが呼び出すものであって、開発者がプログラムで呼び出すものではない。
- コンストラクタとみなされる条件
  - メソッド名がクラス名と完全に等しい
  - メソッド宣言に戻り値の方が記述されていない（voidもダメ）
- オーバーロードで別パターンのコンストラクタを作れる

```java
public class クラス名 {
  ...コード

  public クラス名(引数) {
    初期化
  }

  // オーバーロード
  public クラス名(引数, 引数) {
    初期化
  }
}

クラス名 変数 = new クラス名(引数);
```

### デフォルトコンストラクタ
- クラスに1ツモコンストラクタが定義されていない場合に限って、「引数なし、処理内容なし」のコンスタラクタがコンパイル時に自動的に追加される。この自動生成されたコンストラクタのことを言う。

### 他のコンストラクタを呼び出す
```java
public class Hero {
  ...コード
  
  public Hero(String name) {
    this.hp = 100;
    this.name = name;
  }

  public Hero() {
    this.Hero("ダミー");
  }
}
```
これはダメ。コンストラクタは、開発者は呼び出せない。JVMのみが呼び出せる。
しかし、JVMにコンストラクタの起動を依頼することができる。
↓
```java
public class Hero {
  ...コード
  
  public Hero(String name) { // コンストラクタ1
    this.hp = 100;
    this.name = name;
  }

  public Hero() { // コンストラクタ2
    this("ダミー"); // コンストラクタ1を呼び出すようJVMに依頼
  }
}
```
- コンストラクタの先頭に記述する
- 同じクラスのコンストラクタが対象となる

## 継承
```java
public class クラス名 extends 元となるクラス {
  public クラス名() { // 親のコンストラクタ呼び出し
    super(引数);
  }
  親クラスとの差分となるメンバ
}
```
※多重継承(親クラスが複数の継承)はJavaは許されていない

### オーバーライド
子クラスで親クラスと同名のメンバを記述すること

### 継承を禁止する「final」
- java.lang系のクラスは継承ができない
  - Stringクラス
  - `public final class String extends Object...`のように"final"で宣言されている。
  - このようにクラスにfinal宣言をすると継承ができなくなる
- メソッドにfinal宣言することができる
  - `public final void slip() {`
  - このようにメソッドにfinal宣言をすると、オーバーライドができなくなる
- フィールドにfinal宣言をすることができるが、実用上用いられることがない

### 親クラスのメンバを子クラスで使う
```java
super.フィールド名;
super.メソッド名(引数);
super(引数); // コンストラクタの呼び出し
```
- `super` は今より1つ内側のインスタンスの部分を表す
- 親インスタンスのメソッドやフィールドに、子インスタンスからアクセスできる
  - 親の親インスタンスへのアクセスは不可能！
- コンストラクタは継承されない。コンスラクタ自体はクラス固有の存在である。

### 継承されたクラスがインスタンス化された場合
1. 親インスタンス部が作られる
2. 1の外部に子インスタンス部が盛られる
3. JVMにより自動的にコンストラクタが呼ばれる
  - Javaは、全てのコンストラクは、その先頭（先頭行）で必ず内部インスタンス（親クラス）のコンストラクタを呼び出さなければならない。
  - コンストラクタのsuperは継承クラスで記述がなかった場合、自動的に挿入される。
  a. 親のコンストラクタが呼ばれる
  b. 子のコンストラクタが呼ばれる

### is-aの原則
- 子クラス is a 親クラス
- 子クラス > 親クラス
- 子クラスは親クラスの1種である
- 継承
- 親子の関係
- 種類の関係

### has-aの原則
- クラスA has a クラスB
- カプセル化、コンポジション
- 所有・パーツの関係

### 凡化・特化の関係_継承ツリー
is-a関係の時、
- 子クラスになるほど「特殊で具体的なもの」に特化していく
- 親クラスになるほど「抽象的で曖昧なもの」に凡化していく

## 抽象クラス
- 通常のクラスを規定クラスとして設計する場合、下記の不安要素がある
  - オーバーライドが前提であると認識されない
  - 空のメソッドが見られた時「現時点で処理内容を確定できないメソッド」なのか、「何もしないメソッド」なのか、区別できない。
  - 未完成部分を含む継承専用のクラスを誤ってnewされる可能性がある。
- 「現時点では何をするかを確定できないメソッド」として明示できる記法が`abstract`
- 抽象メソッド（クラス）を扱うとオーバーライドを強制させられる
- 抽象メソッドを含むクラスは、クラス宣言にabstractを付与する必要がある
- 抽象クラスは、newによるインスタンス化が禁止される
- 未定だったメソッド（抽象メソッド）の内容が確定することを「実装（implements）」という

```java
/* 抽象メソッド（詳細み設定メソッド）の宣言 */
public abstract 戻り値の型 メソッド名(引数リスト);

/* 抽象メソッドを含むクラスの宣言 */
public abstract class クラス名 {}

/* 使用例 */
public abstract class Character () {
  フィールド

  public abstract void attack(Matango m);
}
```

## インターフェース
- 抽象クラスの中の抽象クラス
- 実装を持たずに定義のみを持つ
- インターフェースとして特別扱いできる条件
  1. 全てのメソッドが抽象
  2. 基本的にフォールドを一つも持たない
    - ただし、`public static final` がついたフィールド（定数）の宣言はOK
    - `public static final double PI = 3.141592;`
    - `double PI = 3.14` のように `public static final` を省略することができる
- インターフェースと名がついていようとも、所詮は抽象クラス
  - 子クラスは抽象メソッドに対してオーバーライドしなくてはいけない
- インターフェースで定義されたメソッドはいずれも使用（オーバーライド）されなくてはならない
- インターフェースの恵み
  - 同じインターフェースをimplementする複数の子クラスたちに、共通のメソッド群を実装するよう強制できる
  - あるクラスがインターフェースを実装していれば、少なくともそのインターフェースが定めたメソどは持っていると保証される
- 「内部実装を一切定義しない」という特性によって、特別に多重継承（2つ以上の親クラスが存在する）が許される。
- 異なる実装が衝突する問題が発生しないため、複数の親インターフェースによる多重継承が認められている。


```java
public interface Creature {
  public abstract void run();
  void run1(); // public abstract を省略できる
  void run1(String spot); // 引数を設定しないと具象クラスで引数が使えない
}
```

- インターフェースをを継承する場合は`extends`ではなく、`implements`を使う
- implements は「実装する」という意味。
- 親インターフェースで未定だった各メソッドの内容をオーバーライドして実装し確定させることが由来する。

```java
public class クラス名 implements インタフェース名 {

}

// 多重継承版
public class クラス名 implements インターフェース1, インターフェース2, ... {

}
```

### インタフェースの継承
- この場合、「extends」であることに注意！
- implements だと、メソッドを具体的に定義する（実装する）必要がある

```java
public interface Human extends Creature {
  // 下記はHumean独自のメソッド
  void talk();
  void watch();
  void hear();

  // さらに自動的にCreatureのメソッドを継承する
}
```

### extends と implements のミックス
- 親クラスは1つだけ（多重継承禁止）
- 

```java
public class クラス名 extends 親抽象クラス implements 親インターフェース1, 親インターフェース2, ... {

}

public class Fool extends Character implements Human {
  // Character から hp や name などのフィールドを継承している

  // Character から継承した抽象メソッドattack()を実装
  public void attack(Matango m) {
    System.out.println(this.name + "は戦わず遊んでいる");
  }

  // さらにHumankら継承した4つの中背用メソッドを実装
  public void talk() {...}
  public void watch() {...}
  public void hear() {...}
  public void run() {...}
}
```

### デフォルト実装
- インターフェースは原則的に処理内容を持たない抽象メソッドに限られるが、デフォルトメソッドは許される
- 継承先でオーバーライドされなかった場合、自動的にデフォルト実装の内容でオーバーライドする。
- 多重継承（ダイアモンドプログラム）が起こる可能性があり扱いに注意が必要

```java
default 戻り値の型 メソッド名() {
  処理のデフォルト実装
}
```

## 多態性
- あるものを「あえてざっくり捉える」ことで、さまざまなメリットを享受できる機能
- 日常の多態性
  - レンタカーを借りて運転する。厳密に言えば初めて乗る車であるのにも関わらず、何の問題なく運転できる。「細かいところは違うけど、ざっくり言えば、どの車も同じ」高級車だろうとライトバンだろうと新車だろうと問題なく運転できる。
  - もしロボットであれば厳密な運転制御プログラムが必要。「もし2021年式のプリウスならば、ハンドルはシートからXcmの高さにあり...」
  - 多態性のある考え => どの車も同じもの
  - 多態性のない考え => どれも違う車
- 本当はSuperHeroインスタンスだが、Characterとして捉えて利用する
- あるインスタンスの捉え方は、代入する変数の型で決まる。
- is-aの関係であるもの = 継承されているものは、変数の型とインスタンスの型が違っていても代入できる（多態性）
- 抽象クラスやインターフェースはインスタンスを生み出せないが、それらの型を利用することはできる
- 全く同一である一つの存在に対して、複数の異なる捉え方ができる、捉え方によって利用方法が変わる
- 呼び出しは同じなのに、結果はそれに適したものが発揮される、ということが可能

```java
// SuperHero の親クラスに Hero
// Hero の親クラスに CHaracter
// があるとする

Character c = new SuperHero();
```

### 型のメソッドしか呼び出せない
```java
public abstract class Character {
  String name;
  int hp;
  public void run() {...}:
  public abstract void attack(Monster m);
}

public class Wizeard extends Character {
  int mp;
  public void attack(Monster m) {...}
  public void fireball(Monster m) {...}
}

public class Main {
  public static void main(String[] args) {
    Wizerd w = Wizard();
    Character c = w;
    Matango m = new Matango();
    ...
    c.attack(m);
    c.attack(m); // コンパイルエラーが発生
    /* ↑ 呼び出す側がWizerdだと思っていないため */
  }
}
```

### メソッドはどちらが実行される？
```java
public abstract class Monster {
  public void run() {
    System.out.println("A");
  }
}

public class Slime extends Monster {
  public void run() {
    System.out.println("B");
  }
}

public class Main {
  public static void main(String[] args) {
    Slime s = new Slime();
    Monster m = new Slime();
    s.run(); // B が表示
    m.run(); // B が表示！
  }
}
```

### 捉え方を変更する （ダウンキャスト）
- キャスト演算子を使う
- 曖昧な方に入っている中身を厳密な方に代入するキャストはダウンキャストと呼ばれ、失敗の危険が伴う
  - クラスを間違っていたら、ClassCastExceptionエラーが発生する

```java
Character c = new Wizard();
// c.fireball(); は実行できないが、
Wizard w = (Wizard) c; // このキャスト演算子で、Wizerd と再定義させられ、
w.fiball(); // が実行できる
```

### ダウンキャストによるエラーを予防
```java
// 変数を型名の箱に代入可能なら、trueが返る
// キャスト後格納変数名 を省略すると、キャスト可能かの判定のみを行う
変数 instanceof 型名 キャスト後格納変数名

// c の中身がSuperHeroとみなしてOKであれば、キャストしてhに代入する
if (c instanceof SuperHero h) { 
  h.fly();
}

// また、上記と同等の方法
if (c instanceof SuperHero) {
  SuperHero h = (SuperHero)c;
  h.fly();
}
```

### 多態性のメリット1
- 下記のケースを考える
  - 5人のキャラクター（Hero x2, Thief x1, Wizard x2）
  - 彼らが宿屋に泊まり、HP50ずつ回復するプログラムを書く場合
- そうすると
  - コードに重複が目立つ
  - 将来的に多くの修正が必要
- このようなケースを解決することができる

```java
/* 前提
- Hero・Thief・Wizardは、抽象クラスCharacterを継承している
- Characterは、name・hpフィールドと、attack()・run()メソッドを持つ
*/
public class Main {
  public static void main(String[] args) {
    Hero h1 = new Hero();
    Hero h2 = new Hero();
    Thief t1 = new Thief();
    Wizard w1 = new Wizard();
    Wizard w2 = new Wizard();

    // 宿屋に泊まる
    h1.hp += 50;
    h2.hp += 50;
    t1.hp += 50;
    w1.hp += 50;
    w2.hp += 50;
  }
}

// 多態性と配列を使うと...↓

public class Main {
  public static void main(String[] args) {
    Character[] c = new Character[5];
    c[0] = new Hero();
    c[1] = new Hero();
    c[2] = new Thief();
    c[3] = new Wizerd();
    c[4] = new Wizerd();

    // 宿に泊まる
    for (Character ch : c) { // 5人をCharacterだとみなせば回せる
      ch.hp += 50;
    }
  }
}
```

## カプセル化
- フィールドへの読み書きやメソッドの呼び出しを制限する
- このメソッドはクラスAからは呼び出せるが、クラスBでは呼び出せない
- アクセス制御を行う
- ４つのレベル（private, protected, public はアクセス修飾子という）
  1. private：
    - 自分自身のクラスのみアクセスを許可
    - this を用いて読み書きができる
  2. package private：
    - 何も書かない指定方法。
    - 自分と同じパッケージに属するクラスのみ許可
  3. protected：
    - 自分と同じパッケージに属するか、継承した子クラスのみアクセス
  4. public：
    - 全てのクラスからアクセスできる
- アクセス修飾子の定石
  - フィールドは全てprivate
  - メソッドは全てpublic
  - クラスは基本的にpublic
- 基本的にフィールドはメソッド経由でアクセスするもの
- getterとsetterを総称して「アクセサ」
  - readOnly・writeOnly を表現できる
  - フィールド名を変更したくなった場合、そのクラスだけで作業を完結できる
  - setterにif文等を使うことによって、値の指定・制限をかけることができる
- 同一クラス内のフィールドを設定する場合も、setterを使う。setterによる値チェックが活用できるため。
- 同一クラス内のフィールドの取り出しは、直接フィールドの値を使うことが一般的だが、将来的な柔軟性を確保する目的でgetterを経由するのもあり。

### getter
- フィールドの値を取り出すだけのメソッド、getter
- `インスタンス変数.フィールド`でフィールドの値を取り出さないかも

```java
public フィールドの型 getフィールド名() {
  return this.フィールド名;
}

public class Hero {
  private String name;
  ...
  public String getName() {
    return this.name;
  }
}
```

### setter
- フィールドに指定された値を代入するメソッド、setter

```java
public void setフィールド名(フィールドの型 任意の変数名) {
  this.フィールド名 = 任意の変数名;
}

public class Hero {
  ...
  public void setName(String name) {
    this.name = name;
  }
}

// 妥当性のチェック
public void setName(String name) {
  if(name == null) {
    throw new IllegalArgumentException("エラーメッセージ");
  }
  this.name = name;
}
```

### クラスのアクセス制御
- package private
  - 何も書かない指定方法
  - 自分と同じパッケージに属するクラスのみアクセスできる
  - クラスの名前はソースファイルと異なってもよい
  - 1つのソースファイルに複数のクラスを宣言してもよい
- public
  - 全てのクラスからアクセスできる
  - 1つのファイルに1つのpublicクラス
  - ファイル名 = publicクラス名

### カプセル化を支える考え方
- メソッドでフィールドを保護する
- 外部から直接触られないように、メソッドという殻（カプセル）によってフィールドが保護されることをカプセル化
- 不具合の多くは、フィールドに予期しない値が入ってしまうことによる。したがってフィールドを保護するのは大事

### 継承関係によるアクセス制御
- protectedアクセス修飾子がついたメンバは自分のクラスの子孫または、同じパッケージからのみアクセスが許される。

## 暗黙の継承
- toString()など、開発者が定義していなくても、自作クラスで使える
- メソッドもフィールドも一切定義していないクラスの特定のメソッドを呼び出せるのは、暗黙の継承という仕組みがjavaに備わっているから
- あるクラスを定義するとき、extendsで親クラスを指定しなければ、`java.lang.Object`を親クラスとして継承したとみなされる
- java.lang.Objectが全てのクラスの祖
  - 多態性を利用できるようになるから
    - これによって、どんなインスタンスでもObject型の変数で代入できる
    - どんなインスタンスでもObject型で引数を渡せる
    - 参照型のインスタンスはなんでもObject型の変数に代入できる
    - 基本データ型（int, long）は代入できない
  - 全クラスが最低限備えるべきメソッドを定義できるから
    - インスタンス同士の内容が同じかチェック
    - インスタンスのな用を文字情報として表示する
    - これらはクラスの種別に問わず使えた方が便利

```java
public class Empty {}

public static void main(String[] args) {
  Empty e = new Empty();
  String s = e.toString();
  System.out.println(s);
}
```

### インスタンスの内容を表示したい
```java
public class Hero {
  String name;
  int hp;
  ...
}

public class Main {
  public static void main(String[] args) {
    Hero h = new Hero();
    h.name = "猫";
    h.hp = 100;

    // この表示結果は Hero@3485a5cc みたいなアドレスになる。
    System.out.println(h.toString());
  }
}
```

↓ オーバーライドをするのが一般的

```java
public class Hero {
  String name;
  int hp;
  ...

  public String toString() {
    return "名前：" + this.name + ", HP" + this.hp; 
  }
}

public class Main {
  public static void main(String[] args) {
    Hero h = new Hero();
    h.name = "猫";
    h.hp = 100;

    System.out.println(h.toString());

    // ちなみに↓でも↑と同等の結果となる。printlnはtoString()を内部的に実行するから。
    System.out.println(h);
  }
}
```

### 等値と等価の違い
- `==` と `equals` の違い
  - 等値「==」
    - 同一の存在であること
    - 同じアドレスを指している
  - 等価「equals」
    - 同じ内容であること
    - 同じアドレスを指していなくてもよい
- String型は参照型なので`==`で評価せず、`equals()`を使う
- 等価は機械の判定はできない、何を持って意味的に同じものとみなすかの判断基準はクラスによってことなり、一律に決められない。
  - クラスごとにequal()をオーバーライドする必要がある。

名前が同じなら同じと判定するオーバーライド
```java
public class Hero {
  String name;
  int hp;
  ...

  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o instanceof Hero h) {
      if (this.name.equals(h.name)) {return true; }
    }
    return false;
  }
}
```

## 静的メンバ
- `static`キーワードが付けられたフィールドやメソッドのこと
- 特別な事情がない限りstaticは付けない
- インスタンスの独立性と関わりがある
  - newによって生成された個々のインスタンスは基本的には独立した存在であるというオブジェクト指向の基本原則
- 独立したインスタンス同士でも、共通のメンバを使いたい場合にstaticをつける
- 使うケース
  1. newせずに手軽に呼び出す
    - `Intger.parseInt()`は静的メソッドとして準備されているからインスタンスを作らなくてよい
  2. 静的メソッドを使ってインスタンスを生成するため
    - 外部からインスタンス化を禁止しているクラスが存在する。
    - このようなクラスはインスタンス生成を担う静的メソッドを準備しており、開発者はこれを使う

### 静的フィールド
- 静的フィールドの3つの効果
  1. フィールド変数の実態がクラスに準備される
    - `クラス名.静的フィールド` と記述する
    - 個々のインスタンスではんく、クラスに対してフィールドが用意されるためである
  2. 全インスタンスにフィールドのアドレスが参照される
    - `インスタンス変数名.静的フィールド`でも静的フィールドの値を得られる
  3. インスタンスを生み出さなくても共有が可能になる
- 静的フィールドはクラス（金型）にフィールドが所属することから、「クラス変数」と呼ばれる

一般的に多く用いられないが、定数と一緒に使われるケースがある
```java
public static final double Rate = 1.413;
```

### 静的メソッド
- mainメソッドに使われている
- 静的メソッドの3つの効果
  1. メソッド自体がクラスに属するようになる
    - その実態がインスタンスではなくクラスに属するため、クラス名を使って呼び出せる
    - `クラス名.メソッド名();`
  2. インスタンスにメソッドの分身が準備される
    - インズタンスにもそのメソッドのアドレスが参照されるため、インスタンス変数からも呼び出せる
    - `インスタンス変数名.メソッド名();`
  3. インスタンスを生み出さなくても呼び出せる
    - mainメソッドがstaticの理由がこれ
- 静的メソッドの制約
  - static がついていないフィールドやメソッドは利用できない
    - static無しはインスタンスができて値が設定されてようやく使えるからである。
    - 値が無いのに用いることはできない。

### static インポート文
```java
import static パッケージ名.クラス名.静的メンバ名;

// そのクラスに属する全ての静的メンバが対象となる
import static パッケージ名.クラス名.*;
```

- java.lang.System クラスは静的フィールドとして、outを持っている
- `import static java.lang.System.out;`と宣言すると、`out.println(...);`と扱うことができる

## 文字列を操作する

### 文字列を調査する
- 内容が等しいか調べる
  - `public boolean equals(Object o)`
- 大文字/小文字を区別せず内容が等しいか調べる
  - `public boolean equalsIgnoeCase(String s)`
- 文字列長を調べる
  - `public int length()`
- 空文字（長さが0）か調べる
  - `public boolean isEmpty()`

### 文字列を検索する
- 一部に文字列 s を含むか調べる
  - `public boolean contains(String s)`
- 文字列 s で始まるか調べる
  - `public boolaen startsWith(String s)`
- 文字列 s で終わるか調べる
  - `public boolean endsWith(String s)`
- 文字ch（文字列s）が最初に登場する位置を調べる
  - `public int indexOf(int ch)`
  - `public int indexOf(String str)`
  - 文字列の先頭位置を0として、部分文字が道かった場合の位置を返す
  - 見つからなかったら-1を返す
- 文字ch（文字列s）を後ろから調べて最初に登場する位置を調べる
  - `public int lastIndexOf(int ch)`
  - `public int lastIndexOf(String str)`
  - 文字列の先頭位置を0として、部分文字が道かった場合の位置を返す
  - 見つからなかったら-1を返す

### 文字列を切り出す
- 指定位置の1文字を切り出す
  - `public char charAt(int index)`
- 指定位置から始まる文字列を任意の長さだけ取り出す
  - `public String substring(int index)`
  - `public String substring(int index, int endIndex)`

実装例：メールアドレスからドメインだけを抽出する
```java
public class TextExtractor {
    public static void main(String[] args) {
        String email = "suzuki.taro@example.com";
        
        // 1. 区切り文字「@」が何番目にあるかを探す
        int atIndex = email.indexOf("@");
        
        // 「@」が見つかった場合だけ処理（-1でなければ存在する）
        if (atIndex != -1) {
            // 2. 「@」の次の位置から、最後までを切り出す
            String domain = email.substring(atIndex + 1);
            
            System.out.println("ドメイン名: " + domain); // 出力: example.com
        }
    }
}
```

### 文字列を変換する
- 大文字を小文字に変換する
  - `public String toLowerCase()`
- 小文字を大文字に変換する
 - `public String toUpperCase()`
- 前後の空白を除去する
  - `public String trim()`
  - `¥t`などのタブ文字も除去できる
  - 全角スペースは除去できない
- 文字列を置き換える
  - `public String replace(String before, String after)`

実装例：大文字小文字を無視してキーワードが含まれるか検索する
```java
public class SearchEngine {
    public static void main(String[] args) {
        // データベースにある商品名（大文字小文字が混ざっている）
        String productName = "Apple iPhone 15 Pro";
        
        // ユーザーが検索窓に入力した文字（小文字で入力されたと想定）
        String searchWord = "iphone";
        
        // プロのテクニック：両方を「一度小文字に変換してから」含まれるかチェックする
        String lowerProductName = productName.toLowerCase();
        String lowerSearchWord = searchWord.toLowerCase();
        
        if (lowerProductName.contains(lowerSearchWord)) {
            System.out.println("検索ヒットしました！");
        }
    }
}
```

実装例：ユーザー登録時の「入力データのクリーニング（サニタイズ）」
```java
public class UserRegistration {
    public static void main(String[] args) {
        // ユーザーが入力したID（後ろに不要なタブやスペースが混ざってしまっている状態）
        String rawInputId = "admin_user01 \t"; 
        
        // 1. プロのテクニック：処理を始める前に、前後のゴミ（空白やタブ）を完全除去！
        String cleanedId = rawInputId.trim();
        // 全角スペース除去はstrip()を使う
        
        // 2. 綺麗になったデータで、未入力チェックや文字数チェックを行う
        if (cleanedId.isEmpty()) {
            System.out.println("IDを入力してください。");
        } else {
            System.out.println("ID「" + cleanedId + "」で登録を進めます。");
            // 出力: ID「admin_user01」で登録を進めます。（綺麗に消えている！）
        }
    }
}
```

### 文字列の連結方法
- `String s = "明日は" + "晴れ";`の演算子の連結より、StringBuilderクラスを使った方が圧倒的に高速
- StringBuilderクラス
  - 内部に連結した文字列を蓄えるメモリ領域（バッファ）を持っている。
  - appendメソッドを読んでバッファに文字列を追加していく
    - 必要に応じた回数呼び出す
  - 最後に1回だけtoString()を呼び、完成した連結済みの文字列を取り出す
  - `public StringBuilder append(String s)`
    - 戻り値が`StringBuilder`
      - `sb.append("hello").append("365")`みたいにメソッドチェーンができるようにするため
- +演算子が遅い理由
  - Stringクラスの特性
    - 危険な拡張を防止するためfinalによる継承の禁止の宣言
    - Stringインスタンスの不変性：Stringインスタンスが保持する文字列情報はインスタンス作成時に初期化され、二度と変化しない
    - 不変：参照やスレッドのような技術に関連した不具合が原理的に起こり得ないという特性を持つ
  - `s = s + "World"`はどうなの？不変じゃなく無い？
    - 古いインスタンスが捨てられ、新しいインスタンスが内部的に生成される
  - ↑つまり内部でnewが行われる
  - newは計算に比べるとJVMに大きな負荷がかかる
- StringBuilderは「可変なクラス」
  - appendメソッドのたびにnewを使うようなことはない
  - バッファを拡大しながら新たな文字列を追記していく設計
- どっちを選ぶ？
  - 数回程度の文字連結であれば+演算子
    - こっちの方がタイピング量が少なく読みやすい

実装例：カンマ区切りの文字列（CSV風データ）を美しく作る
```java
import java.util.List;

public class MemberListBuilder {
    public static void main(String[] args) {
        // データベースから数万件のデータが届いたと仮定（今回は3件で疑似再現）
        List<String> memberIdList = List.of("ID-001", "ID-002", "ID-003");
        
        // 1. 大量の連結に備えて StringBuilder を用意
        StringBuilder sb = new StringBuilder();
        
        for (String id : memberIdList) {
            // 2. まずはバッファにデータを追記
            sb.append(id);
            // 3. すぐ後ろに区切り文字（カンマとスペース）を追記
            sb.append(", ");
        }
        
        // 【プロの技】このままだと末尾が「ID-003, 」と、余計なカンマが残ってしまう！
        // 4. バッファが空でなければ、最後の余計な「, 」（2文字分）をスパッと削除する
        if (sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        
        // 5. 最後に1回だけ取り出す
        String result = sb.toString();
        System.out.println("送信データ: " + result); // 出力: 送信データ: ID-001, ID-002, ID-003
    }
}
```

## 正規表現
- 「直前の文字の0回の繰り返し」とは、その文字がないものもtrue

### 正規表現の基本文法１
- 通常の文字：その文字でなければならない

```java
String s = "Java";
s.matches("Java"); // true
s.matches("Javajava"); // false
s.matches("java"); // false
```

### 正規表現の基本文法2
- ピリオド：任意の1文字であればよい
- パターン中にピリオド記号「`.`」があった場合、その部部には任意の1文字があればよい

```java
"Java".matches("J.va"); // true
"Jeva".matches("J.va"); // true
"Jあva".matches("J.va"); // true
```

### 正規表現の基本文法3
- アスタリスク：直前の文字の0回以上の繰り返し

```java
"ABホゲホゲ".matches("AB*"); // true
"ABBBB".matches("AB*"); // true
"AB".matches("AB*"); // true
"A".matches("AB*"); // true
"jaaaava".matches("ja*va"); // true
"hoge".matches("AB*"); // false

"あいおxx000".matches(".*"); // 全ての文字列を許可する
s.matches("Ma.*"); // Maで始まる任意の文字列 = startsWith("Ma");
s.matches(".*ful"); // fulで始まる任意の文字列 = endsWith("ful");
```

### 正規表現の基本文法4
- 波カッコ：指定回数の繰り返し
- `{n}`：直前の文字のn回の繰り返し
- `{n,}`：直前の文字のn回以上の繰り返し
- `{n, m}`：直前の文字のn回以上m回以下の繰り返し
- `?`：直前の文字の0回または1回の繰り返し
- `+`：直前の文字の1回以上の繰り返し

```java
"HELLLO".matches("HEL{3}O"); // true
```

### 正規表現の基本文法5
- 角カッコ：いずれかの文字
```java
"URL".matches("UR[LIN]"); // true
"URI".matches("UR[LIN]"); // true
"URN".matches("UR[LIN]"); // true
```

### 正規表現の基本文法6
- 角カッコ内のハイフン：指定範囲のいずれかの文字
- `[a-z]`：小文字のaからz
- `[A-Z]`：大文字のAからZ
- `[a-zA-Z]`：小文字のaから大文字のZ
- `[0-9]`：0から9

ちなみに、多用されるパターンは文字クラスとして定義されている
- `¥d`：`[0-9]`と同義
- `¥w`：`a-zA-z0-9`と同義
- `¥s`：空白文字（スペース、タブ文字、改行文字）

ちなみに特殊文字は`¥`の後につけると含めることができる
- `¥¥`：`¥`が対象
- `¥[`：`[`が対象

### 正規表現の基本文法7
- ハット「^」とダラー「$」：先頭と末尾

```java
"jeep".matches("^j.*p"); // true
"jp".matches("^j.*p"); // true
```

### 実装例：プレイヤー名のチェック
- 8文字
- 使える文字はA~Zと0~9
- 最初の文字に数字は使えない

```java
public boolean isValidPlayerName(String name) {
  return name.matches("[A-Z][A-Z0-9]{7}");
}
```

### 文字列の分裂
```java
public class Main {
  public static void main(String[] args) {
    String s = "abc,def:ghi";
    String[] words = s.split("[,:]"); // , か : で分割
    for (String w : words) {
      System.out.print(w + "→");
    }
  }
}
```

### 文字列の置換
```java
public class Main {
  public static void main(String[] args) {
    String s = "abc,def:ghi";
    String w = s.replaceAll("[beh]", "X");
    System.out.println(w); // aXc,dXf:gXi
  }
}
```

## 文字列の書式整形
- format() を使う
- static として定義されている
- 「書式指定文字列」
  - `%`はプレースホルダと呼ぶ
  - `%修飾桁型`の構成
    - 修飾
      - 省略可能
      - `,`：3桁ごとにカンマを入れる
      - `0`：空き領域を0埋め
      - `-`：左寄せ
      - `+`：富豪を矯正表示
    - 桁
      - 省略可能
      - 表示桁数を指定する
      - n.m形式でs指定した場合、全体n桁、小数点m桁での表示となる
    - 型
      - 必須
      - `d`：整数、decimal
      - `s`：文字列
      - `f`：少数
      - `b`：真偽値

```java
// 戻り値：3日でスッキリわかるJava入門
String.format("%d日で%sわかる%s入門", 3, "スッキリ", "Java");

final String FORMAT = "%-9s %-13s 所持金%,6d";
String s = String.format(FORMAT, hero.getName(), hero.getJob(), hero.getGold());

// ちなみに書式を指定して画面に表示する方法
System.out.printf(FORMAT, hero.getName(), hero.getJob(), hero.getGold());
```

## 日付と時刻
- 基準日時（エポック）：1970年1月1日0時0分0秒
- 日時の情報を表す形式が4つもある
  1. long型の数値
    - エポックから経過したミリ秒（1/1000秒）で日時情報を表現する方法
    - コンピューターにとって扱いやすい
    - 人間には分かりにくい
    - long型は2時地情報以外にも用いられるので要注意s
    - `System.currentTimeMills()`：現在日時をlong型で得られる
  2. Date型のインスタンス
    - `java.util.Date`クラスを使う
    - 内部でlong値を保持している
    - 人間にわかりやすい
    - 現在日時をもつDateインスタンスの生成
      - `Date d = new Date();`
    - 指定時点の日時をもつDateインスタンスの生成
      - `Date d = new Date(long値);`
  3. 人間が指定しやすい「6つのint」形式
    - 年・月・日・時・分・秒
    - 入力値としてはintでくるのが一般的、フォームとかね
  4. 人間が読みやすいString型のインスタンス
    - 2023年9月18日5時53分20秒
    - 2023/9/18 5:53:20
    - 2023-9-18 05:53:20AM

### 処理時間を計測
```java
public class Main {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();

    // なんらかの処理

    long end = System.currentTimeMillis();
    System.out.print("処理にかかった時間は" + (end - start) + "ミリ秒でした");
  }
}
```

### 現在日時を表示する
```java
import java.util.Date;

public class Main {
  public static void main(String[] args) {
    Date now = new Date();
    System.out.println(now); // Fri Nov 03 13:00:00 JST 2023
    System.out.println(now.getTime()); // 16989490000

    Date past = new Date(16989490000);
    System.out.print(past); // Fri Nov 03 13:00:00 JST 2023
  }
}
```

### Date型と6つのint型の相互変換
- java.util.Calendarクラスを使う

「6つのint値」からDateインスタンスを生成する
```java
Calendar c = Calendar.getInstance();
c.set(年,月,日,時,分,秒); // フル版
c.set(Calendar.~, 値); // 個別版
c.set(Calendar.MONTH, 値); // 月の値は0~11 2月の場合は1を入力
Date d = c.getTime();
```

Dateインスタンスから「6つのint値」を生成する
```java
Calendar c = Calendar.getInstance();
c.setTime(Date型変数);
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY);
int hour = c.get(Calendar.HOUR);
int minute = c.get(Calendar.MINUTE);
int second = c.get(Calendar.SECOND);
```

6つのint値とDate型の相互変換
```java
import java.util.*;

public class Main {
  public static void main(String[] args) {
    Calendar c = Calendar.getInstance();

    // 6つのint値からDateインスタンスを生成
    c.set(2023, 8, 18, 5, 53, 20);
    c.set(Calendar.MONTH, 9); // 10月であることに注意！

    Date d = c.getTime();
    System.out.println(d);

    // Dateインスタンスからint値を作成
    Date now = new Date();
    c.setTime(now);
    int y = c.get(Calendar.YEAR);
    System.out.println("今年は" + y + "年です");
  }
}
```

String から Date インスタンスを生成する
```java
SimpleDateFormat f = new SimpleDateFormat(書式文字列);
Date d = f.parse(文字列);
```

DateインスタンスからStringを生成する
```java
SimpleDateFormat f = new SimpleDateFormat(書式文字列);
String s = f.format(d);
```

String型とDate型の相互変換
```java
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
  public static void main(String[] args) throws Exception {
    SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    // 文字列からDateインスタンスを生成
    Date d = f.parse("2023/09/18 05:53:20");
    System.out.println(d);

    // Dateインスタンスから文字列を生成
    Date now = new Date();
    String s = f.format(now);
    System.out.println("現在は" + s + "です");
  }
}
```

### Date や Calendar が抱えている問題
1. 使い方が紛らわしいAPIが存在する
  - 3月を指定するのに「2」と表記する、1~12ではなく0~11
2. 並列処理で用いるとインスタンスの内容が壊れることがある
  - 複数の処理を同時に実行するスレッドという仕組みで発生する
3. 日付や時刻を正確かつ便利に扱うための必要な機能を十分に備えていない
  - 最小でもミリ秒単位でしか扱えない
  - コンピューターはナノ秒単位で動く
  - 私たちが日常利用する「曖昧な日時」を表せない
  - 私たちが日常利用する「時間の幅」を表せない

## Time API
- DateやCalendarと比較して直感的でわかりやすいAPI構造
- スレッドとの併用でもインスタンスが壊れない

### java.timeパッケージの代表的なクラス
- `Instant` / `ZonedDateTime`
  - 世界における、ある瞬間の時刻を、ナノ秒単位で厳密にさし示し保持する
- `LocalDateTime`
  - 日常的に使われる曖昧な日時を保持する
- `Duration` / `Period`
  - 2つの異なる時刻や日付の期間を保持する

### より正確な「瞬間」を表すクラス
- Instantクラス
  - 瞬間という意味
  - エポックからの経過時間をナノ秒で格納し、この世界における「ある瞬間」を指し示すことができる
- ZonedDateTimeクラス
  - Instantクラスと同様にある瞬間を格納できるクラス
  - ただし、エポックからの経過時間ではない
  - 形式：東京における西暦2023年8月10日 7時11分9秒 392411ナノ秒
  - Calendarクラスの後継

InstantとZonedDateTimeを利用
```java
import java.time.*;

public class Main {
  public static void main(String[] args) {
    // Instantの生成
    Instant i1 = Instant.now(); // newを使わない静的メソッド

    // Instantとlong値との相互変換
    Instant i2 = Instant.ofEpochMilli(1600705425827L);
    long l = i2.toEpochMilli();

    // ZonedDateTimeの生成
    ZonedDateTime z1 = ZonedDateTime.now() // newを使わない静的メソッド
    ZonedDateTime z2 = ZonedDateTime.of(2023, 1, 2, 3, 4, 5, 6, ZoneId.of("Asia/Tokyo"));

    // Instant と ZonedDateTimeの相互変換
    Instant i3 = z2.toInstant();
    ZonedDateTime z3 = i3.atZone(ZoneId.of("Europe/London"));

    // ZoneDateTimeの利用方法
    System.out.println("東京：" + z2.getYear() + z2.getMonth() + z2.getDayOfMonth());
    System.out.println("ロンドン：" + z3.getYear() + z3.getMonth() + z3.getDayOfMonth());

    if (z2.isEqual(z3)) {
      System.out.println("同じ瞬間だね");
    }
  }
}
```

### あいまいな日時を表すクラス
- 日常生活では、「年・月・日・時...ミリ秒」などの情報が欠落した曖昧な日時情報を使っている
  - 子供の日は？ => 5/5
- DateクラスやCalendarクラス、ZonedDateTimeは全情報を持つため、ミリ秒やタイムゾーンを含まない日時情報は格納し辛い
- 旧来、Dateクラスなどでタイムゾーンを0にして対応をとってきた
  - しかし、0にするとロンドンを意味してしまい、オブジェクト指向的に不整合となる
- ↑ LocalDateTimeクラス を使う！
  - タイムゾーン情報がない
  - ※LocalDateTimeインスタンス単体では「どの瞬間をさし示しているか」を確定できない

LocalDateTimeを利用する
```java
import java.time.*;

public class Main {
  public static void main(String[] args) {
    // LocalDateTimeの生成方法
    LocalDateTime l1 = LocalDateTime.now();
    LocalDateTime l2 = LocalDateTime.of(2024, 1, 1, 9, 5, 0, 0);

    // LocalDateTimeとZoneDateTimeの相互変換
    ZonedDateTime z1 = l2.atZone(ZoneId.of("Europe/London"));
    LocalDateTime l3 = z1.toLocalDateTime();
  }
}
```

各種日時クラスのメソッドを利用する
```java
import java.time.*;
import java.time.format.*;

public class Main {
  public static void main(String[] args) {
    // 文字列からLocalDateを生成
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    LocalDate ldate = LocalDate.parse("2023/09/22", fmt);

    // 1000日後を計算
    LocalDate ldatep = ldate.plusDays(1000);
    String str = ldatep.format(fmt);
    System.out.println("1000日後は" + str);

    // 現在の日付と比較
    LocalDate now = LocalDate.now();
    if (now.isAfter(ldatep)) {
      System.out.println("本日は、その日より未来です");
    }
  }
}
```

### 時間や期間を表すクラアス
- Durationクラス
  - 「時・分・秒」単位で収まる比較的短い間隔を表す場合
- Periodクラス
  - サマータイムや閏年なども考慮する日数ベースの間隔
- 両クラスの静的メソッドでインスタンスを取得できる
  - `between()`
  - `ofDays()`
  - `ofMonths()`

Periodクラスを利用する
```java
import java.time.*;

public class Main {
  public static void main(String[] args) {
    LocalDate d1 = LocalDate.of(2023, 1, 1);
    LocalDate d2 = LocalDate.of(2023, 1, 4);

    // 3日間を表すPeriodを2通りの方法で生成
    Period p1 = Period.ofDays(3);
    Period p2 = Period.between(d1, d2);

    // d2のさらに3日後を計算する
    LocalDate d3 = d2.plus(p2);
  }
}
```

## コレクション
- コレクションフレームワーク
  - java.utilに属している
  - リスト
    - 順序通りに並べて格納する
    - 中身の重複はOK
    - `ArrayList`, `LinkedList`
  - セット
    - 順序があるとは限らない
    - 中身の常服は不可
    - `HashSet`, `LinkedHashSet`, `TreeSet`
  - マップ
    - ペアで対応つけて格納する
    - `HashMap`, `LinkedHashMap`, `TreeMap`
- 配列と違って、インスタンスじゃないものは格納できない
  - 基本データ型の情報を格納することはできない

### ArrayList
- 配列と似ている
- import文を記述する
- <>記号を使って、格納するインスタンスの方を指定する
  - <>はジェネリクスという
  - どんな種類のインスタンスでも格納できる
- 宣言時のサイズ指定は不要、要素は随時追加できる
  - データを追加しようとした時にもし、ヒープ領域が不足していれば、自動的に追加されていく
- ただし、配列の方がメモリ効率や性能は高い

配列を使ったコード
```java
// 配列を準備
String[] names = new String[3];

// 3人を追加
names[0] = "tanaka";
names[1] = "鈴木";
names[2] = "斉藤";

System.out.println(names[1]);
```

ArrayListを使ったコード
```java
import java.util.ArrayList;

// ArrayListを準備
ArrayList<String> names = new ArrayList<String>();

// 3人を追加
names.add("ta中");
names.add("鈴木");
names.add("斉藤");

System.out.println(names.get(1));
```

### ラッパークラス
- コレクションは基本データ型の情報を格納することはできないが、
- ラッパークラスをを使用すると格納できる
  - 基本データ型の情報を中身に保持するクラス
  - java.lang.Byte
  - java.lang.Short
  - java.lang.Integer
  - java.lang.Long
  - java.lang.Float
  - java.lang.Double
  - java.lang.Character
  - java.lang.Boolean
- `ArrayList<int>` はダメ、`ArrayList<Integer>`はOK
- ラッパー型 <=> 基本データ型 の自動変換機能がある
  - これにより、`Integer i = new Integer(1);`といちいち定義してArrayListに格納する必要は無くなった
  - オートボクシング
  - オートアンボクシング

ラッパークラスをArrayListに格納する
```java
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    ArrayList<Integer> points = new ArrayList<Integer>();

    // オートボクシングにより下記は不要
    // Integer i1 = new Integer(1);
    // points.add(i1);

    points.add(10);
    points.add(52);
    for (int i : points) { // int型として扱える
      System.out.println(i);
    }
  }
}
```

### ArrayListの使い方
- `ArrayList<Integer> points = new Array<>()`
  - <> のように中身を空にしても良い
  - 右を見て自動的に推測する
  - 空の<>をダイヤモンド演算子という
- `add(インスタンス型名)`
  - リストの最後に要素を追加
  - 戻り値：boolean
- `add(int, インスタンス型名)`
  - int番目に要素を追加
  - 戻り値：void
  - 上書きもできる
- `get(int)`
  - int番目の要素を取り出す
- `size()`
  - 格納されている要素数を返す
  - 戻り値：int
- `isEmpty()`
  - 要素数がゼロであるかを判定
  - 戻り値：boolean
- `contains(インスタンス型名)`
  - 指定要素が含まれているかを判定
  - 戻り値：boolean
- `indexOf(インスタンス型名)`
  - 指定要素が何番目にあるか検索
  - 戻り値：int
- `clear()`
  - 要素を全て削除する
  - void
- `remove(int)`
  - int番目の要素を削除する
  - 削除した分、前へ詰める
- `iterator()`
  - 要素を順に処理するイテレーターを返す
  - 戻り値：Iterator<インスタンス型名>

### 要素を順に取り出す方法
```java
// その1
for (int i = 0; i  < リスト変数.size(); i++) {
  リスト変数.get(i)
}

// その2
for (リスト要素の型 e : リスト変数) {
  e を使って読み書き
}
```

### イテレーターとは
- コレクションクラスの中身を順に取り出すための専用の道具
- リストの要素を指し示す矢印のようなもの「↑イマココ！」
- new演算子でインスタンスは作らない

```java
// この時点では、リストの先頭よりももっと前を刺した状態のイテレータが取得される
Iterator<リスト要素の型> it = リスト変数.iterator();

while (it.hasNext()) { // 次の要素をさせるかを判定
  リスト要素の型 e = it.next(); // 次の要素を指し、その内容を返す
  要素e を用いた処理
}
```

イテレータを使ったArrayListの繰り返し処理
```java
import java.util.*;

public class Main {
  public static void main(String[] args) {
    ArrayList<String> names = new ArrayList<>();
    names.add("ほげ");
    names.add("ほげ-た");
    names.add("ほげいる");

    Iterator<String> it = names.iterator();
    while (it.hasNext()) {
      String e = it.next();
      System.out.println(e);
    }
  }
}
```

## LinkedListの使い方
- ArrayListで使えるメソッドの大抵のものが使える
  - どちらもListインターフェースで実装されている
  - コレクションインスタンスの変数型は曖昧な型で使うことも多い
    - `List<String> list = new ArrayList<String>();`
    - `List<Hero> list = new LinkedList<Hero>();`
- ArrayListは配列を応用して作られたものだが、LinkListは連結リストと呼ばれる構造を応用して作られている
- ガソリン自動車と電気自動車のようなもの
- ArrayListは隙間なく並んだ箱
- LinkedListは数珠つなぎの箱
- ArrayListに比べて
  - add()・remove()は早い
    - ArrayListの場合、追加（削除）された以降の要素を玉突き方式で整理しなければいけない
    - LinkedListは対象の要素の一つ前の藻に対して、次の要素の連結情報を書き換えるだけで良い
  - get()のような、指定位置の要素の取得は遅い
    - LinkedListの要素には添え字がない
- ただし、要素数が多い場合、末尾付近の要素をadd()/remove()するときは要注意
  - 要素にたどり着くまで延々と辿る必要がある

### ポイント
インターフェース型を活用しよう。
引数・戻り値・ローカル変数には、極力曖昧な型を利用できないか検討し積極的に活用する。

## Setクラス（集合）
- java.util.Setインターフェースに属する
- それぞれの要素には、重複が許されない
- それぞれの要素には、基本的に順序関係がない
- Listとの違い
  - 重複した値を格納しようとすると無視される
  - set()やget()がない
    - Setにはn番目という概念がなく、添え字を使った操作は行えない
    - 拡張for文やイテレータを使って取り出す
  - 要素は順不同で取り出される
    - 格納した順序とは異なる取り出しとなる
    - 実行するたびに異なる順序となるかもしれない
- 順序が保証されるSetのバリエーション
  - LinkedHashSet
    - 値を格納した順に整列する
  - TreeSet
    - 自然順序付けで整列する
    - それぞれのクラス固有の順序となる
      - Stringだと辞書順になる

TreeSetから文字列を取り出す
```java
import java.util.Set;
import java.util.TreeSet;

public class Main {
  public static void main(String[] args) {
    Set<String> words = new TreeSet<String>();

    words.add("add");
    words.add("cat");
    words.add("wolf");

    for (String s : words) {
      System.out.print(s + "->");
    }
  }
}
```

### Setインターフェースが備えるメソッドの一覧
- `add(要素)`
  - セットに要素を追加
  - 戻り値：boolean
- `int`
  - 格納されている要素数を返す
  - 戻り値：int
- `isEmpty()`
  - 要素数がゼロであるかを判定
  - 戻り値：boolean
- `contains(要素)`
  - 指定要素が含まれているか判定
  - 戻り値：boolean
- `clear()`
  - 要素を全て削除する
  - 戻り値：void
- `remove(要素)`
  - 指定した内容の要素を削除する
  - 戻り値：void
- `iterator()`
  - 要素を順に処理するイテレータを返す
  - 戻り値：Iterator<要素>

## Mapの使い方
- Mapとはキーと値のペアで格納するデータ構造
- キーの重複は許されない
  - 同じキーで異なる値をput()すると上書きする

HashMapのインスタンス化
```java
Map<キーの型, 値の型>　マップ変数 = new HashMap<キーの型, 値の型>();
Map<キーの型, 値の型>　マップ変数 = new HashMap<>();
```

### HashMapの備えるメソッド
- `put(キー, 値)`
  - マップにキーと値のペアを格納する
  - 戻り値：値
- `get(キー)`
  - キーに対応する値を取得
  - 無ければnull
  - 戻り値：値
- `size()`
  - 格納されているペア数を数える
  - 戻り値：int
- `isEmpty()`
  - 要素がゼロであるかを判定
  - 戻り値：boolean
- `containsKey(キー)`
  - 指定データがキーに含まれているか判定
  - boolean
- `containsValue(値)`
  - 指定データが値に含まれているか判定
- `clear()`
  - 要素を全て削除する
  - void
- `remove(キー)`
  - 指定した内容の要素を削除
  - 戻り値：値
- `keySet()`
  - 格納されているキーの一覧を返す
  - 戻り値：Set<キー>

HashMapクラスの利用
```java
import java.util.*;

public class Main {
  public static void main(String[] args) {
    Map<String, Integer> prefs = new HashMap<String, Integer>();

    prefs.put("京都府", 255);
    prefs.put("東京都", 1215);
    prefs.put("熊本県", 181);

    int tokyo = prefs.get("東京都");
    System.out.println("東京都の人口は、" + tokyo);

    prefs.remove("京都府");
    prefs.put("熊本県", 182);
  }
}
```

マップに格納された情報を1つずつ取り出す
```java
import java.util.*;

public class Main {
  public static void main(String[] args) {
    Map<String, Integer> prefs = new HashMap<String, Integer>();

    prefs.put("京都府", 255);
    prefs.put("東京都", 1451);
    prefs.put("熊本県", 135);

    // 順序は保証されない
    // 格納順に取り出したい場合はLinkedHashMap
    // 自然順序で取り出したい場合はTreeMap
    for (String key : prefs.keySet()) {
      int value = prefs.get(key);
      System.out.println(key + "の人口は、" + value);
    }
  }
}
```

## 例外
- エラーの種類3
  1. 文法エラー Syntax Error
    - コードの形式的誤り
    - コンパイルするとエラーが発生
    - コンパイラが指摘したコードの箇所を修正する
  2. 実行時エラー Runtime Error
    - 実行中に想定外の事態が発生して動作継続ができない
    - 実行すると途中で強制終了
    - あらかじめ「エラーが発生した時の対策」を記述し、事前回避
    - いわゆる「例外的状況」
  3. 論理エラー LogicError
    - 記述した処理内容に誤りがある
    - 実行すると想定外の処理結果
    - 原因箇所を自力で探してコード修正

### 例外的状況
- PCのメモリが足りなくなった
- 存在すべきファイルが見つからない
- nullが入っている変数を利用しようとした

### try-catch文
- tryブロック実行中に例外的状況の発生をJVMが検知すると、処理は直ちにcatchブロックに移行する
- tryブロックは、「この部分は例外的状況が発生する可能性がある、その検証を試みながら実行しなさい」とJVMに指示する文

```java
try {
  通常実行される文
} catch (例外クラス 例外インスタンス変数名) {
  例外発生に実行される文
}

/* ２種類以上の例外をキャッチ 
catchは上から下へ順に検索する
*/
try {
  通常実行される文
} catch (IOException e) {
  例外発生に実行される文
} catch (NullPointerException e) {
  例外発生に実行される文
} catch (例外クラス1 | 例外クラス2 e) {
  例外クラス1 でも 例外クラス2 でもキャッチする
} catch (Exception e) {
  ざっくりキャッチできる
  Exceptionの子孫をキャッチ
} finally {
  例外があってもなくても必ず実行する処理
}
```

## 例外クラス
- 発生した例外を区別できるように、それぞれの例外的状況を表すクラスが用意された
- `Throwableインターフェース`
  - `Errorインターフェース`
    - 回復見込みがない致命的な状況
    - `Error系例外クラス`
      - catchする必要がない
      - OutOfMemoryError
      - ClassFormatError
        - ファイルクラスが壊れている
  - `Exceptionインターフェース`
    - 回復の見込みがある状況
    - try-catch文を書かないとコンパイルエラーになる
    - メソッドに対応した例外クラスが定められている
      - APIリファレンスに`throws IOException` などのように宣言文に書かれている
    - `Exception系例外`
      - catchすべき
      - IOException
      - ConnectException
  - `RuntimeExceptionインターフェース`
    - 回復が必須ではない状況
    - いちいち想定するとキリがないもの
    - `RuntimeException系例外`
      - catchしなくても良い
      - NullPointerException
        - 変数がnull
      - ArrayIndexOutOfBoundsException
        - 配列の添え字が不正

### 例外インスタンスの受け渡し
- catch の変数eに格納された情報
  - 例外的状況の詳細情報が詰めこまれた例外インスタンスをcatch文で指定された変数eに代入
- 例外インスタンスが備えているメソッド
  - `getMessage()`
    - 例外的状況の解説文（エラーメッセージ）を取得
  - `printStackTrace()`
    - スタックトレースの内容を画面に出力
    - スタックトレースとは、JVMがプログラムのメソッドを、どのような順序で呼び出し、どこで例外が発生したか、という経緯が記録された情報

```java
try {
  FileWriter fw = new FileWriter("data.txt");
  fw.write("hello");
  fw.close();
} catch(IOException e) {
  System.out.println("エラー：" + e.getMessage());
}
```

### 後片付け処理の対応
```java
import java.io.*;

public class Main {
  public static void main(String[] args) {
    try {
      FileWriter fw = new FileWriter("data.txt");
      fw.write("hello");
      fw.close(); // 例外が起きた場合、実行されない！
    } catch (Exception e) {
      System.out.println("何らかの例外が発生しました");
    }
  }
}
```

このケースの対処法 ↓
```java
import java.io.*;

public class Main {
  public static void main(String[] args) {
    FileWriter fw = null;
    try {
      fw = new FileWriter("data.txt");
      fw.write("hello");
    } catch (Exception e) {
      System.out.println("何らかの例外が発生しました");
    } finally { // 例外が発生してもしなくても実行される
      if (fw != null) {
        try {
          fw.close();
        } catch (IOException e) {
          ; // close()失敗時は何もしない
          // 空文というれっきとした構文、何もしないと明示
        }
      }
    }
  }
}
```

↑をもっとスマートに（最新の構文）、try-with-resources文を使う
```java
import java.io.*;

public class Main {
  public static void main(String[] args) {
    try (FileWriter fw = new FileWriter("data.text");) {
      fw.write("hello");
    } catch (Exception e) {
      System.out.println("何らかの例外が発生しました");
    }
    // FileWriterクラスはjava.lang.AutoClosebleインターフェースで実装されている
    // そのため、JVMによって自動クローズされるのでclose()は必要ない
  }
}
```

### 例外の伝搬
- 例外がキャッチされない限り、メソッドのヨボだし元まで処理をたらい回しにする性質
  - mainメソッド <- subメソッド <- subsubメソッド
  - mianメソッドもキャッチしなければ強制終了し、伝搬は止まる
- Exception系例外（チェック例外）はtry-catch文が必須のため、基本的に例外の伝搬は起こらない
- メソッド宣言時に「スロー宣言」を行うと、発生するチェック例外をキャッチせず呼び出し元へ伝搬する
- メソッド定義において、チェック例外をキャッチしないことを`throws`で宣言できる
  - この時、メソッド内でtry-catch文によるキャッチをしなくてもコンパイルエラーにならない
  - なぜ、コンパイルエラーにならないかというと、そのメソッドが「私はメソッドないでチェック例外が発生しても処理しませんが、私の呼び出し元が処理します」と表明するから
- スロー宣言が含まれるメソッドを呼び出す側は、このメソッドを呼び出すと、呼び出し先で発生した例外が処理されずに自分に伝搬していく可能性があることを考慮すべき
- スロー宣言が及ぼす影響
  1. 呼び出しされる側のメソッドは、メソッド内部での例外のキャッチが義務ではなくなる
  2. 呼び出す側のメソッドは、例外に伝搬してくる可能性があるメソッド呼び出しをtry-catch文で囲む義務が生まれる
- 例外処理の方針
  1. チェック例外を自分で処理
    - 例外的状況をその場で処理する
    - try-catch文で処理する
    - 空のキャッチ文は極力避ける
  2. チェック例外を処理せず、呼び出し元に委ねる
    - その場では処理せず、呼び出し元に処理を任せる
    - メソッド定義にスロー宣言を加える
    - 委ねる例外処理の種類を表明する

スロー宣言による例外伝搬の許可
```java
アクセス修飾子 戻り値 メソッド名(引数リスト) throws 例外クラス1, 例外クラス2, ... {
  メソッドの処理内容
}

public static coid subsub() throws IOException {
  FileWriter fw = new FileWriter("data.txt");
  // try-catch文がなくてもコンパイルエラーにならない
}
```

### JVMに例外的状況を報告する
- 例外的状況はJVMが監視する
- 監視中のJVMに例外的状況を報告することを「例外を投げる」「例外を送出する」と表現する
- 例外が投げれるとJVMはそれを検知して、即座にcatchブロックの実行や例外の伝搬をする
- `throws` と `throw` は違うので注意
- オリジナルの例外クラスを定義することができる
  - 大抵、既存の例外クラスを継承してオリジナル例外クラスを作る

```java
throw 例外インスタンス;
throw new 例外クラス名("エラーメッセージ");
```

例外インスタンスを自分で投げる
```java
public class Person {
  int age;
  public void setAge(int age) {
    if (age < 0) {
      throw new IllegalArgumentException("年齢を0以上の数で指定してください");
    }
    this.age = age;
  }
}

public class Main {
  public static void main(String[] args) {
    Person p = new Person();
    p.setAge(-128); // 例外が発生する
  }
}
```

オリジナル例外を定義し使用する
```java
public class UnsupportedMusicFileException extends Exception {
  // エラーメッセージを受け取るコンストラクタ
  public UnsupportedMusicFileException(String msg) {
    super(msg);
  }
}

public class Main {
  public static void main(String[] args) {
    // 試験的に例外を発生させる
    try {
      throw new UnsupportedMusicFileException("未対応のファイルです")
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
```

## ファイルを読み込む
- ストリーム
  - ファイルを少しずつ読んだり書いたりする機能
  - ファイルを一度に読むとメモリが足りないため
- FileReaderは源流にある小川のようなもの
  - read()を呼ぶたびに1文字取り出す
  - read()やclose()は IOExceptionを送出する可能性があり、例外処理を送る

ファイルから1文字ずつ読み込む
```java
import java.io.*;

public class Main {
  public static void main(String[] args) throws Exception {
    FileReader fr = new FileReader("data.txt");
    int input = fr.read();
    While (input != -1) {
      System.out.print((char)input);
      input = fr.read();
    }
    fr.close();
  }
}
```

## ファイルへ文字を書き込む
- FileWriterを使う
- ストリームに流した文字がファイルに書き込まれるイメージ

```java
import java.io.*;

public class Main {
  public static void main(String[] args) throws Exception {
    FileWriter fw = new FileWriter("data.txt");
    fw.write('そ');
    fw.write('れ');
    fw.write('で');
    fw.write('は');
    fw.close();
  }
}
```

## Webページを取得する
```java
import java.io.InputStream;
import java.net.URL;

public class Main {
  public static void main(String[] args) throws Exception {
    URL u = new URL("https://book.impress.co.jp/");
    InputStream is = u.openStream(); // インターネットへ接続
    int i = is.read();
    while (i != -1) { // ページの終わりまで繰り返す
      char c = (char)i;
      System.out.print(c);
      i = is.read();
    }
  }
}
```

## データベースを操作する
```java
import java.sql.*;

public class Main {
  public static void main(String[] args) throws Exception {
    Class.forName("org.h2.Driver");
    String dburl = "jdbc:h2:/test";
    String sql = "INSERT INTO EMPLOYEES(name) VALUES('aoki')";
    Connection conn = DrivarManager.getConnection(dburl);
    conn.createStatement().executeUpdate(sql);
    conn.close();
  }
}
```

## GUI
```java
import java.awt.FlowLayout;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    JFrame frame = new JFrame("初めてのGUI");
    Jlabel label = new JLabel("Hello World");
    JButton button = new JButton("推して");
    frame.getContentPane().setLayout(new FlowLayout());
    frame.getContentPane().add(label);
    frame.getContentPane().add(button);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300,300);
    frame.setVisible(true);
  }
}
```

## 現在時刻を表示するサーブレット
```java
import java.io.*;
import java.util.Date;
import javax.servlet.http.*;

@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    Date d = new Date();
    PrintWriter w = res.getWriter();
    res.setContentType("text/html");
    w.write("<html><body>");
    w.write("Today is " + d.toString());
    w.write("</body></html>");
  }
}
```

## SpringBootによるWebAPI
```java
package com.example.restservice;

import org.spring.framework.web.bind.annotation.*;

@RestController
public class MonseterController {
  @GetMapping("/monster")
  public Monster monster(@requestParam(value = "id") String id) {
    return new Monster(id);
  }
}
```

## DI 依存性の注入
- 依存性のある状態
  - A