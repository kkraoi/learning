# Typescriptについて

## keyof typeof とは
- オブジェクトからキー名を型として取り出す組み合わせ
    - オブジェクトをタイプにして、キーの型を取り出す
- オブジェクトのキーが増減・変更されても、型定義を手で書き換えなくて済むようにするために使う

### 使い方
```tsx
type Colors = {
  light: {
    text: '#000',
    background: '#fff',
    tint: '#2f95dc',
    border: ...
  },
  dark: {
    text: '#fff',
    background: '#000',
    tint: '#fff'
  }
}

colorName: typeof Colors.light
// => colorName: {
//     text: string;
//     background: string;
//     tint: string;
//     border: string;
// }
// オブジェクトをタイプオブジェクトとして取り出せる

colorName: keyof typeof Colors.light
// => colorName: "text" | "background" | "tint" | "border"

colorName: keyof typeof Colors.light & keyof typeof Colors.dark
// => colorName: "text" | "background" | "tint"
// & で積集合のため、light と dark の共通を取り出す
```

## ?:
- このプロパティは省略しても良いという意味になる（オプション化）

### 使い方
```tsx
props: { light?: string; dark?: string }

↑これに近い
props: {
  light: string | undefined;
  dark: string | undefined;
}
```

## NonNullable<T>
-  型 T から null と undefined を取り除くTypeScript標準のユーティリティ型

### 使い方
```ts
type A = string | null | undefined;

type B = NonNullable<A>;
// => type B = string;
```