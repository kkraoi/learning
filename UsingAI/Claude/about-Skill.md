# Claude Code Skills ガイド

## Skill とは

Skill は Claude Code の機能を拡張するカスタムスラッシュコマンド。  
`/review` や `/deploy` のように `/コマンド名` で呼び出す。

**通常の Commands との違い**

| 特性 | Skill | Command (.md ファイル) |
|------|-------|----------------------|
| ファイル構造 | ディレクトリ + SKILL.md | 単一の .md ファイル |
| 追加ファイル | scripts/, examples/ 等を同梱可能 | なし |
| Claude の自動実行 | 制御できる | 手動のみ |
| 動的コンテキスト注入 | `` !`command` `` でシェル結果を埋め込み可能 | なし |

---

## 配置場所とスコープ

| パス | 適用範囲 |
|-----|---------|
| `~/.claude/skills/<name>/SKILL.md` | 全プロジェクト共通 |
| `.claude/skills/<name>/SKILL.md` | そのプロジェクトのみ |

ネストしたディレクトリ内で名前が衝突した場合はコマンド名が `apps/web:deploy` のようなパス付きになる。

---

## ファイル構造

```
my-skill/
├── SKILL.md          # 必須：frontmatter + 指示
├── reference.md      # 任意：詳細リファレンス
└── scripts/
    └── helper.py     # 任意：補助スクリプト
```

---

## Skill の作り方（手順）

### 1. ディレクトリを作成する

```bash
# グローバル（全プロジェクトで使いたい場合）
mkdir -p ~/.claude/skills/summarize-changes

# プロジェクト固有
mkdir -p .claude/skills/summarize-changes
```

### 2. SKILL.md を作成する

```markdown
---
name: summarize-changes
description: 現在の Git 差分を要約し、リスクをフラグする
---

## 現在の差分

!`git diff HEAD`

## 指示

上の差分を2〜3行の箇条書きで要約します。
エラーハンドリング漏れ、ハードコード値、テスト更新が必要な箇所があればリストアップします。
```

### 3. 動作確認する

```
# Claude Code セッション内で
/summarize-changes        # 直接呼び出し

# または Claude に自然言語で話しかけると自動呼び出しされる場合もある
What did I change?
```

---

## SKILL.md の frontmatter フィールド

```yaml
---
name: my-skill                     # コマンド名（省略時はディレクトリ名）
description: 何をするスキルか        # Claude の自動呼び出し判断にも使われる
argument-hint: "[issue-number]"    # オートコンプリート時のヒント
arguments: [component, target]     # 位置引数の名前（$0, $1 で展開）
disable-model-invocation: false    # true にすると Claude の自動実行を禁止
user-invocable: true               # false にすると / メニューに表示されない
allowed-tools: "Bash(git *) Read"  # このスキル実行中に許可するツール
disallowed-tools: "AskUserQuestion"
model: claude-opus-4               # このスキル実行時に使うモデル
effort: high                       # 推論レベル: low / medium / high / max
context: fork                      # fork にすると subagent で実行
agent: Explore                     # context: fork 時の subagent タイプ
paths: ["src/**/*.ts"]            # このパスのファイル編集時に自動呼び出し
shell: powershell                  # !`command` で使うシェル（デフォルト: bash）
---
```

### 呼び出し制御の早見表

| 設定 | ユーザーが呼び出せる | Claude が自動実行 |
|------|:---:|:---:|
| デフォルト | ✓ | ✓ |
| `disable-model-invocation: true` | ✓ | ✗ |
| `user-invocable: false` | ✗ | ✓ |

---

## 動的コンテキスト注入（`` !`command` ``）

Skill が Claude に送られる前にシェルコマンドを実行し、結果を埋め込む機能。

**インライン形式**

```markdown
## PR 情報
- 差分: !`gh pr diff`
- コメント: !`gh pr view --comments`
```

**マルチライン形式**

````markdown
## 環境
```!
node --version
npm --version
git status --short
```
````

---

## 引数の使い方

```yaml
---
arguments: [component, from-fw, to-fw]
---

$0 を $1 から $2 に移行します。
```

呼び出し：`/migrate-component SearchBar React Vue`

**特別な変数**

| 変数 | 内容 |
|-----|------|
| `$ARGUMENTS` | 全引数（文字列） |
| `$0`, `$ARGUMENTS[0]` | 最初の引数 |
| `$1`, `$ARGUMENTS[1]` | 2番目の引数 |
| `${CLAUDE_SKILL_DIR}` | SKILL.md があるディレクトリのパス |
| `${CLAUDE_EFFORT}` | 現在の推論レベル |

---

## Subagent で実行する（`context: fork`）

Skill をメイン会話と切り離した独立 agent で実行できる。  
大規模な調査や分析に向いている。

```yaml
---
description: コードを詳しく調査する
context: fork
agent: Explore
---

$ARGUMENTS に関連するファイルを Glob と Grep で探し、
コードを読み込んでサマリーとファイル参照をまとめます。
```

---

## 設定で Skill を制御する（`skillOverrides`）

`.claude/settings.local.json` で個別に制御できる：

```json
{
  "skillOverrides": {
    "heavy-context-skill": "name-only",
    "legacy-skill": "off"
  }
}
```

| 値 | Claude に見える | / メニューに出る |
|---|:---:|:---:|
| `"on"`（デフォルト） | 名前 + 説明 | ✓ |
| `"name-only"` | 名前のみ | ✓ |
| `"user-invocable-only"` | なし | ✓ |
| `"off"` | なし | ✗ |

---

## ベストプラクティス

- `description` は Claude がキーワードマッチで判断するので具体的に書く
- Skill 本体は 500 行以下に収め、詳細は別ファイルへ
- デプロイ等の副作用がある操作は `disable-model-invocation: true` をつける
- 引数は 1〜3 個が実用的。複雑な入力は会話で聞く方が良い
- `allowed-tools` は必要最小限に絞る

---

## よくあるトラブル

| 問題 | 確認ポイント |
|-----|------------|
| Skill が `/ メニュー` に出ない | パスが `skills/<name>/SKILL.md` になっているか確認 |
| Claude が自動呼び出ししない | `description` のキーワードを具体的にする |
| 権限エラーが出る | `.claude/` フォルダの信頼を許可する（プロジェクト初回） |
| `!` コマンドが実行されない | `disable-model-invocation` や管理者設定を確認 |

Skillという機能についてですが、下記の使い方は可能なのでしょうか？ /.claude/skills/mentor/Skill.md を作って、メンターのようにふるまってもらう これを作った場合、セッションでは会話でどの様に記述したら使えるのでしょうか