package DAO;

import com.google.firebase.database.*;
import Model.Cuenta_Anfitrion;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AnfitrionDAO {
    private DatabaseReference anfitrionesRef;
    
    public AnfitrionFirebaseDAO() {
        this.anfitrionesRef = FirebaseConfig.getDatabase().getReference("anfitriones");
    }
    
    // Registrar anfitrión
    public CompletableFuture<Void> registrarAnfitrion(Cuenta_Anfitrion anfitrion) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        anfitrionesRef.child(anfitrion.getDocumento()).setValue(anfitrion, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
    
    // Obtener anfitrión por documento
    public CompletableFuture<Cuenta_Anfitrion> obtenerAnfitrion(String documento) {
        CompletableFuture<Cuenta_Anfitrion> future = new CompletableFuture<>();
        
        anfitrionesRef.child(documento).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Cuenta_Anfitrion anfitrion = dataSnapshot.getValue(Cuenta_Anfitrion.class);
                future.complete(anfitrion);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        
        return future;
    }
    
    // Listar anfitriones
    public CompletableFuture<Map<String, Cuenta_Anfitrion>> listarAnfitriones() {
        CompletableFuture<Map<String, Cuenta_Anfitrion>> future = new CompletableFuture<>();
        
        anfitrionesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Cuenta_Anfitrion> anfitriones = new HashMap<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Cuenta_Anfitrion anfitrion = snapshot.getValue(Cuenta_Anfitrion.class);
                    anfitriones.put(snapshot.getKey(), anfitrion);
                }
                future.complete(anfitriones);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        
        return future;
    }
    
    // Actualizar anfitrión
    public CompletableFuture<Void> actualizarAnfitrion(String documento, Cuenta_Anfitrion anfitrion) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        anfitrionesRef.child(documento).setValue(anfitrion, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
    
    // Eliminar anfitrión
    public CompletableFuture<Void> eliminarAnfitrion(String documento) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        anfitrionesRef.child(documento).removeValue((error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
}
