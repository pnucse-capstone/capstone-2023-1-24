package com.example.c9pay.module

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

fun createQRCode(QRstring : String, QRview : ImageView){
    val qrCode = QRCodeWriter()
    val bitMtx = qrCode.encode(QRstring,
        BarcodeFormat.QR_CODE, 512, 512)
    val bitmap: Bitmap = Bitmap.createBitmap(bitMtx.width, bitMtx.height, Bitmap.Config.RGB_565)

    for(i in 0 until bitMtx.width){
        for(j in 0 until bitMtx.height){
            var color = 0
            color = if(bitMtx.get(i, j)) {
                Color.BLACK
            } else {
                Color.WHITE
            }
            bitmap.setPixel(i, j, color)
        }
    }

    QRview?.setImageBitmap(bitmap)
}