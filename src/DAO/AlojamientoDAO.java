package DAO;

import com.google.firebase.database.*;
import Model.Alojamiento;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class AlojamientoDAO {
    private DatabaseReference alojamientosRef;
    
    public AlojamientoFirebaseDAO() {
        this.alojamientosRef = FirebaseConfig.getDatabase().getReference("alojamientos");
    }
    
    // Registrar alojamiento (genera key automática)
    public CompletableFuture<String> registrarAlojamiento(Alojamiento alojamiento) {
        CompletableFuture<String> future = new CompletableFuture<>();
        
        DatabaseReference nuevoRef = alojamientosRef.push();
        String key = nuevoRef.getKey();
        
        nuevoRef.setValue(alojamiento, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(key);
            }
        });
        
        return future;
    }
    
    // Registrar alojamiento con key específica
    public CompletableFuture<Void> registrarAlojamientoConKey(String key, Alojamiento alojamiento) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        alojamientosRef.child(key).setValue(alojamiento, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
    
    // Obtener alojamiento por key
    public CompletableFuture<Alojamiento> obtenerAlojamiento(String key) {
        CompletableFuture<Alojamiento> future = new CompletableFuture<>();
        
        alojamientosRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Alojamiento alojamiento = dataSnapshot.getValue(Alojamiento.class);
                future.complete(alojamiento);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        
        return future;
    }
    
    // Listar todos los alojamientos
    public CompletableFuture<Map<String, Alojamiento>> listarAlojamientos() {
        CompletableFuture<Map<String, Alojamiento>> future = new CompletableFuture<>();
        
        alojamientosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Alojamiento> alojamientos = new HashMap<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Alojamiento alojamiento = snapshot.getValue(Alojamiento.class);
                    alojamientos.put(snapshot.getKey(), alojamiento);
                }
                future.complete(alojamientos);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        
        return future;
    }
    
    // Actualizar alojamiento por key
    public CompletableFuture<Void> actualizarAlojamiento(String key, Alojamiento alojamiento) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        Map<String, Object> updates = new HashMap<>();
        updates.put(key, alojamiento);
        
        alojamientosRef.updateChildren(updates, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
    
    // Eliminar alojamiento por key
    public CompletableFuture<Void> eliminarAlojamiento(String key) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        alojamientosRef.child(key).removeValue((error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
}
