
$(document).ready(function() {

  var intervalDuration = 6000;
  var intervalId = setInterval(exposeFiles, intervalDuration);

  function exposeFiles () {
    $.ajax({
        url: "//" + location.hostname + ":9082/assets"
    }).then(function(data) {
      console.debug(data);

      if (!data.content.length) {
          $(".expose-content").replaceWith("<h5 class='expose-content'>Vault is empty...</h5>");
          return;
      }

      var tmp = "<ul class='list-group expose-content'>";

      for(datum of data.content) {
        tmp += "<li class='list-group-item'><span class='badge' title='" + datum.fileSize + " Bytes'>" + bytesToSize(datum.fileSize) + "</span><a href='//" + location.hostname + ":9082/download/" + datum.id + "' target='_blank'>" + datum.fileName + "</a></li>"
      }

      tmp += "</ul>";

      $(".expose-content").replaceWith(tmp);
    });
  }

  $("#search-txt").keypress(function (e) {
    if (e.which == 13) {
      $("#search-btn").click();
      return false;
    }
  });

  $("#search-btn").click(function() {
    if (intervalId !== undefined) {
      clearInterval(intervalId);
      intervalId = undefined;
    }

    var query = $("#search-txt").val().trim();

    if (!query || query.length === 0) {
      intervalId = setInterval(exposeFiles, intervalDuration);
      alert("Please enter a valid search query.");
      $("#search-txt").focus();
      return;
    }

    $(".expose-content").replaceWith("<h5 class='expose-content'>Searching vault...</h5>");

    $.ajax({
        url: "//" + location.hostname + ":9088/search?userId=" + encodeURIComponent(query)
    }).then(function(data) {
      console.debug(data);

      if (data.length === 0) {
        $(".expose-content").replaceWith("<h5 class='expose-content'>Sorry nothing matches <b>" + encodeURIComponent(query) + "</b> in the vault...</h5>");
        intervalId = setInterval(exposeFiles, intervalDuration);
        return;
      }

      var tmp = "<ul class='list-group expose-content'>";

      for(datum of data) {
        tmp += "<li class='list-group-item'><span class='badge' title='" + datum.fileSize + " Bytes'>" + bytesToSize(datum.fileSize) + "</span><a href='//" + location.hostname + ":9082/download/" + datum.id + "' target='_blank'>" + datum.fileName + "</a></li>"
      }

      tmp += "</ul>";

      $(".expose-content").replaceWith(tmp);
    });

    $("#search-txt").focus();
  });

  function resetPage () {
    if (intervalId === undefined) {
      intervalId = setInterval(exposeFiles, intervalDuration);
    }

    $("#search-txt").val("");
    $("#search-txt").focus();
    exposeFiles();
  }

  $("#clear-search-btn").click(function() {
    resetPage();
    return false;
  });

  $("#refresh-expose-lnk").click(function() {
    resetPage();
    return false;
  });

  Dropzone.autoDiscover = false;
  var theDropzone = new Dropzone("#upload-form", { url: "//" + location.hostname + ":9081/upload", maxFilesize: 100});

  theDropzone.on("addedfile", function(file) {
    exposeFiles();
  });

  $("#clear-ingest-lnk").click(function() {
      theDropzone.removeAllFiles();
      return false;
  });

  function bytesToSize(bytes) {
     var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
     if (bytes == 0) return '0 Byte';
     var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
     return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
  };

  exposeFiles();

});
