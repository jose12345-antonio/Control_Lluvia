package com.example.control_lluvia;

import android.os.Parcel;
import android.os.Parcelable;

public class Logear_usuario {

    private int id;
    private String nombres;
    private String apellidos;
    private String nombre_usuario;
    private String correo;
    private String password;
    private int rol;
    private String nombre_rol;

    public Logear_usuario() {

    }

    public Logear_usuario(int id, String nombres, String apellidos, String nombre_usuario, String correo, String password, int rol, String nombre_rol) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombre_usuario = nombre_usuario;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
        this.nombre_rol = nombre_rol;
    }
//Getter
    public int getId() {
        return id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }

    public int getRol() {
        return rol;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    //SETTER

    public void setId(int id) {
        this.id = id;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }
    @Override
    public String toString(){
        return ""+id+"   "+nombre_usuario;
    }


    public int describeContent(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(this.id);
        dest.writeString(this.nombres);
        dest.writeString(this.apellidos);
        dest.writeString(this.nombre_usuario);
        dest.writeString(this.correo);
        dest.writeString(this.password);
        dest.writeInt(this.rol);
        dest.writeString(this.nombre_rol);
    }

    protected Logear_usuario(Parcel in){
        this.id=in.readInt();
        this.nombres=in.readString();
        this.apellidos=in.readString();
        this.nombre_usuario=in.readString();
        this.correo=in.readString();
        this.password=in.readString();
        this.rol=in.readInt();
        this.nombre_rol=in.readString();
    }

    public static final Parcelable.Creator<Logear_usuario> CREATOR = new Parcelable.ClassLoaderCreator<Logear_usuario>() {

        @Override
        public Logear_usuario createFromParcel(Parcel source, ClassLoader loader) {
            return null;
        }

        @Override
        public Logear_usuario createFromParcel(Parcel source) {
            return new Logear_usuario(source);
        }

        @Override
        public Logear_usuario[] newArray(int size) {
            return new Logear_usuario[size];
        }
    };

    public void setNombre(String nombreusuario) {
    }

    public void setContraseña(String password) {
    }
}
