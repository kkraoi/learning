# 環境構築メモ

## fnm
- node.jsバージョン管理ツール
- 公式：https://github.com/Schniz/fnm/blob/master/README.md

### 導入
1. インストール
```sh
winget install Schniz.fnm
```
2. シェルへの設定
- プロファイルへの書き込み
    - `fnm env --use-on-cd --shell powershell | Out-String | Invoke-Expression`
    - これによって、プロジェクトのルートディレクトリに`.node-version`というファイルを置き、バージョンの指定をすると、そのプロジェクトをひらいたときに、そのNodeがてきようされる

### .node-version
```.node-version
22.11.0
```

### よく使うコマンド
```sh
# バージョンは大雑把にしていしてOK
fnm install 22

# 最新のLTS（長期サポート版）
fnm install --lts

# PC内にインストールされているNode.jsの一覧
fnm list

# インストール可能なすべてのNode.jsのバージョン
fnm ls-remote

# 現在の画面だけ、一時的にNode.jsを切り替える
fnm use 20

# 今使っているNode.jsのバージョンを確認する
fnm current

# 現在のNode.jsのバージョンをそのままファイルに書き出す
node -v > .node-version
```

## powershell

### プロファイル
- powershellを起動したら、指定した動作を自動実行する
- .zshrcに相当する
- `C:\Users\（あなたのユーザー名）\Documents\WindowsPowerShell\Microsoft.PowerShell_profile.ps1`がプロファイル
    - 自主的に作成する
    - 作成方法  
        1. 現在の存在の確認
            -  `Test-Path $profile`
            - False であれば作成されていない
        2. フォルダとファイルを自動生成
            - `if (-not (Test-Path $profile)) { New-Item $profile -Type File -Force }`
            - $profileという変数はpowershellに応じた適切な保存先パスへ格納される
        3. ファイルを開いて編集する
            - 起動時に実行したい設定を書き込んで保存する
            - 環境変数、エイリアス、関数など
        4. 設定の即時反映
            - ファイル編集後、powershellを再起動するor下記コマンドを実行
            - `& $profile`
        5. 「& : このシステムではスクリプトの実行が無効になってい...」という赤字のエラーが出る場合
            - Windowsのセキュリティ初期設定が「安全のために外部のスクリプトを一切実行しない」となっているために発生
            - 対処方法
                1. 画面左下のWindowsのスタートボタン（または検索バー）をクリック。
                2. 「powershell」と入力。
                3. 検索結果に表示された「Windows PowerShell」を右クリックし、「管理者として実行」を選択。 (「このアプリがデバイスに変更を加えることを許可しますか？」と出たら「はい」を押す)
                4. あたらしいPowerShellウィンドウが出たら、下記をコマンドを実行
                    - `Set-ExecutionPolicy RemoteSigned -Scope LocalMachine`
                5. 変更を認証する

## yarn
### 導入する
- node.jsをインストールした状態にする
    - `npm -v`で確認
- `npm install -g yarn`
- `yarn -v`

## eas-cli
### 導入する
- `npm install -g eas-cli`
- `eas -v`

### ログインする
- `eas login`
- emailやユーザーネーム・パスワードを入力する

### アプリを起動する
```sh
npx expo start
npx expo start --tunnel
```

## ウィジェットを無効にする
管理者ターミナルにて
```sh
Get-AppxPackage *WebExperience* | Remove-AppxPackage
```

## AndroidStudio
### emulator コマンドを使えるようにする
1. SDK Manager > Languages & Frameworks > Android SDK
2. SDK Tools から下記をチェック（されているか確認）
  - Android SDK Build-Tools
  - Android Emulator
  - Android SDK Platform-Tools
3. Android SDK Location: からパスをコピーする
4. 設定 > バージョン情報 > システムの詳細設定 > 環境変数 > Path に3で取得したパスをペースト&末尾に`\emulator`を追記
5. 再起動する
6. `emulator --version` 
7. コマンドラインでエミュレーターを立ち上げる
  - `emulator -list-avds` で仮想端末をリストアップ
  - `emulator -avd @仮想端末`でエミュレーター起動
    - 工場出荷状態にして起動したい場合は`emulator -avd @仮想端末 -wipe-data`

## 4本スワイプをしてデスクトップを移動するとサブ画面も連動する問題
- 特定のアプリを「すべてのデスクトップに表示」させる
    1. トラックパッドを3本指で上へスワイプ（または Windows ＋ Tab キー）して、タスクビュー画面を開きます。
    2. サブディスプレイ側で常に固定しておきたいアプリのウィンドウを右クリックします。
    3. メニューから 「このウィンドウをすべてのデスクトップに表示する」 をクリックします。

## Java
### 21をインストール
- Microsoft Build of OpenJDKを使う
    - [ここ](https://learn.microsoft.com/ja-jp/java/openjdk/download)からダウンロード
- windowsであれば、タイプがmsiのものを選ぶ