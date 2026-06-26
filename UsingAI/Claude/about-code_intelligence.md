# Code Intelligence ガイド

Claude Code がコードを「理解する」仕組みと、IDE と連携してコード品質を高める機能のまとめ。

---

## Code Intelligence とは

Claude Code における Code Intelligence とは、単なるテキスト補完ではなく、**コードベース全体を文脈として把握しながら開発を支援する機能群**のこと。

主な構成要素：

| 機能 | 概要 |
|------|------|
| コードベース理解 | ファイルをまたいでコード全体を把握 |
| 診断情報の取得 | IDE の言語サーバー（LSP）のエラー・警告を参照 |
| 選択コンテキスト共有 | エディタで選択中のコードを自動的に Claude に渡す |
| @メンション参照 | ファジーマッチでファイルやシンボルを指定 |
| インライン Diff | 編集前後の差分をエディタ内で確認 |

---

## コードベース理解

Claude Code はセッション開始時にプロジェクト全体を読み取り、複数ファイルにまたがる変更や参照解決を行う。

- 単一ファイルではなくプロジェクト全体を文脈として保持
- `CLAUDE.md` にアーキテクチャ方針・コーディング規約を書くことで理解精度が向上する
- 長いセッションでは `/compact` で文脈を圧縮して効率化できる

---

## IDE 診断機能（getDiagnostics）

VS Code 拡張機能が有効なとき、Claude は IDE の **Problems パネルに表示されているエラー・警告**を直接参照できる。

```
内部ツール名: mcp__ide__getDiagnostics
```

- 対象ファイルを絞り込んでスコープ指定も可能
- 型エラー・lint エラーなど言語サーバーが検出した問題を Claude に渡して修正依頼できる

**使い方の例：**

```text
> Problems パネルの TypeScript エラーをすべて修正して
> auth.ts の lint エラーだけ直して
```

---

## TypeScript プラグイン導入ハンズオン

VS Code + TypeScript 環境を整えると、`getDiagnostics` が型エラーを Claude に渡せるようになる。

### 前提条件

- Node.js（LTS 版）がインストール済みであること
- VS Code がインストール済みであること
- Claude Code VS Code 拡張機能が有効であること

### 1. TypeScript をインストールする

```bash
# プロジェクトローカルにインストール（推奨）
npm init -y
npm install --save-dev typescript

# バージョン確認
npx tsc --version
```

### 2. VS Code の TypeScript 拡張機能を確認する

VS Code には **TypeScript 言語サービス**（`vscode.typescript-language-features`）が組み込み済みのため、追加インストールは不要。

確認手順：
1. `Ctrl+Shift+X` で拡張機能パネルを開く
2. `@builtin typescript` と検索
3. **TypeScript and JavaScript Language Features** が有効になっていることを確認

> プロジェクトローカルの TypeScript を使わせるには、後述の `tsconfig.json` が必要。

### 3. tsconfig.json を作成する

プロジェクトルートで以下を実行：

```bash
npx tsc --init
```

生成された `tsconfig.json` を目的に合わせて最低限編集する：

```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "commonjs",
    "strict": true,
    "outDir": "./dist",
    "rootDir": "./src"
  },
  "include": ["src/**/*"]
}
```

`strict: true` を有効にすると、型エラーを最大限に検出できるため `getDiagnostics` との相性が良くなる。

### 4. 動作確認

1. `src/index.ts` を作成して意図的に型エラーを書く

    ```typescript
    const x: number = "hello"; // 型エラー
    ```

2. VS Code の **Problems パネル**（`Ctrl+Shift+M`）にエラーが表示されることを確認する
3. ステータスバー右下の TypeScript バージョン表示（例：`TS 5.x.x`）を確認する

### 5. Claude Code から診断情報を渡す

Problems パネルにエラーが出ている状態で、Claude Code のプロンプトに以下のように入力する：

```text
> TypeScript のエラーをすべて修正して
> src/index.ts の型エラーだけ直して
```

Claude は `getDiagnostics` 経由で VS Code の型エラー一覧を取得し、修正案を提示する。

### トラブルシューティング

| 症状 | 対処 |
|------|------|
| Problems パネルに何も表示されない | `tsconfig.json` の `include` パスを確認 |
| TS バージョンが古い | ステータスバーの `TS x.x.x` をクリック→「Select TypeScript Version」でワークスペース版を選択 |
| `tsc` コマンドが見つからない | `npm install --save-dev typescript` を再実行 |
| 診断が Claude に届かない | `/ide` を実行して VS Code との接続を確認 |

---

## Java プラグイン導入ハンズオン

VS Code + Java 環境を整えると、`getDiagnostics` がコンパイルエラーや警告を Claude に渡せるようになる。

### 前提条件

- JDK 17 以上がインストール済みであること（`java -version` で確認）
- VS Code がインストール済みであること
- Claude Code VS Code 拡張機能が有効であること

JDK が未インストールの場合：[https://adoptium.net/](https://adoptium.net/) から Temurin 版を取得する。

### 1. Extension Pack for Java をインストールする

`Ctrl+Shift+X` で拡張機能パネルを開き、以下を検索してインストールする：

```
Extension Pack for Java  （vscjava.vscode-java-pack）
```

このパックには以下が含まれる：

| 拡張機能 | 役割 |
|---------|------|
| Language Support for Java (Red Hat) | LSP による型チェック・補完 |
| Debugger for Java | ブレークポイント・ステップ実行 |
| Test Runner for Java | JUnit / TestNG の実行 |
| Maven for Java | Maven プロジェクト管理 |
| Project Manager for Java | プロジェクトナビゲーター |

### 2. JAVA_HOME を設定する（必要な場合）

VS Code が JDK を見つけられない場合、`settings.json` に以下を追加する：

```json
{
  "java.jdt.ls.java.home": "C:\\Program Files\\Eclipse Adoptium\\jdk-17.x.x"
}
```

設定手順：`Ctrl+Shift+P` → `Open User Settings (JSON)` → 上記を追記。

### 3. プロジェクトを作成する

**Maven プロジェクトの場合（推奨）：**

```bash
mvn archetype:generate \
  -DgroupId=com.example \
  -DartifactId=my-app \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
```

VS Code でフォルダを開く（`Ctrl+K Ctrl+O`）と、Java プロジェクトとして自動認識される。

**単一ファイルの場合（最小確認用）：**

`Hello.java` を任意のフォルダに作成して VS Code で開くだけでよい。

### 4. 動作確認

1. `src/main/java/com/example/App.java` を開く
2. 意図的に型エラーを書く

    ```java
    public class App {
        public static void main(String[] args) {
            int x = "hello"; // 型エラー
        }
    }
    ```

3. VS Code の **Problems パネル**（`Ctrl+Shift+M`）にエラーが表示されることを確認する
4. ステータスバーに Java 言語サービスのロード状態が表示される（初回は数十秒かかる）

### 5. Claude Code から診断情報を渡す

Problems パネルにエラーが出ている状態で Claude Code に入力する：

```text
> Java のコンパイルエラーをすべて修正して
> App.java のエラーだけ直して
```

### トラブルシューティング

| 症状 | 対処 |
|------|------|
| ステータスバーに「Java」が出ない | `vscjava.vscode-java-pack` が有効か確認。再起動も試す |
| 「JDK not found」エラー | `java.jdt.ls.java.home` にフルパスを指定する |
| 初回起動が遅い | Java Language Server の初期化中のため待つ（完了まで 1〜2 分） |
| Maven プロジェクトが認識されない | `pom.xml` があるフォルダをルートとして開く |
| 診断が Claude に届かない | `/ide` を実行して VS Code との接続を確認 |

---

## 選択コンテキスト共有

VS Code でコードを選択すると、プロンプトボックスのフッターに選択行数が表示され、Claude は自動的にその内容を受け取る。

| 操作 | 動作 |
|------|------|
| コードを選択してプロンプト送信 | 選択範囲が自動的にコンテキストに含まれる |
| `Option+K` / `Alt+K` | `@ファイル名#行番号` 形式の参照をプロンプトに挿入 |
| 選択インジケーターをクリック | 選択をコンテキストから除外する（スラッシュアイコン） |

`.env` など機密ファイルを Read の deny ルールに追加すれば、選択内容が Claude に渡るのを防げる。

---

## @メンション によるシンボル参照

`@` に続けてファイル名やフォルダ名を入力することで、Claude に読み込ませる対象を明示的に指定できる。

```text
@auth.ts         → auth.ts を読み込む
@auth            → auth.js / AuthService.ts など fuzzy match
@src/components/ → フォルダ配下をまとめて参照（末尾 / が必要）
@app.ts#5-10     → 5〜10 行目だけを参照
```

大きな PDF は `pages 1-10` のようにページ範囲を指定して読み込ませることも可能。

---

## インライン Diff（編集の可視化）

Claude がファイルを編集しようとするとき、VS Code では変更前・変更後の差分がサイドバイサイドで表示される。

- **Accept** → 変更を適用
- **Reject** → 変更を破棄
- Diff ビュー内を直接編集してから Accept すると、Claude はその修正を認識して以降の提案に反映する

---

## チェックポイント（変更の巻き戻し）

VS Code 拡張機能はファイル編集の履歴（チェックポイント）を自動的に記録する。

| 操作 | 内容 |
|------|------|
| Fork conversation from here | 会話を分岐（コード変更は保持） |
| Rewind code to here | ファイルを指定時点に巻き戻す（会話履歴は保持） |
| Fork conversation and rewind code | 会話とコードの両方を分岐・巻き戻し |

メッセージにカーソルを合わせると巻き戻しボタンが表示される。

---

## Jupyter Notebook 対応

VS Code 拡張機能が有効な場合、Python コードをノートブックのカーネルで実行できる。

```
内部ツール名: mcp__ide__executeCode
```

実行前に必ず VS Code 側のダイアログで確認（Execute / Cancel）が求められる。サイレント実行はできない。

前提条件：
- Jupyter 拡張機能（`ms-toolsai.jupyter`）がインストール済み
- アクティブなノートブックとカーネルが存在する
- カーネルが Python であること

---

## IDE MCP サーバーの仕組み

VS Code 拡張機能がアクティブなとき、内部で **ローカル MCP サーバー**（`ide`）が起動する。CLI はこのサーバーを通じて IDE と通信する。

- `127.0.0.1` のランダムポートにバインド（外部からアクセス不可）
- 起動のたびに新しい認証トークンを生成（`~/.claude/ide/` に `0600` 権限で保存）
- CLI 上で `/ide` を実行することで、外部ターミナルからでも VS Code に接続できる

---

## JetBrains 対応

IntelliJ IDEA・PyCharm・WebStorm などにも対応。

- インタラクティブな Diff ビュー
- 選択コンテキスト共有
- Claude Code CLI の別途インストールが必要

---

## まとめ

```
コードベース理解 ──→ 全ファイルを文脈として保持
診断情報取得    ──→ IDE の LSP エラー・警告を参照して修正
選択コンテキスト ──→ 選択行を自動送信 / @メンションで明示指定
インライン Diff  ──→ 変更前後を視覚的に確認してから適用
チェックポイント ──→ 変更を特定時点に巻き戻し可能
```

Claude Code の Code Intelligence は、IDE の言語サーバーと連携することで、単なるコード生成ではなく**現在のプロジェクト状態を把握した上での修正・改善**を実現している。

---

*参考：[Claude Code IDE integrations](https://code.claude.com/docs/en/ide-integrations) / [Overview](https://code.claude.com/docs/en/overview)*
