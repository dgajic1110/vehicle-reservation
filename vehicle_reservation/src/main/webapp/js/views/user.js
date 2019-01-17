var addCompanyUserCompanyId;

userView = {

    panel: {
        id: "userPanel",
        adjust: true,
        rows: [{
            view: "toolbar",
            padding: 8,
            css: "panelToolbar",
            cols: [{
                view: "label",
                width: 400,
                template: "<span class='fa fa-user'></span> Korisnici"
            }, {}, {
                id: "addUserBtn",
                view: "button",
                type: "iconButton",
                label: "Dodajte korisnika",
                icon: "plus-circle",
                click: 'userView.showAddDialog',
                autowidth: true
            }]
        }, {
            view: "datatable",
            css: "webixDatatable",
            multiselect: false,
            id: "userDT",
            resizeColumn: true,
            resizeRow: true,
            onContext: {},
            columns: [{
                id: "id",
                hidden: true,
                fillspace: true,

            }, {
                id: "firstName",
                editable: false,
                fillspace: true,
                editor: "text",
                sort: "string",
                header: [
                    "Ime", {
                        content: "textFilter"
                    }
                ]
            }, {
                id: "lastName",
                editable: false,
                fillspace: true,
                editor: "text",
                sort: "string",
                header: [
                    "Prezime", {
                        content: "textFilter"
                    }
                ]
            }, {
                id: "username",
                editable: false,
                fillspace: true,
                editor: "text",
                sort: "string",
                header: [
                    "Korisničko ime", {
                        content: "textFilter"
                    }
                ]
            }, {
                id: "email",
                fillspace: true,
                editable: false,
                editor: "text",
                sort: "text",
                header: [
                    "E-mail", {
                        content: "textFilter"
                    }
                ]
            }
            ],
            select: "row",
            navigation: true,
            editable: false,
            url: "user",
            on: {

                onAfterContextMenu: function (item) {
                    this.select(item.row);
                }
            }
        }]
    },

    addDialog: {
        view: "popup",
        id: "addUserDialog",
        modal: true,
        position: "center",
        body: {
            id: "addUserInside",
            rows: [{
                view: "toolbar",
                cols: [{
                    view: "label",
                    label: "<span class='webix_icon fa-briefcase'></span> Dodavanje korisnika",
                    width: 400
                }, {}, {
                    hotkey: 'esc',
                    view: "icon",
                    icon: "close",
                    align: "right",
                    click: "util.dismissDialog('addUserDialog');"
                }]
            }, {
                view: "form",
                id: "addUserForm",
                width: 600,
                elementsConfig: {
                    labelWidth: 200,
                    bottomPadding: 18
                },
                elements: [
                    {
                        view: "text",
                        id: "email",
                        name: "email",
                        label: "E-mail:",
                        required: true,
                        invalidMessage: "Unesite odgovarajuću e-mail adresu"
                    },{
                        view: "richselect",
                        id: "role",
                        name: "role",
                        label: "Uloga:",
                        value: 4,
                        options: [
                            {id:4, "value":"korisnik"},
                            {id: 3, "value":"napredni korisnik"},
                            ]
                    },
                    {
                        margin: 5,
                        cols: [{}, {
                            id: "saveUser",
                            view: "button",
                            value: "Dodajte korisnika",
                            type: "form",
                            click: "userView.save",
                            hotkey: "enter",
                            width: 150
                        }]
                    }],
                rules: {
                    "email": function (value) {
                        if (!value) {
                            $$('addUserForm').elements.email.config.invalidMessage = 'Unesite E-mail!';
                            return false;
                        }
                        if (value.length > 100) {
                            $$('addUserForm').elements.email.config.invalidMessage = 'Maksimalan broj karaktera je 100';
                            return false;
                        }
                        if (!webix.rules.isEmail(value)) {
                            $$('addUserForm').elements.email.config.invalidMessage = 'E-mail nije u validnom formatu.';
                            return false;
                        }

                        return true;
                    }
                }
            }]
        }
    },

    addSuperAdminDialog: {
        view: "popup",
        id: "addSuperAdminDialog",
        modal: true,
        position: "center",
        body: {
            id: "addSuperAdminInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa-briefcase'></span> Dodavanje administratora sistema",
                            width: 400
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('addSuperAdminDialog');"
                        }
                    ]
                },
                {
                    id: "addSuperAdminForm",
                    view: "form",
                    width: 600,
                    elementsConfig: {
                        labelWidth: 200,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            id: "email",
                            name: "email",
                            view: "text",
                            label: "E-mail adresa:",
                            required: true
                        },
                        {
                            margin: 5,
                            cols: [
                                {},
                                {
                                    id: "saveSuperAdminBtn",
                                    view: "button",
                                    value: "Dodajte",
                                    type: "form",
                                    click: "userView.saveSuperAdmin",
                                    hotkey: "enter",
                                    width: 150
                                }
                            ]
                        }
                    ],
                    rules: {
                        "email": function (value) {
                            if (!value) {
                                $$('addSuperAdminForm').elements.email.config.invalidMessage = 'Molimo Vas da unesete e-mail adresu novog korisnika.';
                                return false;
                            }
                            if (value.length > 128) {
                                $$('addSuperAdminForm').elements.email.config.invalidMessage = 'Maksimalan broj karaktera je 128';
                                return false;
                            }
                            if (!webix.rules.isEmail(value)) {
                                $$('addSuperAdminForm').elements.email.config.invalidMessage = 'E-mail adresa nije u validnom formatu.';
                                return false;
                            }
                            return true;
                        }
                    }
                }
            ]
        }
    },

    selectPanel: function () {
        $$("main").removeView(rightPanel);
        rightPanel = "userPanel";

        var panelCopy = webix.copy(this.panel);

        $$("main").addView(webix.copy(panelCopy));
        connection.attachAjaxEvents("userDT", "user");
        $$("userDT").detachEvent("onBeforeDelete");

        webix.ui({
            view: "contextmenu",
            id: "userContextMenu",
            width: 200,
            data: [{
                id: "1",
                value: "Deaktivirajte",
                icon: "trash"
            }],
            master: $$("usersList"),
            on: {
                onItemClick: function (id) {
                    var context = this.getContext();
                    switch (id) {
                        case "1":
                            var item = $$("usersList").getItem(context.id.row);
                            if(item.active == 0){
                                util.messages.showErrorMessage("Korisnik je već deaktiviran");
                                break;
                            }
                            var updateBox = (webix.copy(commonViews.deaktivacijaPotvrda("korisnika", "korisnika")));
                            updateBox.callback = function (result) {
                                if (result == 1) {
                                    connection.sendAjax("GET", "api/user/deactivate/" + item.id, function (text, data, xhr) {
                                        if (text) {
                                            $$("usersList").remove(item.id);
                                            util.messages.showMessage("Uspješno deaktiviranje");
                                        }
                                    }, function (text, data, xhr) {
                                        util.messages.showErrorMessage(text);
                                    });
                                }
                            };
                            webix.confirm(updateBox);
                            break;
                    }
                }
            }
        })
    },

    showAddDialog: function () {
        if (util.popupIsntAlreadyOpened("addUserDialog")) {
            webix.ui(webix.copy(userView.addDialog)).show();
            webix.UIManager.setFocus("name");
        }
    },

    save: function () {
        var form = $$("addUserForm");
        if (form.validate()) {
            var newUser = {
                email: $$("addUserForm").getValues().email,
                roleId: $$("role").getValue(),
                companyId: userData.companyId,
                active: 1
            };
            $$("userDT").add(newUser);
            util.dismissDialog('addUserDialog');
        }
    },

    companyUsersDialog: {
        view: "popup",
        id: "companyUsersDialog",
        modal: true,
        position: "center",
        body: {
            id: "addCompanyInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa-user'></span> Korisnici kompanije",
                            width: 400
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('companyUsersDialog');"
                        }
                    ]
                },
                {
                    id: "usersList",
                    view: "list",
                    width: 400,
                    height: 300,
                    dynamic: true,
                    template: "<div class='list-name'>#firstName# #lastName# - #roleName#</div> <span class='delete-user'><span class='webix fa fa-close'/></span>",
                    onClick: {
                        'delete-user': function (e, id) {
                            userView.deactivateCompanyUser(id, this);
                            return false;
                        }
                    },
                    select: false
                }
            ]
        }
    },

    deactivateCompanyUser: function (id, list) {
        var object = $$("usersList").getItem(id);
        var updateBox = (webix.copy(commonViews.deaktivacijaPotvrda("korisnika", "korisnika")));
        updateBox.callback = function (result) {
            if (result == 1) {
                webix.ajax().get("api/user/deactivate/" + object.id).then(function (data) {
                    if(data.text() === "Success"){
                        util.messages.showMessage("Uspješna deaktivacija korisnika.")
                    }
                    else{
                        util.messages.showErrorMessage("Neuspješna deaktivacija korisnika.")
                    }
                }).fail(function (error) {
                    util.messages.showErrorMessage(error.responseText);
                });
                list.remove(id);
            }
        };
        webix.confirm(updateBox);
    },

    showCompanyUsersDialog: function (company) {
        if (util.popupIsntAlreadyOpened("companyUsersDialog")) {
            webix.ui(webix.copy(userView.companyUsersDialog)).show();
            $$("usersList").load("api/user/companyUsers/" + company.id);
        }
    },

    addCompanyUserDialog: {
        view: "popup",
        id: "addCompanyUserDialog",
        modal: true,
        position: "center",
        body: {
            id: "addCompanyUserInside",
            rows: [
                {
                    view: "toolbar",
                    cols: [
                        {
                            view: "label",
                            label: "<span class='webix_icon fa-briefcase'></span> Dodavanje novog korisnika",
                            width: 400
                        },
                        {},
                        {
                            hotkey: 'esc',
                            view: "icon",
                            icon: "close",
                            align: "right",
                            click: "util.dismissDialog('addCompanyUserDialog');"
                        }
                    ]
                },
                {
                    id: "addCompanyUserForm",
                    view: "form",
                    width: 600,
                    elementsConfig: {
                        labelWidth: 200,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            id: "email",
                            name: "email",
                            view: "text",
                            label: "E-mail adresa:",
                            required: true
                        },
                        {
                            id: "roleId",
                            name: "roleId",
                            view: "select",
                            value: 2,
                            label: "Vrsta:",
                            options: [
                                {
                                    value: "Administrator",
                                    id: 2
                                },
                                {
                                    value: "Korisnik",
                                    id: 3
                                }
                            ]
                        },
                        {
                            margin: 5,
                            cols: [
                                {},
                                {
                                    id: "saveCompanyUserBtn",
                                    view: "button",
                                    value: "Dodajte",
                                    type: "form",
                                    click: "userView.saveCompanyUser",
                                    hotkey: "enter",
                                    width: 200
                                }
                            ]
                        }
                    ],
                    rules: {
                        "email": function (value) {
                            if (!value) {
                                $$('addCompanyUserForm').elements.email.config.invalidMessage = 'Molimo Vas da unesete e-mail adresu novog korisnika.';
                                return false;
                            }
                            if (value.length > 128) {
                                $$('addCompanyUserForm').elements.email.config.invalidMessage = 'Maksimalan broj karaktera je 128';
                                return false;
                            }
                            if (!webix.rules.isEmail(value)) {
                                $$('addCompanyUserForm').elements.email.config.invalidMessage = 'E-mail adresa nije u validnom formatu.';
                                return false;
                            }
                            return true;
                        }
                    }
                }
            ]
        }
    },

    saveCompanyUser: function () {
        var form = $$("addCompanyUserForm");
        if (form.validate()) {
            var newUser = {
                email: form.getValues().email,
                roleId: form.getValues().roleId,
                companyId: addCompanyUserCompanyId,
                active: 1,
                deleted: 0
            };

            webix.ajax().header({"Content-type": "application/json"})
                .post("api/user", newUser).then(function (data) {
                util.messages.showMessage("Uspješno dodavanje novog korisnika.");
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });

            util.dismissDialog('addCompanyUserDialog');
        }
    },

    showAddCompanyUserDialog: function (company) {
        if (util.popupIsntAlreadyOpened("addCompanyUserDialog")) {
            addCompanyUserCompanyId = company.id;
            webix.ui(webix.copy(userView.addCompanyUserDialog)).show();
            webix.UIManager.setFocus("email");
        }
    },

    saveSuperAdmin: function () {
        var form = $$("addSuperAdminForm");
        if (form.validate()) {
            var newUser = {
                email: form.getValues().email,
                active: 1,
                deleted: 0,
                roleId: 1
            };

            webix.ajax().header({"Content-type": "application/json"})
                .post("api/user", newUser).then(function (data) {
                util.messages.showMessage("Uspješno dodavanje administratora sistema.");
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });

            util.dismissDialog('addSuperAdminDialog');
        }
    },

    showAddSuperAdminDialog: function () {
        if (util.popupIsntAlreadyOpened("addSuperAdminDialog")) {
            webix.ui(webix.copy(userView.addSuperAdminDialog)).show();
            webix.UIManager.setFocus("email");
        }
    },

}