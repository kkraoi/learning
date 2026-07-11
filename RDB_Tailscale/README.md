# Tailscale + RDP でWindowsをMacから遠隔操作する

## 目的

高性能Windows機（Docker・重量級ブラウジング等）を、出先（スーパー銭湯など）から低スペックMacで遠隔操作する。
完全無料構成（Tailscale Personal + Windows標準RDP + Microsoft Remote Desktop）。

## 全体構成

```
[Mac]                          [Windows 11 Pro]
 Tailscale (クライアント)  <-- 暗号化P2P -->  Tailscale (クライアント)
 Microsoft Remote Desktop  ---- RDP接続 ---->  リモートデスクトップ(有効化)
   ↑                                              ↑
 スーパー銭湯のWi-Fi/テザリング              自宅ネット(常時起動)
```

- Tailscaleが両端末間にプライベートな仮想LAN（メッシュVPN）を作る。ポート開放・固定IP・DDNS設定は不要。
- Windows側のRDPポートはインターネットに直接晒さない（Tailscaleネットワーク内からのみ到達可能にする）。
- 実際の画面転送はRDPが担当する。

## 前提条件

- Windows 11 **Pro**（RDPホスト機能はPro/Enterprise限定。Home不可）
- Windows側は外出中も起動しっぱなし（スリープ無効化が必要）
- 両端末に同じTailscaleアカウント（GoogleアカウントやMicrosoftアカウントでOK）でログインする

## 作業の流れ

1. [windows_setup.md](./windows_setup.md) — Windows側の設定（Tailscale導入＋RDP有効化）
2. [mac_setup.md](./mac_setup.md) — Mac側の設定（Tailscale導入＋Remote Desktopで接続）
3. 最後に外出先を想定し、自宅Wi-Fiを切ってスマホのテザリングなど別回線から接続テストを行う

## 実施順序の注意

**必ずWindows側を先に完了させてから**Mac側の接続作業に進むこと（接続先のTailscale IP/ホスト名がWindows側の作業で確定するため）。
