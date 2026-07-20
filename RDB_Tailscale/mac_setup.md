# Mac側セットアップ手順

対象: macOS Tahoe 26.4

前提: [windows_setup.md](./windows_setup.md) が完了し、Windows機のTailscale IP（例: `100.101.102.103`）またはMagicDNS名（例: `desktop-abc123.tailxxxx.ts.net`）を控えていること。

## STEP 1. MacにTailscaleをインストール

App Store経由が最も簡単（アップデートも自動管理される）。

1. Mac App Storeを開き「Tailscale」を検索
2. インストール
3. インストール後、メニューバーにTailscaleアイコンが表示される
4. アイコンをクリック →「Log in...」→ ブラウザが開くので **Windows側と同じアカウント** でログイン
5. 「Connect」を承認 → メニューバーのアイコンが接続済み状態になる

> Homebrewを使う場合は `brew install --cask tailscale-app` でも同じものが入る（cask名は`tailscale`ではなく`tailscale-app`）。

## STEP 2. `tailscale`コマンドをターミナルで使えるようにする（PATHを通す）

STEP1のApp Store経由でインストールした場合、CLIバイナリはアプリ内部に同梱されているだけでターミナルの`PATH`には追加されない。そのため何もせずに`tailscale status`等を実行すると`zsh: command not found: tailscale`になる（GUI上の接続自体はこれで正常に動作しているので心配ない）。

### インストール方法によって対応が変わる

- **App Store版**（STEP1の手順どおりの場合）: CLIはアプリ内部の固定パスにあるので、フルパス実行かエイリアス登録で対応する（下記）
- **スタンドアロン版**（https://tailscale.com/download/mac からdmgを直接ダウンロードした場合）: メニューバーのTailscaleアイコン →「Settings...」→ CLI項目の「Install Now」から`/usr/local/bin/tailscale`にランチャーを自動インストールできる

### App Store版の場合の対応

CLI本体は次の場所にある:

```bash
/Applications/Tailscale.app/Contents/MacOS/Tailscale status
```

毎回フルパスを打ちたくない場合は`~/.zshrc`にエイリアスを追加する:

```bash
echo 'alias tailscale="/Applications/Tailscale.app/Contents/MacOS/Tailscale"' >> ~/.zshrc
source ~/.zshrc
```

新しいターミナルを開くか`source`し直せば、以後は`tailscale status`がそのまま使える。

> エイリアスはインタラクティブシェル専用。スクリプト等の非対話処理から呼びたい場合はシンボリックリンクを使う:
>
> ```bash
> sudo ln -s /Applications/Tailscale.app/Contents/MacOS/Tailscale /usr/local/bin/tailscale
> ```

設定できたら、ターミナルを開き直して以下で確認する:

```bash
which tailscale
tailscale version
```

## STEP 3. 接続確認（重要）

いきなりRDPクライアントを設定する前に、Tailscale同士が疎通しているか確認する。

1. `ターミナル.app` を開く
2. 以下を実行:
   ```bash
   tailscale status
   ```
   Windows機のマシン名が一覧に表示され、行頭にオフラインを示す記号がなければ接続できている
3. 疎通テスト:
   ```bash
   ping 100.101.102.103   # Windows側で控えたTailscale IPに置き換える
   ```
   応答が返ってくればOK（`Ctrl + C`で停止）

うまく通らない場合は「トラブルシューティング」セクションを参照。

## STEP 4. Windows Appをインストール

1. Mac App Storeで「Windows App」を検索
2. インストール（Microsoft公式・無料）
3. アプリを起動

## STEP 5. 接続先(PC)を登録する

1. Windows Appを開き、左上「+」→「PC を追加」
2. 入力項目:
   - **PC name**: Windows側のTailscale IP（例: `100.101.102.103`）またはMagicDNS名
   - **User account**: 「追加」から新規作成し、Windowsのユーザー名とパスワードを入力（毎回入力したくない場合はここで保存）
3. 「Friendly name」に分かりやすい名前（例: `自宅Windows`）を付ける
4. その他の設定項目:
   - **Display**: 外出先の細い回線を考慮し、解像度は「ウィンドウに合わせる」、Retina解像度使用は必要に応じてオフ（帯域節約）
   - **Device & Audio**: 使わないなら音声・クリップボード共有はオフのままでよい（帯域節約）
   - **Performance / Experience**: 接続速度のプロファイルを「低速回線(56kbps~1Mbps相当)」〜「中速(WAN)」から回線状況に応じて選ぶ。外出先のテザリング等、不安定な回線を想定するなら控えめな設定を選んでおくと体感が安定する
5. 保存

## STEP 6. 接続テスト（自宅Wi-Fi内で）

1. 登録したPCのサムネイルをダブルクリック
2. 初回は証明書警告（自己署名証明書のため）が出るので「続ける」「常に接続する」を選択
3. Windowsのログイン画面 → ユーザー名/パスワードでサインイン
4. Windowsのデスクトップが表示されれば成功

## STEP 7. 外出先を想定した接続テスト

1. MacのWi-Fiを切り、iPhoneのテザリング等 **自宅ネットワークと異なる回線** に接続し直す
2. 同様にWindows Appから接続
3. これが問題なく通れば、Tailscaleのメッシュ接続（NAT越え）が正しく機能している証拠

## 接続後の運用メモ

- 終了時は単に「切断」（Windows側の画面右上✕ではなくMac側のウィンドウを閉じる/切断操作）を使うこと。ログオフしてしまうとWindows側の作業状態（開いていたアプリ等）が失われる可能性がある
- 接続を切断してもWindows側のDocker等のプロセスは動き続ける（RDP切断はサインアウトではない）

## トラブルシューティング

| 症状                                   | 確認ポイント                                                                                                                                                                                                           |
| -------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `zsh: command not found: tailscale`    | STEP2を参照。App Store版はCLIがPATHに自動追加されないため、フルパス実行かエイリアス/シンボリックリンクの設定が必要                                                                                                     |
| `tailscale status` にWindows機が出ない | Windows側でTailscaleがログイン済み・起動しているか確認。両者が同じTailscaleアカウント/tailnetに属しているか確認                                                                                                        |
| pingは通るがRDP接続できない            | Windows側のリモートデスクトップ設定(トグル)がオンか再確認。Windows側ファイアウォールの「リモートデスクトップ」ルールが有効か確認                                                                                       |
| 「資格情報が正しくありません」         | Windowsのユーザー名は `PC名\ユーザー名` 形式が必要な場合がある。ローカルアカウントなら単純なユーザー名でよいがMicrosoftアカウント連携の場合はメールアドレスを試す                                                      |
| 外出先だけ繋がらない                   | テザリング元のスマホ側でVPN/専用線的な機能を制限していないか確認。公衆Wi-Fiの場合、UDP/一部ポートをブロックしている場合がありTailscaleがリレー経由(DERP)にフォールバックすることがある（速度は落ちるが接続自体は可能） |
| 画面がカクつく・重い                   | STEP5の「Performance」設定を回線に合わせて下げる。Retina解像度をオフにする                                                                                                                                             |

ここまで完了すれば、Mac→Windowsの遠隔操作環境は完成。
