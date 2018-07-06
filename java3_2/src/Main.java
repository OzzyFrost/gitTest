import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String... arg) {
        //createProductTable();
        //dropProductTable();
        // create("dress2","woman dress super22",20000);
        //System.out.println(getCastForName("woman dress super22"));
        clearTableProduct();
        fillProductTable();
        String command = "/";
        System.out.println("Для завершения введите команду /exit");
        while (!command.equals("/exit")) {
            command = sc.nextLine();
            if (command.startsWith("/цена") || command.startsWith("/cost")) {
                findCostforName(command.substring(6));
            }
            if (command.startsWith("/сменитьцену")) {
                String[] param = command.split(" ");
                updateCost(param[1], param[2]);
                System.out.println("цена успешно изменена");
            }
            if (command.startsWith("/товарыпоцене")) {
                String[] param = command.split(" ");
                selectForCost(param[1], param[2]);
            }


        }
    }

    public static void createProductTable() {
        Connection conn = null;
        Statement st = null;
        try {
            conn = MyConnection.getConnection();
            st = conn.createStatement();
            st.execute("CREATE TABLE PRODUCT(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "PRODID CHAR(50)    NOT NULL," +
                    "TITLE  CHAR(255) NOT NULL  ," +
                    "COST  INTEGER(8))");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void dropProductTable() {
        Connection conn = null;
        Statement st = null;
        try {
            conn = MyConnection.getConnection();
            st = conn.createStatement();
            st.execute("DROP TABLE PRODUCT");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getCastForName(String name) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int cast = -1;
        try {
            conn = MyConnection.getConnection();

            st = conn.prepareStatement("SELECT * FROM PRODUCT WHERE TITLE=?");

            st.setString(1, name);
            rs = st.executeQuery();
            rs.next();
            cast = rs.getInt("COST");

        } catch (SQLException e) {
            //e.printStackTrace();
            //System.out.println("Такого товара нет!");
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cast;
    }

    public static void create(String prodid, String title, int cost) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement("INSERT INTO PRODUCT (PRODID, TITLE, COST) VALUES(?,?,?)");
            st.setString(1, prodid);
            st.setString(2, title);
            st.setInt(3, cost);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void clearTableProduct() {
        Connection conn = null;
        Statement st = null;
        try {
            conn = MyConnection.getConnection();
            st = conn.createStatement();
            st.executeUpdate("DELETE FROM PRODUCT ");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void fillProductTable() {

        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = MyConnection.getConnection();
            conn.setAutoCommit(false);//

            st = conn.prepareStatement("INSERT INTO PRODUCT (PRODID, TITLE, COST) VALUES(?,?,?)");

            for (int i = 1; i <= 10000; i++) {
                st.setString(1, "" + i);
                st.setString(2, "товар" + i);
                st.setInt(3, i * 10);
                st.executeUpdate();
            }
            int[] count = st.executeBatch();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void findCostforName(String name) {
        {
            int cost = getCastForName(name);
            if (cost == -1) {
                System.out.println("Товар: " + name + " не найден.");
            } else {
                System.out.println("Цена товара " + name + ": " + cost);
            }
        }
    }

    public static void updateCost(String name, String newCost) {
        Connection conn = null;
        Statement st = null;
        try {
            conn = MyConnection.getConnection();
            st = conn.createStatement();
            st.execute("UPDATE PRODUCT SET COST='" + newCost + "' WHERE TITLE='" + name + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void selectForCost(String beginCost, String endCost) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement("SELECT * FROM PRODUCT WHERE COST >= ? AND COST <= ?");
            st.setString(1,beginCost);
            st.setString(2,endCost);
            //st.setInt(1, Integer.getInteger(beginCost));
            //st.setInt(2, Integer.getInteger(endCost));

            rs = st.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("PRODID") + " " +
                        rs.getString("TITLE") + " " + rs.getInt("COST"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
