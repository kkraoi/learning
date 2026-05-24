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