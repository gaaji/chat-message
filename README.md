# chat-message

## API
| Protocol  |          Endpoint          |              Description               |
|:---------:|:--------------------------:|:--------------------------------------:|
|   Http    |        [GET] /token        |          WebSocket 토큰을 요청한다.           |
| WebSocket |       /ws/gaaji-chat       |  Http 프로토콜에서 WebSocket으로 handshake한다.  |
| WebSocket |         /app/chat          |               채팅을 요청한다.                |
| WebSocket |       /app/chat/list       |            이전 채팅 리스트를 요청한다.            |
| WebSocket | /topic/chat/room/{room_id} |   툭정 채팅방(room_id)을 구독하는 destination    |
| WebSocket |   /user/queue/chat/list    |      이전 채팅 리스트를 응답하는 destinaiton       |
| WebSocket |     /user/queue/error      | 채팅 메시지 서버의 Exception을 구독하는 destination |

## Rule of commit
### 타입
- feat : 새로운 기능 추가
- fix : 버그 수정
- docs : 문서 수정
- style : 코드 formatting, 세미콜론(;) 누락, 코드 변경이 없는 경우
- refactor : 코드 리팩터링
- test : 테스트 코드, 리팩터링 테스트 코드 추가(프로덕션 코드 변경 X)
- chore : 빌드 업무 수정, 패키지 매니저 수정(프로덕션 코드 변경 X)
- design : CSS 등 사용자 UI 디자인 변경
- comment : 필요한 주석 추가 및 변경
- rename : 파일 혹은 폴더명을 수정하거나 옮기는 작업만인 경우
- remove : 파일을 삭제하는 작업만 수행한 경우
- !BREAKING CHANGE : 커다란 API 변경의 경우
- !HOTFIX : 급하게 치명적인 버그를 고쳐야 하는 경우
