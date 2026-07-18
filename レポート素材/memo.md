# React Native, Expo カリキュラムのレポート
- 知らなかったこと・覚えたいことを記載する

## VSCode
### shell command "code"をインストール
- cntl + shift + P で shellを検索して、インストールする
- `code`とターミナルで実行するとvscodeが起動する

## Expoアプリの始め方
```sh
# プロジェクトの立ち上げ
npx create-expo-app プロジェクト名

cd プロジェクト名

# アプリ起動
npx expo start

# 起動中のターミナルでwを押下するとローカルホストでアプリが立ち上がる、べんり。
```

## ReactNative
### コンポーネントの基本形
src/components/ の中にコンポーネントを作るのが基本。
```tsx
import { View, Text, StyleSheet } from 'react-native';

const Hello = () => {
    return (
        <View>
            <Text>
                Hello
            </Text>
        </View>
    )
}

export default Hello;

```

## FireBase
### カリキュラムで使用するサービス
- オーセンティケーション: ログイン・会員登録
- FireStore: データベース

### プロジェクトの始め方
1. https://firebase.google.com/?hl=ja へアクセス、「コンソールへ移動」
2. プロジェクトを作成にて、プロジェクトの名前をつける
3. （カリキュラムではアナリティクスは無効にした）
4. スパークプランでプロジェクトができる
5. 「アプリを追加」を押下、カリキュラムでは「ウェブ」を選択
6. ニックネームを記入。アプリを登録を押下。（「Firebase Hosting」のチェックはずした）
    - Firebase Hosting はウェブサイトをホスティングできる
7. `const firebase config = `の情報をメモ
