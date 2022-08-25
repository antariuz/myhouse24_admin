// вывод алерта при клике на кнопку со свойством disabled=true
let allButtons = document.getElementsByName('removeBtn');
let disabledButtons = [];

for (let i=0; i<allButtons.length; i++) {
    if (allButtons[i].disabled === true) {
        disabledButtons.push(allButtons[i])
    }
}

for (let i=0; i < disabledButtons.length; i++){
    disabledButtons[i].parentElement.onclick = function () {
        alert("Эта ед.изм. используется в услуге. Удаление невозможно.");
    }
}


// скрипты красоты
    // честно спижжено у Тимура
    function swalAction(title) {
    Swal.fire({
        icon: 'success',
        title: title,
        showConfirmButton: false,
        timer: 1500,
    });
}
    // сам написал (нет)
    function saving(){
    Swal.fire({
        title: 'Ну что, сохранять эту дичь?',
        showDenyButton: false,  showCancelButton: false,
        confirmButtonText: `Да, 100%!`,
        //  denyButtonText: `Нет`,
    }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
            Swal.fire('Улетело в БД!', '', 'success');
            setTimeout(() => {  window.location.reload(); }, 1500);
        }
    });
}

//function submitForm3() {
   // document.forms[2].requestSubmit();
//submitForm3();
//    document.forms["form3"].submit();
//}
const btn = document.getElementById('thisButton');
const form = document.getElementById('id-form-3');

//btn.addEventListener('click', function () {
//    form.submit();
 //   console.log("***************+++++++++++++++++++++++++++++------------------")
//})


    // скрипты формы form3, обновление и удаление уже существующих в БД units
    //function updateUnitInput(input) {
    function updateUnitInput(input) {
    // передача значения конкретного поля и его id,
    // обновление поля
     //   let input = e.target;

        console.log("1111111111***+++++++++++++++++++++++++++++------------------")

        let id = input.id;
    let nameValue = input.value;
    let sendData = new Array();
    sendData.push(id, nameValue);
    let json = JSON.stringify(sendData);

    $.ajax({
    type: 'GET',
    contentType: "application/json",
    url: "/update-units",
    data: json,
    dataType: "json"
    });
    //window.location.reload();
};

    function removeUnitInput(element){
    // передача id конкретного поля
    let id = element.id;
    $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/unit/remove/" + id,
    data: id,
    dataType: "json"
});
    // скрыть видимость элемента после удаления из БД
    element.parentElement.parentElement.parentElement.parentElement.parentElement.hidden = true;
    swalAction('Удалено без особых усилий');
};




    // скрипты формы form4, добавление и удаление новых units в БД
    // - Добавление новой единицы - unit пока ещё без добавления в БД
    document.getElementById('raz').onclick = function() {
    let theDiv = document.createElement('div');
    theDiv.innerHTML = '<div id="form-serviceunit-rows">' +
    '<div class="row form-serviceunit">' +
    '<div class="col-xs-12">' +
    '<div class="form-group" style="margin-left: 20px">' +
    '<label for="test-unit-id">Ед. изм.</label>' +
    '<div class="input-group">' +
    '<input id="test-unit-id" type="text" class="form-control" name="addUnitName"> ' +
    '<span class="input-group-btn">' +
    '<button name="removeBtn" type="button" onclick="removeNewUnit(this)" class="btn btn-default" ><i class="fa fa-trash"></i></button>' +
    '</span></div></div></div></div></div>';  // его содержимое

    this.parentNode.insertBefore(theDiv, this.previousSibling);
    getComputedStyle(theDiv).opacity;
    theDiv.style.opacity = '1';
    }

    // - удаление новой единицы - unit пока ещё не добавленой в БД
    function removeNewUnit(elem) {
    elem.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.remove();
    swalAction('Удалено без особых усилий');
}

    // сохранение одной или многих новых units в БД
    function submitNewUnits() {





    let myForm = document.getElementsByName('addUnitName');
    let sendData = new Array();
    for (let i = 0; i < myForm.length; i++) {
    sendData.push(myForm[i].value);
    }

    let json = JSON.stringify(sendData);
    //saving();

    $.ajax({
    type: 'POST',
    contentType: "application/json",
    url: "/add-units",
    data: json,
    dataType: "json"
});
    window.location.reload();
}

