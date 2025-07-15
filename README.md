1. java 17 버전 설치 및 환경변수 설정
2. node.js 설치(npm 사용)
3. vscode > ctrl + `로 터미널 열기 > 터미널에 npm install -g @vue/cli
ㄴ npm : 이 시스템에서 스크립트를 실행할 수 없으므로 C:\Program Files\nodejs\npm.ps1 파일을 로드할 수 없습니다. 
자세한 내용은 about_Execution_Policies(https://go.microsoft.com/fwlink/?LinkID=135170)를 참조하십시오.
위 오류가 난다면 시작 메뉴 > PowerShell 관리자 권한으로 실행
 > Set-ExecutionPolicy -Scope CurrentUser -ExecutionPolicy RemoteSigned 명령어 입력 후 Y > 확인 후 npm install -g @vue/cli 명령어로 vue 재설치
4. vue-project 오픈 후 터미널 열기 > npm install 명령어로 vite 설치 > npm run dev 명령어로 vue 서버 실행 정상적으로 동작하는지 확인
