package pt.isec.gestaodepinformatica.Modelo;

import java.util.Date;

public class FuncionarioModelo {

    Integer ID;
    String  username, password, nomeCompleto, genero, contrato, servico, morada, email, telefone;
    Date dataNascimento;
    Double salario;
    Boolean isAdmin;


    public FuncionarioModelo(Integer ID, String username, String password, String nomeCompleto, String genero, String contrato, String servico, String morada, String email, String telefone, Date dataNascimento, Double salario, Boolean isAdmin) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.nomeCompleto = nomeCompleto;
        this.genero = genero;
        this.contrato = contrato;
        this.servico = servico;
        this.morada = morada;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.salario = salario;
        this.isAdmin = isAdmin;
    }



    public FuncionarioModelo(String username,String queryNomeCompleto, String queryservico, String queryContrato, String queryTelefone, Date queryDataNascimento, String queryMorada, String queryEMail, String queryGenero, Double querySalario) {
        this.username = username;
        this.nomeCompleto = queryNomeCompleto;
        this.genero = queryGenero;
        this.contrato = queryContrato;
        this.servico = queryservico;
        this.morada = queryMorada;
        this.email = queryEMail;
        this.telefone = queryTelefone;
        this.dataNascimento = queryDataNascimento;
        this.salario = querySalario;
    }

    public Integer getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getGenero() {
        return genero;
    }

    public String getContrato() {
        return contrato;
    }

    public String getServico() {
        return servico;
    }

    public String getMorada() {
        return morada;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public Double getSalario() {
        return salario;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }
}
