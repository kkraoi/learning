# SpringBootについて

## ひな型作成
Spring Initializr である程度設定してひな型をダウンロードする

### 関連リンク
- Qiita：https://qiita.com/lz910201/items/ee5546a614ed3ccaaa23
- 公式：https://start.spring.io/

## Spring Initializr
- Spring Boot プロジェクトを簡単に作成するためのツール
- Web上のツールとして提供される
- eclipseからも利用できる

## application.properties
- アプリケーションの設定ファイル
- Springがアプリの実行時の動作を制御する
    - DB接続
    - サーバーポート
    - ロギング設定

## プロジェクトのフォルダ構成のポイント
もちろんSpringInitialzrが自動作成してから手動で追加してもよい

### src/main/java/
- javaのソースファイル格納
- Spring Initialzrで指定したパッケージの配下に作成してく
- パッケージ名/
    - プロジェクト名Application.java
        - Spring Boot アプリの入り口・エントリ
    - これをすることによって、パッケージ配下に作成したステレオタイプアノテーション付きのクラスがBean化の対象となり、アプリ開始時にコンポーネントスキャンを行うようになる

### src/main/resources/
- javaソース以外の必要なファイルを格納
- static/
    - cssやjsを格納
    - コンテキストルートにコピーされる
        - コンテキストルート：アプリの最上位の公開フォルダ
- templates/
    - テンプレートエンジンで使用されるファイル
- application.properties
    - アプリケーションの動作を制御

### Maven依存関係
- Mavenが管理しているローカルリポジトリ
- .jarファイル一覧の集まり

### pom.xml
- Maven用の設定ファイル
- ビルド・依存関係・プロジェクト構成などの設定

## DI Dependency Injection
- 依存挿入という
- 利用する（依存する）オブジェクトを外部から注入する設計手法
- インスタンス生成をSpringに任せる
- 利用する部品の切替が容易になりシステムの柔軟性が向上する

### 要約
- インターフェースXからインスタンスB・Cを作り、クラスAでそれらを切り替えて利用するとする
- Springは、修飾子と変数のみを記述するだけで、裏側で、B/Cのインスタンス記述をする必要がない
- メソッドを直接書くだけで実行できる

```java
X x;
x.メソッド名();
```

### 機能の利用方法
#### 用語
- DIコンテナ
    - 指定されたインスタンスを生成して格納（Bean化）しておく入れ物
- Bean
    - DIコンテナに入れておくインスタンス
- ステレオタイプアノテーション
    - Bean化したいクラスにつけるアノテーション
    - @Componentや、それを含むアノテーション
    - @Controller, @Service, @Repository

#### Bean化
1. ステレオタイプアノテーションをクラスに付加する
2. コンポーネントスキャンする
    - 指定されたパッケージを対象にステレオタイプアノテーションが付いたクラスを探し、見つかったクラスをインスタンス化してDIコンテナに入れる
    - SpringInitialzr で指定したパッケージ配下のすべてのパッケージを対象としてスキャンする、アプリ開始時に。
    - /アプリ名/src/main/java/com/example/demo/アプリ名Application.java
        - これはアプリの入り口
        - これに`@SpringBootApplication`があるが、これには`@ComponentScan`が含まれていて、これによって配下をスキャン対象とする。

```java
@Service
public class xServiceImpl implements X {

    @Override
    public String m(){
        return "ほげ";
    }
}
```

- @Serviceがついたクラスが適用される
= 呼び出し側のコードを修正せずに、切り替えられる

#### Controller
このアノテーションがついたクラスは、Bean化され、DIコンテナに入れて管理され、フロントコントローラーがDIして使用する

#### DIする方法
- 前提：DIする対象の変数をフィールドにする
    - フィールドがインタフェース型の場合、そのインタフェースを実装しているBeanがDIされる
    - 該当のBeanが複数あったり、なかったりするとエラーとなる

##### 1. フィールドインジェクション
Beanを設定してほしいフィールドに@Autowiredアノテーションを付ける

```java
@Controller
public class XController {

    @Autowired
    private X x;

    SpringがxにBeanを設定して、xという変数で、これ以降、xオブジェクト（Bean）を使用できる
}
```

##### 2. セッターインジェクション
```java
@Controller
public class XController {

    // 1. Beanを設定してほしいフィールドを定義
    private X x;

    // アノテーションを付けつつ、セッターを定義する
    @Autowired
    public void setX(X x) {
        this.x = x;
    }

    SpringがxにBeanを設定して、xという変数で、これ以降、xオブジェクト（Bean）を使用できる
}
```

3. コンストラクタインジェクション
これが主流。finalが使えるから。

```java
@Controller
public class XController {

    // 1. Beanを設定してほしいフィールドを定義
    private final X x;

    // アノテーションを付けつつ、そのフィールドを初期化するコンストラクタを定義する。
    @Autowired
    public XController(X x) {
        this.x = x;
    }

    SpringがxにBeanを設定して、xという変数で、これ以降、xオブジェクト（Bean）を使用できる
}
```

## SpringでのDBアクセスの仕組み
インフラ層 <=> DB を仲介する技術スタックの選択肢は複数
- Spring Data JPA
    - Springプロジェクトでデータアクセスを簡単に行えるようにしたもの
- MyBatis
    - Spring外部のライブラリ
    - 現在人気とのこと
- Spring JDBC
    - Spring Framework の DBアクセス機能

## Spring JDBC
- Javaが標準で提供するJDBCを使いやすくしたプログラミング部品
- 直接的なSQL操作が行えるため、SQLを学んだことがある人は利用しやすい
- 処理速度はJDBCとほとんど変わらない

### 利用手順
1. pom.xmlに依存関係（dependency）を追加
    
2. application.properties に接続情報を追加
3. JdbcTemplateオブジェクトを利用
