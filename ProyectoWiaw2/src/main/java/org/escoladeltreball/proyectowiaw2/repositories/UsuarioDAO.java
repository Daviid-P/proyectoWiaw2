package org.escoladeltreball.proyectowiaw2.repositories;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.escoladeltreball.proyectowiaw2.entities.Autoridad;
import org.escoladeltreball.proyectowiaw2.entities.PasswordResetToken;
import org.escoladeltreball.proyectowiaw2.entities.TipoPrueba;
import org.escoladeltreball.proyectowiaw2.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class UsuarioDAO {
	
	@Autowired
	PasswordResetTokenDAO passwordResetTokenDao;
	
	@PersistenceContext
	protected EntityManager manager;
	
	public Autoridad roleDash(String dni){
		Query queryUsuario = manager.createNamedQuery("findUserByDni").setParameter("dni", dni);
		
		Usuario usuario = (Usuario)queryUsuario.getSingleResult();
		
		Query queryAutoridad = manager.createNamedQuery("findAutoridad").setParameter("usuario", usuario);
		
		Autoridad autoridad = (Autoridad)queryAutoridad.getSingleResult();
		
		return autoridad;
	}
	
	public Usuario usuario(String dni){
		Query queryUsuario = manager.createNamedQuery("findUserByDni").setParameter("dni", dni);
		
		Usuario usuario = (Usuario)queryUsuario.getSingleResult();
		
		return usuario;
	}
	
	//Actualiza/Crea el perfil d'un usuari
	public void save(Usuario usuario){
		
		manager.merge(usuario);
		manager.flush();
	}
	
	//Devuelve una lista con todos los tipos de pruebas que se realizan en la clínica
	public List<TipoPrueba> listAllTipoPruebas() {
		Query query = manager.createNamedQuery("findAllTipoPruebas");
	
		List<TipoPrueba> tiposPruebas = query.getResultList();
	
		return tiposPruebas;
	}
	
	//Devuelve un tipo de prueba concreto a partir de un id
	public TipoPrueba listOneTipoPruebas(long id) {
		Query query = manager.createNamedQuery("findTipoPruebaById").setParameter("id", id);
	
		TipoPrueba tipoPrueba = (TipoPrueba)query.getSingleResult();
	
		return tipoPrueba;
	}
	
	public void saveResetToken(String token, Usuario usuario) {
		
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, usuario);
		
		List<PasswordResetToken> allTokens = passwordResetTokenDao.findAll();
		for (PasswordResetToken prt : allTokens) {
			if (prt.getUser().equals(usuario)) {
				// AutoUpdates expiration date
				prt.updateToken(token);
				passwordResetToken = new PasswordResetToken(prt);
				break;
			}
			
		}

        passwordResetTokenDao.save(passwordResetToken);
		
	}
	
}
