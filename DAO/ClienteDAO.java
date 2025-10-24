package DAO;

import com.google.firebase.database.*;
import Model.Cuenta_cliente;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ClienteDAO {
    private DatabaseReference clientesRef;
    
    public ClienteFirebaseDAO() {
        this.clientesRef = FirebaseConfig.getDatabase().getReference("clientes");
    }
    
    // Registrar cliente (usando documento como key)
    public CompletableFuture<Void> registrarCliente(Cuenta_cliente cliente) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        clientesRef.child(cliente.getDocumento()).setValue(cliente, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
    
    // Obtener cliente por documento
    public CompletableFuture<Cuenta_cliente> obtenerCliente(String documento) {
        CompletableFuture<Cuenta_cliente> future = new CompletableFuture<>();
        
        clientesRef.child(documento).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Cuenta_cliente cliente = dataSnapshot.getValue(Cuenta_cliente.class);
                future.complete(cliente);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        
        return future;
    }
    
    // Listar todos los clientes
    public CompletableFuture<Map<String, Cuenta_cliente>> listarClientes() {
        CompletableFuture<Map<String, Cuenta_cliente>> future = new CompletableFuture<>();
        
        clientesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Cuenta_cliente> clientes = new HashMap<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Cuenta_cliente cliente = snapshot.getValue(Cuenta_cliente.class);
                    clientes.put(snapshot.getKey(), cliente);
                }
                future.complete(clientes);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        
        return future;
    }
    
    // Actualizar cliente
    public CompletableFuture<Void> actualizarCliente(String documento, Cuenta_cliente cliente) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        clientesRef.child(documento).setValue(cliente, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
    
    // Eliminar cliente
    public CompletableFuture<Void> eliminarCliente(String documento) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        clientesRef.child(documento).removeValue((error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
}