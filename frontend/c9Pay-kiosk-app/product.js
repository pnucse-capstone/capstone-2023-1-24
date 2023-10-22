exports.getProductList = async (storeCookie, id) => {
    const baseUrl = "http://api.teamcloudnine.link/store-service/api/" + id + "/product";

    const response = await fetch(baseUrl, {
        method: "GET",
        headers: {
            cookie: storeCookie
        }
    })

    let isOk = response.status === 200;
    let json = await response.json()
    console.log(json)
    let obj = {'isOk': isOk, 'json': json }
    return obj
}

exports.purchase = async (storeCookie, id, payload) => {
    const baseUrl = "http://api.teamcloudnine.link/store-service/api/" + id + "/product/sale";
    console.log(payload)
    const response = await fetch(baseUrl, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            cookie: storeCookie
        },
        body: payload
    })

    let isOk = response.status === 200;
    let json = await response.json()
    console.log(json)
    let obj = {'isOk': isOk, 'json': json }
    return obj
}