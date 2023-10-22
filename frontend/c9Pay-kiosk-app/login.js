const electron = require("electron");

exports.userLogin = async (id, password) => {
    const user = {
        "userId": id,
        "password": password
    }

    const response = await fetch("http://api.teamcloudnine.link/user-service/api/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(user)
    })

    let isOk = response.status === 200;
    let json = await response.json()

    let obj = {'isOk': isOk, 'json': json }
    return obj
}
function getUserInfo(cookie) {
    const baseUrl = "http://api.teamcloudnine.link/user-service/api";

    electron.session.defaultSession.cookies.get({ url: '' }).then(()=> {
        fetch(baseUrl + "/user", {
            method: "GET",
            credentials: "include",
            headers: {
                cookie: cookie
            }
        }).then((response) => {
            response.text().then((text) => {
                console.log(text)
            })
        });
    })
}