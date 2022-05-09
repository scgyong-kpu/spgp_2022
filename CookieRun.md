# CookieRun Copy

## 프로젝트를 만들기 전에
* 범용으로 사용할 수 있는 것들 정리
  * objects: `Score`, `Gauge`
  * `MainGame` 에서 공통으로 사용할 것들 분리
* package refactor
```
- framework
  - game
  - interfaces
  - objects
  - res
  - util
  - view
```

## CookieRun project
* Skeleton
  * &plus; framework files
  * Manifest: `GameActivity` 추가, landscape only, 
  * themes.xml: NoActionBar
  * `MainActivity` -> `GameActivity` 실행코드 추가
    * Debugging 편의를 위해 시작하면 바로 GameActivity 로 진입하도록 수정
  * `MainGame` 생성
    * `get()` 으로 singleton 얻도록
    * `Layer` enum 생성
    * cookie.png 추가하고 `Sprite` 로 생성
* `HorzScrollBackground` 적용
  * Layer 를 3장으로 겹쳐 사용하고 각각 -10dp, -20dp, -30dp 로 움직임
* Framework 수정
  * `GameObject.update()` 가 `float frameTime` 을 전달받도록 수정: 학생 제안
  * `BitmapPool` 이 이미지를 로드할 때 원본이미지 크기 그대로 로드하도록
    * `boolean BitmapFactory.Options.inScaled` 를 `false` 로.
  * Collision Box 를 그릴지 여부를 `BaseGame.showsBoxCollidables` 로 지정하게 함
    * 나중에 이것은 `BuildConfig` 를 이용하는 것으로 수정한다.
* 크기 결정 시스템
  * 화면 높이의 1/10 을 기준으로 모든 크기를 지정하도록 함
* GameObjects
  * `Platform` : 10x2, 2x2, 3x1
    * Java 의 enum 에는 함수를 정의할 수 있다
  * `ScrollObject` 를 분리해낸다 - 나중에 `MapSprite` 로 이름을 변경.
    * 화면 왼쪽으로 벗어나면 삭제
    * 재활용 가능하도록
  * `JellyItem`
    * index 를 0~59 로 지정
    * 하나의 이미지 파일에 가로 30 세로 2 총 60장이 들어 있다
* `MapLoader`
  * 초기 버전은 랜덤하게 생성
    * `itemLeft`, `platformLeft` 값을 기억하여 마지막에 만든 곳 뒤에 붙인다
* Stage 선택 구조
  * `MainActivity` 에서 `GameActivity` 로 넘어갈 때 parameter 전달
    * `Intent` 내에 Extras 를 설정. 
  * `MainGame` 을 초기화할 때 전달받은 `mapIndex` 를 넘김
  * Text file 로부터 맵 정보를 읽어옴
    * Android Asset 이용
      * Context context = GameView.view.getContext()
      * AssetManager assets = context.getAssets()
      * InputStream is = assets.open(filename)
      * isr = new InputStreamReader(inputStream)
      * reader = new BufferedReader(isr)
      * reader.readLine()
    * try/catch 로 `IOException` 에 대한 대비를 해야 함
    * Header 를 제외한 모든 라인을 읽어 ArrayList<String> 에 읽어둠
  * 읽어온 Map String 을 기반으로 객체 생성
    * 1~9 는 Item 으로, O~Q 는 Platform 으로 생성
* `Player`
  * `framework.objects.SheetSprite` 생성 후 상속받음
  * 첫번째 버전에서는 jelly.png 를 이용하여 5 프레임짜리로 만들어봄
