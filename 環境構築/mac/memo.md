# 環境構築メモ
## Android Studio
1. SDK Manager > Languages & Frameworks > Android SDK
3. SDK Tools から下記をチェック（されているか確認）
  - Android SDK Build-Tools
  - Android Emulator
  - Android SDK Platform-Tools
4. Android SDK Location: からパスをコピーする
5. `vim ~/.zshrc`でファイル編集。下記を追記
  ```sh
  # Android Studio
  <!-- export ANDROID_HOME=Android SDK Location からのパス -->　# Users/user は$HOMEで代入できるので↓
  export ANDROID_HOME=$HOME/Library/Android/sdk
  export PATH=$ANDROID_HOME/platform-tools:$PATH
  export PATH=$ANDROID_HOME/emulator:$PATH
  ```
  - a でインサート
  - esc でキー入力
  - :wq で保存
  - :q で保存せず終了
6. 一度ターミナルを閉じて、開き直し、`echo $ANDROID_HOME`・`echo $Path`が通っているか確認する
7. コマンドラインでエミュレーターを立ち上げる
  - `emulator -list-avds` で仮想端末をリストアップ
  - `emulator @仮想端末`でエミュレーター起動