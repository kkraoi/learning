# ActiveStorageとは
Railsで画像の投稿や表示を行うためのものです。
画像は通常のカラムとして保存できないため、特別な保存方法が必要になり、それを行うのがActiveStorageです。

```
# このモデルに画像ファイルを1つだけ紐付けられるようにする」 という命令
rails active_storage:install
```
でインストール、マイグレーションファイルができる。

この1行を書くだけで、Active Storage が次のようなことを自動でやってくれる。
- post_image.image.attach(...) で画像のアップロードができる
- post_image.image.attached? で画像があるか確認できる
- post_image.image で画像データにアクセスできる
- post_image.image.blob や .filename などでメタ情報も取得可能

その後、
```
rails db:migrate
```
でマイグレートする。

その後、models/モデルファイル:モデル名(小文字).rbに、
```
has_one_attached :image
```
を書き込んで、imageカラムが追記されたかのように扱われるようにする。

## 画像が投稿されていない場合はエラーが出る
エラーを回避するためにsample_appでは以下のようにビューでif式を使っていました。
```
<% if list.image.attached? %>
    <%= image_tag list.image, size: "200x200" %>
<% else %>
    <%= image_tag 'no_image', size: "200x200" %>
<% end %>
```
これでは画像の表示をする際に毎回同じ記述を書く必要があり、開発効率も可読性も落ちてしまいます。<br>
そこで、models/モデルファイル:モデル名(小文字).rbに、
```
  ...略
  ##
  # @モデル名に含まれるイメージを表示させるメソッドを実行する
  # @return [String] 画像ファイルの文字列
  def get_image
    if image.attached?
      image
    else
      'no_image.jpg'
    end
  end
end
```
get_imageメソッドを作ってやると良い。
```
@モデル名(小文字).get_image
```

もしくは、上記のget_imageメソッドは、Railsで画像のサイズ変更を行うことができないため、
```
  ##
  # @post_imageに含まれるイメージを表示させるメソッドを実行する。
  # no_image.jpgをActiveStorageに格納する。
  # @return [Object] ctiveStorage の添付ファイルオブジェクト。
  def get_image
    unless image.attached?
      file_path = Rails.root.join('app/assets/images/no_image.jpg')
      image.attach(io: File.open(file_path), filename: 'default-image.jpg', content_type: 'image/jpeg')
    end
    image
  end
```
とする。