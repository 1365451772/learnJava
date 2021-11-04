package me.sunpeng.utils;

import java.io.Closeable;

/**
 * @description 用于关闭各种连接，缺啥补啥
 * @author sp
 * @date 2021-11-03 20:09
 */
public class CloseUtil {
    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }

    public static void close(AutoCloseable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception e) {
                // 静默关闭
            }
        }
    }
}
