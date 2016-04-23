/*
 * Wabi-Sabi DAM solution
 * Open source Digital Asset Management platform of great simplicity and beauty.
 * Copyright (C) 2016 Urchinly <wabi-sabi@urchinly.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
        url: "//" + service.hostname + ":" + service.port + "/info"
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
