angular.module('SocketApp.controllers', [])
    .controller('socketCtrl', function($scope) {
        $scope.result="";
        $scope.isConnect=false;

        $scope.handler =  function(calResult){
            $scope.result=JSON.parse(calResult.body).result;
            $scope.$apply();
        };

        var stompClientServerMsgs = undefined;

        /*В качестве параметра будем передавать функцию, которая выполняет обработки пришедшего объекта*/
        $scope.connect = function(handler){
            var socket = new SockJS('/getFirstMessage'); /*создаем сокет и указываем адрес, который он должен слушать*/
            stompClientServerMsgs = Stomp.over(socket); /*получаем клиента для работы с созданным сокетом*/
            /*Выполняем коннект по сокету*/
            stompClientServerMsgs.connect({}, function(frame) {
                //выполняем подписку на адрес, который будем слушать
                $scope.isConnect=true;
                stompClientServerMsgs.subscribe('/topic/msgFromServer', function(calResult){
                    handler(calResult);/*как только к нам пришел результат - мы его отображаем*/
                });
            });
        };

        $scope.disconnect = function(){
            if (stompClientServerMsgs) {
                stompClientServerMsgs.disconnect();
                console.log("Disconnected");
                $scope.isConnect=false; /*Для разблокировки кнопки коннекта*/
                $scope.result="";
            }
        }

        $scope.send= function() {
            /*отправка по адресу, по которому был создан сокет*/
            stompClientServerMsgs.send("/socketApp/getFirstMessage", {} /*, А вот сюда можно добавить JSON-объект для рассылки*/);
        }
    });