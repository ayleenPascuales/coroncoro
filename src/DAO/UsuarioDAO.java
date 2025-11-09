package DAO;

import com.google.firebase.database.*;
import Model.Usuario;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class UsuarioDAO {
    private DatabaseReference usuariosRef;
    
    public UsuarioFirebaseDAO() {
        this.usuariosRef = FirebaseConfig.getDatabase().getReference("usuarios");
    }
    
    // Registrar usuario (usando documento como key)
    public CompletableFuture<Void> registrarUsuario(Usuario usuario) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        usuariosRef.child(usuario.getDocumento()).setValue(usuario, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
    
    // Obtener usuario por documento (key)
    public CompletableFuture<Usuario> obtenerUsuario(String documento) {
        CompletableFuture<Usuario> future = new CompletableFuture<>();
        
        usuariosRef.child(documento).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                future.complete(usuario);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        
        return future;
    }
    
    // Login de usuario
    public CompletableFuture<Usuario> login(String usuario, String contraseña) {
        CompletableFuture<Usuario> future = new CompletableFuture<>();
        
        usuariosRef.orderByChild("usuario").equalTo(usuario)
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Usuario user = snapshot.getValue(Usuario.class);
                        if (user.getContraseña().equals(contraseña)) {
                            future.complete(user);
                            return;
                        }
                    }
                    future.complete(null);
                }
                
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    future.completeExceptionally(databaseError.toException());
                }
            });
        
        return future;
    }
    
    // Listar todos los usuarios
    public CompletableFuture<Map<String, Usuario>> listarUsuarios() {
        CompletableFuture<Map<String, Usuario>> future = new CompletableFuture<>();
        
        usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Usuario> usuarios = new HashMap<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    usuarios.put(snapshot.getKey(), usuario);
                }
                future.complete(usuarios);
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
                future.completeExceptionally(databaseError.toException());
            }
        });
        
        return future;
    }
    
    // Actualizar usuario
    public CompletableFuture<Void> actualizarUsuario(String documento, Usuario usuario) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        usuariosRef.child(documento).setValue(usuario, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
    
    // Eliminar usuario
    public CompletableFuture<Void> eliminarUsuario(String documento) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        
        usuariosRef.child(documento).removeValue((error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException());
            } else {
                future.complete(null);
            }
        });
        
        return future;
    }
}