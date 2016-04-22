
$(document).ready(function() {

  var events = [];
  var template = $.templates("#eventTemplate");
  template.link("#eventsContainer", events);

  function connect() {
    var socket = new SockJS("//" + location.hostname + ':7081/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/events', function(event){
          if(events.length > 9) {
            $.observable(events).remove(0);
          }

          $.observable(events).insert(JSON.parse(event.body));
        });
    });
  }

  connect();
});
