#!/usr/bin/env bash

PROJECT_ROOT="/home/ec2-user/external-api"
JAR_FILE="$PROJECT_ROOT/external-api.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# MYSQL 설정 환경변수 주입
# CodeDeploy 에서 절대경로로 넣어줘야 실행함
chmod +x /home/ec2-user/external-api/scripts/secret.sh
source /home/ec2-user/external-api/scripts/secret.sh

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar -Dspring.profiles.active=real $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG