# Powershell について

## よく使うコマンド
### PowerShellのスペック確認
```pwsh
$PSVersionTable

# バージョンだけを見たいとき
$PSVersionTable.PSVersion
```

### ファイル作成
```pwsh
New
```

### ファイル・フォルダ削除
```pwsh
rm node_modules -Recurse -Force
rm package-lock.json -Force
```