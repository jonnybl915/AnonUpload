function getFiles(data) {
    for (var i in data) {

        var elem = $("<a>");
        elem.attr("href", "files/" + data[i].realFileName);
        elem.text(data[i].comment + " id=" + data[i].id);
        $("#list").append(elem);
        $("#list").append($("<br>"));
    }
}
$.get("/files", getFiles);