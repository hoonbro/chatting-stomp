## WebSocket이란? 
  - 기존의 단방향 HTTP 프로토콜과 호환되어 양방향 통신을 제공하기 위해 개발된 프로토콜.   
  - 일반 Socket통신과 달리 HTTP 80 Port를 사용하므로 방화벽에 제약이 없으며 통상 WebSocket으로 불린다. 
  - 접속까지는 HTTP 프로토콜을 이용하고, 그 이후 통신은 자체적인 WebSocket 프로토콜로 통신하게 된다.
  -  웹소켓은 클라이언트가 접속 요청을 하고 웹 서버가 응답한 후 연결을 끊는 것이 아닌 Connection을 그대로 유지하고 클라이언트의 요청 없이도 데이터를 전송할 수 있는 프로토콜이다.

## WebSocket을 사용하는 이유
### HTTP통신
![image](https://user-images.githubusercontent.com/66583397/124771147-df923200-df75-11eb-97e8-ba78ee8b252c.png) 

- <b>모든 HTTP를 사용한 통신은 클라이언트가 먼저 요청을 보내고, 그 요청에 따라 웹 서버가 응답하는 형태이며 웹 서버는 응답을 보낸 후 웹 브라우저와의 연결을 끊는다.</b> 양쪽이 데이터를 동시에 보내는 것이 아니기 때문에 이러한 통신 방식을 반이중 통신(Half Duplex)라고 한다.

- 사실 HTTP만으로도 원하는 정보를 송수신할 수 있었지만, 인간의 욕심은 끝이 없기에 인터넷이 발전함에 따라 원하는 것이 더욱 다양해졌다. 예를 들어 클라이언트가 먼저 요청하지 않아도 서버가 먼저 데이터를 보내거나, 표준 TCP/IP 통신을 사용해 특정 서버와 통신을 하는 등 원하는 것이 늘어가자 그것을 이루고자 많은 플러그인 및 웹 기술이 개발되었다.


### 이전 방식
#### polling
![image](https://user-images.githubusercontent.com/66583397/124771961-8b3b8200-df76-11eb-8062-53f8a7171d94.png)

    - 클라이언트가 평범한 HTTP Request를 서버로 계속 요청해 이벤트 내용을 전달받는 방식.
    - 가장 쉬운 방법이지만 클라이언트가 지속적으로 Request를 요청하기 때문에 클라이언트의 수가 많아지면 서버의 부담이 급증한다.
    - HTTP Request Connection을 맺고 끊는 것 자체가 부담이 많은 방식이고, 클라이언트에서 실시간 정도의 빠른 응답을 기대하기 어렵다.

#### Long polling
![image](https://user-images.githubusercontent.com/66583397/124771981-8ecf0900-df76-11eb-83a2-315e373ba21a.png)

    - 클라이언트에서 서버로 일단 HTTP Request를 요청한다.
    - 이 상태로 계속 기다리다가 서버에서 해당 클라이언트로 전달할 이벤트가 있다면 그 순간 Response 메세지를 전달하며 연결이 종료된다.
    - 곧이어 클라이언트가 다시 HTTP Request를 요청해 서버의 다음 이벤트를 기다리는 방식.
    - polling보다 서버의 부담이 줄겠으나, 클라이언트로 보내는 이벤트들의 시간간격이 좁다면 polling과 별 차이 없게 되며,
      다수의 클라이언트에게 동시에 이벤트가 발생될 경우 서버의 부담이 급증한다.

#### Streaming
![image](https://user-images.githubusercontent.com/66583397/124771992-91c9f980-df76-11eb-9eb3-9d8ca962862b.png)

    - Long Polling과 마찬가지로 클라이언트 -> 서버로 HTTP Request를 요청한다.
    - 서버 -> 클라이언트로 이벤트를 전달할 때 해당 요청을 해제하지 않고 필요한 메세지만 보내기(Flush)를 반복하는 방식.
    - Long Polling과 비교하여 서버에 메세지를 보내지 않고도 다시 HTTP Request 연결을 하지 않아도 되어 부담이 경감된다고 한다.

#### WebSocket
![image](https://user-images.githubusercontent.com/66583397/124772009-94c4ea00-df76-11eb-8ab7-1f975568a2fd.png)

    - 이처럼 HTTP 통신의 특징인 (연결 -> 연결 해제) 때문에 효율이 많이 떨어지게 되고, 웹 브라우저 말고 외부 플러그인이 항상 필요하게 되었다.
    - 그래서 이런 상황을 극복하고자 2014년 HTML5에 웹 소켓을 포함하게 되었다. 웹소켓은 클라이언트가 접속 요청을 하고 웹 서버가 응답한 후
      연결을 끊는 것이 아닌 Connection을 그대로 유지하고 클라이언트의 요청 없이도 데이터를 전송할 수 있는 프로토콜이다.
    - 프로토콜의 요청은 [ws://~]로 시작한다.

## WebSocket 접속 과정
![image](https://user-images.githubusercontent.com/66583397/124769793-bae98a80-df74-11eb-910a-a5c13f2e6f4c.png)

  - 웹소켓을 이용하여 서버와 클라이언트가 통신을 하려면 먼저 웹소켓 접속 과정을 거쳐야 한다. 웹소켓 접속 과정은 TCP/IP 접속 그리고 웹소켓 열기 HandShake 과정으로 나눌 수 있다. 웹소켓도 TCP/IP위에서 동작하므로, 서버와 클라이언트는 웹소켓을 사용하기 전에 서로 TCP/IP 접속이 되어있어야 한다. TCP/IP 접속이 완료된 후 서버와 클라이언트는 웹소켓 열기 HandShake 과정을 시작한다.

### 웹소켓 열기 HandShake
> 웹소켓 열기 핸드셰이크는 클라이언트가 먼저 핸드셰이크 요청을 보내고 이에 대한 응답을 서버가 클라이언트로 보내는 구조이다. 서버와 클라이언트는 HTTP 1.1 프로토콜을 사용하여 요청과 응답을 보낸다. 다음은 Request와 Response의 예시이다.


[출처] https://dev-gorany.tistory.com/212  
[출처] https://javaengine.tistory.com/entry/Spring-websocket-chatting-server2-%E2%80%93-Stomp%EB%A1%9C-%EC%B1%84%ED%8C%85%EC%84%9C%EB%B2%84-%EA%B3%A0%EB%8F%84%ED%99%94%ED%95%98%EA%B8%B0?category=755679
