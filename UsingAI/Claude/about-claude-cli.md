# Claude Code CLI ガイド

Claude Code はターミナルから使える AI コーディングアシスタント。コードベースの理解・編集・テスト・デプロイを自然言語で操作できる。

---

## 導入方法（Windows 11 Pro）

### 前提条件

| 項目 | 要件 |
|------|------|
| OS | Windows 10 1809 以降（Windows 11 Pro 対応） |
| アカウント | Claude Pro / Max / Teams / Enterprise、または Anthropic Console |
| Git（任意） | Bash ツールを使う場合に必要 |

> **注意：** 無料の Claude アカウントでは Claude Code は利用できない。

### 1. インストール

**PowerShell で実行（推奨）：**

```powershell
irm https://claude.ai/install.ps1 | iex
```

Node.js は不要。バイナリが `%USERPROFILE%\.local\bin` にインストールされる。

**代替方法：**

```powershell
# WinGet を使う場合
winget install Anthropic.ClaudeCode

# npm を使う場合（Node.js 18 以上が必要）
npm install -g @anthropic-ai/claude-code
```

### 2. パスを通す

インストーラーが PATH を自動設定するが、反映されないことがある。以下のどちらかの方法で追加し、PowerShell を再起動する。

**PowerShell で追加する場合：**

```powershell
[Environment]::SetEnvironmentVariable("PATH", $env:PATH + ";$env:USERPROFILE\.local\bin", "User")
```

**GUI で追加する場合（Windows 11）：**

1. `Win + I` → 「システム」→「バージョン情報」→「システムの詳細設定」を開く
2. 「環境変数」ボタンをクリック
3. 「ユーザー環境変数」の一覧から `Path` を選択し「編集」をクリック
4. 「新規」をクリックして以下を入力：
   ```
   %USERPROFILE%\.local\bin
   ```
5. 「OK」で閉じる

### 3. 動作確認

```powershell
claude --version
claude doctor   # 環境診断
```

### 初回認証

```powershell
cd C:\path\to\your\project
claude
```

初回起動時にブラウザが自動で開き、Claude.ai のアカウントでログインする。認証情報は `%USERPROFILE%\.claude\.credentials.json` に保存され、次回以降は不要。

ブラウザが開かない場合：プロンプト上の URL をコピーしてブラウザに手動で貼り付け、表示された認証コードをターミナルに入力する。

### Git for Windows（任意）

Bash ツールを使う場合はインストールする。

```powershell
winget install Git.Git
```

カスタムパスにインストールした場合は `%USERPROFILE%\.claude\settings.json` に以下を追加：

```json
{
  "env": {
    "CLAUDE_CODE_GIT_BASH_PATH": "C:\\Program Files\\Git\\bin\\bash.exe"
  }
}
```

### トラブルシューティング

**TLS エラーが出る場合：**

```powershell
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
irm https://claude.ai/install.ps1 | iex
```

**プロキシ環境下：**

```powershell
$env:HTTPS_PROXY = 'http://proxy.example.com:8080'
irm https://claude.ai/install.ps1 | iex
```

### アンインストール

```powershell
# Native Install の場合
Remove-Item -Path "$env:USERPROFILE\.local\bin\claude.exe" -Force
Remove-Item -Path "$env:USERPROFILE\.local\share\claude" -Recurse -Force

# WinGet の場合
winget uninstall Anthropic.ClaudeCode

# npm の場合
npm uninstall -g @anthropic-ai/claude-code

# 設定ファイルも削除する場合（全設定が消える）
Remove-Item -Path "$env:USERPROFILE\.claude" -Recurse -Force
```

---

## 起動方法

```bash
claude                    # インタラクティブモード
claude "質問"             # 初期プロンプト付きで起動
claude -p "質問"          # 非インタラクティブ実行（結果を出力して終了）
claude -c                 # 最新の会話を再開
claude -r "セッション名"  # 指定セッションを再開
claude -n "タスク名"      # セッション名を付けて起動
```

---

## スラッシュコマンド一覧

セッション内で `/コマンド名` と入力して実行する。

### セッション管理

| コマンド | 説明 |
|---------|------|
| `/clear [名前]` | 新規会話を開始 |
| `/resume [セッション]` | セッションを再開 |
| `/branch [名前]` | 現在の会話を分岐して別の方向を試す |
| `/rename [名前]` | セッション名を変更 |
| `/exit` | セッション終了（`Ctrl+D` でも可） |

### コンテキスト・メモリ

| コマンド | 説明 |
|---------|------|
| `/context [all]` | コンテキスト使用量を表示 |
| `/compact [指示]` | 会話を要約してコンテキストを解放 |
| `/memory` | CLAUDE.md の編集・自動メモリ管理 |
| `/init` | CLAUDE.md テンプレートを生成 |

### モデル・実行制御

| コマンド | 説明 |
|---------|------|
| `/model [モデル名]` | 使用モデルを切り替え（例: `/model sonnet`） |
| `/effort [レベル]` | 思考深度を調整（low / medium / high / xhigh / max） |
| `/fast [on\|off]` | ファストモードを切り替え |
| `/advisor [モデル\|off]` | アドバイザー機能（別モデルがレビュー） |

### 設定・ユーティリティ

| コマンド | 説明 |
|---------|------|
| `/help` | 利用可能コマンドを表示 |
| `/config` / `/settings` | 設定インターフェースを開く |
| `/permissions` | パーミッション設定を管理 |
| `/theme` | カラーテーマを変更 |
| `/keybindings` | キーバインディング設定 |
| `/hooks` | フック設定を表示 |
| `/mcp` | MCP サーバーを管理 |
| `/usage` | セッションのコスト・使用量を表示 |
| `/doctor` | 環境・設定を診断 |

### コード操作

| コマンド | 説明 |
|---------|------|
| `/diff` | 変更内容を表示 |
| `/code-review [難度]` | コードレビューを実行（low / medium / high / ultra） |
| `/simplify` | コードを簡潔にリファクタ |
| `/security-review` | セキュリティレビューを実行 |
| `/run` | アプリを起動して動作確認 |
| `/verify` | コード変更の動作確認 |

### 並列処理・自動化

| コマンド | 説明 |
|---------|------|
| `/batch <指示>` | 大規模変更を複数ワーカーで並列実行 |
| `/background [プロンプト]` | セッションをバックグラウンド化 |
| `/tasks` | バックグラウンドのタスク一覧を表示 |
| `/loop [間隔] [プロンプト]` | 定期実行（例: `/loop 5m /code-review`） |
| `/schedule [説明]` | クラウドで定時実行ルーチンを作成 |

---

## CLIフラグ一覧

`claude [フラグ] "プロンプト"` の形式で起動時に指定する。

### セッション制御

| フラグ | 説明 |
|--------|------|
| `-p, --print` | 非インタラクティブ実行 |
| `-c, --continue` | 最新の会話を再開 |
| `-r, --resume` | セッション ID / 名で再開 |
| `-n, --name` | セッション名を設定 |
| `-w, --worktree` | 別の git worktree で実行 |
| `--bg, --background` | バックグラウンド実行 |
| `--max-turns` | 最大ターン数を制限 |
| `--max-budget-usd` | 利用予算の上限を設定 |

### モデル・パフォーマンス

| フラグ | 説明 |
|--------|------|
| `--model` | モデルを指定（例: `--model claude-sonnet-4-6`） |
| `--effort` | 思考深度を指定（low / medium / high / xhigh / max） |
| `--fast` | ファストモードを有効化 |
| `--advisor` | アドバイザーモデルを指定 |

### パーミッション・セキュリティ

| フラグ | 説明 |
|--------|------|
| `--permission-mode` | デフォルトモードを指定（下記参照） |
| `--allowedTools` | 許可するツールを指定 |
| `--disallowedTools` | 禁止するツールを指定 |
| `--dangerously-skip-permissions` | すべてのパーミッション確認をスキップ |

**パーミッションモード一覧：**

| モード | 動作 |
|--------|------|
| `default` | 毎回確認（デフォルト） |
| `acceptEdits` | ファイル編集を自動承認 |
| `plan` | 計画を実行前に確認 |
| `auto` | 安全な操作は自動実行 |
| `bypassPermissions` | すべてスキップ（起動フラグが必要） |

### システムプロンプト

| フラグ | 説明 |
|--------|------|
| `--system-prompt "..."` | システムプロンプトを置換 |
| `--system-prompt-file ./file` | ファイルからシステムプロンプトを読み込み |
| `--append-system-prompt "..."` | システムプロンプトに追記 |

### 出力

| フラグ | 説明 |
|--------|------|
| `--output-format` | 出力形式を指定（text / json / stream-json） |
| `--verbose` | 詳細ログを出力 |
| `--debug` | デバッグモードを有効化 |

### その他

| フラグ | 説明 |
|--------|------|
| `--version, -v` | バージョンを表示 |
| `--safe-mode` | カスタマイズを無効化して起動 |
| `--add-dir` | 追加の作業ディレクトリを指定 |
| `--mcp-config` | MCP サーバー設定 JSON を指定 |
| `--settings` | 設定ファイルパスを指定 |

---

## キーボードショートカット

| キー | 動作 |
|------|------|
| `Enter` | メッセージ送信 |
| `Ctrl+J` | 改行（送信しない） |
| `Ctrl+C` | 現在の操作を中止 |
| `Ctrl+D` | セッション終了 |
| `Ctrl+R` | コマンド履歴を検索 |
| `Ctrl+L` | 画面をクリア・再描画 |
| `Ctrl+O` | トランスクリプト表示を切り替え |
| `Ctrl+T` | タスクリスト表示を切り替え |
| `Shift+Tab` | パーミッションモードを切り替え |
| `Cmd+P` / `Meta+P` | モデルピッカーを開く |
| `Cmd+O` / `Meta+O` | ファストモードを切り替え |
| `Cmd+T` / `Meta+T` | 拡張思考を切り替え |
| `Ctrl+G` | 外部エディタで編集 |
| `Tab` | オートコンプリートを受け入れ |
| `↑` / `↓` | コマンド履歴を移動 |

---

## 設定ファイル

### 配置場所と優先順位（高→低）

| スコープ | パス | 説明 |
|---------|------|------|
| マネージド | OS 設定ディレクトリ | 組織管理（上書き不可） |
| ローカル | `.claude/settings.local.json` | プロジェクト個人設定（gitignore） |
| プロジェクト | `.claude/settings.json` | プロジェクト共有設定（git コミット可） |
| グローバル | `~/.claude/settings.json` | 全プロジェクト共通 |

マネージド設定の場所:
- macOS: `/Library/Application Support/ClaudeCode/`
- Linux: `/etc/claude-code/`
- Windows: `C:\Program Files\ClaudeCode\`

### 主要な設定項目

```json
{
  "model": "claude-sonnet-4-6",
  "effortLevel": "high",
  "defaultPermissionMode": "default",
  "autoMemoryEnabled": true,
  "theme": "dark",
  "permissions": {
    "allow": [
      "Bash(npm run test *)",
      "Edit(src/**/*.ts)"
    ],
    "deny": [
      "Read(.env)"
    ]
  },
  "env": {
    "NODE_ENV": "development"
  }
}
```

---

## MCP（Model Context Protocol）

外部ツール・サービスを Claude Code に統合するプロトコル。

### サーバーの追加

```bash
# HTTP サーバー（推奨）
claude mcp add --transport http <名前> <URL>

# ローカル stdio サーバー
claude mcp add --transport stdio <名前> -- npx <パッケージ>

# 環境変数付き
claude mcp add --env API_KEY=xxx --transport stdio <名前> -- npx <パッケージ>
```

### MCP 管理コマンド

| コマンド | 説明 |
|---------|------|
| `claude mcp list` | 登録済みサーバー一覧 |
| `claude mcp get <名前>` | サーバー詳細を表示 |
| `claude mcp remove <名前>` | サーバーを削除 |
| `claude mcp login <名前>` | OAuth 認証 |

### スコープ

| スコープ | 保存先 | 用途 |
|---------|------|------|
| local（デフォルト） | `~/.claude.json` | このプロジェクトのみ |
| project | `.mcp.json` | チーム共有（git コミット） |
| user | `~/.claude.json` | すべてのプロジェクト |

---

## Hooks（フック）

Claude Code のライフサイクルの特定タイミングでシェルコマンドを自動実行する機能。設定ファイルの `hooks` キーに定義する。

### フックイベント

| イベント | 発火タイミング | 主な用途 |
|---------|----------------|---------|
| `SessionStart` | セッション開始・再開時 | 環境の初期化 |
| `PreToolUse` | ツール実行前 | 実行の許可・ブロック |
| `PostToolUse` | ツール実行後 | 自動フォーマット |
| `PermissionRequest` | 権限ダイアログ発生時 | 自動承認・拒否 |
| `Notification` | 通知発生時 | デスクトップ通知 |
| `Stop` | Claude の応答終了時 | 条件判定・継続指示 |
| `CwdChanged` | ディレクトリ変更時 | 環境変数の再読み込み |
| `SessionEnd` | セッション終了時 | クリーンアップ |

### 設定例

```json
{
  "hooks": {
    "PostToolUse": [
      {
        "matcher": "Edit|Write",
        "hooks": [
          {
            "type": "command",
            "command": "npx prettier --write \"$CLAUDE_TOOL_INPUT_FILE_PATH\""
          }
        ]
      }
    ],
    "Notification": [
      {
        "matcher": "",
        "hooks": [
          {
            "type": "command",
            "command": "osascript -e 'display notification \"Claude が確認を求めています\" with title \"Claude Code\"'"
          }
        ]
      }
    ]
  }
}
```

### フックの終了コード

| コード | 意味 |
|--------|------|
| 0 | 成功・処理継続 |
| 2 | ブロック（アクション中止） |
| その他 | エラー（処理は継続） |
