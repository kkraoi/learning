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
接続するために必要なソフトウェア
MySQL Connector/J をインストールする

#### 3. DBeaverのインストール
DB内のテーブルの状況確認にも使う

#### 4. 管理者アカウント(root)で接続

#### 5. スキーマ作成

#### 6. ユーザー作成

#### 7. アクセス権限付与

#### 8. ユーザーアカウントで接続

#### 9. テーブル作成・制約作成

#### 10. レコード追加
