# Ground Rule
## 1. Branch 관련
- 기본 브랜치

  - master : 서버 Release를 위해 사용되는 브랜치

  - develop : 개발용 브랜치

  - feature_[기능] : 특정 기능 개발 브랜치

  - hotfix_[버그] : 심각한 버그 수정

  - fix_[버그] : 단순한 수정

  - refac_[리팩토링 내용] : 코드 구조 개선

  - docs_[문서] : 개발 문서

- 메인 브랜치(master, develop) 을 제외한 브랜치는 merge 후 제거합니다.

- hotfix 브랜치 merge 후에 파생 브랜치들은 merge 작업을 수행합니다.

- merge 순서

  - master ← develop

  - develop ← feature

  - develop ← fix

  - develop ← refac

  - develop ← docs

  - master ← hotfix

## 2. Commit 관련
- Commit Message 구조
```bash
type(타입) : title(제목)
body(본문, 생략 가능)
Resolives : #issue, ...(해결한 이슈, 생략 가능)
See also : #issue, ...(참고 이슈, 생략 가능)
```

- type Format

  - [FEAT] : 기능 추가

  - [FIX] : 버그 수정

  - [DOCS] : 문서 수정

  - [STYLE] : 코드 스타일 혹은 포맷

  - [REFACTOR] : 코드 리팩토링

  - [TEST] : 테스트 코드

  - [CHORE] : 빌드 업무 수정, 패키지 매니저 수정


- 좋은 Git커밋 메시지를 작성하기위한 약속([좋은 git 커밋 메시지를 작성하기 위한 7가지 약속 : NHN Cloud Meetup](https://meetup.toast.com/posts/106))

  - 제목과 본문을 한 줄 띄워 분리하기

  - 제목은 영문 기준 50자 이내로

  - 제목 첫글자를 대문자로

  - 제목 끝에 . 금지

  - 제목은 명령조로

  - 본문은 영문 기준 72자마다 줄 바꾸기

  - 본문은 어떻게 보다 무엇을, 왜에 맞춰 작성기

## 3. Coding Style 관련
- JAVA : [Google Coding Style](https://google.github.io/styleguide/javaguide.html)

- JavaScript : [Google Coding Style](https://google.github.io/styleguide/jsguide.html)

## 4. Pull Request 관련
- PR 시에 전반적인 설명(이유)에 대해서 작성

- PR은 기능 단위로 수행

- 템플릿 작성 ([좋은 Pull Request를 만드는 방법과 PR Template 구성](https://2jinishappy.tistory.com/337))

- Branch Protection Rule ([[GitHub] Branch Protection Rule 적용해 브랜치 보호하기](https://kotlinworld.com/292) )

- merge는 PR 요청자가 한다.

## 5. Review 관련
- 코드 리뷰는 존중하는 마음으로 합니다.

- 코드 리뷰는 최대 1000 라인

- 네이밍에 대한 조언은 함수가 어떤 역할을 수행하는지 뚜렷하지 않을 때 합니다.

- 코드 리뷰는 실력이 좋은 사람이 하는 것이 아닙니다.

## 6. Main Branch Protection Rule 

- [X] Branch name pattern : develop
  - protection Rule을 적용할 브랜치

- [X] Require a pull request before merging
  - 별도의 브랜치를 만들어 Pull Request를 해야함(승인 인원 >= 1)

- [ ] Require status checks to pass before merging
  - 테스트 결과 이상 없을 시에만 Merge 가능

- [X] Require conversation resolution before merging
  - Conversation이 모두 solved 되었을때만 merge 가능

- [ ] Require signed commits
  - Commit이 sign이 되어있어야함(key를 가진 인원이 commit 시)

- [ ] Require linear history
  - 분기가 불가능

- [X] Include administrators
  - 관리자에게도 5가지 Rule이 적용

- [ ] Allow force pushes
  - force push 허용

- [ ] Allow deletions
  - 삭제 허용
