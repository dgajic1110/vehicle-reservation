var MENU_STATES = {
    COLLAPSED: 0,
    EXPANDED: 1
};
var menuState = MENU_STATES.COLLAPSED;

var userData = null;
var companyData = null;
var userForRegistration=null;

var menuActions = function (id) {

    switch (id) {
        case "company":
            companyView.selectPanel();
            break;
        case "building":
            buildingView.selectPanel();
            break;
        case "gear":
            gearView.selectPanel();
            break;
        case "logger":
            loggerView.selectPanel();
            break;
        case "dashboard":
            dashboardView.selectPanel();
            break;
        case "registration":
            registrationView.selectPanel();
            break;
        case "user":
            userView.selectPanel();
            break;
        case "report":
            reportView.selectPanel();
            break;
    }
};

var menuSuperAdmin = [
    {
        id: "company",
        value: "Kompanije",
        icon: "briefcase"
    },{
        id: "logger",
        value: "Logger korisničkih akcija",
        icon: "history"
    },
    {
        id:"report",
        value:"Izvještaj",
        icon:"percent"
    }
];

var menuAdmin = [
    {
        id: "dashboard",
        value: "Početna",
        icon: "home"
    },

    {
        id: "building",
        value: "Zgrade",
        icon: "building"
    },
    {
        id: "room",
        value: "Sale",
        icon: "cube"
    },
    {
        id: "gear",
        value: "Oprema",
        icon: "wrench"
    }, {
        id: "logger",
        value: "Logger korisničkih akcija",
        icon: "history"
    }, {
        id: "user",
        value: "Korisnici",
        icon: "user"
    },
    {
        id:"report",
        value:"Izvještaj",
        icon:"percent"
    }
];

var menuRegularUser = [
    {
        id: "dashboard",
        value: "Početna",
        icon: "home"
    }
];

var menuRegistration=[
    {
        id:"registration",
        value:"Registracija",
        icon:"user"
    }
];

var panel = {id: "empty"};
var rightPanel = null;

var init = function () {
    console.log('init');
    if (!webix.env.touch && webix.ui.scrollSize) webix.CustomScroll.init();
    webix.i18n.parseFormat = ("%d.%m.%Y.")
    webix.i18n.setLocale("sr-SP");
    webix.Date.startOnMonday = true;
    webix.ui(panel);
    panel = $$("empty");
    const urlQuery=window.location.search;
    if (urlQuery && urlQuery.startsWith('?q=reg')){
        const token=urlQuery.split('=')[2];
        connection.sendAjax("GET","api/user/check/"+token).then(result=> {
            const userId=result.json();
        showRegistration(userId);
    }).fail(err=> {
            util.messages.showErrorMessage("Token je istekao ili nije validan!");
        checkState();
    });
    }else{
        checkState();
    }

};

const checkState=function(){
    console.log('checkState');
    connection.sendAjax("GET","api/user/state").then(data=> {
        userData = data.json();
    showApp();
    }).fail(err=> {
        showLogin();
    });
};

const menuEvents = {
    onItemClick: function (item) {
        menuActions(item);
    }
};

const showLogin = function () {
    const login = webix.copy(loginLayout);
    webix.ui(login, panel);
    panel = $$("login");

};

const showRegistration = function (userId) {
    const registration=webix.copy(registrationLayout);
    webix.ui(registration,panel);
    panel=$$("registration");
    $$("registrationForm").setValues({
        id:userId
    });

};

const showApp = function () {
    const promise = preloadDependencies();
    const main = webix.copy(mainLayout);
    webix.ui(main, panel);
    panel = $$("app");
    let localMenuData = null;
    webix.ui({
        id: "menu-collapse",
        view: "template",
        template: '<div id="menu-collapse" class="menu-collapse">' +
            '<span></span>' +
            '<span></span>' +
            '<span></span>' +
            '</div>',
        onClick: {
            "menu-collapse": function (e, id, trg) {
                const elem = document.getElementById("menu-collapse");
                if (menuState == MENU_STATES.COLLAPSED) {
                    elem.className = "menu-collapse open";
                    menuState = MENU_STATES.EXPANDED;
                    $$("mainMenu").toggle();
                } else {
                    elem.className = "menu-collapse";
                    menuState = MENU_STATES.COLLAPSED;
                    $$("mainMenu").toggle();
                }
            }
        }
    });
    switch (userData.roleId) {
        case role.systemAdministrator:
            localMenuData = menuSystemAdmin;
            break;
        case role.companyAdministrator:
            localMenuData=menyCompanyAdmin;
            $$("showReportBtn").show();
            break;
        case role.user:
            localMenuData=menuCompanyUser;
            break;
    }
    $$("mainMenu").define("data", localMenuData);
    $$("mainMenu").define("on", menuEvents);
    rightPanel = "emptyRightPanel";
    promise.then(value=> {
        if (userData.roleId === role.systemAdministrator) {
        companyView.selectPanel();
        $$("mainMenu").select("company");
    }else{
        locationView.selectPanel();
        $$("mainMenu").select("dashboard");
    }
}).fail(err=> {
        //   connection.reload();
    });

};

const preloadDependencies = function () {
    const promises=[];
    promises.push(connection.sendAjax("GET","api/role").then(data=> {
        const roles = [];
    const array = [];
    data.json().forEach(obj=> {
        roles[obj.id] = obj.value;
    array.push(obj);
});
    dependencyMap["role"] = roles;
    dependency["role"] = array;

}));
    promises.push(connection.sendAjax("GET","api/status").then(data=> {
        const status = [];
    const array = [];

    data.json().forEach(obj=> {
        status[obj.id] = obj.value;
    array.push(obj);
});
    dependencyMap["status"] = status;
    dependency["status"] = array;
}));
    promises.push(connection.sendAjax("GET","api/expense-type").then(data=> {
        const expenseTypes = [];
    const array = [];

    data.json().forEach(obj=> {
        expenseTypes[obj.id] = obj.value;
    array.push(obj);
});
    dependencyMap["expenseType"] = expenseTypes;
    dependency["expenseType"] = array;
}));
    promises.push(connection.sendAjax("GET","api/notification-type").then(data=> {
        const notificationTypes = [];
    const array = [];
    data.json().forEach(obj=> {
        notificationTypes[obj.id] = obj.value;
    array.push(obj);
});
    dependencyMap["notificationType"] = notificationTypes;
    dependency["notificationType"] = array;

}));
    promises.push(connection.sendAjax("GET","api/fuel-type").then(data=> {
        const fuel = [];
    const array = [];
    data.json().forEach(obj=> {
        fuel[obj.id] = obj.value;
    array.push(obj);
});
    dependencyMap['fuelType'] = fuel;
    dependency['fuelType'] = array;

}));
    return webix.promise.all(promises);

};

//main call
window.onload = function () {
    init();
};

var loginLayout = {
    id: "login",
    width: "auto",
    height: "auto",
    rows: [
        {
            cols: [
                {},
                {
                    height: 60,
                    view: "label",
                    label: "Schedule Up",
                    css: "appNameLabel"
                }
            ]
        },
        {
            cols: [
                {},
                {
                    view: "form",
                    id: "loginForm",
                    width: 400,
                    elementsConfig: {
                        labelWidth: 140,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            id: "username",
                            name: "username",
                            view: "text",
                            label: "Korisničko ime:",
                            invalidMessage: "Korisničke ime je obavezno!",
                            required: true
                        },
                        {
                            id: "password",
                            name: "password",
                            view: "text",
                            type: "password",
                            label: "Lozinka:",
                            invalidMessage: "Lozinka je obavezna!",
                            required: true
                        },
                        {
                            id: "companyName",
                            name: "companyName",
                            view: "text",
                            label: "Kompanija:"
                        }, {
                            margin: 5,
                            cols: [
                                {
                                    id: "registerBtn",
                                    view: "button",
                                    value: "Registrujte se",
                                    type: "form",
                                    click: "register",
                                    width: 150
                                },
                                {},
                                {
                                    id: "loginBtn",
                                    view: "button",
                                    value: "Prijavite se",
                                    type: "form",
                                    click: "login",
                                    align:"right",
                                    hotkey: "enter",
                                    width: 150
                                }
                                ]
                        },
                        {
                            margin: 5,
                            cols:[
                                {},
                                {
                                    width: 150,
                                    align:"right",
                                    view:"label",
                                    label:"<a href='javascript:showForgottenPasswordPopup();'>Zaboravili ste lozinku?</a>"
                                }

                            ]
                        }
                    ]
                }
                ,
                {}
            ]
        }
    ]
};
var register = function () {
    var register = webix.copy(registrationLayout);
    webix.ui(register, panel);
    panel = $$("registration");
};
var registrationLayout={
    id: "registration",
    width: "auto",
    height: "auto",
    rows: [
        {
            cols: [

                {
                    height: 60,
                    align:"center",
                    view: "label",
                    label: "Schedule Up",
                    css: "appNameLabel"
                }
            ]

        },
        {
            cols: [
                {},
                {
                    view: "form",
                    id: "tokenForm",
                    width: 400,
                    elementsConfig: {
                        labelWidth: 60,
                        bottomPadding: 18
                    },
                    elements: [
                        {
                            id: "token",
                            name: "token",
                            view: "text",
                            label: "Token",
                            invalidMessage: "Token je obavezan!",
                            required: true
                        },
                        {
                            margin: 5,
                            cols: [{}, {
                                id: "registerBtn",
                                view: "button",
                                align:"center",
                                value: "Potvrdi",
                                type: "form",
                                click: "tokenConfirm",
                                hotkey: "enter",
                                width: 150
                            },{}]
                        }]},{}]}]
};
var tokenConfirm = function () {
    var token=($$("tokenForm")).getValues().token;
    webix.ajax().get("user/registration/"+token, {
        success: function (text, data, xhr) {
            var jsonData = data.json();
            userForRegistration=jsonData;
            if(userForRegistration==null){
                util.messages.showErrorMessage("Neispravan ili istekao token.")

            }else{
                webix.ajax().get("company/" + userForRegistration.companyId, {
                    success: function (text, data, xhr) {
                        var company = data.json();
                        if (company != null) {
                            companyData = company;
                            companyData.deleted = 0;
                            showApp();
                        } else {
                            userForRegistration=null;
                            showLogin();

                        }
                    },
                    error: function (text, data, xhr) {
                        userData = null;
                        showLogin();
                    }
                });
            }
        },
        error: function (text, data, xhr) {
            util.messages.showErrorMessage(text);
        }
    });

};
var login = function () {

    console.log($$("loginForm").getValues());
    if ($$("loginForm").validate()) {
        webix.ajax().headers({
            "Content-type": "application/json"
        }).post("user/login", $$("loginForm").getValues(), {
            success: function (text, data, xhr) {
                var user = data.json();
                console.log(user);
                if (user != null) {
                    if (user.roleId === 1) {
                        userData = user;
                        companyData = null;
                        showApp();
                        $$("userInfo").setHTML("<p style='display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>"+userData.firstName+" "+userData.lastName+"<br> super admin</p>");

                    } else {
                        webix.ajax().get("company/" + user.companyId, {
                            success: function (text, data, xhr) {
                                var company = data.json();
                                if (company != null) {
                                    userData = user;
                                    companyData = company;
                                    companyData.deleted = 0;
                                    showApp();
                                    if(userData.roleId===2)
                                        $$("userInfo").setHTML("<p style='display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>"+userData.firstName+" "+userData.lastName+"<br> administrator</p>");
                                    else if(userData.roleId===3)
                                        $$("userInfo").setHTML("<p style='display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>"+userData.firstName+" "+userData.lastName+"<br> napredni korisnik</p>");
                                    else $$("userInfo").setHTML("<p style='display: table-cell; line-height: 13px; vertical-align: text-top; horizontal-align:right;font-size: 14px; margin-left: auto;margin-right: 0;}'>"+userData.firstName+" "+userData.lastName+"<br> korisnik</p>");

                                } else {
                                    util.messages.showErrorMessage("Prijavljivanje nije uspjelo!");

                                }
                            },
                            error: function (text, data, xhr) {
                                util.messages.showErrorMessage("Prijavljivanje nije uspjelo!");
                            }
                        });
                    }
                } else {
                    util.messages.showErrorMessage("Prijavljivanje nije uspjelo!");
                }
            },
            error: function (text, data, xhr) {
                console.log("NIJE" + text);
                util.messages.showErrorMessage("Prijavljivanje nije uspjelo!");
            }
        });
    }

};

var logout = function () {
    webix.ajax().get("user/logout", function (text, data, xhr) {
        if (xhr.status == "200") {
            userData = null;
            companyData = null;
            util.messages.showLogoutMessage();
            connection.reload();
        }
    });
};
var mainLayout = {
    id: "app",
    width: "auto",
    height: "auto",
    rows: [
        {
            cols: [{
                view: "template",
                width: 240,
                css: "logoInside",
                template: '<img id="appLogo" src="img/telegroup-logo.png"/>'
            }, {
                view: "toolbar",
                css: "mainToolbar",
                height: 50,
                cols: [
                    {
                        id: "appNameLabel",
                        view: "label",
                        css: "appNameLabel",
                        label: "Schedule Up"
                    },
                    {},{},{
                            id: "userInfo",
                            view: "label",
                            align: "right",
                        labelPosition:"top",
                        css:"custom_menu_alignment_style",
                            label: ""
                    },
                    {view:"menu",
                        id:"userMenu",
                        align:"right",
                        width:50,
                        css:"custom_menu_list_item",
                        data:[
                            {id:"1",value:"",icon:"cog",config:{  width:200  }, submenu:[

                                     {value:"Izmjena profila", icon:"user",autowidth:true},{value:"Izmjena lozinke",icon:"key",width:400},{value:"Odjavite se", icon:"sign-out",width:400}

                                ]}
                        ],
                        openAction:"click",
                        on:{
                            onMenuItemClick:function(id){
                                switch (this.getMenuItem(id).value) {
                                    case "Izmjena profila":
                                        clickProfile();
                                        break;
                                    case "Izmjena lozinke":
                                        clickPassword();
                                        break;
                                    case "Odjavite se":
                                        logout();
                                        break;
                                }
                            }
                        },
                        type:{
                            subsign:true
                        }}
                ]
            }
            ]

        },
        {
            id: "main", cols: [{
                rows: [
                    {id: "mainMenu", css: "mainMenu", view: "sidebar", gravity: 0.01, minWidth: 41, collapsed: true},
                    {
                        id: "sidebarBelow",
                        css: "sidebar-below",
                        view: "template",
                        template: "",
                        height: 50,
                        gravity: 0.01,
                        minWidth: 41,
                        width: 41,
                        type: "clean"
                    }
                ]
            },
                {id: "emptyRightPanel"}
            ]
        }
    ]
};
var clickProfile=function(){
    webix.ui(webix.copy(profileView.changeProfileDialog));
    $$("profileForm").load("user/"+userData.id);
    $$("photo").setValues({src:"data:image/png;base64,"+userData.photo});
    setTimeout(function () {
        $$("changeProfileDialog").show();
    }, 0);
};
var clickPassword=function(){

    webix.ui(webix.copy(profileView.changePasswordDialog));

    setTimeout(function () {
        $$("changePasswordDialog").show();
    }, 0);


};
/*
var menuEvents = {
    onItemClick: function (item) {
        menuActions(item);
    }
};
*/
/*
var showLogin = function () {
    var login = webix.copy(loginLayout);
    webix.ui(login, panel);
    panel = $$("login");
};
*/
/*
var showApp = function () {
    var main = webix.copy(mainLayout);
    webix.ui(main, panel);
    panel = $$("app");
    if (companyData != null)
        document.getElementById("appLogo").src = "data:image/jpg;base64," + companyData.companyLogo;
    var localMenuData = null;
    if(userData!=null)
    {
    switch (userData.roleId) {
        case 1:
            localMenuData = webix.copy(menuSuperAdmin);
            break;
        case 2:
            localMenuData = webix.copy(menuAdmin);
            break;
        case 3:
            localMenuData = webix.copy(menuAdvancedUser);
            break;
        case 4:
            localMenuData = webix.copy(menuUser);
            break;
    }}
    else if(userForRegistration!=null) localMenuData = webix.copy(menuRegistration);
    webix.ui({
        id: "menu-collapse",
        view: "template",
        template: '<div id="menu-collapse" class="menu-collapse">' +
        '<span></span>' +
        '<span></span>' +
        '<span></span>' +
        '</div>',
        onClick: {
            "menu-collapse": function (e, id, trg) {
                var elem = document.getElementById("menu-collapse");
                if (menuState == MENU_STATES.COLLAPSED) {
                    elem.className = "menu-collapse open";
                    menuState = MENU_STATES.EXPANDED;
                    $$("mainMenu").toggle();
                } else {
                    elem.className = "menu-collapse";
                    menuState = MENU_STATES.COLLAPSED;
                    $$("mainMenu").toggle();
                }
            }
        }
    });

    $$("mainMenu").define("data", localMenuData);
    $$("mainMenu").define("on", menuEvents);

    rightPanel = "emptyRightPanel";
    if(userData!=null)
    {
    if (userData.roleId === 1) {
        companyView.selectPanel();
        $$("mainMenu").select("company");
    } else {
        dashboardView.selectPanel();
        $$("mainMenu").select("dashboard");
    }
    }
    else if(userForRegistration!=null){
        registrationView.selectPanel();
        $$("mainMenu").select("registration");
        $$("userMenu").hide();
        $$("userInfo").hide();
    }
};
*/
var showForgottenPasswordPopup=function(){
    if (util.popupIsntAlreadyOpened("forgottenPasswordPopup")){
        webix.ui(webix.copy(forgottenPasswordPopup)).show();
        $$("forgottenPasswordForm").focus();
    }
};

var forgottenPasswordPopup={
    view:"popup",
    id:"forgottenPasswordPopup",
    modal:true,
    position:"center",
    body:{
        rows:[
            {
                view:"toolbar",
                cols:[
                    {
                        view:"label",
                        label:"Zaboravljena lozinka",
                        width:400
                    },
                    {},
                    {
                        view:"icon",
                        icon:"close",
                        align:"right",
                        hotkey:"esc",
                        click:"util.dismissDialog('forgottenPasswordPopup');"
                    }
                ]
            },
            {
                width:400,
                view:"form",
                id:"forgottenPasswordForm",
                elementsConfig: {
                    labelWidth: 150,
                    bottomPadding: 18
                },
                elements:[
                    {

                        view:"text",
                        id:"username",
                        name:"username",
                        required:"true",
                        label:"Korisničko ime:",
                        invalidMessage:"Korisničko ime je obavezno!"
                    },
                    {

                        view:"text",
                        id:"companyName",
                        name:"companyName",
                        required:"true",
                        label:"Kompanija:",
                        invalidMessage:"Kompanija je obavezna!"
                    },
                    {
                        cols:[
                            {},
                            {
                                view:"button",
                                value:"Generišite lozinku",
                                type:"form",
                                click:"generatePassword();",
                                hotkey: "enter",
                            }
                        ]
                    }
                ]
            }
        ]
    }
};

var generatePassword= function(){
    var form=$$("forgottenPasswordForm");
    if (form.validate()) {
        var loginInformation = JSON.stringify(form.getValues());
        webix.ajax().headers({
            "Content-type": "application/json"
        }).post("user/resetPassword", loginInformation).then(function (result) {
            if (result.text()) {
                util.messages.showMessage("Uspješno ste resetovali lozinku. Provjerite vaš e-mail.");
            } else {
                util.messages.showErrorMessage("Greška prilikom resetovanja lozinke!");
            }
        }).fail(function (error) {
            util.messages.showErrorMessage(error.responseText);
        });
        util.dismissDialog("forgottenPasswordPopup");
    }
};

//main call
window.onload = function () {
    init();
};

