package com.manuel.rosaurazapatainv.Firebase;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.manuel.rosaurazapatainv.ActividadesPrincipales.MenuActivity;
import com.manuel.rosaurazapatainv.Interfaces.Almacenamiento;
import com.manuel.rosaurazapatainv.Interfaces.Estado;
import com.manuel.rosaurazapatainv.Modelos.ActivoFijo;
import com.manuel.rosaurazapatainv.Modelos.MaterialConsumo;
import com.manuel.rosaurazapatainv.Modelos.MaterialDidactico;
import com.manuel.rosaurazapatainv.Modelos.TecnicoPedagogico;
import com.manuel.rosaurazapatainv.Modelos.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirebaseHelper {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static Usuario usuario;

    public void iniciarSesion(Context context, String usu, String contra, ProgressDialog progressDialog) {
        DocumentReference docRef = db.collection("Usuarios").document(usu);
        docRef.get().addOnCompleteListener(task -> {
            boolean bandera = false;
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                assert document != null;
                if (document.exists()) {
                    if (String.valueOf(document.get("Usuario")).equals(usu) && String.valueOf(document.get("Contrasena")).equals(contra)) {
                        bandera = true;
                        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "Usuario");
                        sharedPreferencesHelper.write("Usuario", document.getId());
                        usuario = new Usuario(Objects.requireNonNull(document.get("Usuario")).toString().trim(), Objects.requireNonNull(document.get("Contrasena")).toString().trim());
                        Intent intent = new Intent(context, MenuActivity.class);
                        context.startActivity(intent);
                        Toast.makeText(context, "Ha iniciado sesión como: " + usu, Toast.LENGTH_LONG).show();
                    }
                    if (!bandera) {
                        Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Usuario inexistente", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Fallo la conexión a Firebase", Toast.LENGTH_SHORT).show();
            }
            progressDialog.dismiss();
        });
    }

    public void add(Estado estado, String collection, String[] keys, String[] datas, final Context context) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "Usuario");
        final String usuario = sharedPreferencesHelper.read("Usuario");
        Map<String, Object> data = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            data.put(keys[i], datas[i]);
        }
        db.collection(collection + "/Usuarios/" + usuario).add(data).addOnSuccessListener(documentReference -> estado.estado("Registro agregado")).addOnFailureListener(e -> estado.estado("Error al agregar el registro"));
    }

    public void editar(Estado estado, String collection, String id, String[] keys, String[] datas, final Context context) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "Usuario");
        final String usuario = sharedPreferencesHelper.read("Usuario");
        Map<String, Object> data = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            data.put(keys[i], datas[i]);
        }
        db.collection(collection + "/Usuarios/" + usuario).document(id).set(data).addOnCompleteListener(task -> estado.estado("Registro editado")).addOnFailureListener(e -> estado.estado("Error al editar el registro"));
    }

    public void eliminar(Estado estado, String collection, String id, final Context context) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "Usuario");
        final String usuario = sharedPreferencesHelper.read("Usuario");
        db.collection(collection + "/Usuarios/" + usuario).document(id).delete().addOnSuccessListener(aVoid -> estado.estado("Registro eliminado")).addOnFailureListener(e -> estado.estado("Error al eliminar el registro"));
    }

    public void leerTecnicoPedagogico(Almacenamiento data, String collection, final Context context) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "Usuario");
        String usuario = sharedPreferencesHelper.read("Usuario");
        db.collection(collection + "/Usuarios/" + usuario).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Object> datos = new ArrayList<>();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    TecnicoPedagogico pedagogico = new TecnicoPedagogico(document.getId(), Objects.requireNonNull(document.getData().get("Numero")).toString(), Objects.requireNonNull(document.getData().get("Descripcion")).toString(), Objects.requireNonNull(document.getData().get("Cantidad")).toString(), Objects.requireNonNull(document.getData().get("Estado")).toString());
                    datos.add(pedagogico);
                }
                data.arrayList(datos);
            }
        });
    }

    public void leerMaterialDidactico(Almacenamiento data, String collection, final Context context) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "Usuario");
        String usuario = sharedPreferencesHelper.read("Usuario");
        db.collection(collection + "/Usuarios/" + usuario).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Object> datos = new ArrayList<>();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    MaterialDidactico materialDidactico = new MaterialDidactico(document.getId(), Objects.requireNonNull(document.getData().get("Numero")).toString(), Objects.requireNonNull(document.getData().get("Descripcion")).toString(), Objects.requireNonNull(document.getData().get("Cantidad")).toString(), Objects.requireNonNull(document.getData().get("Estado")).toString());
                    datos.add(materialDidactico);
                }
                data.arrayList(datos);
            }
        });
    }

    public void leerMaterialConsumo(Almacenamiento data, String collection, final Context context) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "Usuario");
        String usuario = sharedPreferencesHelper.read("Usuario");
        db.collection(collection + "/Usuarios/" + usuario).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Object> datos = new ArrayList<>();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    MaterialConsumo materialConsumo = new MaterialConsumo(document.getId(), Objects.requireNonNull(document.getData().get("Numero")).toString(), Objects.requireNonNull(document.getData().get("Descripcion")).toString(), Objects.requireNonNull(document.getData().get("Cantidad")).toString());
                    datos.add(materialConsumo);
                }
                data.arrayList(datos);
            }
        });
    }

    public void leerActivoFijo(Almacenamiento data, String collection, final Context context) {
        SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(context, "Usuario");
        String usuario = sharedPreferencesHelper.read("Usuario");
        db.collection(collection + "/Usuarios/" + usuario).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Object> datos = new ArrayList<>();
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    ActivoFijo activoFijo = new ActivoFijo(document.getId(), Objects.requireNonNull(document.getData().get("Numero")).toString(), Objects.requireNonNull(document.getData().get("Clave")).toString(), Objects.requireNonNull(document.getData().get("Descripcion")).toString(), Objects.requireNonNull(document.getData().get("Cantidad")).toString(), Objects.requireNonNull(document.getData().get("Precio")).toString(), Objects.requireNonNull(document.getData().get("Total")).toString(), Objects.requireNonNull(document.getData().get("Estado")).toString());
                    datos.add(activoFijo);
                }
                data.arrayList(datos);
            }
        });
    }
}