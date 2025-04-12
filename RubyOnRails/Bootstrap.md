# Bootstrapについて

https://getbootstrap.com/docs/4.6/getting-started/introduction/

## インストール手順

### 1: yarnでインストール
```
yarn add jquery bootstrap@4.6.2 popper.js
```

### 2: 環境ファイル編集
config/webpack/environment.jsにて
```
...

const webpack = require('webpack')
environment.plugins.prepend(
  'Provide',
  new webpack.ProvidePlugin({
    $: 'jquery/src/jquery',
    jQuery: 'jquery/src/jquery',
    Popper: 'popper.js'
  })
)
```
を追記。

### 3: scssファイル編集
app/javascript/stylesheets/application.scss を作成し、<br>
（`mkdir app/javascript/stylesheets/ && app/javascript/stylesheets/application.scss`）
```
@use '~bootstrap/scss/bootstrap';
```
を記述。

### 4: jsファイル編集
app/javascript/packs/application.js にて
```
...
import "channels"

# ここから
// Bootstrap
import "jquery";
import "popper.js";
import "bootstrap";
import "../stylesheets/application";
# ここまで

Rails.start()
...
```
を追記。

### 5: ビューファイル編集
app/views/layouts/application.html.erbにて
```
<%= stylesheet_link_tag 'application', media: 'all', 'data-turbolinks-track': 'reload' %>
↓　これを
<%= stylesheet_pack_tag 'application', media: 'all', 'data-turbolinks-track': 'reload' %>
```
に変更。

stylesheet_link_tagの場合cssファイルは、app/assets 配下のファイルを参照します。<br>
stylesheet_pack_tagの場合は、app/javascript 配下のファイルを参照するようになります。

ちなみに、stylesheet_pack_tagのみの記述で独自のcssを読み込ませる方法を紹介しておきます。<br>
1. app/javascript/stylesheets/application.scssにcssを記述する。
2. app/javascript/packs/application.jsに追加したcssファイルをimport で追加する。

例: app/javascript/stylesheets/にmystyle.cssを作成した場合<br>
packs/application.jsにて
```
import '../stylesheets/mystyle.css'
```
