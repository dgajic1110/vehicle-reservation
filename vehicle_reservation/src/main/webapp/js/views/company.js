var companyView = {
    panel: {
        id: "companyPanel",
        adjust: true,
        rows: [{
            view: "toolbar",
            padding: 8,
            css: "panelToolbar",
            cols: [{
                view: "label",
                width: 400,
                template: "<span class='fa fa-briefcase'></span> Kompanije"
            }, {}, {
                id: "addSuperAdminBtn",
                view: "button",
                type: "iconButton",
                label: "Dodajte administratora",
                icon: "user",
                click: 'userView.showAddSuperAdminDialog',
                autowidth: true
            }, {
                id: "addCompanyBtn",
                view: "button",
                type: "iconButton",
                label: "Dodajte kompaniju",
                icon: "plus-circle",
                click: 'companyView.showAddDialog',
                autowidth: true
            }]
        }, {
            view: "datatable",
            css: "webixDatatable",
            editable:true,
            editaction:"dblclick",
            multiselect: false,
            id: "companyDT",
            resizeColumn: true,
            resizeRow: true,
            onContext: {},
            columns: [{
                id: "id",
                hidden: true,
                fillspace: true,

            },{
                id:"companyLogo",
                fillspace:true,
                header:{text:"Logo", css:"wrap-line"},
                template:"<img src='data:image/jpg;base64,#companyLogo#'/>"

            } ,{
                id: "name",
                fillspace: true,
                editor: "text",
                sort: "string",
                header: [
                    "Naziv", {
                        content: "textFilter"
                    }
                ]
            }
            ],
            select: "row",
            navigation: true,
            url: "api/company",
            on: {

                onAfterContextMenu: function (item) {
                    this.select(item.row);
                }
            }
        }]
    },

    selectPanel: function () {
        $$("main").removeView(rightPanel);
        rightPanel = "companyPanel";

        var panelCopy = webix.copy(this.panel);

        $$("main").addView(webix.copy(panelCopy));
        connection.attachAjaxEvents("companyDT", "api/company", true);

        //konteksni meni
        webix.ui({
            view: "contextmenu",
            id: "companyContextMenu",
            width: 200,
            data: [{
                id: "1",
                value: "Izmijenite",
                icon: "pencil-square-o"
            }, {
                id: "2",
                value: "Obrišite",
                icon: "trash"
            }, {
                $template: "Separator"
            }, {
                id: "3",
                value: "Dodajte korisnika",
                icon: "user"
            }],
            master: $$("companyDT"),
            on: {
                onItemClick: function (id) {
                    var context = this.getContext();
                    switch (id) {
                        case "1":
                            companyView.showChangeCompanyDialog($$("companyDT").getItem(context.id.row));
                            break;
                        case "2":
                            var delBox = (webix.copy(commonViews.brisanjePotvrda("kompanije", "kompaniju")));
                            delBox.callback = function (result) {
                                if (result == 1) {
                                    var item = $$("companyDT").getItem(context.id.row);
                                    $$("companyDT").detachEvent("onBeforeDelete");
                                    connection.sendAjax("DELETE", "api/company/" + item.id, function (text, data, xhr) {
                                        if (text) {
                                            $$("companyDT").remove(context.id.row);
                                            util.messages.showMessage("Uspjesno brisanje");
                                        }
                                    }, function (text, data, xhr) {
                                        util.messages.showErrorMessage(text);
                                    }, item);
                                }
                            };
                            webix.confirm(delBox);
                            break;
                        case "3":
                            companyView.showAddCompanyUserDialog($$("companyDT").getItem(context.id.row));
                            break;
                    }
                }
            }
        })
    },

    addDialog: {
        view: "popup",
        id: "addCompanyDialog",
        modal: true,
        position: "center",

        body: {
            id: "addCompanyInside",
            rows: [{
                view: "toolbar",
                cols: [{
                    view: "label",
                    label: "<span class='webix_icon fa-briefcase'></span> Dodavanje kompanije",
                    width: 400
                }, {}, {
                    hotkey: 'esc',
                    view: "icon",
                    icon: "close",
                    align: "right",
                    click: "util.dismissDialog('addCompanyDialog');"
                }]
            }, {
                view: "form",
                id: "addCompanyForm",
                width: 600,
                elementsConfig: {
                    labelWidth: 200,
                    bottomPadding: 18
                },
                elements: [{
                    view: "text",
                    id: "name",
                    name: "name",
                    label: "Naziv:",
                    invalidMessage: "Unesite naziv kompanije!",
                    required: true
                }, {
                        height:50,
                        cols:[
                            {
                                view:"label",
                                width:200,
                                bottomPadding:18,
                                leftPadding:3,
                                required:true,
                                label:"Logo kompanije: <span style='color:#e32'>*</span>"
                            },
                            {
                                view:"list",
                                name:"companyLogoList",
                                rules:{
                                    content:webix.rules.isNotEmpty
                                },
                                scroll:false,
                                id:"companyLogoList",
                                width:290,
                                type: {
                                    height: "auto"
                                },
                                css:"relative image-upload",
                                template:"<img src='data:image/jpg;base64,#content#'/> <span class='delete-file'><span class='webix fa fa-close'/></span>",
                                onClick:{
                                    'delete-file':function (e,id) {
                                        this.remove(id);
                                        return false;
                                    }
                                }
                            },{},
                            {
                                view:"uploader",
                                id:"photoUploader",
                                width:24,
                                height:24,
                                css:"upload-photo",
                                template:"<span class='webix fa fa-upload' /></span>",
                                on: {
                                    onBeforeFileAdd: function (upload) {
                                        var type = upload.type.toLowerCase();
                                        if (type != "jpg" && type != "png"){
                                            util.messages.showErrorMessage("Dozvoljene ekstenzije  su jpg i png!")
                                            return false;
                                        }
                                        var file = upload.file;
                                        var reader = new FileReader();
                                        reader.onload = function (event) {
                                            var img=new Image();
                                            img.onload=function (ev) {
                                                if (img.width===220&& img.height===50) {
                                                    var newDocument = {
                                                        name: file['name'],
                                                        content: event.target.result.split("base64,")[1],
                                                    };
                                                    $$("companyLogoList").clearAll();
                                                    $$("companyLogoList").add(newDocument);
                                                }else{
                                                    util.messages.showErrorMessage("Dimenzije logoa moraju biti 220x50 px!")
                                                }
                                            };
                                            img.src=event.target.result;
                                        };
                                        reader.readAsDataURL(file);
                                        return false;
                                    }
                                }
                            },
                        ]
                    },
                    {
                        height:18,
                        cols:[

                            {},
                            {
                                id:"invalidLabel",
                                view:"label",
                                label:"Odaberite logo kompanije!",
                                css:" invalid-message-photo-alignment",
                                hidden:true

                            },
                            {}
                        ]
                    }
                    ,

                    {
                        margin: 5,
                        cols: [{}, {
                            id: "saveCompany",
                            view: "button",
                            value: "Dodajte kompaniju",
                            type: "form",
                            click: "companyView.save",
                            hotkey: "enter",
                            width: 150
                        }]
                    }],
                rules: {
                    "name": function (value) {
                        if (!value)
                            return false;
                        if (value.length > 128) {
                            $$('addCompanyForm').elements.name.config.invalidMessage = 'Maksimalan broj karaktera je 128!';
                            return false;
                        }
                        return true;
                    }
                }
            }]
        }
    },

    showAddDialog: function () {
        if (util.popupIsntAlreadyOpened("addCompanyDialog")) {
            webix.ui(webix.copy(companyView.addDialog)).show();
            webix.UIManager.setFocus("name");
        }
    },

    save: function () {
        var form = $$("addCompanyForm");
        var logo=$$("companyLogoList");
        var photoValidation=logo.count()===1;
        if (!photoValidation){
            webix.html.addCss(logo.getNode(),"image-upload-invalid");
            $$("invalidLabel").show();
        }else{
           webix.html.removeCss(logo.getNode(),"image-upload-invalid");
            $$("invalidLabel").hide();
        }
        var validation=form.validate();
        if (validation && photoValidation) {
            var newCompany = {
                name: form.getValues().name,
                companyLogo: logo.getItem(logo.getLastId()).content,
                deleted: 0
            };
            $$("companyDT").add(newCompany);
            util.dismissDialog('addCompanyDialog');
        }
    }

    ,
    changeCompanyDialog: {
        view: "popup",
        id: "changeCompanyDialog",
        modal: true,
        position: "center",

        body: {
            id: "changeCompanyInside",
            rows: [{
                view: "toolbar",
                cols: [{
                    view: "label",
                    label: "<span class='webix_icon fa-briefcase'></span> Izmjena kompanije",
                    width: 400
                }, {}, {
                    hotkey: 'esc',
                    view: "icon",
                    icon: "close",
                    align: "right",
                    click: "util.dismissDialog('changeCompanyDialog');"
                }]
            }, {
                view: "form",
                id: "changeCompanyForm",
                width: 600,
                elementsConfig: {
                    labelWidth: 200,
                    bottomPadding: 18
                },
                elements: [{
                    view: "text",
                    name: "id",
                    hidden: true
                },{
                    view: "text",
                    id: "name",
                    name: "name",
                    label: "Naziv:",
                    invalidMessage: "Unesite naziv kompanije!",
                    required: true
                },
                    {
                        height:50,
                        cols:[
                            {
                                view:"label",
                                width:200,
                                bottomPadding:18,
                                leftPadding:3,
                                required:true,
                                label:"Logo kompanije: <span style='color:#e32'>*</span>"
                            },
                            {
                                view:"list",
                                id:"changeCompanyLogoList",
                                name:"changeCompanyLogoList",
                                rules:{
                                    content:webix.rules.isNotEmpty
                                },
                                scroll:false,
                                id:"changeCompanyLogoList",
                                width:290,
                                type: {
                                    height: "auto"
                                },
                                css:"relative image-upload",
                                template:"<img src='data:image/jpg;base64,#content#'/> <span class='delete-file'><span class='webix fa fa-close'/></span>",
                                onClick:{
                                    'delete-file':function (e,id) {
                                        this.remove(id);
                                        return false;
                                    }
                                }
                            },{},
                            {
                                view:"uploader",
                                id:"photoUploader",
                                width:24,
                                height:24,
                                css:"upload-photo",
                                template:"<span class='webix fa fa-upload' /></span>",
                                on: {
                                    onBeforeFileAdd: function (upload) {
                                        var type = upload.type.toLowerCase();
                                        if (type != "jpg" && type != "png"){
                                            util.messages.showErrorMessage("Dozvoljene ekstenzije  su jpg i png!")
                                            return false;
                                        }
                                        var file = upload.file;
                                        var reader = new FileReader();
                                        reader.onload = function (event) {
                                            var img=new Image();
                                            img.onload=function (ev) {
                                                if (img.width===220&& img.height===50) {
                                                    var newDocument = {
                                                        name: file['name'],
                                                        content: event.target.result.split("base64,")[1],
                                                    };
                                                    $$("changeCompanyLogoList").clearAll();
                                                    $$("changeCompanyLogoList").add(newDocument);
                                                }else{
                                                    util.messages.showErrorMessage("Dimenzije logoa moraju biti 220x50 px!")
                                                }
                                            };
                                            img.src=event.target.result;
                                        };
                                        reader.readAsDataURL(file);
                                        return false;
                                    }
                                }
                            },
                        ]
                    },
                    {
                        height:18,
                        cols:[

                            {},
                            {
                                id:"invalidLabel",
                                view:"label",
                                label:"Odaberite logo kompanije!",
                                css:" invalid-message-photo-alignment",
                                hidden:true

                            },
                            {}
                        ]
                    }
                    ,

                    {
                        margin: 5,
                        cols: [{}, {
                            id: "changeCompany",
                            view: "button",
                            value: "Sačuvajte izmjene",
                            type: "form",
                            click: "companyView.saveChangedCompany",
                            hotkey: "enter",
                            width: 150
                        }]
                    }],
                rules: {
                    "name": function (value) {
                        if (!value)
                            return false;
                        if (value.length > 128) {
                            $$('changeCompanyForm').elements.name.config.invalidMessage = 'Maksimalan broj karaktera je 128!';
                            return false;
                        }
                        return true;
                    }
                }
            }]
        }
    },


    showChangeCompanyDialog: function (company) {

        webix.ui(webix.copy(companyView.changeCompanyDialog));
        var form = $$("changeCompanyForm");
        form.elements.id.setValue(company.id);
        form.elements.name.setValue(company.name);

        setTimeout(function () {
            $$("changeCompanyDialog").show();
            webix.UIManager.setFocus("name");
            var newDocument = {
                name: '',
                content: company.companyLogo,
            };
            $$("changeCompanyLogoList").clearAll();
            $$("changeCompanyLogoList").add(newDocument);
        }, 0);
    },

    saveChangedCompany: function () {
        var form = $$("changeCompanyForm");
        var logo=$$("changeCompanyLogoList");
        var photoValidation=logo.count()===1;
        if (!photoValidation){
            webix.html.addCss(logo.getNode(),"image-upload-invalid");
            $$("invalidLabel").show();
        }else{
            webix.html.removeCss(logo.getNode(),"image-upload-invalid");
            $$("invalidLabel").hide();
        }
        var validation=form.validate();
        if (validation && photoValidation) {
            var newCompany = {
                id: form.getValues().id,
                name: form.getValues().name,
                companyLogo: logo.getItem(logo.getLastId()).content,
                deleted: 0
            };
            connection.sendAjax("PUT", "api/company/" + newCompany.id,
                function (text, data, xhr) {
                    if (text) {
                        util.messages.showMessage("Kompanija uspješno izmjenjena.");
                        $$("companyDT").updateItem(newCompany.id, newCompany);
                    } else
                        util.messages.showErrorMessage("Neuspješna izmjena.");
                }, function (text, data, xhr) {
                    util.messages.showErrorMessage(text);
                }, newCompany);

            util.dismissDialog('changeCompanyDialog');
        }

    }

    
};
