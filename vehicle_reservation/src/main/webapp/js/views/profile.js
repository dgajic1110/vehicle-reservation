var profileView={
    changeProfileDialog: {
        view: "popup",
        id: "changeProfileDialog",
        modal: true,
        position: "center",
        body: {
            rows: [{
                view: "toolbar",
                padding: 8,
                cols: [{
                    view: "label",
                    label: "<span class='fa fa-user'></span> Profil"
                },{}, {
                    view: "icon",
                    icon: "close",
                    align: "right",
                    click: "util.dismissDialog('changeProfileDialog');"

                }]
            }, {
                cols: [
                    {
                        "view": "template",
                        borderless:true,
                        width: 30,
                        "template": "<p></p>"
                    },
                    {
                        view: "form",
                        borderless:true,
                        id: "profileForm",
                        elementsConfig: {
                            bottomPadding: 20
                        },
                        elements: [
                            {
                                view: "text",
                                width:400,
                                align:"left",
                                id: "username",
                                name: "username",
                                readonly:true,
                                label: "Korisničko ime:",
                                labelAlign:'left',
                                labelWidth:118,
                                invalidMessage: "Unesite korisničko ime!",
                                //required: true
                            },
                            {
                                view: "text",
                                name: "base64ImageUser",
                                hidden: true
                            },
                            {
                                view: "text",
                                id: "firstname",
                                name: "firstName",
                                width:400,
                                align:"left",
                                label: "Ime:",
                                readonly:false,

                                invalidMessage: "Unesite ime!",
                                labelAlign:'left',
                                labelWidth:118,
                                required: true
                            },
                            {
                                view: "text",
                                id: "lastname",
                                name: "lastName",
                                width:400,
                                align:"left",
                                label: "Prezime:",
                                readonly:false,
                                editaction:"dblclick",
                                invalidMessage: "Unesite prezime!",
                                labelAlign:'left',
                                labelWidth:118,
                                required: true
                            },
                            {
                                view: "text",
                                id: "email",
                                name: "email",
                                width:400,
                                align:"left",
                                label: "E-mail:",
                                readonly:true,
                                invalidMessage: "Unesite email!",
                                labelAlign:'left',
                                labelWidth:118
                                // required: true
                            },
                            {
                                cols:[
                                    {
                                        margin:5,
                                        id: "saveProfileBtn",
                                        view: "button",
                                        align:"right",
                                        value: "Sačuvajte",
                                        type: "form",
                                        click: "profileView.saveChanges",
                                        hotkey: "enter",
                                        width:200,
                                        //  hidden:true
                                    }
                                ]
                            }
                        ],
                        rules: {
                            "firstName": function (value) {
                                if (!value)
                                {return false;}
                                if (value.length > 128) {
                                    $$('profileForm').elements.firstname.config.invalidMessage = 'Maksimalan broj karaktera je 128!';
                                    return false;
                                }
                                return true;
                            },
                            "lastName": function (value) {
                                if (!value)
                                {return false;}
                                if (value.length > 128) {
                                    $$('profileForm').elements.lastname.config.invalidMessage = 'Maksimalan broj karaktera je 128!';
                                    return false;
                                }
                                return true;
                            },

                        }}]}]
        }},
    saveChanges:function() {
        if ($$("profileForm").validate()) {
            var helpUser = userData;
            helpUser.firstName = $$("profileForm").getValues().firstName;
            helpUser.lastName = $$("profileForm").getValues().lastName;
            connection.sendAjax("PUT", "api/user/" + helpUser.id,
                function (text, data, xhr) {
                    if (text) {
                        util.dismissDialog('changeProfileDialog');
                        util.messages.showMessage("Podaci su uspješno izmijenjeni.");
                        userData = helpUser;
                        console.log('roleID=' + userData.roleId);
                        if(userData.roleId===1){
                            $$("userInfo").setHTML("<p style='display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>"+userData.firstName+" "+userData.lastName+"<br> Administrator sistema</p>");
                        }
                        else if(userData.roleId===2){
                            $$("userInfo").setHTML("<p style='display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>"+userData.firstName+" "+userData.lastName+"<br> Administrator</p>");
                        }
                        else if(userData.roleId===3){
                            $$("userInfo").setHTML("<p style='display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>"+userData.firstName+" "+userData.lastName+"<br> Korisnik</p>");
                        }
                    } else
                        util.messages.showErrorMessage("Podaci nisu izmijenjeni.");
                }, function (text, data, xhr) {
                    util.messages.showErrorMessage(text);
                }, helpUser);
        }
    },

    changePasswordDialog: {
        view: "popup",
        id: "changePasswordDialog",
        modal: true,
        position: "center",
        body: {
            id: "changePasswordInside",
            rows: [{
                view: "toolbar",
                cols: [{
                    view: "label",
                    label: "<span class='fa fa-key'></span> Izmjena lozinke",
                    width: 400
                }, {}, {
                    hotkey: 'esc',
                    view: "icon",
                    icon: "close",
                    align: "right",
                    click: "util.dismissDialog('changePasswordDialog');"
                }]
            }, {
                view: "form",
                id: "changePasswordForm",
                width: 600,
                elementsConfig: {
                    labelWidth: 200,
                    bottomPadding: 18
                },
                elements: [
                    {
                        view: "text",
                        type:"password",
                        id: "oldPassword",
                        name: "oldPassword",
                        label: "Lozinka: ",
                        invalidMessage: "Unesite lozinku!",
                        required: true
                     },
                    {
                        id: "newPassword1",
                        name: "newPassword1",
                        view: "text",
                        type:"password",
                        label: "Nova lozinka: ",
                        invalidMessage: "Unesite lozinku!",
                        required: true
                    }, {
                        id: "newPassword2",
                        name: "newPassword2",
                        view: "text",
                        type:"password",
                        label: "Potvrdite novu lozinku: ",
                        invalidMessage: "Unesite lozinku!",
                        required: true
                    }, {
                        margin: 5,
                        cols: [{}, {
                            id: "savePasswordBtn",
                            view: "button",
                            value: "Sačuvajte",
                            type: "form",
                            click: "profileView.save",
                            hotkey: "enter",
                            width: 150
                        }]
                    }],
                rules: {
                    "oldPassword":function (value) {
                        if (!value)
                            return false;
                        return true;
                    },
                    "newPassword1":function (value) {
                        var re1 = /[0-9]/;
                        var re2 = /[a-z]/;
                        var re3 = /[A-Z]/;
                        var re4=/[@#$%^&+=]/;
                        if (!value)
                            return false;
                        if(value.length<8) {
                            $$('changePasswordForm').elements.newPassword1.config.invalidMessage = 'Lozinka mora da ima više od 8 karaktera!';
                            return false;
                        }
                        if(!re1.test(value)){
                            $$('changePasswordForm').elements.newPassword1.config.invalidMessage = 'Lozinka mora da sadrži bar jedan broj!';
                            return false;
                        }
                        if(!re2.test(value)){
                            $$('changePasswordForm').elements.newPassword1.config.invalidMessage = 'Lozinka mora da sadrži bar jedno malo slovo!';
                            return false;
                        }
                        if(!re3.test(value)){
                            $$('changePasswordForm').elements.newPassword1.config.invalidMessage = 'Lozinka mora da sadrži bar jedno veliko slovo!';
                            return false;
                        }
                        if(!re4.test(value)){
                            $$('changePasswordForm').elements.newPassword1.config.invalidMessage = 'Lozinka mora da sadrži specijalni karakter: (@ # $ % ^ & + =) !';
                            return false;
                        }
                        return true;
                    },
                    "newPassword2":function (value) {
                        if (!value)
                            return false;
                        if(value!=$$("changePasswordForm").getValues().newPassword1)
                        {
                            $$('changePasswordForm').elements.newPassword2.config.invalidMessage = 'Unešene lozinke nisu iste!';
                            return false;
                        }
                        return true;
                    },
                }
            }]
        }
    },


    hide:function(){
        $$("tmpUser").hide();
    },

    save:function(){
        if ($$("changePasswordForm").validate()) {
            var passwordInformation={
                oldPassword:$$("changePasswordForm").getValues().oldPassword,
                newPassword:$$("changePasswordForm").getValues().newPassword1,
                repeatedNewPassword:$$("changePasswordForm").getValues().newPassword2,
            };

            connection.sendAjax("POST", "api/user/updatePassword",
                function (text, data, xhr) {
                    if (text) {
                        util.messages.showMessage("Uspješna izmjena lozinke.");
                    } else
                        util.messages.showErrorMessage("Neuspješna izmjena lozinke.");
                }, function (text, data, xhr) {
                    util.messages.showErrorMessage(text);
                }, passwordInformation);
            util.dismissDialog('changePasswordDialog');


        }
    },

    notificationSettingsDialog: {
        view: "popup",
        id: "notificationSettingsDialog",
        modal: true,
        position: "center",
        body: {
            id: "notificationSettingsInside",
            rows: [{
                view: "toolbar",
                cols: [{
                    view: "label",
                    label: "<span class='fa fa-bell'></span> Podešavanje email obavještenja",
                    width: 400
                },
                    {},
                    {
                        hotkey: 'esc',
                        view: "icon",
                        icon: "close",
                        align: "right",
                        click: "util.dismissDialog('notificationSettingsDialog');"
                    }]
            }, {
                id: "notificationSettingsForm",
                view: "form",
                width: 800,
                elementsConfig: {
                    labelWidth: 350,
                    bottomPadding: 18
                },
                elements: [
                    {
                        id: "mailNotification",
                        name: "mailNotification",
                        view: "radio",
                        label: "Primanje obavještenja na email:",
                        required: true,
                        invalidMessage: "Potrebno je da odaberete jednu opciju.",
                        options: [
                            {
                                id: 1,
                                value: "Za rezervacije vezane za vozila sa moje lokacije",
                                newline: true
                            },
                            {
                                id: 2,
                                value: "Za rezervacije cijele kompanije",
                                newline: true
                            },
                            {
                                id: 3,
                                value: "Nikad",
                                newline: true
                            }
                        ]
                    },
                    {
                        margin: 5,
                        cols: [
                            {},
                            {
                                id: "saveNotificationSettings",
                                view: "button",
                                value: "Sačuvajte",
                                type: "form",
                                click: "profileView.saveNotificationSettings",
                                hotkey: "enter",
                                width: 150
                            }
                        ]
                    }
                ]
            }
            ]
        }
    },

    saveNotificationSettings:function () {
        if ($$("notificationSettingsForm").validate()) {
            var status = $$("notificationSettingsForm").getValues().mailNotification;
            webix.ajax().header({"Content-type": "application/x-www-form-urlencoded"})
                .post("api/user/mailStatus/" + userData.id, "status=" + status).then(function (data) {
                if (data.text() === "Success") {
                    userData.notificationTypeId = status;
                    util.messages.showMessage("Podešavanja uspješno izmijenjena.")
                } else {
                    util.messages.showErrorMessage("Podešavanja neuspješno izmijenjena.");
                }
            }).fail(function (error) {
                util.messages.showErrorMessage(error.responseText);
            });
        }

        util.dismissDialog('notificationSettingsDialog');
    }

};