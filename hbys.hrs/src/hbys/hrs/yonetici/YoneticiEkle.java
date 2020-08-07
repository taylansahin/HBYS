package hbys.hrs.yonetici;

import hbys.hrs.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class YoneticiEkle extends JFrame
{
    private JButton btn_kaydet = null;
    private JButton btn_geri = null;

    private JTextField textbox_tc = new JTextField(11);
    private JTextField textbox_ad = new JTextField(30);
    private JTextField textbox_soyad = new JTextField(30);
    private JTextField textbox_kul_adi = new JTextField(30);
    private JTextField textbox_sifre = new JTextField(30);

    private boolean hata_var_mi = false;

    public YoneticiEkle()
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Yönetici Ekle");
        setSize(390, 350);  //boyut
        setLocationRelativeTo(null);    //konumunu ayarlamak için; null ortada
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //kapanırken ne olacak???

        JLabel tc_yazi = new JLabel("TC Kimlik No   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=0;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(tc_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=0;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_tc,gc);

        JLabel ad_yazi = new JLabel("Ad");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=1;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(ad_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=1;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_ad,gc);

        JLabel soyad_yazi = new JLabel("Soyad");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(soyad_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=2;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_soyad,gc);

        JLabel kul_adi_yazi = new JLabel("Kullanıcı Adı");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(kul_adi_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_kul_adi,gc);

        JLabel sifre_yazi = new JLabel("Şifre   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(sifre_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_sifre,gc);

        gc.fill=GridBagConstraints.BOTH;
        gc.gridx=0;//sütun
        gc.gridy=9;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=2;//yükseklik
        add(geri_buton(),gc); //KAPAT BUTONUNU EKLE

        gc.fill=GridBagConstraints.BOTH;
        gc.gridx=2;//sütun
        gc.gridy=9;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=2;//yükseklik
        add(kaydet_buton(),gc);    //KAYDET BUTONU EKLE


    }

    public JButton kaydet_buton()   //KAYDET BUTONU
    {
        if (btn_kaydet == null)
        {
            btn_kaydet = new JButton();
            //   btn_kaydet.setBounds(103, 110, 100, 54);
            btn_kaydet.setText("Kaydet");
            Color renk = new Color(137, 200, 116);
            btn_kaydet.setBackground(renk);

            btn_kaydet.addActionListener(new ActionListener()   //KAYDET BUTONUNA TIKLANIRSA
            {
                public void actionPerformed(ActionEvent e)
                {
                    String tc = "";
                    String ad = "";
                    String soyad = "";
                    String kul_ad = "";
                    String sifre = "";

                    if (textbox_ad.getText().trim().isEmpty() || textbox_tc.getText().trim().isEmpty() || textbox_soyad.getText().trim().isEmpty() || textbox_kul_adi.getText().trim().isEmpty() || textbox_sifre.getText().trim().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Alanlar Boş Geçilemez!", "Yönetici Ekle", 0);
                        hata_var_mi = true;
                    }

                    else //kutular boş değilse
                    {
                        hata_var_mi = false;

                        tc = textbox_tc.getText();
                        ad = textbox_ad.getText().toUpperCase(Locale.forLanguageTag("Tr"));
                        soyad = textbox_soyad.getText().toUpperCase(Locale.forLanguageTag("TR"));
                        kul_ad = textbox_kul_adi.getText();
                        sifre = textbox_sifre.getText();

                        if (Long.parseLong(tc) < 10000000000L || Long.parseLong(tc) > 99999999999L)
                        {
                            JOptionPane.showMessageDialog(null, "Lütfen Geçerli TC Kimlik Numarası Giriniz!" , "Yönetici Ekle", 0);
                            hata_var_mi = true;
                        }

                        boolean kullanici_adi_kayitli = false;

                        try
                        {
                            String sql0 = "SELECT HRS_KUL_AD FROM HRS_KULLANICI";

                            DatabaseConnection db_hasta_ara = new DatabaseConnection();
                            ResultSet gelen_veriler0 = db_hasta_ara.db_hasta_ara(sql0);

                            while (gelen_veriler0.next())
                            {
                                String db_kul_adi = gelen_veriler0.getString("HRS_KUL_AD");
                                if (db_kul_adi.equals(kul_ad))
                                {
                                    kullanici_adi_kayitli = true;
                                    break;
                                }
                            }
                        }
                        catch (SQLException se)
                        {
                            se.printStackTrace();
                        }

                        if (kullanici_adi_kayitli)
                        {
                            JOptionPane.showMessageDialog(null, "Kullanıcı Adı Kullanımda, Farklı Bir Kullanıcı Adı Seçiniz!" , "Yönetici Ekle", -1);
                        }

                        if (hata_var_mi == false && hata_var_mi == false)
                        {
                            String sql = "INSERT INTO HRS_KULLANICI "
                                    + "VALUES ('"+tc+"', '"+ad+"', '"+soyad+"', '"+kul_ad+"', '"+sifre+"') ";

                            DatabaseConnection con_hst_ekle = new DatabaseConnection();

                            String hata_durum = con_hst_ekle.database_sql(sql);

                            JOptionPane.showMessageDialog(null, ""+hata_durum , "Yönetici Ekle", -1);

                            if (hata_durum.equals("İşlem Başarılı."))    //İŞLEM BAŞARILI OLDUYSA
                            {//FORMU TEMİZLEME İŞLEMLERİ
                                textbox_tc.setText("");
                                textbox_ad.setText("");
                                textbox_soyad.setText("");
                                textbox_kul_adi.setText("");
                                textbox_sifre.setText("");
                            }
                        }
                    }
                }
            });//listener sonu
        }//IF SONU
        return btn_kaydet;
    }//KAYDET BUTONU METOD SONU

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
