package com.hxx.demo.utils;/**
 * ClassName: ParamsInitUtils <br/>
 * Description: <br/>
 * date: 2019/12/4 上午11:45<br/>
 *
 * @author Hesion<br />
 * @version
 * @since JDK 1.8
 */

import com.hxx.demo.entity.GridRequest;
import com.hxx.demo.entity.OrderCondition;
import com.hxx.demo.entity.WhereCondition;
import com.hxx.demo.entity.entityMenu.WhereConditionMethod;
import org.springframework.lang.Nullable;

/**
 * @program: college_security
 * @description:
 * @author: hesion
 * @create: 2019-12-04 11:45
 **/
public class ParamsInitUtils<T> {
    public String initParams(GridRequest gridRequest, @Nullable String tableTitle) {
        //排序条件关键字
        String orderPrefix = "Order By";
        //where语句中AND
        String whereAND = " AND ";

        //语句中的空格
        String sqlSpace = " ";
        //查询条件的sql
        String whereSql = "";
        //排序条件的sql语句
        String querySql = "";
        //最终的sql
        String sql = "";
        //查询的条件
        if(gridRequest.getWhereConditions().isEmpty() ){
           return "1=-1";
        }
        for (int i = 0; i < gridRequest.getWhereConditions().size(); i++) {
            WhereCondition whereCondition = gridRequest.getWhereConditions().get(i);

            if( whereCondition.getMethod()==WhereConditionMethod.Like){
                whereSql += sqlSpace + whereCondition.getField() + sqlSpace + whereCondition.getMethod() + sqlSpace +"\'%"+ whereCondition.getValue() +"%\'"+ whereAND;
            }else if(whereCondition.getMethod()==WhereConditionMethod.Equal){
                whereSql += sqlSpace + whereCondition.getField() + sqlSpace + "=" + sqlSpace +"\'"+ whereCondition.getValue() +"\'"+ whereAND;
            }
        }
        //去除最后的AND
        String wheresqlEnd = whereSql.substring(0,whereSql.length()-5);
        //排序规则
        querySql += orderPrefix;
        for (int i = 0; i < gridRequest.getOrderConditions().size(); i++) {
            OrderCondition orderCondition = gridRequest.getOrderConditions().get(i);
            querySql += sqlSpace + orderCondition.getField() + sqlSpace + orderCondition.getMethod() + ",";
        }
        //取出最后的逗号
        String querySqlEnd = querySql.substring(0, querySql.length() - 1);
        sql +=  wheresqlEnd+sqlSpace+querySqlEnd;
        return sql;
    }
}
