//отправка на сервер без нажатия на кнопку /сохранить'
//для фотогалереи
document.getElementById("photo-about-us").onchange = function() {
    document.getElementById("fileUploadForm1").submit();
};



//для дополнительных фотографий
document.getElementById("additional-photo").onchange = function() {
    document.getElementById("fileUploadForm2").submit();
};

//Изменение стиля полей ввода документов - hidden
document.getElementById("doc-button").onclick = function() {
    document.getElementById("websitedocument").hidden = false;
};

<!--Photo director's preview-->
function readPhotoDirImgURL(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#directors-photo-image').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#directors-photo").change(function() {
    readPhotoDirImgURL(this);
});



<!-- summernote init -->


<!-- Sidebar tab active -->
    $(function () {
    var url = window.location;
    $('ul.nav-sidebar a').filter(function () {
    if (this.href) return this.href === url || url.href.indexOf(this.href) === 0;
}).addClass('active');
});

