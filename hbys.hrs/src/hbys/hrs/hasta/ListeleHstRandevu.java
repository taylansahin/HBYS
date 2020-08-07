package hbys.hrs.hasta;



import hbys.hrs.database.DatabaseConnection;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ListeleHstRandevu extends JFrame
{
    private JPanel contentPane;
    private JTable table;

    static String hst_tc = "";

    public ListeleHstRandevu()
    {
        setTitle("Hasta Randevu Listesi");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(500,350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(500,350);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        String zaman = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());
        int c_ay = Integer.parseInt(zaman.substring(4,6));  //hangi ayda olduğumuz
        int c_gun = Integer.parseInt(zaman.substring(6,8)); //ayın kaçıncı gününde olduğumuz
        String db_gun = "";
        String db_ay = "";

        try
        {
            DatabaseConnection db_hasta_ara = new DatabaseConnection();

            String sql0 = "SELECT HRS_DR_TC, HRS_RANDEVU_TRH, HRS_RANDEVU_SAAT FROM HRS_RANDEVU"
                    + " WHERE HRS_HASTA_TC_KIMLIK_NO='" +hst_tc+ "'";

            ResultSet gelen_veriler0 = db_hasta_ara.db_hasta_ara(sql0);

            int toplam_satir = 0;
            while (gelen_veriler0.next())
            {
                db_gun = gelen_veriler0.getString("HRS_RANDEVU_TRH").substring(8,10);
                db_ay = gelen_veriler0.getString("HRS_RANDEVU_TRH").substring(5,7);

                if (c_ay == Integer.parseInt(db_ay) && Integer.parseInt(db_gun) < c_gun)
                {
                    continue;
                }
                toplam_satir++;
            }

            String [][]data = new String[toplam_satir][5];
            int satir1 = 0;

            if (toplam_satir > 0)
            {
                String sql1 = "SELECT a.HRS_DR_AD, a.HRS_DR_SOYAD, b.HRS_POL_ADI, c.HRS_RANDEVU_TRH, c.HRS_RANDEVU_SAAT " +
                        "FROM HRS_DR_KIMLIK_BILGI a, HRS_POLIKLINIK b, HRS_RANDEVU c " +
                        "WHERE a.HRS_DR_TC_KIMLIK_NO=c.HRS_DR_TC AND b.HRS_POL_ID=a.HRS_DR_POLIKLINIK_ID AND c.HRS_HASTA_TC_KIMLIK_NO='" +hst_tc+ "'";

                ResultSet gelen_veriler1 = db_hasta_ara.db_hasta_ara(sql1);

                while (gelen_veriler1.next())    //SQL
                {
                    db_gun = gelen_veriler1.getString("HRS_RANDEVU_TRH").substring(8,10);
                    db_ay = gelen_veriler1.getString("HRS_RANDEVU_TRH").substring(5,7);

                    if (c_ay == Integer.parseInt(db_ay) && Integer.parseInt(db_gun) < c_gun)
                    {
                        continue;
                    }

                    data[satir1][0] = gelen_veriler1.getString("HRS_DR_AD");
                    data[satir1][1] = gelen_veriler1.getString("HRS_DR_SOYAD");
                    data[satir1][2] = gelen_veriler1.getString("HRS_POL_ADI");
                    data[satir1][3] = gelen_veriler1.getString("HRS_RANDEVU_TRH");
                    data[satir1][4] = gelen_veriler1.getString( "HRS_RANDEVU_SAAT").substring(0,5);
                    satir1++;
                }
            }

            else if (toplam_satir < 1)
            {
                JOptionPane.showMessageDialog(null, "Randevu Bulunamadı", "Hasta Randevu Görüntüle", JOptionPane.ERROR_MESSAGE);
                setVisible(false);
            }

            String []baslik=new String[5];
            baslik[0] = "Doktor Adı";
            baslik[1] = "Doktor Soyadı";
            baslik[2] = "Poliklinik";
            baslik[3] = "Tarih";
            baslik[4] = "Saat";

            //Dizileri model kısmına aktarıyoruz.
            TableModel tablemodel=new DefaultTableModel(data,baslik);

            //Modelide Table aktarıyoruz.
            table.setModel(tablemodel);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            //  JOptionPane.showMessageDialog(null, ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }

        //Comboboxta olduğu gibi burdada bir model oluşturuyoruz.
        //Fakat comboboxtan farklı olarak 2 adet dizi alıyor.
        //ilk dizi 1 boyutlu dizi bu dizi Table nin sütün adları
        //ikinci dizi 2 boyutlu olacak bu dizi Table nin satırlarını alacaktır.
    }
}
