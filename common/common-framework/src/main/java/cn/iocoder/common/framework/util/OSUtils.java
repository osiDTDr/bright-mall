package cn.iocoder.common.framework.util;

import cn.hutool.system.SystemUtil;

/**
 * 操作系统工具类
 */
public class OSUtils {

    /**
     * 取得当前主机的名称。
     *
     * @return 当前主机的名称。
     */
    public static String getHostName() {
        return SystemUtil.getHostInfo().getName();
    }

}
