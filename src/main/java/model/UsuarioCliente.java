package model;

public class UsuarioCliente {
	private Usuario usuario;
	private Cliente cliente;
	
	 public UsuarioCliente(Usuario usuario, Cliente cliente) {
	        this.usuario = usuario;
	        this.cliente = cliente;
	    }

	    public Usuario getUsuario() {
	        return usuario;
	    }

	    public void setUsuario(Usuario usuario) {
	        this.usuario = usuario;
	    }

	    public Cliente getCliente() {
	        return cliente;
	    }

	    public void setCliente(Cliente cliente) {
	        this.cliente = cliente;
	    }
	}

