// скрипты формы form1, обновление и удаление уже существующих в БД units
function updateServiceInput(input) {
    // обновление значения поля name
    let id = input.id;
    let nameValue = input.value;
    let sendData = new Array();
    sendData.push(id, nameValue);
    let json = JSON.stringify(sendData);

    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: "/update-service-name",
        data: json,
        dataType: "json"
    });
};

function updateServiceSelect(input) {
    // обновление значения поля select

      let idUnit = input.value;
      let idService = input.id;
      let sendData = new Array();
      sendData.push(idUnit, idService);
      let json = JSON.stringify(sendData);

    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: "/update-service-select",
        data: json,
        dataType: "json"
    });
};


function updateServiceCheckBox(input) {
    // обновление значения поля checkbox

    let idService = input.id;

    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: "/update-service-show-in-counters",
        data: idService,
        dataType: "json"
    });
};


function removeServiceInput(element){
    // передача id конкретного поля
    let id = element.id;
    $.ajax({
        type: 'GET',
        contentType: "application/json",
        url: "/service/remove/" + id,
        data: id,
        dataType: "json"
    });
    // скрыть видимость элемента после удаления из БД
    element.parentElement.parentElement.parentElement.parentElement.parentElement.hidden = true;
    swalAction('Услуга удалена');
};


// скрипты формы form2, добавление и удаление новых services в БД
//сохранение одной или многих новых services в БД
function submitNewServices() {
    let nameInputs = document.getElementsByName('addServiceName');
    let selectedInputs = document.getElementsByName('select');
    let showInCountersInputs = document.getElementsByName('showInCountersJs');

    let serviceObject = new Object();
    let allServiceObjects = new Array();

    for (let i = 0; i < nameInputs.length; i++) {
        serviceObject = {
            "name": nameInputs[i].value,
            "showInCounters": showInCountersInputs[i].checked,
            "unitId": selectedInputs[i].value//,
            //"unitName": selectedInputs[i].textContent
        }
        allServiceObjects.push(serviceObject);
    }

    let json = JSON.stringify(allServiceObjects);

    saving();

    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: "/add-services",
        data: json,
        dataType: "json"
    });
}




  // - Добавление новой единицы - service пока ещё без добавления в БД
        document.getElementById('base-service').onclick = function() {
            let theDiv = document.createElement('div');
            theDiv.innerHTML = '<div class="row form-service">'+
                '<div class="col-xs-12 col-sm-7">'+
                '<div class="form-group">'+
                '<label for="service-name">Услуга</label>'+
                '<input type="text" id="service-name" class="form-control" name="addServiceName">'+
                '</div></div>'+
                '<div class="col-xs-12 col-sm-5">'+
                '<div class="form-group">'+
                '<label for="units-select">Ед. изм.</label>'+
                '<div class="input-group">'+
                '<select name="select" required="true" class="minimal"><option  value="">Выберите...</option></select>'+ //onclick="showAllOptions(this)"
                '<span class="input-group-btn">'+
                '<button onclick="removeNewService(this)" type="button" class="btn btn-default"><i class="fa fa-trash"></i></button>'+
                '</span> </div> </div> </div>'+
                '<div class="col-xs-12">'+
                '<input type="hidden" name="showInCounters" value="false">'+
                '<label><input type="checkbox" id="service-counter" name="showInCountersJs"  checked=""> Показывать в счетчиках</label>'+
                '<div style="margin-bottom: 16px;"></div></div></div>';

            this.parentNode.insertBefore(theDiv, this.previousSibling);
            getComputedStyle(theDiv).opacity;
            theDiv.style.opacity = '1';

            let unitsNames = new Array();
            for (let i=0; i < units.length; i++){
                unitsNames[i]=units[i].name;
            }

            let unitsIds = new Array();
            for (let i=0; i < units.length; i++){
                unitsIds[i]=units[i].id;
            }
            console.log('**********************');
            console.log(unitsIds);
            console.log(unitsNames);
            console.log('**********************');
            const selects = document.getElementsByName('select');

            for (let s = 0; s < selects.length; s++) {
                for (let i = 0; i <= units.length; i++) {
                    const option = document.createElement('option');
                    option.textContent  = unitsNames[i];
                    option.value = unitsIds[i];
                    selects[s].append(option);
                }
            }
            console.log('**********************');
            console.log(selects);
            console.log('**********************');
        }


        // - удаление новой единицы - service пока ещё не добавленой в БД
        function removeNewService(elem) {
            elem.parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.remove();
            swalAction('Услуга удалена');
        }