package com.lemon.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.aspectj.bridge.MessageWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Project: java_api_framework
 * @Author: fanfan
 * @Create: 2021-07-20 15:01
 * @Desc：
 **/
public class JDBCUtils {
    public static Connection getConnection() {
        //定义数据库连接
        //Oracle：jdbc:oracle:thin:@localhost:1521:DBName
        //SqlServer：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=DBName
        //MySql：jdbc:mysql://localhost:3306/DBName";
        //定义数据库连接对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 修改数据库操作，包括插入，修改，删除
     *
     * @param sql
     */
    public static void updateData(String sql) {
        //建立连接
        Connection conn = getConnection();
        //生成QueryRunner对象
        QueryRunner qr = new QueryRunner();
        try {
            qr.update(conn, sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //关闭连接
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 查询单个字段的值
     * @param sql
     * @return 返回查询结果
     */
    public static Object querySingleData(String sql) {
        //建立连接
        Connection conn = getConnection();
        //生成QueryRunner对象
        QueryRunner qr = new QueryRunner();
        Object data = null;
        try {
            data = qr.query(conn, sql, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return data;

    }

    /**
     * 查询所有结果
     * @param sql
     * @return 返回查询结果
     */
    public static Object queryAllData(String sql) {
        //建立连接
        Connection conn = getConnection();
        //生成QueryRunner对象
        QueryRunner qr = new QueryRunner();
        List<Map<String, Object>> list = null;
        try {
            list = qr.query(conn, sql, new MapListHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;

    }


    /**
     * 查询结果中的第一条数据
     * @param sql
     * @return 返回查询结果
     */
    public static Object queryFirstData(String sql) {
        //建立连接
        Connection conn = getConnection();
        //生成QueryRunner对象
        QueryRunner qr = new QueryRunner();
        Map<String, Object> map= null;
        try {
            map = qr.query(conn, sql, new MapHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return map;

    }


    public static void main(String[] args) throws SQLException {
        //建立连接
        Connection connection = getConnection();
        //插入
        // QueryRunner queryRunner=new QueryRunner();
//        String sql="INSERT INTO member (reg_name,pwd,mobile_phone,leave_amount)VALUES('哈ha哈','25D55AD283AA400AF464C76D713C07AD','18661223890',10000);";
//        queryRunner.update(connection,sql);
        //修改
//        String sql="UPDATE member SET mobile_phone='18467548908' WHERE id='10005795';";
//        queryRunner.update(connection,sql);
        //查询操作
        //--保存查询结果中的第一条数据
//        QueryRunner qr=new QueryRunner();
//        String sql="SELECT * from member WHERE id<=10;";
//        Map<String, Object> map = qr.query(connection, sql, new MapHandler());
//        System.out.println(map);
        //---保存所有记录
//        QueryRunner qr = new QueryRunner();
//        String sql = "SELECT * from member WHERE id<=10;";
//        List<Map<String, Object>> list = qr.query(connection, sql, new MapListHandler());
//        System.out.println(list);
        //--保存单个数据的值
        QueryRunner qr = new QueryRunner();
        String sql = "SELECT COUNT(*) from member WHERE id<=10;";
        Object o = qr.query(connection, sql, new ScalarHandler<Object>());
        System.out.println(o);


    }
}
