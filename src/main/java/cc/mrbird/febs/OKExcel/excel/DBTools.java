package cc.mrbird.febs.OKExcel.excel;


import java.sql.*;


public class DBTools {

        public static void main(String[] args) {
            Connection conn = null;
            Statement stmt = null;
            //使用jdbc连接数据库分为六步走
            //DriverManager.registerDriver(new        com.mysql.jdbc.Driver());第一种注册驱动的方法
            //注册驱动 （就是告诉java的虚拟机我连接是什么数据库或是....其它）
            try {
                //注册驱动 （就是告诉java的虚拟机我连接是什么数据库或是）*
//                Driver driver = new com.mysql.jdbc.Driver();//采用了多态的机制子类型的引用指向父类型的对象
//                DriverManager.registerDriver(driver);//在这里会报错我们使用trycatch包围
                Class.forName("com.mysql.jdbc.Driver");//加载驱动
                //获取连接  （此时java的虚拟机进程环和数据库进程的通道被打开属于进程间的通信是重量级的）
                String url = "jdbc:mysql://localhost:3306/febs_base?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&allowMultiQueries=true&serverTimezone=GMT%2b8";//localhost也可以填写ip号：是端口 zhiyong是我创建的数据库
                String user = "root";//用户名我的是root
                String password = "111111";//密码我的是root
                conn = DriverManager.getConnection(url,user,password);//到了这里我们可以先执行编一下看是否连接到数据库
                System.out.println("数据库连接对象"+ conn);
                //在此处获取连接就是填写你的ip地址 端口号 你创建的数据库 以及数据库的用户名和密码
                //获取数据库的操作对象 （就是要有一个对象去执行sql语句）
                stmt = conn.createStatement();
                //执行sql （就是dql）增 删 改
                String sql = "select *  from febs_base.db_image";
                ResultSet rs=stmt.executeQuery(sql);     //将sql语句传至数据库，返回的值为一个字符集用一个变量接收
                while (rs.next()){

                    System.out.println(rs.getString(1)+" "+
                            rs.getString(2)+" "+
                            rs.getString(3)+" "+
                            rs.getString(4)+" "+
                            rs.getString(5)+" "+
                            rs.getString(6)+" "+
                            rs.getString(7)+" "+
                            rs.getString(8)+" "+
                            rs.getString(9)+" "+
                            rs.getString(10)+" "+
                            rs.getString(11)+" "+
                            rs.getString(12)+" "+
                            rs.getString(13)+" "+
                            rs.getString(14)+" "+
                            rs.getString(15)+" "+
                            rs.getString(16)+" "+
                            rs.getString(17)+" "+
                            rs.getString(18)+" "+
                            rs.getString(19)+" "+
                            rs.getString(20)+" "+
                            rs.getString(21)+" "+
                            rs.getString(22)+" "+
                            rs.getString(23)+" ");
                }
                //处理查询的结果集 （当第四部执行select的操作时就执行）
                //因为这是增删改操作所以没有第五步
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                /* 释放资源 （就是数据库连接使用结束后要关闭） */
                //在释放资源的时候我们要按从小到大依次进行关闭
                //要分别对其用trycatch如果将其放在一个trycatch内就会报错
                if (stmt != null){
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (conn != null){
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

}
