package com.blogger.aiweiergou.pattern.immutableobject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunyinjie on 2017/9/23.
 */
public final class MMSCRouter {
    //volatile修饰，保证多线程环境下的可见性
    private static volatile MMSCRouter instance = new MMSCRouter();
    //路由映射关系
    private final Map<String, MMSCInfo> routerMap;

    public MMSCRouter() {
        //数据加载
        this.routerMap = MMSCRouter.retrieveRouteMapFromDb();
    }

    private static Map<String, MMSCInfo> retrieveRouteMapFromDb() {
        Map<String, MMSCInfo> map = new HashMap<String, MMSCInfo>();
        //....
        return map;
    }

    public static MMSCRouter getInstance() {
        return instance;
    }

    /**
     * 根据参数获取对应的信息
     * @param arg
     * @return
     */
    public MMSCInfo getMMSC(String arg) {
        return routerMap.get(arg);
    }

    /**
     * 将当前mmscrouter的实例更新为指定的新实例
     * @param newInstance
     */
    public static void setInstance(MMSCRouter newInstance) {
        instance = newInstance;
    }

    private static Map<String, MMSCInfo> deepCopy(Map<String, MMSCInfo> m) {
        Map<String, MMSCInfo> result = new HashMap<String, MMSCInfo>();
        for (String key : m.keySet()) {
            result.put(key, new MMSCInfo(m.get(key)));
        }
        return result;
    }

    public Map<String,MMSCInfo> getRouterMap() {
        //做防御性复制
        return Collections.unmodifiableMap(deepCopy(routerMap));
    }
}
