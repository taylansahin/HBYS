package hbys.hrs.doktor;

import hbys.hrs.database.DatabaseConnection;
import hbys.hrs.dto.DTO;
import hbys.hrs.other.ComboDoldur;
import hbys.hrs.yonetici.YoneticiMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Locale;

public class DoktorEkle extends JFrame
{
    DTO dto = new DTO();

    private JTextField textbox_tc = new JTextField(11);
    private JTextField textbox_ad = new JTextField(30);
    private JTextField textbox_soyad = new JTextField(30);

    private JComboBox cmb_pol;
    private JComboBox cmb_unvan;

    private JButton btn_geri = null;
    private JButton btn_kaydet = null;

    private boolean hata_var_mi = false;

    public Object item = null;
    public String deger = "";

    public DoktorEkle()
    {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        setLayout(layout);

        setTitle("Doktor Ekle");
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

        JLabel unvan_yazi = new JLabel("Unvan   ");
        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(unvan_yazi,gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=4;//satır
        gc.gridwidth=3;//genişlik
        gc.gridheight=1;//yükseklik
        add(unvan_combo(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=0;//sütun
        gc.gridy=5;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(geri_buton(),gc);

        gc.fill=GridBagConstraints.HORIZONTAL;
        gc.gridx=2;//sütun
        gc.gridy=5;//satır
        gc.gridwidth=2;//genişlik
        gc.gridheight=1;//yükseklik
        add(kaydet_buton(),gc);
    }

    public JComboBox pol_combo()
    {
        cmb_pol = new JComboBox();
        String sql = "SELECT HRS_POL_ID, HRS_POL_ADI FROM HRS_POLIKLINIK";

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
        }

        catch (Exception x)
        {
            // TODO: handle exception
            x.printStackTrace();
        }
        return cmb_pol;
    }

    public JComboBox unvan_combo()
    {
        cmb_unvan = new JComboBox();
        String sql = "SELECT HRS_UNV_ID, HRS_UNV_AD FROM HRS_UNVANLAR";

        DatabaseConnection db_hasta_ara = new DatabaseConnection();

        ResultSet gelen_veriler = db_hasta_ara.db_hasta_ara(sql);

        try
        {
            String temp_unv_id;
            String temp_unv_adi;
            cmb_unvan.addItem(new ComboDoldur("Seçiniz","-1"));

            while(gelen_veriler.next())
            {
                temp_unv_id = gelen_veriler.getString("HRS_UNV_ID");
                temp_unv_adi = gelen_veriler.getString("HRS_UNV_AD");

                cmb_unvan.addItem(new ComboDoldur(temp_unv_adi,temp_unv_id));
            }
        }

        catch (Exception x)
        {
            // TODO: handle exception
            x.printStackTrace();
        }
        return cmb_unvan;
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
                    if (cmb_pol.getSelectedIndex()==0 || cmb_unvan.getSelectedIndex()==0 || textbox_ad.getText().trim().isEmpty() || textbox_tc.getText().trim().isEmpty() || textbox_soyad.getText().trim().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "Alanlar Boş Geçilemez!", "Doktor Ekle", 0);
                        hata_var_mi = true;
                    }

                    else //kutular boş değilse
                    {
                        hata_var_mi = false;

                        dto.setDr_tc(textbox_tc.getText());

                        dto.setDr_ad(textbox_ad.getText().toUpperCase(Locale.forLanguageTag("Tr")));

                        dto.setDr_soyad(textbox_soyad.getText().toUpperCase(Locale.forLanguageTag("TR")));

                        if (Long.parseLong(dto.getDr_tc()) < 10000000000L || Long.parseLong(dto.getDr_tc()) > 99999999999L)
                        {
                            JOptionPane.showMessageDialog(null, "Lütfen Geçerli TC Kimlik Numarası Giriniz!" , "Doktor Ekle", 0);
                            hata_var_mi = true;
                        }
                        item = cmb_pol.getSelectedItem();
                        deger = ((ComboDoldur)item).getValue();
                        String pol_id = deger;

                        item = cmb_unvan.getSelectedItem();
                        deger = ((ComboDoldur)item).getValue();
                        String unv_id = deger;

                        if (!hata_var_mi)
                        {
                            String sql = "INSERT INTO HRS_DR_KIMLIK_BILGI "
                                    + "VALUES ('"+dto.getDr_tc()+"', '"+dto.getDr_ad()+"', '"+dto.getDr_soyad()+"', '"+pol_id+"', '"+unv_id+"') ";

                            DatabaseConnection con_hst_ekle = new DatabaseConnection();

                            String hata_durum = con_hst_ekle.database_sql(sql);

                            JOptionPane.showMessageDialog(null, ""+hata_durum , "Doktor Ekle", -1);

                            if (hata_durum.equals("İşlem Başarılı."))
                            {
                                //İŞLEM BAŞARILI OLDUYSA{//FORMU TEMİZLEME İŞLEMLERİ
                                textbox_tc.setText("");
                                textbox_ad.setText("");
                                textbox_soyad.setText("");
                                cmb_unvan.setSelectedIndex(0);
                                cmb_pol.setSelectedIndex(0);
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

