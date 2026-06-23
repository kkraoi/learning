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

## AOP
- Aspect Oriented Programing アスペクト指向プログラミング
- プログラムの中に分散している共通の処理を分離して集中管理する手法
    - 共通の処理：
        - ログ出力
        - エラー処理
        - トランザクション管理
- メリット
    - 再利用性
    - 可読性
    - 保守性

### サービス層で使うとして
#### 何を作るか？
- Advice
    - 何をするか
    - メソッド
- Pointcut
    - どこで
    - アノテーション
- Advice の種類
    - どんな条件で
        - `@Before`
            - 対象のメソッドの開始前にAdviceを行う
        - `@After`
            - 対象のメソッドの終了時に、例外の発生にかかわらずAdviceを実行する
        - `@AfterReturning`
            - 対象のメソッドが正常に終了した後にAdviceを実行する
        - `@AfterThrowing`
            - 対象のメソッドが例外発生した場合にAdviceを実行する
        - `@Around`
            - 対象のメソッドの前後で実行される
            - 最も柔軟性がある
    - アノテーションのオプション
- Aspect
    - まとまり
    - クラスで表現
    - アノテーションを2つつける
        - `@Aspect`
            - このクラスがAspectであることを示す
        - `@Componet`
            - Bean化してDIコンテナで管理する

```java
@Aspect
@Component
public class LoggingAspect {

	@Before("execution(* com.example.demo.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        outputLog("メソッド開始", joinPoint);
    }
    
    @AfterReturning("execution(* com.example.demo.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
    	outputLog("メソッド終了", joinPoint);
    }
    
    // 共通ログ出力メソッド
    private void outputLog(String str, JoinPoint joinPoint) {
    	// 現在時刻文字列取得
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String strNow = LocalDateTime.now().format(formatter);
        // クラス名・メソッド名取得
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        // ログ表示
        System.out.println(
        		strNow + " : " + str + " : " + 
        		className + "." + methodName + "()"		);
    }
}
```

##### Pointcut式（execution指示子）
```java
execution(戻り値の型の指定 パッケージ指定.クラス指定.メソッド指定(引数の型指定))

// サービス層配下のメソッドすべてを対象とする
execution(* com.example.demo.service..*.*(..))

// サービス層の引数がint型1つのメソッド
execution(* com.example.demo.service.*.*(int))

// サービス層のfindで始まるメソッド
execution(* com.example.demo.service.*.find*(..))
```
ワイルドカード指定をつかって表現
- `*`：任意の１つを指定
- `..`：0個以上の任意の個数を指定

### どうやってつくるか？
- Spring AOPを使うには依存関係設定を行う
- Mavenの場合、pom.xmlにサンプル1を追記

サンプル1
```xml
<!-- AOP -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<!-- AOP(End) -->
```

#### どこにつくるか？
`com.example.demo.aop`パッケージ内

## PRG 実装
- POST - Requiest - Get
- Webアプリでフォームデータの2重送信を防ぐための設計パターン

### PRG実装をしないと生じる問題
前提：登録確認画面で登録実行ボタンを押すシチュエーションを想像する。
1. 登録確認画面：登録実行ボタン（`POST /confirm-regist`）を押下
2. サーバー：DB登録処理 & 完了画面表示レスポンス
3. 完了画面：リロード（前回のリクエストを再送 ※完了画面のリロードを要求するのではない！）
    - つまり、`POST /confirm-regist` が発火される
4. サーバー：DB登録処理 & 完了画面表示レスポンス が再び実行
    - DBに再度登録される問題発生！

### PRG実装で対策した場合
1. 登録確認画面：登録実行ボタン（`POST /confirm-regist`）を押下
2. サーバー：DB登録処理 & **Redirect レスポンス（`/complete` 完了画面 指定）**
    - Redirectレスポンス：指定したURLパターンに対してリクエストを再送する指示を出すレスポンス
3. ブラウザ（自動）：指定したURL（/complete）にGETリクエストを送る
    - つまり、`GET /complete` が発火される
4. サーバー：完了画面表示レスポンス
5. 完了画面：リロード（前回のリクエストを再送 ※完了画面のリロードを要求するのではない！）
    - つまり、`GET /complete` が発火される
6. サーバー：完了画面表示レスポンス
7. 完了画面：何度リロードされてもGETリクエストの発火となるのでDBに重複登録される心配はない

### 実装方法（基本）
```java
// コントローラ内
@PostMapping("/confirm-regist")
public String confirmRegist(~~){

    登録処理

    return "redirect:/complete"
}

@GetMapping("/complete")
public String complete() {
    return "complete";
}
```

### 実装方法（フラッシュスコープ）
前提：
- confirmRegist() => complete.html の流れで、Modelを介してデータを渡す

#### フラッシュスコープを使わないと生じる問題

原則：
- Modelが利用できるのは1回のリクエスト&レスポンス内だけ

1. confirmRegist()：
    - POSTリクエストを受け取る
    - (登録処理など...)
    - Modelにcomplete.htmlで使うデータを登録
    - リダイレクトレスポンスを送る
        - ここでModelの利用範囲が完結
2. complete()：
    - GETリクエストを受け取る
    - 当然、confirmRegist() のModelは知る由もない
    - complete.html表示レスポンスを送る
3. complete.html：
    - modelのテンプレートは不能となる！

#### フラッシュスコープを使うと解決する
フラッシュスコープの特性：
- フラッシュスコープに格納したデータは**次のリクエストまで**データが保持される
- フラッシュスコープ内のデータはSpringが自動的にModelへコピーされる

1. confirmRegist()：
    - POSTリクエストを受け取る
    - (登録処理など...)
    - **フラッシュスコープ**にcomplete.htmlで使うデータを登録
    - リダイレクトレスポンスを送る
        - （ここでModelの利用範囲が完結）
2. complete()：
    - GETリクエストを受け取る
    - フラッシュスコープのデータをModelへコピーされている
    - modelにデータが存在している！
    - complete.html表示レスポンスを送る
        - ここでModelの利用範囲が完結
3. complete.html：
    - modelのテンプレートは適用される

```java
// コントローラ内
@PostMapping("/confirm-regist")
public String confirmRegist(
    ~~,
    RedirectAttributes redirectAttributes // 重要
){

    登録処理

    redirectAttributes.addFlashAttribute("msg", "(レビュー登録)");
}

@GetMapping("/complete")
public String complete() {
    return "complete";
}
```

```html
<p th:text="${msg}"></p>

↓

<p>(レビュー登録)</p>
```


## プレゼン層とインフラ層の連携 （3層レイヤー構造）

### 要件（例）
- フォームで検索ワードを入力すると一覧が返ってくる

### フォルダ構成
- com/example/demo/
    - controller/
    - entity/
    - form/
    - repository/
    - service/

### 作成手順
1. リスト（一覧）のジェネリクスに対応したDTOを作成
    - entity/Xxxx クラスを作成
    - リストに対応したフィールドを用意
    - ゲッター・セッターを用意
    - ちなみに
        - @AllArgsConstructor
            - 全フィールドを引数に持つコンストラクタを作る
            - 値をまとめて渡してオブジェクトを作りやすくする
            - `UserDto dto = new UserDto(1, "田中");`が可能となる
        - @NoArgsConstructor
            - 引数なしコンストラクタを作る
            - `UserDto dto = new UserDto();` => `dto.setId(1);` が可能となる
2. 検索ワードをわたすために、モデル用データを作成
    - form/SearchForm.xxxxName フィールドを作成
    - バリデーションを指定
    - ゲッター・セッターも必要
3. コントローラ作成
    - controller/ListController.search()を作成
    - サービス層のメソッド（service.findByNameWildcard）から検索結果を取得する
        - フォームから検索ワードを受け取って、メソッドに渡す
    - modelに検索結果のリストを加える
    - 戻り値は検索結果を表示する画面のファイル名
4. サービス層のメソッド作成
    - service/SearchXxxxService インタフェース作成
        - 検索ワードを受けとってリストが返ってくることを想定とした型のメソッドを用意する
    - service/SearchXxxxServiceImple 実装クラス作成
        - インフラ層のメソッドをDIする
        - インタフェースをオーバーライドする
            - インフラ層のメソッドを用いてリストを返す
            - インフラ層のメソッドに検索ワードを渡す
5. インフラ層のメソッドを作成
    - repository/XxxxRepository インタフェース作成
        - 検索ワードを受けとってリストが返ってくることを想定とした型のメソッドを用意する
    - repository/XxxxRepositoryImpl 実装クラスを作成
        - JdbcTemplate をDIする
        - インタフェースをオーバライドする
            - SQL文を記述し、jdbcTemplate.queryForList(sql, "%検索ワード%")でDB処理結果を受け取る
            - 受け取った処理結果をリストに変換して返す