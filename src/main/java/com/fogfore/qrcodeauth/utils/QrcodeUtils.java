package com.fogfore.qrcodeauth.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

@Slf4j
public class QrcodeUtils {
    public static BufferedImage generateQrcode(String content) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.MARGIN, 0);
        BufferedImage image = null;
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 100, 100, hints);
            image = MatrixToImageWriter.toBufferedImage(matrix);
//            ImageIO.write(image, "jpg", new File("D://test.png"));
        } catch (Exception e) {
            log.error("生成二维码失败", e);
        }
        return image;
    }

    public static void generateQrcodePic(String content) {

        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.MARGIN, 0);

        try {
            // 构造二维字节矩阵
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 100, 100, hints);

            // 将二位字节矩阵按照指定图片格式，写入指定文件目录，生成二维码图片
            MatrixToImageWriter.writeToFile(bitMatrix, "png", new File("F://test.png"));
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}
