package DAO;

import com.google.firebase.database.*;
import Model.Cuenta_anfitrion;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AnfitrionDAO {
    private DatabaseReference anfitrionesRef;
    
    public AnfitrionFirebaseDAO() {
        this.anfitrionesRef = FirebaseConfig.getDatabase().getReference("anfitriones");
    }
    
    // Registrar anfitrión
    public CompletableFuture<Void> registrarAnfitrion(Cuenta_anfitrion anfitrion) {
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
    public CompletableFuture<Cuenta_anfitrion> obtenerAnfitrion(String documento) {
        CompletableFuture<Cuenta_anfitrion> future = new CompletableFuture<>();
        
        anfitrionesRef.child(documento).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Cuenta_anfitrion anfitrion = dataSnapshot.getValue(Cuenta_anfitrion.class);
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
    public CompletableFuture<Map<String, Cuenta_anfitrion>> listarAnfitriones() {
        CompletableFuture<Map<String, Cuenta_anfitrion>> future = new CompletableFuture<>();
        
        anfitrionesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Cuenta_anfitrion> anfitriones = new HashMap<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Cuenta_anfitrion anfitrion = snapshot.getValue(Cuenta_anfitrion.class);
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
    public CompletableFuture<Void> actualizarAnfitrion(String documento, Cuenta_anfitrion anfitrion) {
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
