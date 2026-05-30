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
