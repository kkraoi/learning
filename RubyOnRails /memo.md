# Ruby On Rails について
## Railsの雛形を作成する
```
rails new アプリケーション名
```

## アプリケーションを削除
```
rm -rf アプリケーション名
```

## 構成
### コア部分
```
|- app/
    |- assets/          => 以下3つのファイルが保存されています。
       |- images/       => 画像ファイルを保存
       |- stylesheets/  => CSSやSCSSファイルを保存
       |- config/       => 画像、CSSやJavaScriptを圧縮するための設定ファイルを保存しています。
    |- controllers/     => MVCにおけるControllerのファイルを保存しています。
    |- javascripts/     => JavaScriptファイルを保存
    |- models/          => MVCにおけるModelのファイルを保存しています。
    |- views/           => MVCにおけるViewのファイル（〜.html.erbと名のつくファイル）を保存しています。
```

### コマンドの設定ファイル
`~/bin/`

### アプリケーションに関する設定
```
|- config/
    |- initializers/  => 初期化ファイルを管理
    |- database.yml   => データベースへの接続設定を記述するファイル
    |- routes.rb      => ルーティングを設定するファイル
```

### データベース
```
|- db/
    |- migrate/  => マイグレーションファイル（データベース上のテーブルを作成・更新するために必要なファイル）を管理しています。
    |- schema.rb => 最新のデータベースの状態をキャプチャするファイルです。
```

### 公開ファイル群
`~/public/`<br>
サーバーにデプロイ（アプリケーションの公開）をした後、ウェブ上に公開されるファイルを保存しています。

### テストファイル群
`~/test/`

## Railsでサーバーを立ち上げる
```
rails s -p 8080
```
「s」は「server」の略

## 必要なプラグインを追加する
```
yarn add @babel/plugin-proposal-private-methods @babel/plugin-proposal-private-property-in-object
```
## MVCモデルとルーティング
### ルーティングの役割
URLを提供する

### コントローラの役割
ルーティングで判断された結果を元に、適切なページを表示させる。

### ビューの役割
ブラウザに表示させるHTMLを実際に組み込む。

### モデルの役割
データベースにアクセスをして、データの登録・取得・削除・更新を行う。

## コントローラを作成する
```
rails g controller コントローラ名
```
gはrails generateの略

## モデルの作成
```
rails g model モデル名
```
モデルの名称は、「List」のように先頭大文字。

## 疑問
### controllerとは？
モデルに対してデータをとってくる命令を出す、ビューに対して、とってきたデータを渡すような司令塔。

### erbファイルとは？
HTMLファイル内でRubyの構文を使えるようにしたもの。

### アクションとは？
コントローラの中の処理内容。ユーザーがこれを使う感じ<br>
コントローラ内のpublicメソッドで、ルーティングを通じてユーザーが直接呼び出せる処理

## Railsアプリケーションを作成する際の手順のまとめ

1. Railsアプリケーションのひな形を作成する
2. 作成したフォルダへ移動する
3. アプリケーションサーバを起動する
4. URLにアクセスする
5. ホスト許可の設定をする
6. アプリケーションサーバを停止する

## RuntimeErrorが返ったら
```
Rails::Application is abstract, you cannot instantiate it directly. (RuntimeError)
```
これが返ってきたら、
1. Gemfile.lockにて、 springが4.3系になっていることを確認する。なっていたら、`4.2.1`に変更する。
2. `spring stop` をする。springが起動していなかったらOK。
3. `bundle install`を実行し、パッケージを更新する。

# EC2 について
## `rails s -p 8080` でブラウザが永遠に読み込み
pumaがうまく起動していないかもしれない。<br>
メモリ不足が原因かもしれない

### pumaのプロセスを確認する
```
ps aux | grep puma
```
a: すべてのユーザーのプロセスを表示<br>
u: ユーザー指向のフォーマット（CPU/メモリ使用量など）<br>
x: ターミナルに紐づかないバックグラウンドプロセスも含める
grep puma: puma という文字列を含む行だけを抽出。（grepのプロセスも表示される）

【正常なケース（Pumaが動作中）】
```
ec2-user 12345  0.5  2.1 1023456 51234 ?  Sl   14:20   0:02 puma 5.6.9 (tcp://0.0.0.0:8000) [your_app]
ec2-user 12350  0.0  0.0  119424   964 pts/0  S+   14:25   0:00 grep --color=auto puma
```
上: Pumaのプロセス<br>
下: grepのプロセス

【 異常なケース（Puma未起動）】
```
ec2-user 12350  0.0  0.0  119424   964 pts/0  S+   14:25   0:00 grep --color=auto puma
```
grepのプロセスしか表示されていない。

### ポート8000の使用状況を確認する
他にポート8000を使用していないか確認。
```
sudo lsof -i :8000
```
losf: プロセスが開いているファイルを表示するコマンド<br>
-i: ネットワークソケットファイルを指定する

これを実行して、戻りメッセージがなければ、他がポートを使用していることはない。

### メモリの使用状況を見る
```
free -m
```
メモリの利用状況を調べる。`-m`はメモリの量をMB単位で表示するオプション。

【正常なケース】
```
              total        used        free      shared  buff/cache   available
Mem:            952         343         277           0         331         450
```

【異常なケース】
```
              total        used        free      shared  buff/cache   available
Mem:            981         950          15          10          15          5
```
free や available が 極端に小さいことに注目。

- total:	システムの全物理メモリ量	952MB	ハードウェア制約
- used:	現在使用中のメモリ（アプリ＋OS）	499MB	過多なら要注意
- free:	完全に未使用のメモリ	252MB	単体では判断不可
- buff/cache: アプリが要求すれば即時解放可能な「疑似空き領域」
- available: 即座に利用可能なメモリ（free + キャッシュ解放可能量）	298MB	実質的な空きメモリ指標

ちなみに、「Swap」とは、物理メモリ（RAM）が不足した時に、ディスク領域を「仮想メモリ」として代用する仕組み。<br>
つまり、Swap.totalは仮想メモリの総量、Swap.usedは仮想メモリの使用量、Swap.freeは仮想メモリの使用可能な量。

### 最終手段
vscodeを閉じて、再びssh接続。(アプリの再起動)

それでもダメだったら

```
sudo reboot
```
にて再起動する（インスタンスの再起動）

## DB作成
既存のテーブルへのカラムの追加や削除を行う場合、テーブルを作成するマイグレーションファイルを直接編集するのは避ける！

### migrationファイルとは
テーブルの作成や変更を管理するファイル。このファイルで、テーブル名・カラム名を決定する。
`rails g model List`このコマンドによって、テーブル名が「lists」に自動的に決定される。

マイグレーションファイル`db/migrate/(作成日時)_create_lists.rb`のブロック内にて、
```
t.型 :カラム名
t.string :title
t.string :body
...
```
でカラム名を決定する。

```
rails db:migrate
```
でマイグレーションファイルからテーブルを作成する。<br>
この際、`db/schema.rb`が自動生成されるので、ちゃんとカラムができているか確認する癖をつけておくと良い。

### 新たに別のカラムを追加したい場合
```
rails g migration Addカラム名Toテーブル名 カラム名:型名

例 ⇩
rails g migration AddNameToLists name:string
rails db:migrate
```

### カラムを削除したい場合
```
rails g migration Removeカラム名Fromテーブル名 カラム名:型名

例 ⇩
rails g migration RemoveNameFromLists name:string
rails db:migrate
```

### マイグレーションファイルのステータスを表示する
```
rails db:migrate:status

⇩ 結果
 Status   Migration ID    Migration Name
--------------------------------------------------
   up     20250406052431  Create lists
   up     20250406060900  Add name to lists
   up     20250406063258  Remove name from lists
```

up: マイグレーションファイルがmigrateされている状態。つまりこのマイグレーションが適用済み。<br>
down: migrateされていない状態。データベースに未適用の変更が待機している状態。

upの状態ではmigrateをすることができない。<br>
一度実行したマイグレーションファイルの中身を修正してmigrateしようとしてもうまくいかない

### 1つ前の状態に戻す（直前のマイグレーションを取り消す）
```
rails db:rollback
```

### 特定のマイグレーションファイルのタイムスタンプを指定して、その時点に戻す
```
rails db:migrate:down VERSION=20250401123456
```

### 全部リセットしてやり直す
```
rails db:migrate:reset
```
誤解ポイント: マイグレーション履歴がリセットされたり、初期のマイグレーションが適用されるのではなくて、テーブルがリセットされる。

【こんな時に使う】<br>
- 複数のマイグレーションファイルに修正が必要
- テーブルをすべて削除し、データをリセットしたい

## フラッシュメッセージ実装

### ３種のキー
- flash[:notice]	成功系のメッセージ（緑系など）
- flash[:alert]	エラー・警告系のメッセージ（赤系など）
- flash[:error]	より重大なエラー向け（alertに近い）

### 基本形
コントローラにて
```
flash[:notice] = "投稿に成功しました。"
```
ビューファイルにて
```
<%= flash[:notice] %>

<% if flash[:notice] %>
  <p class="notice"><%= flash[:notice] %></p>
<% end %>

<% flash.each do |message_type, message| %>
  <div class="flash-message <%= message_type %>">
    <%= message %>
  </div>
<% end %>
```



# ruby文法

## メソッド シンボル ブロック
```
create_table :lists do |t|
  t.string :title
  t.timestamps
end
```
これは、js風でいえば、
```
createTable("lists", (t) => {
  t.string("title");
  t.timestamps();
});
```
こんな感じ。

## form_withについて
フォームを作るための便利なヘルパーメソッド

## リンクを作る
```
<%= link_to 表示させるテキスト , リンク先URL [,オプション] %>
⇩ 例
<%= link_to list.title, list_path(list.id) %>
```

## 名前付きルート
`as: "名前"`<br>
名前付きルートがあると、その名前をredirect_toやlink_toでも使用することができます。
```
get 'lists/:id' => 'lists#show', as: 'banana'
⇩ ルートで上のように設定すると、下のように呼び出す。
banana_path(モデルのインスタンス | id)

例
banana_path(list.id)
⇩ この場合、下と同様になる
"/lists/#{list.id}"
```

## ActiveStorageとは
Railsで画像の投稿や表示を行うためのものです。
画像は通常のカラムとして保存できないため、特別な保存方法が必要になり、それを行うのがActiveStorageです。

```
rails active_storage:install
```
でインストール、マイグレーションファイルができる。

その後、
```
rails db:migrate
```
でマイグレートする。

その後、モデルファイル:モデル名(小文字).rbに、
```
has_one_attached :image
```
を書き込んで、imageカラムが追記されたかのように扱われるようにする。

## 画像のサイズ変更
「image_processing」というGemを用いて画像サイズの変更を行う。

```
# Gemfile
gem 'image_processing', '~>1.2'
```
を追記またはコメントアウト解放。その後、`bunlde install`

エラー回避のため、
```
# config/environments/development.rb
config.active_job.queue_adapter = :inline
```
を追記

## createアクションとの関係

## オプション
url: どのURLへフォームの情報を送信するか。<br>
method: HTTPメソッドをシンボルで指定<br>
なお、これら二つは、省略できる。<br>
model: モデルのインスタンスを入れる

###　注意事項
-  tableタグ直下、trタグ直下で使えない。

# パスについて
## ルーティングとは
このURL(パス)だったら、このプログラム(アクション)を実行する、みたいな感じ。
例えば、`http://127.0.0.1:8080/top`(/topだったら)の場合、topアクションを実行する(home#top)

/topにアクセスした場合
1. /topにアクセス。
2. /config/routes.rb で`/top`が使われているアクションを探す。
3. /app/controllers/homes_controller.rb でアクションを実行。
4. /app/views/homes/top.html.erb でHTMLをブラウザに表示。

ルーティングを指定するためには、routes.rbファイルを編集する。

## パスを調べる
```
rails routes

⇩ 実行結果
Prefix Verb   URI Pattern     Controller#Action
    top GET    /top(.:format)
```

- Prefix: パスの代わりとなる文字列。
- Verb: HTTPメソッド。GET, POST, PUT, DELETE。
- URI Pattern: ルーティングのうち「パス」を表す。(.:format) ... アドレスバー上では127.0.0.1:8080/topにアクセスした時のアクションを定義する、ということ。
- Controller#Action(アクション): ルーティングのうち「アクション」を表す。「コントローラ名#アクション」

## パスを調べるタイミング
- 新しくルーティングを作ったとき

## 注意
###　命名規則
モデルの名称は、先頭大文字: List<br>
コントローラの名称は、複数形かつ全て小文字: homes

## redirect_to と　reder の違い
redirect_toはアクションを新たに実行し、renderはアクションを新たに実行しない

エラーメッセージを扱う際にはrender、それ以外はredirect_toを使う!

### redirect_to
1. redirect_toがルーティングにURLを送る（内部的にはブラウザを介してルーティングにURLを送る）
2. ルーティングに送られてきたURLとHTTPメソッドを照らし合わせて、どのコントローラのどのアクションを実行するかを決める
3. アクションを実行する
4. ビューを表示する

### reder
renderで定義したビューファイルを表示する。

画面遷移はなく、表示されているHTMLが入れ替わるのみ。

renderするビューに必要なインスタンス変数は、あらかじめ用意しなくてはならない!

## rails c

"rails c"では、主に下記三つのことを行うことができます。

1. 実装する前に実装を考えているメソッドの動きを確認する
2. テーブルにどんなデータが格納されているか確認する
3. createが正常に動作し、テーブルに値が格納されたか確かめる

```
rails c
```
によってプロンプトが表示される。

```
exit
```
で抜けられる。

## rspec導入
1. [ダウンロード](https://wals.s3.ap-northeast-1.amazonaws.com/curriculum/rails/rails6/spec_bookers1.zip)
2. spec_bookers1.zipを解凍し、アプリのappと同階層に置く。
3. Gemfileの group :test do の中を※1のように変更し、末行に`gem 'net-smtp'`を追加する
4. `bundle install`
5. test.rbを編集する(:stderrを:silenceに変更する)。※2
6. テスト用のデータベースを作成する。`rails db:migrate RAILS_ENV=test`
7. テストを行う。`bundle exec rspec spec/ --format documentation`

※1
```
group :test do
  gem 'capybara', '>= 2.15'
  gem 'rspec-rails'
  gem 'factory_bot_rails'
  gem 'faker'
end
```

※2
```
 # Print deprecation notices to the stderr.
  config.active_support.deprecation = :silence
```

#　アプリケーション作成作業まとめ
下記の順で作業を進めていく。

## アプリケーション作成
```
rails new アプリケーション名
cd アプリケーションディレクトリ
```

## github連携
1. `git branch -M main`
2. `git remote add origin リモートリポジトリURL`
3. 初回コミット。`[Start]コミットメッセージ`

## Springダウングレード
1. Gemfile.lockにて、 springが4.3系になっていることを確認する。なっていたら、`4.2.1`に変更する。
2. `spring stop` をする。springが起動していなかったらOK。
3. `bundle install`を実行し、パッケージを更新する。

## solargraphインストール
```
# Gemfile
group :development do
  gem 'solargraph'
end
```
そして、`bundle install`を実行し、パッケージを更新する。

## ホスト許可の設定
config/environments/development.rbを編集。
```
  ...
  config.hosts.clear
end
```
末行に`config.hosts.clear`を記述。
「メンターが課題のレビューをする」「チーム開発等で他人と共同開発をする」ため。

## pry 導入
【Gemfile】
```
group :development, :test do
  gem 'byebug', platforms: [:mri, :mingw, :x64_mingw]
  gem 'pry-rails' <= 追加
end
```
その後、`bundle install`。`rails c`を実行して、
```
username:~/environment/sample_app $ rails c
Running via Spring preloader in process 7584
Loading development environment (Rails 6.1.7.7)
[1] pry(main)>
```
のような表示になればOK。

## フロントで必要なプラグインを追加する
```
yarn add @babel/plugin-proposal-private-methods @babel/plugin-proposal-private-property-in-object
```

## モデルを作成
```
rails g model モデル名(先頭文字大文字, 例: List)
```
db/migrate/(作成日時)_create_lists.rb(マイグレーションファイル)にて、スキーマ(カラム)作成。<br>
ブロックのコーディングルール: ` t.データ型 :カラム名`。

```
rails db:migrate
```
にてスキーム（テーブル）作成。db/schema.rbにて問題ないか確認。

```
  create_table "モデル名(小文字)s", force: :cascade do |t|
    t.string カラム名1
    t.string カラム名2
    t.datetime "created_at", precision: 6, null: false
    t.datetime "updated_at", precision: 6, null: false
  end
```
となっていればOK。

### 注意点
既存のテーブルへのカラムの追加や削除を行う場合、テーブルを作成するマイグレーションファイルを直接編集するのは避けましょう。
直接テーブルの編集を行ってしまうと、次のような問題が発生する場合があります。

修正したテーブルの内容を反映させるために、マイグレーションをやり直す必要があるため既存のデータが消えてしまう
他のテーブルで使用されているカラムなどを消してしまった場合、マイグレーションそのものがうまく行かなくなることがある
基本的にはカラムの変更については、カラムの追加/削除のコマンドを使用して行うようにしましょう。

## コントローラ作成
```
rails g controller コントローラ名(小文字s) ビューページ1 ビューページ2
⇩ 例
rails g controller homes top
```
実はこのコマンドで、コントローラ作成と同時に、必要なviewファイルの作成や記述の追加を、簡単に実現できる。<br>
つまり、下記の4つの手順が全て完了となる。<br>
=> `rails g controller コントローラ名(小文字s) new index show edit`

1. コントローラ初期化: `rails g controller コントローラ名`
2. `app/controllers/コントローラ名_controller.rb`にて、メソッドとしてアクションを追加する。
3. `config/routes.rb`にて、`HTTPメソッド 'URL' => 'コントローラ#アクション'`により、ルーティングを作成する。
4. `app/views/コントローラ名/アクション名.html.erb`を作りHTMLを書き込むことで、ビューを作成する。

ちなみに、
- new:	データの新規作成フォームを表示する
- create:	データを追加（保存）する
- index:	データの一覧を表示する
- show:	データの内容（詳細）を表示する
- edit:	データを更新するためのフォームを表示する
- update:	データを更新する
- destroy:	データを削除する
これらのアクションを作るのが一般的なのでコントローラファイルに

### ルートトップページ作成
```
rails g controller homes top
```
より、トップ用のビューファイルを作り、
```
Rails.application.routes.draw do
  root to: 'homes#top'
  ...
```
とする。ちなみに`to:`は省略できる。

また、resourcesメソッドではまとめる必要はない(まとめられない)

## ルートを設定する
ルーティングを一括して自動生成する。

【config/routes.rb】
```
resources :小文字モデルs
⇩　例

Rails.application.routes.draw do
  resources :lists
end
```

`rails routes`コマンドでルーティングを確認。