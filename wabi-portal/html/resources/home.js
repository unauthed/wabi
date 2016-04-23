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

  var intervalDuration = 6000;
  var intervalId = setInterval(exposeFiles, intervalDuration);

  function exposeFiles () {
    $.ajax({
        url: "/assets"
    }).then(function(data) {
      console.debug(data);

      if (!data.content.length) {
          $(".expose-content").replaceWith("<h5 class='expose-content'>Vault is empty...</h5>");
          return;
      }

      var tmp = "<ul class='list-group expose-content'>";

      for(datum of data.content) {
        tmp += "<li class='list-group-item'><span class='badge' title='" + datum.fileSize + " Bytes'>" + bytesToSize(datum.fileSize) + "</span><a href='/download/" + datum.id + "' target='_blank'>" + datum.fileName + "</a></li>"
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
    var query = $("#search-txt").val().trim();

    if (!query || query.length === 0) {
      resetPage ();
      return false;
    }

    if (intervalId !== undefined) {
      clearInterval(intervalId);
      intervalId = undefined;
    }

    $(".expose-content").replaceWith("<h5 class='expose-content'>Searching vault...</h5>");

    $.ajax({
        url: "/search?userId=" + encodeURIComponent(query)
    }).then(function(data) {
      console.debug(data);

      if (data.length === 0) {
        $(".expose-content").replaceWith("<h5 class='expose-content'>Sorry nothing matches <b>\"" + htmlEncode(query) + "\"</b> in the vault...</h5>");
        return;
      }

      var tmp = "<ul class='list-group expose-content'>";

      for(datum of data) {
        tmp += "<li class='list-group-item'><span class='badge' title='" + datum.fileSize + " Bytes'>" + bytesToSize(datum.fileSize) + "</span><a href='/download/" + datum.id + "' target='_blank'>" + datum.fileName + "</a></li>"
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
  var theDropzone = new Dropzone("#upload-form", { url: "/upload", maxFilesize: 100});

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

  function htmlEncode(value){
    //create a in-memory div, set it's inner text(which jQuery automatically encodes)
    //then grab the encoded contents back out.  The div never exists on the page.
    return $('<div/>').text(value).html();
  }

  function htmlDecode(value){
    return $('<div/>').html(value).text();
  }

  exposeFiles();

});
