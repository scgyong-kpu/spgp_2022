# TapTU

* Project Skeleton
  * framework package 추가
  * `MainActivity` 를 `.app` 으로 옮김
  * `GameActivity` 추가, `MainScene` 추가, Screen Orientation = portrait, Start Button, NoActionBar
* `Song`
  * Load from stage_01.txt
    * `gen.html` 을 이용하여 JavaScript 로 note 생성
  * parse Title, Note
    * `String.split()`
    * `InputStream.close()`
  * Filename pass from `MainActivity` to `GameActivity`
* `NoteGen`, `NoteSprite`
  * `Song.Note` class 정보로부터 `NoteSprite` 생성
  * 화면 하단을 벗어나면 삭제
  * 초기 버전은 생성 위치를 화면 상단으로. 이후 버전은 노트 시간에 맞춰서.
  * Scroll 속도 쉽게 변경할 수 있도록
* Stage 2
  * `MainActivity` 에 버튼 추가하여 stage_02.txt 로드하록 함
* Background Sprite 추가
* Touch Event 처리
  * `Pret` class 를 이용하여 터치 처리
  * 가장 가까운 Note 를 찾아 삭제
    * 가장 가까운 Note 까지의 거리를 시각적으로 표시
* 판정
  * `Call` class 로 판정 종류 표시
  * Animation
* Song 목록 읽어 오는 구조 변경
  * songs.json 을 parse
  * `Song` 을 `JsonReader` 로 생성
  * 각 곡의 Album Art 읽어들이기
* Song 목록을 `MainActivity` 에서 `ListView` 를 이용하여 표시
  * `BaseAdapter` 기본 구현
  * `ListView` Item Recycling
  * Item from `LayoutInflater` (item.xml)
  * `Holder` 를 tag 로 사용
    * Constructor refactor
  * `ListView` Item Selection
    * Background Selector Drawable
  * Start Button 눌렀을 때 Selected Position 얻기
  * Deselect on Resume
* `MainActivity` 에서  `GameActivity` 로 넘어갈 때 자료 넘기기
  * index 만? String arg 여러개로? `Parcelable` 로?
  * JSON String 을 넘기는 것으로 결정
* Music Play
  * `AssetManager` -> `openFd()` -> `setDataSource()`
  * minSdk = 24
  * 음악 Play 시간에 따라 Note 움직임
