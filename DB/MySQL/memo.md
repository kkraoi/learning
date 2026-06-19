# MySQLについて

## 環境構築
- クライアントツールはDBeaverを利用

### 概要
1. 管理者アカウントでスキーマ（aji_dbと仮称する）を作成
2. 管理者は利用するユーザー（aji_user）とパスワード（aji）を作成する
    - 管理者権限でプログラムからアクセスすることができるが、通常は一般ユーザを作成してDBにアクセスする。
3. ユーザーはクライアントツールでテーブルと制約をスキーマに作成する

### 手順
#### 1. MySQLインストール
- 管理者アカウント（root）が作成される
- パスワードを設定する

1. [公式](https://www.mysql.com/jp/)にアクセス
2. ダウンロードページにアクセス
3. MySQL Community Edition の「MySQL Early Access Release Downloads »」にアクセス
    - 商用利用なども含めて完全にフリーで使いたい場合は、上記の Community Edition を選ぶ
4. マシンにあったインストーラーを選んでアクセス
5. Select Version:は最新、Select Operating System: はあったもので間違いないか確認
6. ダウンロードボタンはサイズが大きいほうを選ぶ
    - 小さいほうは必要部分をダウンロードしながらインストールする方式
7. ログインとか必要ない、「No thanks, just start my download.」を押下
8. ダウンロードしたインストーラーを実行
9. ウィザードを進める
    - Choosing Setup Type: 「Server only」を選択
    - Check Requirements: Excute を実行。ウィンドウの事項を読んで同意しセットアップを完了する。 おわったらNext
        - MicrosoftVisualC++の再配布可能パッケージが事前に必要という画面
    - Installation: Excuteを実行。終わったらNext
    - Product Configuration: Next
        - MySQL の環境設定をする画面
    - Type and Networking: そのままで Next
        - 設定のタイプ・ネットワークの設定をする画面
    - Authentication Method: User Legacy Authentication Method を選ぶ。Next
        - Use Strong Password Encryption for Authentication は 新しい認証方式で、クライアントからの接続が対応していない可能性がある
    - Accounts and Roles: パスワードを入力。Next
        - 管理者アカウントのパスワードを設定
    - Windows Service: MySQL80, Start the MySQL Server at System Startupにチェック, Standard System Accountが選択（そのまま）
        - MySQLはWindowsのServiceという機能を常に動作させておく
        - MySQL80というサービス名で登録して、Windows立ち上げ時にWindowsの管理者アカウントで自動的に起動する
    - Server File Permissons: Yes, grant full access...を選択
        - 必要なファイルへのアクセス許可を設定
    - Apply Configuraition: Execute、Finish
        - ステップが表示される
    - Product Configuration（再）: Next
    - Installaction Complete: Finish

#### 2. JDBCドライバのインストール
- 接続するために必要なソフトウェア
- MySQL Connector/J をインストールする

8.0.33をインストールする
1. [ダウンロードサイト](https://dev.mysql.com/downloads/connector/j/)を開く
2. Archives にアクセス
3. Product Version: で8.0.33 を選ぶ
4. Operating System: で Platform Independent を選ぶ
5. ダウンロード選択肢で (ZIP Platform Independent (Architecture Independent),ZIP Archive) を選んで「Download」ボタンを押してダウンロード
6. ファイルを展開して、「`mysql-connector-j-8.0.33\mysql-connector-j-8.0.33\mysql-connector-j-8.0.33.jar`」ファイルを「`C:\Program Files (x86)\MySQL\Connector J 8.0`」の中にコピー

#### 3. DBeaverのインストール
DB内のテーブルの状況確認にも使う

1. [公式](https://dbeaver.io/)にアクセス
2. Downloadをアクセス
3. DBeaver Community の最新版を選んでダウンロード
4. 日本語を選択
5. ウィザードを進める
    1. セットアップへようこそ: 特になし、次へ
    2. 使用許諾契約: 同意する
    3. Choose Users: For anyone... を選択
        - とりあえず、このPCの利用者誰でも使用できるようにする
    4. 5-1, 5-2 が繰り返されるので同様に選択
    5. 構成要素の選択: DBeaver, Include Java が選んである状態で次へ
    6. インストール先の選択: そのままで次へ
    7. スタートメニューのフォルダの選択: そのままでインストール
    8. DBeaver Community セットアップの完了: Create Desctop Shortcutにチェック入れてもいいね、完了

#### 4. 管理者アカウント(root)で接続
1. DBeaverを起動
2. データベース > 新しい接続
3. 接続タイプはMySQLを選択、次へ
4. MySQL接続設定画面: ドライバの設定を編集を押下
5. ライブラリタブを選択
    1. ライブラリにあるものをすべて削除
    2. 「ファイルを追加」より、`c:\Program Files (x86)\MySQL\Connector J 8.0\mysql-connector-j-8.0.33.jar`を選択して開く
    3. ライブラリに追加されたのを確認して、OK
6. MySQL接続設定画面:
    1. Server Host: localhost / Port: 3306 であることを確認
        - このPCにインストールしたMySQLを指す
    2. 認証: ユーザー名 = root, パスワード = MySQLで設定したもの
    3. テスト接続を押下 => 接続済みの画面が表示されるとOK
    4. 終了
7. 左側の大きな枠（データベースナビゲーター）:
    1. localhost が作られるので、ダブルクリックで接続 => みどりのチェックマークがでればOK

#### 5. スキーマ作成
1. DBeaverでlocalhostが接続されている状態
2. データベースナビゲーターのlocalhostを右クリック > SQLエディタ > SQLコンソールを開く
    - ここでSQLを入力して実行することができる
    - 試しにサンプル1をコーディング
    - 行始まりの「▶」でコードの一行を実行
3. localhost を選択、右クリック > 更新
4. localhost > データベース > データベース名 でスキーマが作成されていることがわかる

サンプル1
```sql
/* DB作成 */
CREATE DATABASE aji_db CHARACTER SET utf8 COLLATE utf8_general_ci;
```

#### 6. ユーザー作成&アクセス権限付与
1. SQLコンソールが開いている状態、コードを空に。
2. サンプル1をコーディング
3. アイコンの3番目「スクリプト実行」を押下
4. localhost を選択、右クリック > 更新
5. localhost > ユーザー に作成したゆーざーができていることがわかる
6. rootaccountの接続を終了
    - localhost > 右クリック > 切断する
    - 緑のチェックマークが消えることを確認

サンプル1
```sql
/* DBユーザを作成 */
-- aji_user が存在していなければそのユーザーを作成する
-- パスワードはajiとする
CREATE USER IF NOT EXISTS aji_user IDENTIFIED BY 'aji';

/* 権限付与 */
-- aji_userを対象に
-- aji_dbのすべてのテーブルに対して権限を付与する
GRANT ALL PRIVILEGES ON aji_db.* TO aji_user;
```

#### 7. ユーザーアカウントで接続
1. 新たな接続を作成
2. データベース > 新しい接続
3. MySQLを選ぶ
4. 接続設定をする
    - Database: 作ったもの（aji_db）
    - ユーザー名: 作ったもの（aji_user）
    - パスワード: 作ったもの（aji）
5. テスト接続で接続済みとなればOK、接続設定を終了

#### 8. テーブル作成・制約作成
1. スキーマ（aji_db） > データベース > aji_db > テーブル を確認、なければ
2. スキーマ（aji_db）右クリック > SQLエディタ > SQLコンソールを開く
3. サンプル1をコーディング
4. スクリプトを実行
5. スキーマを更新
6. スキーマ（aji_db） > データベース > aji_db > テーブル にテーブルができていることを確認

サンプル1
```sql
/*--●aji_userで実行する（テーブル作成）-----------------*/
/* 店舗マスタ作成 */
CREATE TABLE m_restaurant (
  restaurant_id    INT          NOT NULL AUTO_INCREMENT,
  restaurant_name  VARCHAR(32)  NOT NULL,
  catch_phrase     VARCHAR(64)  NOT NULL,
  PRIMARY KEY(restaurant_id)
);

/* レビューテーブル作成 */
CREATE TABLE t_review (
  review_id        INT          NOT NULL AUTO_INCREMENT,
  restaurant_id    INT          NOT NULL,
  user_id          VARCHAR(16)  NOT NULL,
  visit_date       DATE         NOT NULL,
  rating           INT          NOT NULL,
  comment          VARCHAR(50)  NOT NULL,
  PRIMARY KEY(review_id),
  FOREIGN KEY(restaurant_id) REFERENCES m_restaurant (restaurant_id)
);
```

#### 9. レコード追加
1. スキーマ（aji_db）右クリック > SQLエディタ > SQLコンソールを開く
2. サンプル1をコーディング
3. スクリプトを実行
4. 更新
5. スキーマ（aji_db） > データベース > aji_db > テーブル > 作ったテーブル をダブルクリック
6. データタブをクリック、レコードが存在していることを確認

```sql
/* 店舗マスタINSERT */
INSERT INTO m_restaurant (restaurant_name, catch_phrase)
     VALUES ('寿司さくら', '伝統と美味しさが交わる');

/* レビューテーブルINSERT */
INSERT INTO t_review (restaurant_id, user_id, visit_date, rating, comment)
     VALUES (1, 'takahashi', '2024-06-03', 4, 'コスパいいね。');
```

