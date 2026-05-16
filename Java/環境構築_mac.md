# ローカル環境構築（Mac）

## 前提知識
### Javaのプログラム開発の3ステップ
1. ソースコードの作成と編集
2. コンパイルによる変換（コンパイラ）, javac
3. 完成プログラムの実行（インタプリタ）, java

### 2つの方式
1. JDK単体利用
2. 総合開発環境：JDK > 総合開発環境（IDE）> プラグイン...
  - プラグインを設定済みのIDEを利用するのも一般的（Pleiades）

## コラム
### JDK
Java Development Kit
様々なJDKがある。ライセンスやサポート機関に違いがある。
- Oracle OpenJDK：オラクル社
- RedHat OpenJDK：レッドハット社
- Eclipse Temurin：Eclipse財団

## M2_JDK単体利用
- 2026.05.16
- M2, Tahoe26.2, 8GB

### これを参考にすると良い
https://sukkiri.jp/technologies/processors/jdk/temurin21-mac_install.html

### パスを通さなくて良い？
- Eclipse などの統合開発環境（IDE）を使うとき
- VSCode のJava拡張機能を使うとき
- Maven や Gradle といった、外部ライブラリを管理するツールを使うとき

上記のケースで「JAVA_HOME」という環境変数（パスの一種）を設定してくださいという状況に出会したら環境変数を設定する。
```zsh
echo 'export JAVA_HOME=$(/usr/libexec/java_home)' >> ~/.zshrc && source ~/.zshrc
```

### ローカル実行方法
1. ソースファイルを作る（`Main.java`）
2. `javac Main.java`でコンパイル → 同階層にclassファイルができる
  - `javac Main.java Sub.java` で複数同時にコンパイルできる
  - `javac *.java`で同階層のすべてのソースファイルをコンパイルできる
3. `java Main`で Main.class　を実行することができる