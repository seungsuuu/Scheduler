# 일정 관리 앱 서버 만들기

## Ver 1

### Use Case 다이어그램
   
   ![scheduler use case 다이어그램](https://github.com/seungsuuu/Scheduler/assets/48900537/79e18f6b-6726-4562-92c3-b7970b4b2764)


### ER 다이어그램
   
   ![scheduler ER 다이어그램](https://github.com/seungsuuu/Scheduler/assets/48900537/cdb69f24-dfaa-4dab-9fc6-4c0317ef6cfa)


### API 명세서
   
   ![scheduler API 명세서(수정)](https://github.com/seungsuuu/Scheduler/assets/48900537/fa445984-64d4-413b-b37d-d52361c689ac)


### 요구 사항

* 공통 조건

   * 일정 작성, 수정, 조회 시 반환 받는 일정 정보에 `비밀번호`는 제외
   * 일정 수정, 삭제 시 선택한 일정의 `비밀번호`와 요청할 때 함께 보낸 `비밀번호`가 일치할 경우에만 수정, 삭제 가능

* 기능

1. 일정 작성
   * `할일 제목`, `할일 내용`, `담당자`, `비밀번호`, `작성일`을 저장
   * 저장된 일정 정보를 반환 받아 확인
  
2. 선택한 일정 조회
   * 선택한 일정의 정보를 조회
      
3. 일정 목록 전체 조회
   * 등록된 일정 전체를 조회
   * 조회된 일정 목록은 `작성일` 기준 내림차순 정렬
      
4. 선택한 일정 수정
   * 선택한 일정의 `할일 제목`, `할일 내용`, `담당자`을 수정
   * 서버에 일정 수정을 요청할 때 `비밀번호`를 함께 전달
   * 수정된 일정의 정보를 반환 받아 확인
      
5. 선택한 일정 삭제
   * 선택한 일정을 삭제
   * 서버에 일정 삭제를 요청할 때 `비밀번호`를 함께 전달
  
-----
   
## Ver 2

### ER 다이어그램

   ![scheduler ER 다이어그램 ver2](https://github.com/seungsuuu/Scheduler/assets/48900537/17c8bfda-c9c8-496c-bed7-66a87480f5ac)


### API 명세서

   ![scheduler API 명세서 ver2](https://github.com/seungsuuu/Scheduler/assets/48900537/e5f4149c-cf07-45f2-8e18-2ddccc8830c5)


### 요구 사항

* 기능

1. 일정과 댓글의 연관관계

   - 지난 과제에서 만든 일정에 `댓글`을 추가
   - ERD에도 댓글 모델을 추가
   - 각 일정에 댓글을 작성할 수 있도록 관련 클래스를 추가하고 `연관 관계`를 설정
   - 매핑 관계를 설정 (1:1 or N:1 or N:M)


2. 선택한 일정에 댓글 등록

   - 댓글이 등록되었다면 client에게 반환
   - 선택한 일정이 DB에 저장되어 있어야 등록 가능
   - 댓글을 식별하는 `고유번호`, `댓글 내용`, 댓글을 작성한 `사용자 아이디`, 댓글이 작성된 `일정 아이디`, `작성일자`를 저장

   - ⚠️ 예외 처리
      - 선택한 일정의 ID를 입력 받지 않은 경우
      - 댓글 내용이 비어 있는 경우
      - 일정이 DB에 저장되지 않은 경우

    
3. 선택한 일정의 댓글을 수정

   - 댓글이 수정되었다면 수정된 댓글을 반환
   - `댓글 내용`만 수정 가능
   - 선택한 일정과 댓글이 DB에 저장되어 있어야 수정 가능
   
   - ⚠️ 예외 처리
      - 선택한 일정이나 댓글의 ID를 입력 받지 않은 경우
      - 일정이나 댓글이 DB에 저장되지 않은 경우
      - 선택한 댓글의 사용자가 현재 사용자와 일치하지 않은 경우


4. 선택한 일정의 댓글을 삭제

   - 성공했다는 `메시지`와 `상태 코드` 반환
   - 선택한 일정과 댓글이 DB에 저장되어 있어야 삭제 가능

   - ⚠️ 예외 처리
      - 선택한 일정이나 댓글의 ID를 입력받지 않은 경우
      - 일정이나 댓글이 DB에 저장되지 않은 경우
      - 선택한 댓글의 사용자가 현재 사용자와 일치하지 않은 경우


4.5 5~7 단계에 적용될 공통 조건

   - `Ver 1` 일정 기능을 구현하면서 나온 예외처리를 Validation 어노테이션을 활용하여 수정
   - 2~4 단계에서 구현한 예외처리를 Validation 어노테이션을 활용하여 수정
   - 로그인을 구현 후 3, 4단계에서 구현한 기능에 해당 사용자가 작성한 댓글만 삭제, 수정 기능을 하도록 수정
  

5. JWT를 이용해 인증/인가 구현 및 1~4단계에 인증/인가 적용

   - Access Token 만료시간 60분
   - Refresh Token 은 8단계이므로 이번에는 구현 X

   - ⚠️ 예외 처리
      - StatusCode : 400
      - client에 반환
      - 토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때 (에러 메세지 : "토큰이 유효하지 않습니다.")
      - 토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닐 때 (에러 메세지 : "작성자만 삭제/수정할 수 있습니다.")
      - DB에 이미 존재하는 `username`으로 회원가입을 요청할 때 (에러 메세지 : "중복된 `username` 입니다.")
      - 로그인 시, 전달된 `username`과 `password` 중 맞지 않는 정보가 있을 때 (에러 메시지 : "회원을 찾을 수 없습니다.")
      - StatusCode 나누기


6. 회원가입 (사용자의 정보를 전달 받아 유저 정보를 저장)

   - 패스워드 암호화 X
   - `username`은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성
   - `password`는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성
   - DB에 중복된 `username`이 없다면 회원을 저장하고 Client 로 성공했다는 `메시지`, `상태코드` 반환


7. 로그인   

   - 기능 : `username`, `password` 정보를 client로부터 전달받아 토큰을 반환
   - DB에서 `username`을 사용하여 저장된 회원의 유무를 확인하고, 저장된 회원이 있다면 `password` 를 비교하여 로그인 성공 유무를 체크
   - 패스워드 복호화 X
   - 로그인 성공 시 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급
   - 발급한 토큰을 `Header`에 추가하고 성공했다는 `메시지` 및 `상태코드`와 함께 client에 반환
