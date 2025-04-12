# ログイン機能を作る divese
「[device](https://github.com/heartcombo/devise?tab=readme-ov-file#getting-started)」という拡張機能を使う。日本語の使い方は[こちら](https://github.com/taiki-t/Devise-readme-ja)

## gemにセット。
```
gem 'devise'
```
Gemfileにこの一行を追記。group内に入れない、末行で良い。<br>
その後`bundle install`。

## deviseをインストール
```
rails g devise:install
```
これにより、必要な設定ファイルを作る。

## モデル・DBを作る
deviseはユーザーテーブルを自動で作成する。
```
rails g devise 頭大文字モデル名(例: User)
```
によってモデルを作る。<br>
ここではUserというモデルを作ると想定して、<br>
上記コマンドより、「User」という名のモデルと、users テーブル用のマイグレーションファイルが作成される。

【Model ファイル: user.rb】
- :database_authenticatable（パスワードの正確性を検証）
- :registerable（ユーザ登録や編集、削除）
- :recoverable（パスワードをリセット）
- :rememberable（ログイン情報を保存）
- :validatable（email のフォーマットなどのバリデーション）

【devise のユーザーテーブル: （年月日時分秒）_devise_create_users.rb】<br>
必要なカラムがあれば、このファイルに
```
## 名前を保存するカラム
t.string :name <= 追加

t.timestamps null: false
end
```
このように追記すれば良い。カラムを追記したら`rails db:migrate`。

`config/routes.rb`より、モデルが作成されたことを確認する。
```
Rails.application.routes.draw do
  devise_for :users <= 自動的に追加される
  root to: 'homes#top'
end
```

以降の文はnameカラムを実装する前提で進める。

## ページを表示させる。
- http://127.0.0.1:8080/users/sign_up
- http://127.0.0.1:8080/users/sign_in
を開いてみよ。

devise をインストールして、deviseのモデルを作成しただけで、以下の最低限の認証機能が完成している。
- 必要なカラムが用意されている
- ルーティングが自動で追加されている
- devise が持っている、基本的な View を表示させる
しかし、 初期状態のdeviseでは name を入力するフォームが存在せず、<br>
ユーザ登録(サインアップ)の際に name を登録できませんし、ログインの際にも name を入力することができません。<br>
なので次のようにviewをカスタマイズする。

## Viewファイルをカスタマイズ
nameフィールドを作るようなカスタマイズをする。

```
rails g devise:views
```
によって、devise のさまざまな機能と関連付ける。

app/views/devise/registrations/new.html.erbより
```
<%= form_for(resource, as: resource_name, url: registration_path(resource_name)) do |f| %>
<%= render "devise/shared/error_messages", resource: resource %>

⇩ これ追加
<div class="field">
  <%= f.label :name %><br>
  <%= f.text_field :name %>
</div>
```

## Controllerの設定
現状、nameを入力してもデータとして保存できない状態になっている。

app/controllers/application_controller.rbより、
```
class ApplicationController < ActionController::Base
  # このように記述することで、devise利用の機能（ユーザ登録、ログイン認証など）が使われる前に
  # configure_permitted_parametersメソッドが実行されます。
  before_action :configure_permitted_parameters, if: :devise_controller?

  # privateは記述をしたコントローラ内でしか参照できません。
  # 一方、protectedは呼び出された他のコントローラからも参照することができます。
  protected

  def configure_permitted_parameters
    # ユーザー登録(sign_up)の際に、ユーザー名(name)のデータ操作を許可しています。
    devise_parameter_sanitizer.permit(:sign_up, keys: [:name])
  end
end
```
このように記述して、ストロングパラメータを設定する。<br>
deviseのコントローラは直接修正できないため、全てのコントローラに対する処理を行える権限を持つ、ApplicationControllerに
記述する必要があります。

ついでに、ログアウトリンクなども作成しておく。<br>
app/views/layouts/application.html.erbにて、
```
  <header>
      <%# if user_signed_in?は、devise 側で用意しているヘルパーメソッド。 %>
      <%# evise の機能を使ってログインしているか、ログインしていないかを判断し、 %>
      <%# どちらかによってブラウザに表示する内容を変更することができる。 %>
      <%# ログイン済みならば true が、ログインしていなければ false が返される。 %>
      <% if user_signed_in? %>
        <li>
          <%= link_to "ログアウト", destroy_user_session_path, method: :delete %>
        </li>
      <% else %>
        <li>
          <%= link_to "新規登録", new_user_registration_path %>
        </li>
        <li>
          <%= link_to "ログイン", new_user_session_path %>
        </li>
      <% end %>
    </header>
  <%= yield %>
```
このようにセットする。

ついでに、form_forメソッドは非推奨なので、form_withに変更する。

app/views/devise/registrations/new.html.erb(ユーザー登録画面)
```
<%= form_for(resource, as: resource_name, url: registration_path(resource_name)) do |f| %>
⇩　
<%= form_with model: @user, url: user_registration_path do |f| %>
```

app/views/devise/sessions/new.html.erb(ログイン画面)
```
<%= form_for(resource, as: resource_name, url: session_path(resource_name)) do |f| %>
⇩
<%= form_with model: @user, url: user_session_path do |f| %>
```

## 特定のページをサインイン/アウト後の遷移先とする方法
app/controllers/application_controller.rbにて
```
class ApplicationController < ActionController::Base
  ...略

  ##
  # サインイン後にどこに遷移するかを設定しているメソッド
  # @param resource [Instance] ログインを実行したモデルのデータ、今回の場合はつまりログインしたUserのインスタンス
  # @return [String] サインイン後にリダイレクトするパス
  def after_sign_in_path_for(resource)
    about_path
  end

  ##
  # サインアウト後にどこに遷移するかを設定しているメソッド
  # @param param [Instance] アウトを実行したモデルのデータ、今回の場合はつまりログインしたUserのインスタンス
  # @return [String] サインアウト後にリダイレクトするパス
  def after_sign_out_path_for(resource)
    about_path
  end

  protected
  ...略
end
```
のように記述をすることで、初期設定を上書きする。

ログイン後にマイページなどのログインしたユーザーに関連するようなページ遷移を実装したい場合は、このresourceを活用して実装します。<br>
今回はマイページ等の設定が現状では無いため、Aboutページへ遷移するように設定しています。

これらを設定しなかった場合は、root_pathにリダイレクトする。

## ヘルパーメソッド
### current_user
ログイン中のユーザーの情報を取得できる。

- `current_user.id`: ログインユーザーの id を取得する
- `current_user.name`: 今ログインしているユーザーの名前を表示
- `current_user.email`: 今ログインしているユーザーのメールアドレスを表示

## 権限の設定
app/controllers/application_controller.rbにて
```
before_action :authenticate_user!, except: [:top]
```
とすることで、ログイン認証が済んでいない状態でトップページ以外の画面にアクセスしても、<br>
ログイン画面へリダイレクトするようになります。<br>
また、ログイン認証が済んでいる場合には全てのページにアクセスすることができます。

- before_action: このコントローラが動作する前に実行されます。
- authenticate_user!: ログイン認証されていなければ、ログイン画面へリダイレクトする。
- except: 指定したアクションをbefore_actionの対象から外します。