# TODO
* `MainGame` 추가하여 GameView 를 완전히 재사용 가능한 것으로 만들기
  * singleton pattern 사용
  * `gameObjects` 저장
  * `float frameTime` 추가하여 이전 프레임과 현재 프레임의 시간차 저장. `GameObject` 들의 이동 등에 사용.
* `Fighter` 가 target point 를 따라가도록 수정
  * 중심점, `dx`, `dy` 를 저장하도록 
  * angle 과 speed 를 저장하도록 수정도 가능
  * 초기 위치(중심점)를 객체 생성시 결정 => 가능한가?
* size 는 dp 로 지정
  * dp to pixel 변환식 사용
  * `Resources` 에서 dimen 로드
* Metrics 추가
  * static member (class variable) 만 사용. singleton 과 비교.
  * `GameView` 의 size 가 결정될 때 초기화되도록
  * View 의 size, dp multiplier 등을 알 수 있도록
* 총알 발사
  * `Bullet` class 생성. 1초당 5개의 총알이 자동 발사되도록
  * 생성시 `x`, `y`, `angle`, `speed` 를 결정
  * 화면 밖으로 나가는 총알은 어떻게 처리해야 하나?
