package disciplinasUFCG.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;


public class Filtro extends GenericFilterBean {

	public final static int TOKEN_INDEX = 7;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		String header = req.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new ServletException("Token inexistente ou mal formatado!");
		}

		// Extraindo apenas o token do cabecalho.
		String token = header.substring(TOKEN_INDEX);

		try {
			Jwts.parser().setSigningKey("login do batman").parseClaimsJws(token).getBody();
		} catch(Exception e) {

           		((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            		
			return;
        }

		chain.doFilter(request, response);
	}

}
