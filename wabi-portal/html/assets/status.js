
$(document).ready(function() {

  var services = null;
  var wabiServicesData = [];
  var sabiServicesData = [];
  var intervalDuration = 30000;
  var intervalId = setInterval(updateStatus, intervalDuration);

  var template = $.templates("#servicesTemplate");
  template.link("#wabiServicesContainer", wabiServicesData);
  template.link("#sabiServicesContainer", sabiServicesData);

  function updateServiceStatus (service, serviceData) {
    $.ajax({
        url: "//" + location.hostname + ":" + service.port + "/info"
    })
    .done(function(data) {
      data.status = "UP";
      $.observable(serviceData).insert(data);
    })
    .fail(function() {
      $.observable(serviceData).insert({"build": {"name": service.name, "description": "Not responding...", "status": "DOWN"}});
    })
  }

  function updateWabiStatus() {
    for (service of services.wabiServices) {
      $.observable(wabiServicesData).remove(0);
      updateServiceStatus(service, wabiServicesData);
    }

    console.log(wabiServicesData);
  }

  function updateSabiStatus() {
    for (service of services.sabiServices) {
      $.observable(sabiServicesData).remove(0);
      updateServiceStatus(service, sabiServicesData);
    }

    console.log(sabiServicesData);
  }

  function updateStatus () {
    updateWabiStatus();
    updateSabiStatus();
  }

  $('.closeall').click(function(){
    $('.panel-collapse.in')
      .collapse('hide');
  });

  $('.openall').click(function(){
    $('.panel-collapse:not(".in")')
      .collapse('show');
  });

  $.getJSON("status.json", function(json) {
    console.log(json);
    services = json;
    updateStatus();
  });

});
