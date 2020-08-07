package hbys.hrs.hasta;

import hbys.hrs.database.DatabaseConnection;
import hbys.hrs.dto.DTO;
import hbys.hrs.yonetici.YoneticiMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Locale;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HastaEkle extends JFrame
{
    DTO dto = new DTO();

    private JButton btn_kaydet = null;
    private JButton btn_geri = null;

    private JComboBox btn_cinsiyet;
    private JComboBox btn_gun;
    private JComboBox btn_ay;
    private JComboBox btn_yil;

    private JTextField textbox_tc = new JTextField(11);
    private JTextField textbox_ad = new JTextField(30);
    private JTextField textbox_soyad = new JTextField(30);
    private JTextField textbox_dog_yer = new JTextField(10);
    private JTextField textbox_anne_ad = new JTextField(30);
    private JTextField textbox_baba_ad = new JTextField(30);

    public boolean hata_var_mi = false;

    public HastaEkle()
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Hasta Ekle");
        setSize(390, 350);  //boyut
        setLocationRelativeTo(null);    //konumunu ayarlamak için; null ortada
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //kapanırken ne olacak???

        JLabel tc_yazi = new JLabel("TC Kimlik No");
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

        JLabel cinsiyet_yazi = new JLabel("Cinsiyet");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(cinsiyet_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(cinsiyet_combo(),gc);

        JLabel dog_tar_yazi = new JLabel("Doğum Tarihi   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(dog_tar_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=1;//genişlik
        gc.gridheight=1;//yükseklik
        add(gun_combo(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=3;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=1;//genişlik
        gc.gridheight=1;//yükseklik
        add(ay_combo(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=4;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=1;//genişlik
        gc.gridheight=1;//yükseklik
        add(yil_combo(),gc);

        JLabel dog_yer_yazi = new JLabel("Doğum Yeri");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=5;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(dog_yer_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=5;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_dog_yer,gc);

        JLabel anne_adi_yazi = new JLabel("Anne Adı");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(anne_adi_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_anne_ad,gc);

        JLabel baba_adi_yazi = new JLabel("Baba Adı");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=7;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(baba_adi_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=7;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(textbox_baba_ad,gc);

        gc.fill=GridBagConstraints.BOTH;
        gc.gridx=0;//sütun
        gc.gridy=9;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=2;//yükseklik
        add(geri_buton(),gc); //KAPAT BUTONUNU EKLE

        gc.fill=GridBagConstraints.BOTH;
        gc.gridx=3;//sütun
        gc.gridy=9;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=2;//yükseklik
        add(kaydet_buton(),gc);    //KAYDET BUTONU EKLE

        textbox_tc.addKeyListener(new java.awt.event.KeyAdapter()    //ENTER BASILDIĞINI ANLAYAN KOD
        {
            public void keyPressed(java.awt.event.KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    String tc_temp = textbox_tc.getText();

                    String sql = "SELECT * FROM HRS_HASTA_KIMLIK_BILGI WHERE HRS_HST_TC_KIMLIK_NO =" + "'"+tc_temp+"'";

                    DatabaseConnection db_hasta_ara = new DatabaseConnection();

                    ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

                    try
                    {
                        while(gelen_veriler.next())
                        {
                            textbox_ad.setText(gelen_veriler.getString("HRS_HST_AD"));
                            textbox_soyad.setText(gelen_veriler.getString("HRS_HST_SOYAD"));
                            textbox_dog_yer.setText(gelen_veriler.getString("HRS_HST_DOG_YER"));
                            textbox_anne_ad.setText(gelen_veriler.getString("HRS_HST_ANNE_AD"));
                            textbox_baba_ad.setText(gelen_veriler.getString("HRS_HST_BABA_AD"));

                            btn_cinsiyet.setSelectedItem(gelen_veriler.getString(4));

                            String dt_temp = gelen_veriler.getString(5);
                            String dt_yil_temp = dt_temp.substring(0,4);
                            String dt_ay_temp = dt_temp.substring(5,7);
                            String dt_gun_temp = dt_temp.substring(8,10);
                            btn_yil.setSelectedItem(dt_yil_temp);
                            btn_ay.setSelectedItem(dt_ay_temp);
                            btn_gun.setSelectedItem(dt_gun_temp);
                        }
                    }
                    catch (Exception x)
                    {
                        x.printStackTrace();
                    }

                }

            }
        });

    }//CONSTRUCTOR SONU

    public JComboBox cinsiyet_combo()
    {
        String[] cinsiyet_secenek = new String[] {"Seçiniz","Erkek","Kadın"};
        btn_cinsiyet = new JComboBox<String>(cinsiyet_secenek);
        return btn_cinsiyet;
    }

    public JComboBox gun_combo()
    {
        String[] gun_secenek = new String[] {"Gün","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
        btn_gun = new JComboBox<String>(gun_secenek);
        return btn_gun;
    }

    public JComboBox ay_combo()
    {
        String[] ay_secenek = new String[] {"Ay","01","02","03","04","05","06","07","08","09","10","11","12"};
        btn_ay = new JComboBox<String>(ay_secenek);
        return btn_ay;
    }

    public JComboBox yil_combo()
    {
        String[] yil_secenek = new String[] {"Yıl","2018","2017","2016","2015","2014","2013","2012","2011","2010","2009","2008","2007","2006","2005","2004","2003","2002","2001","2000","1999","1998","1997","1996","1995","1994","1993","1992","1991","1990","1989","1988","1987","1986","1985","1984","1983","1982","1981","1980","1979","1978","1977","1976","1975","1974","1973","1972","1971","1970","1969","1968","1967","1966","1965","1964","1963","1962","1961","1960","1959","1958","1957","1956","1955","1954","1953","1952","1951","1950","1949","1948","1947","1946","1945","1944","1943","1942","1941","1940","1939","1938","1937","1936","1935","1934","1933","1932","1931","1930","1929","1928","1927","1926","1925","1924","1923","1922","1921","1920","1919","1918","1917","1916","1915","1914","1913","1912","1911","1910","1909","1908","1907","1906","1905","1904","1903","1902","1901","1900","1899","1898","1897","1896","1895","1894","1893","1892","1891","1890","1889","1888","1887","1886","1885","1884","1883","1882","1881","1880","1879","1878","1877","1876","1875","1874","1873","1872","1871","1870","1869","1868","1867","1866","1865","1864","1863","1862","1861","1860","1859","1858","1857","1856","1855","1854","1853","1852","1851","1850","1849","1848","1847","1846","1845","1844","1843","1842","1841","1840","1839","1838","1837","1836","1835","1834","1833","1832","1831","1830","1829","1828","1827","1826","1825","1824","1823","1822","1821","1820","1819","1818","1817","1816","1815","1814","1813","1812","1811","1810","1809","1808","1807","1806","1805","1804","1803","1802","1801","1800"};
        btn_yil = new JComboBox<String>(yil_secenek);
        return btn_yil;
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
                    if (textbox_ad.getText().trim().isEmpty() || textbox_tc.getText().trim().isEmpty() || textbox_soyad.getText().trim().isEmpty() || textbox_dog_yer.getText().trim().isEmpty() || textbox_anne_ad.getText().trim().isEmpty() || textbox_baba_ad.getText().trim().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Alanlar Boş Geçilemez!", "Hasta Ekle", 0);
                        hata_var_mi = true;
                    }

                    else //kutular boş değilse
                    {
                        hata_var_mi = false;

                        dto.setHst_tc_kimlik_no(textbox_tc.getText());

                        dto.setHst_ad(textbox_ad.getText().toUpperCase(Locale.forLanguageTag("Tr")));

                        dto.setHst_soyad(textbox_soyad.getText().toUpperCase(Locale.forLanguageTag("TR")));

                        dto.setHst_cinsiyet((String) btn_cinsiyet.getSelectedItem());

                        String hst_dog_tar_gun = (String) btn_gun.getSelectedItem();
                        String hst_dog_tar_ay = (String) btn_ay.getSelectedItem();
                        String hst_dog_tar_yil = (String) btn_yil.getSelectedItem();

                        dto.setHst_dog_yer(textbox_dog_yer.getText().toUpperCase(Locale.forLanguageTag("TR")));

                        dto.setHst_anne_ad(textbox_anne_ad.getText().toUpperCase(Locale.forLanguageTag("TR")));

                        dto.setHst_baba_ad(textbox_baba_ad.getText().toUpperCase(Locale.forLanguageTag("TR")));


                        if (Long.parseLong(dto.getHst_tc_kimlik_no()) < 10000000000L || Long.parseLong(dto.getHst_tc_kimlik_no()) > 99999999999L)
                        {
                            JOptionPane.showMessageDialog(null, "Lütfen Geçerli TC Kimlik Numarası Giriniz!" , "Hasta Ekle", 0);
                            hata_var_mi = true;
                        }

                        if (dto.getHst_cinsiyet().equals("SEÇİNİZ"))
                        {
                            JOptionPane.showMessageDialog(null, "Lütfen Cinsiyet Seçiniz!" , "Hasta Ekle", 0);
                            hata_var_mi = true;
                        }

                        if (hst_dog_tar_gun.equals("Gün") || hst_dog_tar_ay.equals("Ay") || hst_dog_tar_yil.equals("Yıl"))
                        {
                            JOptionPane.showMessageDialog(null, "Lütfen Geçerli Bir Doğum Tarihi Giriniz!" , "Hasta Ekle", 0);
                            hata_var_mi = true;
                        }

                        if (!(Integer.parseInt(hst_dog_tar_yil) % 4 == 0 && Integer.parseInt(hst_dog_tar_yil) % 100 !=0 || Integer.parseInt(hst_dog_tar_yil) %400 == 0))
                        {
                            if (hst_dog_tar_ay.equals("02") && (hst_dog_tar_gun.equals("29") || hst_dog_tar_gun.equals("30") || hst_dog_tar_gun.equals("31")))
                            {
                                JOptionPane.showMessageDialog(null, "" +Integer.parseInt(hst_dog_tar_yil)+" Yılının Şubat Ayı 28 Gündür!" , "Hasta Ekle", 0);
                                hata_var_mi = true;
                            }

                        }

                        if (hst_dog_tar_gun.equals("31") && hst_dog_tar_ay.equals("02"))
                        {
                            JOptionPane.showMessageDialog(null, "Şubat Ayı 28 Gündür!" , "Hasta Ekle", 0);
                            hata_var_mi = true;
                        }

                        if (hst_dog_tar_gun.equals("31") && hst_dog_tar_ay.equals("04"))
                        {
                            JOptionPane.showMessageDialog(null, "Nisan Ayı 30 Gündür!" , "Hasta Ekle", 0);
                            hata_var_mi = true;
                        }

                        if (hst_dog_tar_gun.equals("31") && hst_dog_tar_ay.equals("06"))
                        {
                            JOptionPane.showMessageDialog(null, "Haziran Ayı 30 Gündür!" , "Hasta Ekle", 0);
                            hata_var_mi = true;
                        }

                        if (hst_dog_tar_gun.equals("31") && hst_dog_tar_ay.equals("09"))
                        {
                            JOptionPane.showMessageDialog(null, "Eylül Ayı 30 Gündür!" , "Hasta Ekle", 0);
                            hata_var_mi = true;
                        }

                        if (hst_dog_tar_gun.equals("31") && hst_dog_tar_ay.equals("11"))
                        {
                            JOptionPane.showMessageDialog(null, "Kasım Ayı 30 Gündür!" , "Hasta Ekle", 0);
                            hata_var_mi = true;
                        }

                        if (hata_var_mi == false)
                        {
                            String dog_tar_temp = "";
                            dog_tar_temp = dog_tar_temp.concat(hst_dog_tar_yil);
                            dog_tar_temp = dog_tar_temp.concat("-");
                            dog_tar_temp = dog_tar_temp.concat(hst_dog_tar_ay);
                            dog_tar_temp = dog_tar_temp.concat("-");
                            dog_tar_temp = dog_tar_temp.concat(hst_dog_tar_gun);

                            dto.setHst_dog_tar(dog_tar_temp);

                            String sql = "INSERT INTO HRS_HASTA_KIMLIK_BILGI"
                                    + "(HRS_HST_TC_KIMLIK_NO, HRS_HST_AD, HRS_HST_SOYAD, HRS_HST_CINSIYET, HRS_HST_DOG_TAR, HRS_HST_DOG_YER, HRS_HST_ANNE_AD, HRS_HST_BABA_AD) "
                                    + "VALUES ('"+dto.getHst_tc_kimlik_no()+"', '"+dto.getHst_ad()+"', '"+dto.getHst_soyad()+"', '"+dto.getHst_cinsiyet()+"', '"+dto.getHst_dog_tar()+"', '"+dto.getHst_dog_yer()+"', '"+dto.getHst_anne_ad()+"', '"+dto.getHst_baba_ad()+"') ";

                            DatabaseConnection con_hst_ekle = new DatabaseConnection();

                            String hata_durum = con_hst_ekle.database_sql(sql);

                            JOptionPane.showMessageDialog(null, ""+hata_durum , "Hasta Ekle", -1);

                            if (hata_durum.equals("İşlem Başarılı."))    //İŞLEM BAŞARILI OLDUYSA
                            {//FORMU TEMİZLEME İŞLEMLERİ
                                textbox_tc.setText("");
                                textbox_ad.setText("");
                                textbox_soyad.setText("");
                                textbox_dog_yer.setText("");
                                textbox_anne_ad.setText("");
                                textbox_baba_ad.setText("");
                                btn_cinsiyet.setSelectedItem("Seçiniz");
                                btn_gun.setSelectedItem("Gün");
                                btn_ay.setSelectedItem("Ay");
                                btn_yil.setSelectedItem("Yıl");
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

}//HastaEkle CLASS SONU

