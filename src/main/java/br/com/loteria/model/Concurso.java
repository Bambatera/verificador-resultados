package br.com.loteria.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author leand
 */
public class Concurso {
    
    private Integer numero;
    private Date dataSorteio;
    private Set<Byte> dezenas;

    public Concurso() {
        this.dezenas = new HashSet<>();
    }

    public Concurso(Integer numero, Date dataSorteio) {
        this.dezenas = new HashSet<>();
        this.numero = numero;
        this.dataSorteio = dataSorteio;
    }

    public Concurso(Integer numero, Date dataSorteio, Set<Byte> dezenas) {
        this.numero = numero;
        this.dataSorteio = dataSorteio;
        this.dezenas = dezenas;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getDataSorteio() {
        return dataSorteio;
    }

    public void setDataSorteio(Date dataSorteio) {
        this.dataSorteio = dataSorteio;
    }

    public Set<Byte> getDezenas() {
        if (dezenas == null) {
            this.dezenas = new HashSet<>();
        }
        return dezenas;
    }

    public void setDezenas(Set<Byte> dezenas) {
        this.dezenas = dezenas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.dezenas);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Concurso other = (Concurso) obj;
        return Objects.equals(this.dezenas, other.dezenas);
    }

    @Override
    public String toString() {
        return "{" + dezenas + '}';
    }
    
}
