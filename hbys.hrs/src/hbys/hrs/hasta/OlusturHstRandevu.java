package hbys.hrs.hasta;

import hbys.hrs.database.DatabaseConnection;
import hbys.hrs.dto.DTO;
import hbys.hrs.other.ComboDoldur;
import hbys.hrs.yonetici.YoneticiMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OlusturHstRandevu extends JFrame
{
    DTO dto = new DTO();

    private JButton btn_kaydet = null;
    private JButton btn_geri = null;

    private JTextField textbox_tc = new JTextField(11);
    private JTextField textbox_ad = new JTextField(20);
    private JTextField textbox_soyad = new JTextField(20);

    private JComboBox cmb_trh;
    private JComboBox cmb_pol;
    private JComboBox cmb_dr;
    private JComboBox cmb_saat;

    public boolean hata_var_mi = false;

    private String[] db_saat=new String[20];
    private String[] db_dakika=new String[20];

    public Object item = null;
    public String deger = "";

    public OlusturHstRandevu()
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Randevu Oluştur");
        setSize(390, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

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

        JLabel ad_yazi = new JLabel("Ad     ");
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
        textbox_ad.setEditable(false);

        JLabel soyad_yazi = new JLabel("Soyad   ");
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
        textbox_soyad.setEditable(false);

        JLabel pol_yazi = new JLabel("Poliklinik   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(pol_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=3;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(pol_combo(),gc);

        JLabel dr_yazi = new JLabel("Doktor   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(dr_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(dr_combo(),gc);

        JLabel tarih_yazi = new JLabel("Tarih   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=5;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(tarih_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=5;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(tarih_combo(),gc);

        JLabel saat_yazi = new JLabel("Saat   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(saat_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=6;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(saat_combo(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=10;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(geri_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=3;//sütun
        gc.gridy=10;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(kaydet_buton(),gc);
        btn_kaydet.setEnabled(false);

        textbox_tc.addKeyListener(new java.awt.event.KeyAdapter()    //ENTER BASILDIĞINI ANLAYAN KOD
        {
            public void keyPressed(java.awt.event.KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    String tc_temp = textbox_tc.getText();

                    if (textbox_tc.getText().trim().isEmpty() || Long.parseLong(tc_temp) < 10000000000L || Long.parseLong(tc_temp) > 99999999999L)
                    {
                        JOptionPane.showMessageDialog(null, "Lütfen Geçerli Bir TC Kimlik Numarası Giriniz!" , "Randevu Oluştur", 0);
                        textbox_ad.setText("");
                        textbox_soyad.setText("");
                        btn_kaydet.setEnabled(false);
                    }

                    else
                    {
                        String sql = "SELECT * FROM HRS_HASTA_KIMLIK_BILGI WHERE HRS_HST_TC_KIMLIK_NO ="+ "'"+tc_temp+"'";

                        DatabaseConnection db_hasta_ara = new DatabaseConnection();

                        ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

                        try
                        {
                            String ad_temp = "";
                            String soyad_temp = "";
                            while(gelen_veriler.next())
                            {
                                ad_temp = gelen_veriler.getString("HRS_HST_AD");
                                soyad_temp = gelen_veriler.getString("HRS_HST_SOYAD");
                            }

                            if (ad_temp.trim().isEmpty())
                            {
                                JOptionPane.showMessageDialog(null, ""+textbox_tc.getText()+" " +
                                        "TC Kimlik Numaralı Hastanın Kaydı Yoktur!", "Randevu Oluştur", 0);
                                textbox_ad.setText("");
                                textbox_soyad.setText("");
                                btn_kaydet.setEnabled(false);
                            }
                            else
                            {
                                textbox_ad.setText(ad_temp);
                                textbox_soyad.setText(soyad_temp);
                                btn_kaydet.setEnabled(true);
                                textbox_tc.setEditable(false);
                            }
                        }
                        catch (Exception x)
                        {
                            x.printStackTrace();
                            btn_kaydet.setEnabled(false);
                        }
                    }
                }
            }
        });
    }//constructor sonu

    public JComboBox pol_combo()
    {
        cmb_pol = new JComboBox();
        String sql = "SELECT HRS_POL_ID,HRS_POL_ADI FROM HRS_POLIKLINIK";

        DatabaseConnection db_hasta_ara = new DatabaseConnection();

        ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

        try
        {
            String temp_pol_id;
            String temp_pol_adi;
            cmb_pol.addItem(new ComboDoldur("Seçiniz","-1"));

            while(gelen_veriler.next())
            {
                temp_pol_id = gelen_veriler.getString("HRS_POL_ID");
                temp_pol_adi = gelen_veriler.getString("HRS_POL_ADI");

                cmb_pol.addItem(new ComboDoldur(temp_pol_adi,temp_pol_id));
            }

            cmb_pol.addActionListener (new ActionListener ()
            {
                public void actionPerformed(ActionEvent e)
                {
                    item = cmb_pol.getSelectedItem();
                    deger = ((ComboDoldur)item).getValue();
                    cmb_dr.setEnabled(true);//düzenlenebilir mi?

                    String sql = " SELECT  a.HRS_DR_AD, a.HRS_DR_SOYAD, a.HRS_DR_TC_KIMLIK_NO, b.HRS_UNV_AD FROM HRS_DR_KIMLIK_BILGI a, HRS_UNVANLAR b "
                            + " WHERE a.HRS_DR_POLIKLINIK_ID='" +deger+ "' AND a.HRS_DR_UNVAN_ID=b.HRS_UNV_ID";

                    DatabaseConnection db_hasta_ara = new DatabaseConnection();

                    ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

                    try
                    {
                        String temp_dr_ad = "";
                        String temp_dr_soyad = "";
                        String temp_dr_ad_soyad = "";
                        String temp_dr_tc = "";
                        String temp_unv_ad = "";
                        cmb_dr.removeAllItems();

                        cmb_dr.addItem(new ComboDoldur("Seçiniz","-1"));
                        while(gelen_veriler.next())
                        {
                            temp_unv_ad = gelen_veriler.getString("HRS_UNV_AD");
                            temp_dr_ad = gelen_veriler.getString("HRS_DR_AD");
                            temp_dr_soyad = gelen_veriler.getString("HRS_DR_SOYAD");
                            temp_dr_ad_soyad=temp_unv_ad+" "+temp_dr_ad+" "+temp_dr_soyad;
                            temp_dr_tc = gelen_veriler.getString("HRS_DR_TC_KIMLIK_NO");

                            cmb_dr.addItem(new ComboDoldur(temp_dr_ad_soyad,temp_dr_tc));
                        }
                    }
                    catch (Exception x)
                    {
                        x.printStackTrace();
                    }
                }
            });
        }
        catch (Exception x)
        {
            x.printStackTrace();
        }
        return cmb_pol;
    }

    public JComboBox dr_combo()
    {
        cmb_dr = new JComboBox();
        cmb_dr.addItem(new ComboDoldur("Seçiniz","-1"));


        cmb_dr.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent arg0)
            {

                if (cmb_dr.getSelectedItem()!=null)
                {
                    item = cmb_dr.getSelectedItem();
                    deger = ((ComboDoldur) item).getValue();
                    dto.setDr_tc(deger);
                }
            }
        });
        return cmb_dr;
    }

    class ItemChangeListener implements ItemListener    //interface
    {
        @Override
        public void itemStateChanged(ItemEvent event)
        {
            if (event.getStateChange() == ItemEvent.SELECTED)
            {
                Object item = event.getItem();
            }
        }
    }

    public JComboBox tarih_combo()
    {
        cmb_trh = new JComboBox();
        cmb_trh.addItem(new ComboDoldur("Seçiniz","-1"));
        String zaman = new SimpleDateFormat("yyyyMMdd_HHmm").format(Calendar.getInstance().getTime());
        int ay_kac_gun = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        int c_yil = Integer.parseInt(zaman.substring(0,4)); //hangi yıldayız
        int c_ay = Integer.parseInt(zaman.substring(4,6));  //hangi ayda olduğumuz
        int c_gun = Integer.parseInt(zaman.substring(6,8)); //ayın kaçıncı gününde olduğumuz
        int c_saat = Integer.parseInt(zaman.substring(9,11));   //şu anki saat
        int c_dakika = Integer.parseInt(zaman.substring(11,13));
        int on_bes_gun = 15;
        for (int i=c_gun ; i<=ay_kac_gun ; i++)
        {
            if (c_saat > 17)
            {
                continue;
            }
            if (i<10)
            {
                cmb_trh.addItem(new ComboDoldur(("0"+i+"."+c_ay+"."+c_yil),(""+c_yil+"-"+c_ay+"-0"+i)));
                on_bes_gun--;
            }
            else if (i>=10)
            {
                cmb_trh.addItem(new ComboDoldur((""+i+"."+c_ay+"."+c_yil),(""+c_yil+"-"+c_ay+"-"+i)));
                on_bes_gun--;
            }

            if (on_bes_gun==0)
                break;
        }
        if (on_bes_gun>=0)
        {
            for (int i=1 ; on_bes_gun>0 ; i++)
            {
                if (i<10)
                {
                    if (c_ay==12)
                    {
                        cmb_trh.addItem(new ComboDoldur(("0"+i+"."+"01"+"."+(c_yil+1)),(""+(c_yil+1)+"-"+"01"+"-0"+i)));
                        on_bes_gun--;
                    }
                    else if (c_ay<12)
                    {
                        cmb_trh.addItem(new ComboDoldur(("0"+i+"."+(c_ay+1)+"."+c_yil),(""+c_yil+"-"+(c_ay+1)+"-0"+i)));
                        on_bes_gun--;
                    }
                }
                else if (i>=10)
                {
                    if (c_ay==12)
                    {
                        cmb_trh.addItem(new ComboDoldur((""+i+"."+"01"+"."+(c_yil+1)),(""+c_yil+"-"+c_ay+"-"+i)));
                        on_bes_gun--;
                    }
                    else if (c_ay<12)
                    {
                        cmb_trh.addItem(new ComboDoldur((""+i+"."+(c_ay+1)+"."+c_yil),(""+c_yil+"-"+c_ay+"-"+i)));
                        on_bes_gun--;
                    }
                }

            }
        }

        cmb_trh.addActionListener (new ActionListener ()
        {
            public void actionPerformed(ActionEvent e)
            {
                item = cmb_trh.getSelectedItem();
                deger = ((ComboDoldur)item).getValue();

                dto.setRandevu_trh(deger);

                String sql = " SELECT HRS_RANDEVU_SAAT FROM HRS_DR_RANDEVU "
                        + " WHERE HRS_RANDEVU_TRH='"+deger+"' AND DR_TC_KIMLIK_NO='"+dto.getDr_tc()+"' AND HRS_RANDEVU_DURUM='"+"-9"+"'";

                DatabaseConnection db_hasta_ara = new DatabaseConnection();

                ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

                try
                {
                    cmb_saat.removeAllItems();
                    cmb_saat.addItem(new ComboDoldur("Seçiniz", "-1"));
                    String gorunen_saat = "";
                    String db_saat = "";
                    String db_dakika = "";
                    String secilen_gun = deger.substring(8,10);

                    while (gelen_veriler.next())
                    {
                        gorunen_saat = gelen_veriler.getString("HRS_RANDEVU_SAAT").substring(0,5);
                        db_saat = gorunen_saat.substring(0,2);
                        db_dakika = gorunen_saat.substring(3,5);
                        if (c_gun==Integer.parseInt(secilen_gun))
                        {
                            if (Integer.parseInt(db_saat) < c_saat)
                            {
                                continue;
                            }
                            if (c_saat == Integer.parseInt(db_saat) && Integer.parseInt(db_dakika) <= c_dakika)
                            {
                                continue;
                            }
                        }
                        cmb_saat.addItem(new ComboDoldur(gorunen_saat,gelen_veriler.getString("HRS_RANDEVU_SAAT")));
                    }
                }

                catch (Exception x)
                {
                    // TODO: handle exception
                    x.printStackTrace();
                }
            }
        });

        return cmb_trh;
    }

    public JComboBox saat_combo()
    {
        cmb_saat = new JComboBox();
        cmb_saat.addItem(new ComboDoldur("Seçiniz","-1"));
        return cmb_saat;
    }

    public JButton kaydet_buton()   //KAYDET BUTONU
    {
        if (btn_kaydet == null)
        {
            btn_kaydet = new JButton();
            btn_kaydet.setText("Kaydet");
            Color renk = new Color(137, 200, 116);
            btn_kaydet.setBackground(renk);

            btn_kaydet.addActionListener(new ActionListener()   //KAYDET BUTONUNA TIKLANIRSA
            {
                public void actionPerformed(ActionEvent e)
                {
                    if (textbox_tc.getText().trim().isEmpty() || textbox_ad.getText().trim().isEmpty() || textbox_soyad.getText().trim().isEmpty() || cmb_pol.getSelectedItem().equals("Seçiniz") || cmb_dr.getSelectedItem().equals("Seçiniz") || cmb_saat.getSelectedItem().equals("Seçiniz") || cmb_trh.getSelectedItem().equals("Seçiniz"))
                    {
                        JOptionPane.showMessageDialog(null, "Alanlar Boş Geçilemez!", "Randevu Oluştur", 0);
                    }

                    else //kutular boş değilse
                    {
                        item = cmb_saat.getSelectedItem();
                        deger = ((ComboDoldur)item).getValue();

                        String sql0 = "UPDATE HRS_DR_RANDEVU SET HRS_RANDEVU_DURUM=9 "
                                + "WHERE DR_TC_KIMLIK_NO='"+dto.getDr_tc()+"' AND HRS_RANDEVU_TRH='"+dto.getRandevu_trh()+"'AND HRS_RANDEVU_SAAT='"+deger+"'";

                        DatabaseConnection con_hst_ekle = new DatabaseConnection();

                        String hata_durum0  = con_hst_ekle.database_sql(sql0);
                        String sql = "INSERT INTO HRS_RANDEVU VALUES ('"+textbox_tc.getText()+"', '"+dto.getDr_tc()+"', '"+dto.getRandevu_trh()+"', '"+deger+"') ";

                        String hata_durum = con_hst_ekle.database_sql(sql);

                        JOptionPane.showMessageDialog(null, ""+hata_durum , "Randevu Oluştur", -1);

                        if (hata_durum.equals("İşlem Başarılı."))    //İŞLEM BAŞARILI OLDUYSA
                        {//FORMU TEMİZLEME İŞLEMLERİ
                            textbox_tc.setEditable(true);
                            textbox_tc.setText("");
                            textbox_ad.setText("");
                            textbox_soyad.setText("");
                            cmb_dr.setSelectedIndex(0);
                            cmb_saat.setSelectedIndex(0);
                            cmb_pol.setSelectedIndex(0);
                            cmb_trh.setSelectedIndex(0);
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

