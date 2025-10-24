package dao;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Clase encargada de establecer la conexión con Firebase Firestore.
 * 
 * 🔹 Solo se inicializa una vez (patrón Singleton).
 * 🔹 Permite conexión segura usando un archivo de credenciales JSON.
 * 🔹 Compatible con proyectos en Maven o NetBeans.
 * 
 * 📁 Asegúrate de colocar el archivo de credenciales en:
 *     src/main/resources/serviceAccountKey.json
 */
public class FirebaseConfig {

    private static boolean initialized = false;

    /**
     * Obtiene la conexión con la base de datos Firestore.
     * 
     * @return Objeto Firestore conectado a Firebase.
     */
    public static Firestore getConnection() {
        if (!initialized) {
            try {
                // Ruta al archivo de credenciales de Firebase
                String path = "src/main/resources/serviceAccountKey.json";

                FileInputStream serviceAccount = new FileInputStream(path);

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        // Cambia esta URL por la de tu proyecto Firebase
                        .setDatabaseUrl("https://TU_PROYECTO.firebaseio.com")
                        .build();

                FirebaseApp.initializeApp(options);
                initialized = true;

                System.out.println("✅ Conexión establecida con Firebase Firestore correctamente.");
            } catch (IOException e) {
                System.err.println("❌ Error al conectar con Firebase:");
                e.printStackTrace();
            }
        }
        return FirestoreClient.getFirestore();
    }
}

