# node.jsに関する沼ったこと

## node -v が異常
## 状態
- nodeが実行できない
- windows11で発生
- fnm にNode.jsがインストール済み、fnmは実行できる
- C:\Users\user\Documents\PowerShell\Microsoft.PowerShell_profile.ps1に`fnm env --use-on-cd --shell powershell | Out-String | Invoke-Expression`がすでに存在している
- PowerShell7 を使っている

```sh
PS C:～> npm -v                                              
npm: The term 'npm' is not recognized as a name of a cmdlet, function, script file, or executable program.
Check the spelling of the name, or if a path was included, verify that the path is correct and try again.
PS C:～> node -v
node: The term 'node' is not recognized as a name of a cmdlet, function, script file, or executable program.
Check the spelling of the name, or if a path was included, verify that the path is correct and try again.
PS C:～> fnm --version
fnm 1.39.0
```

## 原因
### 間接原因
- PowerShell5 から PowerShell7 にアップデートした

### 直接原因
- PowerShell7 のプロファイルパスが存在していない
    - `$PSVersionTable.PSVersion`よりターミナルで利用しているPowerShellのバージョンを確認
    - 続いて、`$PROFILE`でそのPowerShellが使っているプロファイルパスを表示
    - そのプロファイルパスが存在していないことをエクスプローラーから確認した

## 対応
- PowerShell7 のプロファイルパスを作成する

### ハンズオン
1. プロファイルパス作成のコマンド
    ```powershell
    Test-Path $PROFILE

    # ↑ False となれば存在しないので、
    New-Item -ItemType File -Path $PROFILE -Force
    ```
    - `$PROFILE`が指すパス（`C:\Users\user\Documents\PowerShell\Microsoft.PowerShell_profile.ps1`）にファイルを新規作成する
    - 親フォルダ`Documents\PowerShell`が存在しなくても`-Force`で一緒に作成される
    - 既存ファイルがある場合`-Force`は中身を空にしてしまうため、まず`Test-Path $PROFILE`で存在確認してから実行する

2. その.ps1に記述すべき内容
    ```powershell
    fnm env --use-on-cd --shell powershell | Out-String | Invoke-Expression
    ```
    - PowerShell5用プロファイル（`Documents\WindowsPowerShell\Microsoft.PowerShell_profile.ps1`）に書かれているのと同じ一行
    - `Add-Content -Path $PROFILE -Value 'fnm env --use-on-cd --shell powershell | Out-String | Invoke-Expression'`で追記できる
    - 記述後、ウィンドウを開き直して`node -v`・`npm -v`が通るか確認する

3. ターミナルの再起動で確認
    ```powershell
    . $PROFILE
    node -v
    npm -v
    ```
    - ウィンドウを閉じて開き直す代わりに、`. $PROFILE`（ドットソース）で現在のセッションにプロファイルを読み直させる
    - `.`（半角ピリオド）と`$PROFILE`の間に半角スペースが必要
    - これで`fnm env`の行が現在のシェルに反映され、`node -v`・`npm -v`が通れば確認完了

## サブ知識
### VS Codeの「pwsh」と「PowerShell Extension」の違い
- pwsh: PowerShell 7そのもの
- PowerShell Extension: VS Codeの拡張機能
    - 下記の機能が拡張されている
        - PowerShellのシンタックスハイライト
        - IntelliSense（補完）
        - デバッグ
        - コマンドレットの補完
        - スクリプト実行
        - PowerShell Language Server