/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pelanggan;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import penjualanbarangdoni.Conn;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ModelPelanggan {

    private String kode, nama, jenisKelamin, alamat, telp;
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    

    public void simpan() {
        try {
            Conn conn = new penjualanbarangdoni.Conn();
            Connection connection = conn.openConnection();
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO pelanggan VALUES("
                    + "'" + getKode() + "'"
                    + ",'" + getNama() + "'"
                    + ",'" + getJenisKelamin() + "'"
                    + ",'" + getAlamat() + "',"
                    + "'" + getTelp() + "')";
            statement.execute(sql);
            setFlag(1);
            statement.close();
            connection.close();
            JOptionPane.showMessageDialog(
                    null,
                    "Data Berhasil Ditambkan",
                    "SIMPAN",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Data Gagal Ditambkan",
                    "GAGAL",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println(e.getMessage());
        }
    }

    public void ubah() {
        try {
            penjualanbarangdoni.Conn conn = new penjualanbarangdoni.Conn();
            Connection connection = conn.openConnection();
            Statement statement = connection.createStatement();
            String sql = "UPDATE pelanggan SET "
                    + "telp = '" + getTelp() + "', "
                    + "nama = '" + getNama() + "',"
                    + "jenis_kelamin = '" + getJenisKelamin() + "',"
                    + "alamat = '" + getAlamat() + "'"
                    + "WHERE kode_pelanggan = '" + getKode() + "'";
            statement.executeUpdate(sql);
            setFlag(3);
            statement.close();
            connection.close();
            JOptionPane.showMessageDialog(
                    null,
                    "Data Berhasil Di Ubah",
                    "HAPUS",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Data Gagal Di Ubah",
                    "GAGAL",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println(e.getMessage());
        }
    }

    public void autoCode() {
        try {
            int hit = 0;
            Conn conn = new Conn();
            Connection connection = conn.openConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT COUNT(kode_pelanggan) FROM pelanggan";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                if (Integer.parseInt(resultSet.getString(1)) == 0) {
                    setKode("P001");
                    statement.close();
                    resultSet.close();
                } else {
                    sql = "SELECT MAX(mid(kode_pelanggan,2,4)) FROM pelanggan";
                    resultSet = statement.executeQuery(sql);
                    resultSet.next();
                    hit = (Integer.parseInt(resultSet.getString(1))) + 1;
                    if (hit < 10) {
                        setKode("P00" + hit);
                    } else if (hit < 100) {
                        setKode("P0" + hit);
                    } else if (hit < 1000) {
                        setKode("P" + hit);
                    }
                    statement.close();
                    resultSet.close();
                }
            }
            System.out.println("kode" + hit);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void tampil() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.openConnection();
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM pelanggan WHERE kode_pelanggan = '" + getKode() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                setFlag(4);
                setKode(resultSet.getString("kode_pelanggan"));
                setNama(resultSet.getString("nama"));
                setAlamat(resultSet.getString("jenis_kelamin"));
                setJenisKelamin(resultSet.getString("harga_brg"));
                setTelp(resultSet.getString("stok"));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public void hapus() {
        try {
            Conn conn = new Conn();
            Connection connection = conn.openConnection();
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM pelanggan WHERE kode_pelanggan = '" + getKode() + "'";
            statement.executeUpdate(sql);
            setFlag(3);
            statement.close();
            connection.close();
            JOptionPane.showMessageDialog(
                    null,
                    "Data Berhasil Di Hapus",
                    "HAPUS",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Data Gagal Di Hapus",
                    "GAGAL",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

}
