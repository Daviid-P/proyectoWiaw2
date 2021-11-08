package org.escoladeltreball.proyectowiaw2.repositories;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.escoladeltreball.proyectowiaw2.entities.PasswordResetToken;
import org.escoladeltreball.proyectowiaw2.entities.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
@Transactional
public class PasswordResetTokenDAO {

	@PersistenceContext
	protected EntityManager manager;
	
	// Busca token a partir de token string
	private PasswordResetToken findByToken(String token) {
		
		Query query = manager.createNamedQuery("findTokenByToken").setParameter("token", token);
		
		PasswordResetToken passwordResetToken = (PasswordResetToken)query.getSingleResult();
		
		return passwordResetToken;
	}

	public List<PasswordResetToken> findAll() {
		
		Query query = manager.createNamedQuery("findAllTokens");
		
		List<PasswordResetToken> allTokens = query.getResultList();
		
		return allTokens;
	}
	
	public void save(PasswordResetToken passwordResetToken) {
		
		manager.merge(passwordResetToken);
		manager.flush();
	}
	
	public boolean validate(String token, String dni) {
		boolean result = false;
		try {
			PasswordResetToken passwordResetToken = findByToken(token);
			if (passwordResetToken != null) {
				System.out.println("Validate Token: " + passwordResetToken.toString());
				if (isExpired(passwordResetToken)) {
					delete(passwordResetToken);
				} else {
					result = passwordResetToken.getUser().getDni().equals(dni);
					result = passwordResetToken.getToken().equals(token);
				}
			}
			
			if (result) {
				// Si se ha autorizado ya puede cambair la contraseña, borramos el token
				// Si algo le falla que pida otro
				System.out.println("Removing after auth");
				//delete(passwordResetToken);
				manager.remove(passwordResetToken);
				manager.flush();
			}
		} catch (NoResultException e) {
			
			return false;
			
		}
		
		
		
		return result;
	}
	
	public Usuario getUsuario(String token) {
		System.out.println("getUsuario");
		PasswordResetToken passwordResetToken = findByToken(token);
		System.out.println("Token: "+passwordResetToken.toString());
		Usuario usuario = passwordResetToken.getUser();
		return usuario;
	}
	
	public void delete(PasswordResetToken passwordResetToken) {
		manager.remove(passwordResetToken);
		manager.flush();
	}
	
	private boolean isExpired(PasswordResetToken passwordResetToken) {
		return passwordResetToken.getExpiryDate().before(new Date());
	}
	
	private void deleteExpiredTokens() {
		Query query = manager.createNamedQuery("findAllTokens");
		
		List<PasswordResetToken> passwordResetTokenList = query.getResultList();
		
		for (PasswordResetToken passwordResetToken : passwordResetTokenList) {
			if (isExpired(passwordResetToken)){
				delete(passwordResetToken);
			}
		}
	}
}
