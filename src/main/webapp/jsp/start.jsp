<!DOCTYPE html>
<html>
<head>
    <title>Calculator App Using Spring 4 WebSocket</title>

    <script src="resources/sockjs-0.3.4.js"></script> <!-- Библиотека для унификации и упрощения работы с сокетами, независимо от используемого браузера -->
    <script src="resources/stomp.js"></script> <!-- Библиотека для работы с протоколом STOMP -->


    <script type="text/javascript">
        var stompClientServerMsgs = null; /*Клиент*/

        function connectMessageFromServer(){
            var socket = new SockJS('/getFirstMessage'); /*/Spring4WebSocket/add*/ /*создаем сокет и указываем адрес, который он должен слушать*/
            stompClientServerMsgs = Stomp.over(socket); /*получаем клиента для работы с созданным сокетом*/
            /*Выполняем коннект по сокету*/
            stompClientServerMsgs.connect({}, function(frame) {
                setConnectedMessageFromServer(true); //устанавливаем в интерфейсе все сведение о том, что выполнено соединение
                //выполняем подписку на адрес, который будем слушать
                stompClientServerMsgs.subscribe('/topic/msgFromServer', function(calResult){
                    showResultMsg(JSON.parse(calResult.body).result);/*как только к нам пришел результат - мы его отображаем*/
                });
            });
        }

        function setConnectedMessageFromServer(connected) {
            document.getElementById('connectMsg').disabled = connected; /*кнопка установки соединения*/
            document.getElementById('disconnectMsg').disabled = !connected; /*кнопка сброса соединения*/
            document.getElementById('sendRequestForMsg').disabled = !connected; /*кнопка отправки сообщения*/
            document.getElementById('calculationDivMsg').style.visibility = connected ? 'visible' : 'hidden'; /*Отображение поля для ввода данных*/
            document.getElementById('calResponseMsg').innerHTML = ''; /*область для отображения результата*/
        }

        /*Закрытие соединения по сокету*/
        function disconnectMsg() {
            stompClientServerMsgs.disconnect(); /*Закрываем созданного клиента*/
            setConnectedMessageFromServer(false); /*приводим интерфейс в соответствующее состояние по отсутствию коннекта*/
            console.log("Disconnected");
        }

        /*Отправка по сокету сведений для обработки на сервере*/
        function sendRequestForMessage() {
            /*отправка по адресу, по которому был создан сокет*/
            stompClientServerMsgs.send("/socketApp/getFirstMessage", {} /*, А вот сюда можно добавить JSON-объект для рассылки*/);
        }

        function showResultMsg(message) {
            var response = document.getElementById('calResponseMsg'); /*Ищем область для отображения*/
            var p = document.createElement('p'); /*Добалвем в область новый элемент*/
            p.style.wordWrap = 'break-word'; /*утсанавливаем стиль для отображения*/
            p.appendChild(document.createTextNode(message)); /*добалвяем в тег пришедший результат*/
            response.appendChild(p); /*заночим созданным элемент со значением в область отображения результатов*/
        }
    </script>
</head>
<body>
<noscript><h2>Enable Java script and reload this page to run Websocket Demo</h2></noscript>
<h1>Messages from server</h1>

<div>
    <div>
        <button id="connectMsg" onclick="connectMessageFromServer();">Connect</button>
        <button id="disconnectMsg" disabled="disabled" onclick="disconnectMsg();">Disconnect</button><br/><br/>
        <button id="sendRequestForMsg" disabled="disabled" onclick="sendRequestForMessage();">Message request</button>
    </div>
    <div id="calculationDivMsg">
        <p id="calResponseMsg"></p>
    </div>
</div>
</body>
</html>