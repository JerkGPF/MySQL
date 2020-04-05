package com.gpfei.mysql;


import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBUtils {
    private static final String TAG = "DBUtils";
    private static Connection getConnection(String dbName) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动
            String ip = "192.168.2.201";//局域网ip地址
            conn = DriverManager.getConnection(
                    "jdbc:mysql://" + ip + ":3306/" + dbName,
                    "root", "root1996");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }
    public static List<Score> getUserInfoByCid(String id) {//计算课程的平均成绩
        try {
            Connection con = getConnection("school");//连接数据库
            PreparedStatement preparedStatement = con.prepareStatement("select * from score where c_id = '" + id + "'");
            ResultSet rs = preparedStatement.executeQuery();
            List<Score> list = new ArrayList<>();
            while (rs.next()){
                Score score = new Score();
                score.setMark(rs.getString("mark"));
                list.add(score);
            }
            con.close();
            preparedStatement.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
        }
        throw new RuntimeException("查询出异常！");
    }
    public static List<Score> getUserInfoByid(String id) {//通过学号计算学生的平均成绩
        try {
            Connection con = getConnection("school");//连接数据库
            PreparedStatement preparedStatement = con.prepareStatement("select mark,s_name from score,student where score.s_id = student.s_id and student.s_id = '"+ id +"'");
            ResultSet rs = preparedStatement.executeQuery();
            List<Score> list = new ArrayList<>();
            while (rs.next()){
                Score score = new Score();
                score.setS_name(rs.getString("s_name"));
                score.setMark(rs.getString("mark"));
                list.add(score);
            }
            con.close();
            preparedStatement.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, " 数据操作异常");
        }
        throw new RuntimeException("查询出异常！");
    }
    public static List<Student>getAll(){//输出所有学生信息
        try {
            Connection con = getConnection("school");//连接数据库
            PreparedStatement preparedStatement = con.prepareStatement("select * from student");
            ResultSet rs = preparedStatement.executeQuery();
            List<Student> list = new ArrayList<Student>();//用数组遍历字段
            while (rs.next()){
                Student student = new Student();
                student.sets_id(rs.getString("s_id"));
                student.setS_name(rs.getString("s_name"));
                student.setS_sex(rs.getString("s_sex"));
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("s_birth"));//时间输出格式化
                String time = new SimpleDateFormat("yyyy年MM月dd日").format(date);//时间输出格式化
                student.setS_birth(time);
                student.setS_class(rs.getString("s_class"));
                list.add(student);
            }
            con.close();
            preparedStatement.close();
            rs.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("查询出异常！");
    }
    public static void insertData(String id,String name,String sex,String birthday,String s_class){
        try{
            Connection con = getConnection("school");//连接数据库
            PreparedStatement preparedStatement = con.prepareStatement("insert into student(s_id,s_name,s_sex,s_birth,s_class) values(?,?,?,?,?)");
            preparedStatement.setString(1,id);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,sex);
            String brith = birthday;
            brith = brith+" 00:00:00";
            Timestamp time = Timestamp.valueOf(brith);
            //将时间转换进行存储
            preparedStatement.setTimestamp(4, time);
            preparedStatement.setString(5,s_class);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

