package cl.inacap.kevinguerrero.afinal.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class BD extends SQLiteOpenHelper {

    public BD(Context ctx){

        super(ctx, "bd", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        String  query = "CREATE TABLE usuarios (" +
                _ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombres TEXT,"+
                "email TEXT,"+
                "password TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int version_ant, int version_nue){

        db.execSQL("DROP TABLE EXISTS usuarios");
        onCreate(db);

    }

    public void eliminar_tabla(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete("usuarios", null, null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME ='usuarios'" );
    }


    public void insertarReg(String nom, String ema, String pass){
        ContentValues valores  = new ContentValues();
        valores.put("nombres", nom);
        valores.put("email", ema);
        valores.put("password", pass);
        this.getWritableDatabase().insert("usuarios", null, valores);
    }

    public String eliminar(String email){
        String[] args = new String[] {email};
        this.getReadableDatabase().execSQL("DELETE FROM usuarios WHERE email=?", args);
        return "Registro Eliminado";
    }

    public String buscar(String email){
        String resultado="";
        String[] campos = new String[] {_ID, "nombres", "email","password"};
        String[] args = new String[] {email};

        Cursor c = this.getReadableDatabase().query("usuarios", campos, "email=?", args, null, null, null);
        int id, nom,  ema, pass;

        id= c.getColumnIndex(_ID);
        nom= c.getColumnIndex("nombres");
        ema= c.getColumnIndex("email");
        pass= c.getColumnIndex("password");

        while(c.moveToNext())
        {
            resultado += c.getString(id)+" " + c.getString(nom) +"  "+
                    c.getString(ema)+"    "+c.getString(pass)+"\n";
        }
        return resultado;
    }

    public String verificarusuario(String email,String password){
        String resultado="";
        String[] campos = new String[] {_ID, "nombres", "email","password"};
        String[] args = new String[] {email,password};

        Cursor c = this.getReadableDatabase().query("usuarios", campos, "email=? and password=?", args, null, null, null);
        int nom;

        nom= c.getColumnIndex("nombres");

        while(c.moveToNext())
        {
            resultado += c.getString(nom);
        }
        return resultado;
    }
    public String leer(){

        String resultado="";

        String filas[] = {_ID, "nombres","email","password"};

        Cursor c = this.getReadableDatabase().query("usuarios", filas,null, null, null, null, null);
        int id, nom, ema,  pass;

        id= c.getColumnIndex(_ID);
        nom= c.getColumnIndex("nombres");
        ema= c.getColumnIndex("email");
        pass= c.getColumnIndex("password");

        while(c.moveToNext())
        {
            resultado += c.getString(id)+" " + c.getString(nom) +"  "+
                    c.getString(ema)+"  "+c.getString(pass)+"\n";
        }

        return resultado;
    }


    public void abrir(){
        this.getWritableDatabase();
    }


    public void cerrar(){
        this.close();
    }
}
