# AWSについて

### マシンイメージAMI


### インスタンスタイプ
=> マシンの性能（CPU、メモリ、ネット性能）の品定め

### 開発インスタンスを通して、本番環境にログイン
```
ssh -i ~/.ssh/practice-aws.pem ec2-user@本番環境パブリックIPアドレス
```
鳥が返ってきたらログイン成功

### puma起動確認
プロセスの確認方法
```
ps aux | grep puma
```

## 開発環境デプロイ
AWSインスタンス画面より、開発環境インスタンスを起動するだけ。

## 開発環境デプロイ停止
1. pumaを停止
2. AWSインスタンス画面より、開発環境インスタンスを停止

### puma停止
開発環境のアプリ階層にて、
```
sudo kill $(cat tmp/pids/puma.pid)
```

### puma開始
```
rails s -e production
```

## エラーが出たら
### nginxのエラー
```
sudo tail /var/log/nginx/error.log
```

### railsのエラー
```
sudo tail -f log/production.log
```

## npmビルドをしたら
```
 RAILS_ENV=production bundle exec rails assets:clobber <= コンパイル済みのアセットを削除
 yarn install
 RAILS_ENV=production bundle exec rails assets:precompile
```

```
RAILS_ENV=production bundle exec rails assets:clobber

↓　成功したら

I, [2025-06-19T07:17:06.417286 #3749]  INFO -- : Removed /home/ec2-user/アプリ名/public/assets
Removed webpack output path directory /home/ec2-user/アプリ名/public/packs
```

```
RAILS_ENV=production bundle exec rails assets:precompile

↓　成功したら

Compiled all packs in /home/ec2-user/アプリ名/public/packs
```

## Gemfileの変更をしたら
```
bundle install --path vendor/bundle --without test development
```

## マイグレーションファイルの変更／追加
```
RAILS_ENV=production bundle exec rails db:migrate
```

## シードを作るとき
```
RAILS_ENV=production bundle exec rails db:seed
```

## データベースをリセット
```
[ec2-user@ip-xx-xx-xx-xx アプリケーション名]$ RAILS_ENV=production DISABLE_DATABASE_ENVIRONMENT_CHECK=1 bundle exec rails db:drop
[ec2-user@ip-xx-xx-xx-xx アプリケーション名]$ rails db:create RAILS_ENV=production
[ec2-user@ip-xx-xx-xx-xx アプリケーション名]$ rails db:migrate RAILS_ENV=productio
```

## .env を更新したとき
開発インスタンス（dmm-webcamp）で下記を行う
```
scp -i ~/.ssh/practice-aws.pem .env ec2-user@IPアドレス:GitHubのリポジトリ名/
```
- scp: secure copy SSH（セキュアシェル）を使ってファイルをリモートサーバー間でコピーするためのコマンド。
- -i 鍵 : EC2接続用の秘密鍵（SSHキー）を指定


## HTTPS接続設定後の起動の仕方
https://web-camp.online/lesson/curriculums/309/contents/2424

### 1. インスタンスを起動する

### 2. EID設定
1. ネットワーク＆セキュリティ > Elastic IP
2. 画面右上の「Elastic IP アドレスの割り当て」ボタンをクリック
3. 標準の設定のまま「割り振る」ボタン（画面最下部右）クリック
4. 作成後に表示されるメッセージにある[このElastic IPアドレスを関連づける]か、対象のIPアドレスを選択し[アクション]メニューの[Elastic IPアドレスの関連付け]をクリック
5. インスタンス欄、割り当てたいインスタンスを指定
6. 「関連付ける」ボタンクリック

```
dig any example.com
または、
dig example.com +short
```
で昔のEIPが使われていないかIPアドレスを確認する。

### 3. ドメイン設定
1. Route53 > ホストゾーン > レコードAを選択し「レコードを編集」ボタンクリック
2. IPの「値」を編集する。
2. `sudo systemctl restart nginx`でウェブサーバー再起動

### 4. HTTPS設定
corbotによるSSH証明書割り当てを行う。
```
sudo certbot --nginx
```
を実行。

2回目以降は、
```
What would you like to do?
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
1: Attempt to reinstall this existing certificate
2: Renew & replace the certificate (may be subject to CA rate limits)
```
の選択肢が出現。1を選択する。

2は、
- 証明書の有効期限（90日）を更新したいとき
- nginx を削除→再インストールしたとき
- ドメイン名 を変更したとき
に選択する。