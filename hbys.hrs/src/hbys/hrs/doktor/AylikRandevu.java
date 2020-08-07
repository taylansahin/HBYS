package hbys.hrs.doktor;

import hbys.hrs.database.DatabaseConnection;
import hbys.hrs.yonetici.YoneticiMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AylikRandevu extends JFrame
{

    private JButton btn_hepsini_olustur = null;
    private JButton btn_geri = null;

    public AylikRandevu()
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Aylık Randevu");
        setSize(390, 350);  //boyut
        setLocationRelativeTo(null);    //konumunu ayarlamak için; null ortada
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //kapanırken ne olacak???

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=5;//satır
        gc.gridwidth=5;//genişlik
        gc.gridheight=1;//yükseklik
        add(hepsini_olustur_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=5;//genişlik
        gc.gridheight=1;//yükseklik
        add(geri_buton(),gc);
    }

    public JButton hepsini_olustur_buton()
    {
        if (btn_hepsini_olustur == null)
        {
            btn_hepsini_olustur = new JButton();
            btn_hepsini_olustur.setText("Hepsini Oluştur");
            Color renk = new Color(137, 200, 116);
            btn_hepsini_olustur.setBackground(renk);

            btn_hepsini_olustur.addActionListener(new ActionListener()   //KAYDET BUTONUNA TIKLANIRSA
            {
                public void actionPerformed(ActionEvent e)
                {
                    String zaman = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());
                    int ay_kac_gun = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
                    int c_yil = Integer.parseInt(zaman.substring(0,4)); //hangi yıldayız
                    int c_ay = Integer.parseInt(zaman.substring(4,6));  //hangi ayda olduğumuz
                    int c_gun = Integer.parseInt(zaman.substring(6,8)); //ayın kaçıncı gününde olduğumuz
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, 1);
                    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    Date sonraki_ay_gun_sayisi = calendar.getTime();
                    String sonraki_ay = sonraki_ay_gun_sayisi.toString();
                    int sonraki_ay_kac_gun = Integer.parseInt(sonraki_ay.substring(8,10));
                    String tarih = "";
                    String saat = "";

                    DatabaseConnection con_hst_ekle = new DatabaseConnection();
                    DatabaseConnection db_hasta_ara = new DatabaseConnection();

                    String sql0 = " SELECT HRS_DR_TC_KIMLIK_NO FROM HRS_DR_KIMLIK_BILGI ";
                    ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql0);
                    String dr_tc = "";
                    try
                    {
                        while (gelen_veriler.next())
                        {
                            dr_tc = gelen_veriler.getString("HRS_DR_TC_KIMLIK_NO");

                            for (int i=c_gun ; i<=ay_kac_gun ; i++)
                            {
                                if (i<10)
                                {
                                    tarih = ""+c_yil+"-"+c_ay+"-0"+i;
                                }
                                else if (i>=10)
                                {
                                    tarih = ""+c_yil+"-"+c_ay+"-"+i;
                                }

                                for(int k=8 ; k<=17 ; k++)
                                {
                                    for(int j=0 ; j<=30 ; j=j+30)
                                    {
                                        if((k==12 && j==30) || (k==13 && j==0) || (k==17 && j==30))
                                        {
                                            continue;
                                        }
                                        if (k<10 && j==0)
                                        {
                                            saat = "0"+k+":0"+j+":00";
                                        }
                                        if (k>=10 && j==0)
                                        {
                                            saat = ""+k+":0"+j+":00";
                                        }
                                        if (k<10 && j==30)
                                        {
                                            saat = "0"+k+":"+j+":00";
                                        }
                                        if (k>=10 && j==30)
                                        {
                                            saat = ""+k+":"+j+":00";
                                        }
                                        String sql = "INSERT INTO HRS_DR_RANDEVU VALUES ('"+dr_tc+"', '"+tarih+"', '"+saat+"', '"+-9+"') ";

                                        String hata_durum = con_hst_ekle.database_sql(sql);
                                    }
                                }
                            }
                            for (int i=1 ; i<=sonraki_ay_kac_gun ; i++)
                            {
                                if (c_ay==12)
                                {
                                    if (i<10)
                                    {
                                        tarih = ""+(c_yil+1)+"-"+1+"-0"+i;
                                    }
                                    else if (i>=10)
                                    {
                                        tarih = ""+(c_yil+1)+"-"+1+"-"+i;
                                    }
                                }
                                else
                                {
                                    if (i<10)
                                    {
                                        tarih = ""+c_yil+"-"+c_ay+"-0"+i;
                                    }
                                    else if (i>=10)
                                    {
                                        tarih = ""+c_yil+"-"+c_ay+"-"+i;
                                    }
                                }

                                for(int k=8 ; k<=17 ; k++)
                                {
                                    for(int j=0 ; j<=30 ; j=j+30)
                                    {
                                        if((k==12 && j==30) || (k==13 && j==0) || (k==17 && j==30))
                                        {
                                            continue;
                                        }
                                        if (k<10 && j==0)
                                        {
                                            saat = "0"+k+":0"+j+":00";
                                        }
                                        if (k>=10 && j==0)
                                        {
                                            saat = ""+k+":0"+j+":00";
                                        }
                                        if (k<10 && j==30)
                                        {
                                            saat = "0"+k+":"+j+":00";
                                        }
                                        if (k>=10 && j==30)
                                        {
                                            saat = ""+k+":"+j+":00";
                                        }
                                        String sql = "INSERT INTO HRS_DR_RANDEVU VALUES ('"+dr_tc+"', '"+tarih+"', '"+saat+"', '"+-9+"') ";

                                        String hata_durum = con_hst_ekle.database_sql(sql);
                                    }
                                }
                            }
                        }
                        JOptionPane.showMessageDialog(null, "Tüm Doktorların Bu Ay ve Gelecek Ay İçin Boş Randevu Çizelgeleri Oluşturuldu.\nMevcut Randevular İşlemden Etkilenmedi." , "Aylık Randevu", -1);
                    }
                    catch (Exception x)
                    {
                        x.printStackTrace();
                    }

                }
            });//listener sonu
        }//IF SONU
        return btn_hepsini_olustur;
    }

    public JButton geri_buton()    //KAPAT BUTONU
    {
        if (btn_geri == null)
        {
            btn_geri = new JButton();
            btn_geri.setBounds(103, 110, 100, 54);
            btn_geri.setText("Geri");
            Color renk = new Color(201, 93, 81);
            btn_geri.setBackground(renk);

            btn_geri.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    YoneticiMenu ynt_ekran = new YoneticiMenu();
                    ynt_ekran.setVisible(true);
                    ImageIcon image = new ImageIcon("Q:\\Java\\hbys.hrs\\resources\\icon3.png");
                    ynt_ekran.setIconImage(image.getImage());
                    setVisible(false);
                }
            });
        }//IF SONU
        return btn_geri;
    }//KAPAT BUTONU METOD SONU
}

