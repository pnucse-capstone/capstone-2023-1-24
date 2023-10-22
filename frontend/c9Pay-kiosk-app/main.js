const { app, BrowserWindow, ipcMain } = require('electron')
const path = require('path')
const { userLogin } = require('./login.js')
const { getStoreList, storeLogin } = require('./store.js')
const { getProductList, purchase } = require("./product.js")


var win;
const createWindow = () => {
    win = new BrowserWindow({
        width: 800,
        height: 600,
        titleBarStyle: 'hiddenInset',
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
        },
    })
    moveLoginPage();
}

// ----------------------- 커스텀 메서드 -------------------------------

var userCookie;
var storeCookie;
var storeName;
var storeId;

/**
 * 로그인 로직
 */
ipcMain.on("userLogin",
    async (e, userId, password) => {
        let response = await userLogin(userId, password)
        if (response.isOk) {
            userCookie = "Authorization=" + response.json.token;
            moveStorePage();
        } else {
            e.reply("loginFail")
        }
    }
)

/**
 * 가게 리스트 획득 로직
 */
ipcMain.on("getStoreList",
    async (e) => {
        let response = await getStoreList(userCookie)
        if (response.isOk) {
            e.reply("storeListResponse", response.json.storeDetails)
        } else {
            e.reply("invalidToken")
            moveLoginPage()
        }
    }
)

/**
 * 상품 페이지 이동 로직
 */
ipcMain.on("moveProductPage",
    async (e, id, name) => {
        let response = await storeLogin(userCookie, id);
        if (response.isOk) {
            storeCookie = "Authorization=" + response.json.token;
            storeId = id;
            storeName = name;
            moveProductPage()
        } else {
            e.reply("invalidToken")
            moveLoginPage()
        }
    }
)

/**
 * 상품 목록 획득 로직
 */
ipcMain.on("getProductList",
    async (e) => {
        let response = await getProductList(storeCookie, storeId)
        if (response.isOk) {
            e.reply("productListResponse", response.json.productDetails, storeName)
        } else {
            e.reply("invalidToken")
            moveLoginPage()
        }
    }
)

/**
 * QR 인식
 */
ipcMain.on("readQRCode",
    (e) => {

    }
)

/**
 * 구매 로직
 */
ipcMain.on("purchaseRequest",
    async (e, payload)=>{
        let response = await purchase(storeCookie, storeId, payload)
        e.reply("purchaseResponse", response.isOk)
    }
)

// ----------------- 페이지 이동 메서드 --------------------------
/**
 * 상품 페이지로 이동
 */
let moveProductPage = () => {
    // win.setSize(1200, 800)
    // win.center()
    win.setFullScreen(true);
    win.loadFile('productinfo.html')
}

/**
 * 가게 페이지로 이동
 */
let moveStorePage = () => {
    win.setSize(1200, 800)
    win.center()
    win.loadFile('storeinfo.html')
}

/**
 * 로그인 페이지로 이동
 */
let moveLoginPage = () => {
    win.setSize(600, 700)
    win.center()
    win.loadFile('login.html')
}


// ------------------------------ 필수 메서드 -------------------------------


app.whenReady().then(() => {
    createWindow()

    app.on('activate', () => {
        if (BrowserWindow.getAllWindows().length === 0) {
            createWindow()
        }
    })
})

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        app.quit()
    }
})