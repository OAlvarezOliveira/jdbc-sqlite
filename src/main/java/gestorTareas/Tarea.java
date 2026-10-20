package gestorTareas;

public class Tarea {

    private int id;
    private String titulo;
    private boolean hecha = false;
    
    
    public Tarea(int id,String titulo, boolean hecha) {
    	this.id = id;
        this.titulo = titulo;
        this.hecha = hecha;
    }


    public Tarea(String titulo, boolean hecha) {
        this.titulo = titulo;
        this.hecha = hecha;
    }


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public String getTitulo() {
	return titulo;
}


public void setTitulo(String titulo) {
	this.titulo = titulo;
}


public boolean isHecha() {
	return hecha;
}


public void setHecha(boolean hecha) {
	this.hecha = hecha;
}


@Override
public String toString() {
	return String.format("Tarea [id=%d, titulo=%s, hecha=%b]", id, titulo, hecha);
}



	
}
