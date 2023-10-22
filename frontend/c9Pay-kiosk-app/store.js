
exports.getStoreList = async (userCookie) => {
    const baseUrl = "http://api.teamcloudnine.link/store-service/api/store";

    const response = await fetch(baseUrl, {
        method: "GET",
        headers: {
            cookie: userCookie
        }
    })

    let isOk = response.status === 200;
    let json = await response.json()
    console.log(json)
    let obj = {'isOk': isOk, 'json': json }
    return obj
}

exports.storeLogin = async (userCookie, id) => {
    let baseUrl = "http://api.teamcloudnine.link/store-service/api/" + id + "/login";

    const response = await fetch(baseUrl, {
        method: "POST",
        credentials: "include",
        headers: {
            cookie: userCookie
        }
    })

    let isOk = response.status === 200;
    let json = await response.json()

    let obj = {'isOk': isOk, 'json': json }
    return obj
}