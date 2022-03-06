package com.lik.util;

import cn.hutool.core.lang.Snowflake;

public class IdUtils {

    /**
     * 雪花算法生成
     *
     * @return
     */
    public static String snfId() {
        // 参数1为终端ID
        // 参数2为数据中心ID
        Snowflake snowflake = cn.hutool.core.util.IdUtil.getSnowflake(1, 1);
        return String.valueOf(snowflake.nextId());
    }

}
