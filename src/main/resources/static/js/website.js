<!-- Sidebar tab active -->
    $(function () {
    var url = window.location;
    $('ul.nav-sidebar a').filter(function () {
    if (this.href) return this.href === url || url.href.indexOf(this.href) === 0;
}).addClass('active');
});



// images preview
function readURL1(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#previewImage1').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#slide1").change(function() {
    readURL1(this);
});

function readURL2(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#previewImage2').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#slide2").change(function() {
    readURL2(this);
});
function readURL3(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#previewImage3').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#slide3").change(function() {
    readURL3(this);
});

function readNextToUsImgURL1(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#next-to-us-image-1').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#next-to-us-imageLink-1").change(function() {
    readNextToUsImgURL1(this);
});

function readNextToUsImgURL2(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#next-to-us-image-2').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#next-to-us-imageLink-2").change(function() {
    readNextToUsImgURL2(this);
});

function readNextToUsImgURL3(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#next-to-us-image-3').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#next-to-us-imageLink-3").change(function() {
    readNextToUsImgURL3(this);
});

function readNextToUsImgURL4(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#next-to-us-image-4').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#next-to-us-imageLink-4").change(function() {
    readNextToUsImgURL4(this);
});

function readNextToUsImgURL5(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#next-to-us-image-5').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#next-to-us-imageLink-5").change(function() {
    readNextToUsImgURL5(this);
});

function readNextToUsImgURL6(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#next-to-us-image-6').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}
$("#next-to-us-imageLink-6").change(function() {
    readNextToUsImgURL6(this);
});
