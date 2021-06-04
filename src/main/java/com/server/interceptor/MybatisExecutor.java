package com.server.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Component
@Intercepts(
        @Signature(type = Executor.class
                , method = "update"
                , args = {MappedStatement.class, Object.class})
)
public class MybatisExecutor implements Interceptor {

    //private final static String[] NOTIFY_EVENT_NAME = {"INSERT", "UPDATE", "DELETE"};
    private final static List<String> NOTIFY_EVENT_NAME = new ArrayList<>();
    static {
        NOTIFY_EVENT_NAME.add("INSERT");
        NOTIFY_EVENT_NAME.add("UPDATE");
        NOTIFY_EVENT_NAME.add("DELETE");
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        String name = sqlCommandType.name();
        boolean contains = NOTIFY_EVENT_NAME.contains(name);
        if(contains){
            //证明数据发生变化，可以通过webSocket向前端通知
        }
        /*String id = mappedStatement.getId();
        System.out.println("id :" + id);
         id ==  com.server.dao.UserMapper.insert
         截取字符串  com.server.dao.UserMapper
                    insert 是方法名
         可以根据反射获取当前方法是否需要监控
        */
        /*BoundSql boundSql = mappedStatement.getBoundSql(invocation.getArgs()[1]);
        String sql = boundSql.getSql();
        System.out.println("sql :"  + sql);*/
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
