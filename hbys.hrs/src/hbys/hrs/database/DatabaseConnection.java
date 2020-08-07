package hbys.hrs.database;

import java.sql.*;

public class DatabaseConnection
{
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://localhost:3306/hrs_database";

    static final String DB_KUL_ADI = "root";
    static final String DB_SIFRE = "";

    ResultSet gelen_veriler = null;

    public ResultSet  db_hasta_ara(String sql)
    {
        Connection conn = null;
        Statement stmt = null;
        try
        {
            conn = DriverManager.getConnection(DB_URL,DB_KUL_ADI,DB_SIFRE);

            stmt = conn.createStatement();
            gelen_veriler = stmt.executeQuery(sql);

            return gelen_veriler;

        }

        catch (SQLException se)
        {
            //JDBC HATASI İÇİN
            se.printStackTrace();

        }

        finally
        {
            //BAĞLANTIYI KAPATMAK İÇİN
            try
            {
                if (stmt != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                //HİÇBİR ŞEY YAPMA
            }

            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {
                //se.printStackTrace();
            }//end finally try
        }//end try

        return gelen_veriler;
    }

    public String  database_sql(String sql)
    {
        Connection conn = null;
        Statement stmt = null;
        try
        {
            conn = DriverManager.getConnection(DB_URL,DB_KUL_ADI,DB_SIFRE);

            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            return "İşlem Başarılı.";
        }
        catch (SQLException sql_hata)
        {
            int hata_durum = sql_hata.getErrorCode();
            if (hata_durum == 1062) //primary key hatasının kodu
                return "Kayıt Zaten Veri Tabanında Bulunmaktadır!";
            else
                return "İşlem Başarısız!";
        }
        finally
        {
            //BAĞLANTIYI KAPAT
            try
            {
                if (stmt != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {

            }
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            } catch (SQLException se)
            {

            }
        }
    }
}



