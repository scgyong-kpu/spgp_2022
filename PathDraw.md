# SmoothingPath

* Skeleton
* New -> UIComponent -> Custom View
  * XML로부터 Attribute 추가할 수 있도록 하는 구조 만들어 줌
  * Color 만 남겨 두고 다른 것은 사용하지 않음
* Touch Event 를 이용하여 눌린 점들을 연결하여 선을 그리도록 함
* 화면 구성
  * Clear버튼 - 점 수 표시 - 애니메이션 시작버튼
  * 애니메이션 속도 조정 Slider
  * PathView
* Path 관리
  * 초기 버전은 Point 만 관리하고 그릴때 Path 생성
  * 이후 버전은 Point 로 Path 까지 생성해 두고 Draw 에서는 생성된 Path 로 그리기
  * android.graphics.PointF 대신 내부 Point 사용
  * 마지막 버전에서는 Bezier Path 로 생성
* 비행기 표시
  * 선 위를 비행기가 따라가는 애니메이션을 보여줌
  * 비행기가 선을 가리므로 이미지를 그릴 때 Alpha 60% 로 그림
  * 각도도 적용되도록 함. 90도??
