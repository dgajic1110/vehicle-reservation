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
                click: 'companyView.showAddCompanyDialog',
                align: "right",
                hotkey: "enter",
                autowidth: true
            }]
        }, {
            view: "datatable",
            css: "webixDatatable",
            editable:false,
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
                },
                onItemDblClick: function (id) {
                    var tmpCompany = $$("companyDT").getItem(id);
                    userView.showCompanyUsersDialog(tmpCompany);
                }
            }
        }]
    },

    selectPanel: function () {
        $$("main").removeView(rightPanel);
        rightPanel = "companyPanel";

        var panelCopy = webix.copy(this.panel);

        $$("main").addView(webix.copy(panelCopy));
        connection.attachAjaxEvents("companyDT", "api/company", false);

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
                icon: "plus-circle"
            }, {
                id: "4",
                value: "Pregled korisnika",
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
                                    $$("companyDT").remove(context.id.row);
                                }
                            };
                            webix.confirm(delBox);
                            break;
                        case "3":
                            userView.showAddCompanyUserDialog($$("companyDT").getItem(context.id.row));
                            break;
                        case "4":
                            var tmpCompany = $$("companyDT").getItem(context.id.row);
                            userView.showCompanyUsersDialog(tmpCompany);
                            break;
                    }
                }
            }
        })
    },

    addCompanyDialog: {
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
                },{
                        margin: 5,
                        cols: [{}, {
                            id: "addCompanyBtn",
                            view: "button",
                            value: "Dodajte",
                            type: "form",
                            click: "companyView.addCompany",
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

    showAddCompanyDialog: function () {
        if (util.popupIsntAlreadyOpened("addCompanyDialog")) {
            webix.ui(webix.copy(companyView.addCompanyDialog)).show();
            webix.UIManager.setFocus("name");
        }
    },

    addCompany: function () {
        var form = $$("addCompanyForm");
        if (form.validate()) {
            var newCompany = {
                id: form.getValues().id,
                name: form.getValues().name,
                deleted: 0
            };
            $$("companyDT").add(newCompany);
            util.dismissDialog('addCompanyDialog');
        }
    },

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
                },{
                        margin: 5,
                        cols: [{}, {
                            id: "saveChangedCompanyBtn",
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
        if (util.popupIsntAlreadyOpened("changeCompanyDialog")) {
            webix.ui(webix.copy(companyView.changeCompanyDialog));
            var form = $$("changeCompanyForm");
            form.elements.id.setValue(company.id);
            form.elements.name.setValue(company.name);
            setTimeout(function () {
                $$("changeCompanyDialog").show();
                webix.UIManager.setFocus("name");
            }, 0);
        }
    },

    saveChangedCompany: function () {
        var form = $$("changeCompanyForm");
        if (form.validate()) {
            var newCompany = {
                id: form.getValues().id,
                name: form.getValues().name,
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
